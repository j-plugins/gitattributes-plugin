package com.github.xepozz.gitattributes.ide.reference

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.roots.ProjectFileIndex
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPolyVariantReferenceBase
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.PsiReferenceRegistrar
import com.intellij.psi.ResolveResult
import com.intellij.util.ProcessingContext

class AttributesReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(AttributesPattern::class.java),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<out PsiReference> {
                    return arrayOf(AttributesPatternReference(element as AttributesPattern))
                }
            }
        )
    }
}

private class AttributesPatternReference(
    element: AttributesPattern,
) : PsiPolyVariantReferenceBase<AttributesPattern>(element, TextRange(0, element.textLength), true) {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val baseDir = element.containingFile.virtualFile?.parent ?: return emptyArray()
        val pattern = GitAttributesPattern.parse(element.text) ?: return emptyArray()
        val psiManager = PsiManager.getInstance(element.project)

        return collectMatchingFiles(baseDir, pattern)
            .mapNotNull { it.toPsiFileSystemItem(psiManager) }
            .map { PsiElementResolveResult(it) }
            .toTypedArray()
    }

    override fun resolve(): PsiElement? = multiResolve(false).firstOrNull()?.element

    override fun getVariants(): Array<Any> {
        val baseDir = element.containingFile.virtualFile?.parent ?: return emptyArray()

        return baseDir.children
            .filter { it.name != ".git" }
            .map {
                LookupElementBuilder.create(if (it.isDirectory) "${it.name}/" else it.name)
                    .withIcon(it.toPsiFileSystemItem(PsiManager.getInstance(element.project))?.getIcon(0))
            }
            .toTypedArray()
    }

    private fun collectMatchingFiles(baseDir: VirtualFile, pattern: GitAttributesPattern): List<VirtualFile> {
        val result = mutableListOf<VirtualFile>()
        val fileIndex = ProjectFileIndex.getInstance(element.project)

        fun visit(file: VirtualFile, relativePath: String) {
            if (file != baseDir && (file.name == ".git" || fileIndex.isExcluded(file))) {
                return
            }

            if (relativePath.isNotEmpty() && pattern.matches(relativePath, file.isDirectory)) {
                result += file
            }

            if (!file.isDirectory) return

            file.children.forEach { child ->
                val childPath = if (relativePath.isEmpty()) child.name else "$relativePath/${child.name}"
                visit(child, childPath)
            }
        }

        visit(baseDir, "")
        return result
    }
}

private class GitAttributesPattern(
    private val anchored: Boolean,
    private val hasSlash: Boolean,
    private val regex: Regex,
    private val directoryOnly: Boolean,
) {
    fun matches(relativePath: String, directory: Boolean): Boolean {
        if (directoryOnly && !directory) return false

        val normalizedPath = relativePath.trimStart('/')
        val subject = when {
            anchored || hasSlash -> normalizedPath
            else -> normalizedPath.substringAfterLast('/')
        }

        return regex.matches(subject)
    }

    companion object {
        fun parse(rawPattern: String): GitAttributesPattern? {
            val decoded = decodePattern(rawPattern)
            val anchored = decoded.startsWith("/")
            val unescaped = decoded.trimStart('/')
            if (unescaped.isEmpty()) return null

            val directoryOnly = unescaped.endsWith("/")
            val normalized = unescaped.trimEnd('/')
            if (normalized.isEmpty()) return null

            val hasSlash = normalized.contains("/")

            return GitAttributesPattern(
                anchored = anchored,
                hasSlash = hasSlash,
                regex = globToRegex(normalized),
                directoryOnly = directoryOnly,
            )
        }

        private fun decodePattern(rawPattern: String): String {
            if (rawPattern.length >= 2 && rawPattern.first() == '"' && rawPattern.last() == '"') {
                return decodeQuotedEscapes(rawPattern.substring(1, rawPattern.lastIndex))
            }

            return decodeUnquotedEscapes(rawPattern)
        }

        private fun decodeQuotedEscapes(value: String): String {
            val result = StringBuilder(value.length)
            var index = 0
            while (index < value.length) {
                val char = value[index]
                if (char == '\\' && index + 1 < value.length) {
                    result.append(
                        when (val next = value[index + 1]) {
                            'n' -> '\n'
                            'r' -> '\r'
                            't' -> '\t'
                            else -> next
                        }
                    )
                    index += 2
                } else {
                    result.append(char)
                    index++
                }
            }
            return result.toString()
        }

        private fun decodeUnquotedEscapes(value: String): String {
            val result = StringBuilder(value.length)
            var index = 0
            while (index < value.length) {
                val char = value[index]
                if (char == '\\' && index + 1 < value.length) {
                    result.append(value[index + 1])
                    index += 2
                } else {
                    result.append(char)
                    index++
                }
            }
            return result.toString()
        }

        private fun globToRegex(pattern: String): Regex {
            val result = StringBuilder("^")
            var index = 0
            while (index < pattern.length) {
                when (val char = pattern[index]) {
                    '*' -> {
                        if (index + 1 < pattern.length && pattern[index + 1] == '*') {
                            if (index + 2 < pattern.length && pattern[index + 2] == '/') {
                                result.append("(?:.*/)?")
                                index += 3
                            } else {
                                result.append(".*")
                                index += 2
                            }
                        } else {
                            result.append("[^/]*")
                            index++
                        }
                    }

                    '?' -> {
                        result.append("[^/]")
                        index++
                    }

                    '[' -> {
                        val endIndex = pattern.indexOf(']', startIndex = index + 1)
                        if (endIndex > index + 1) {
                            appendCharacterClass(result, pattern.substring(index + 1, endIndex))
                            index = endIndex + 1
                        } else {
                            result.append("\\[")
                            index++
                        }
                    }

                    else -> {
                        if (".()[]{}+^$|\\".contains(char)) {
                            result.append('\\')
                        }
                        result.append(char)
                        index++
                    }
                }
            }
            result.append('$')
            return Regex(result.toString())
        }

        private fun appendCharacterClass(result: StringBuilder, content: String) {
            result.append('[')
            var startIndex = 0
            if (content.firstOrNull() == '!') {
                result.append('^')
                startIndex = 1
            }

            for (index in startIndex until content.length) {
                val char = content[index]
                if (char == '\\' || char == '[' || char == ']') {
                    result.append('\\')
                }
                result.append(char)
            }
            result.append(']')
        }
    }
}

private fun VirtualFile.toPsiFileSystemItem(psiManager: PsiManager): PsiFileSystemItem? =
    if (isDirectory) psiManager.findDirectory(this) else psiManager.findFile(this)
