package com.github.xepozz.gitattributes.ide.documentation

import com.github.xepozz.gitattributes.language.AttributesFile
import com.github.xepozz.gitattributes.language.psi.AttributesDefinition
import com.intellij.extapi.psi.ASTDelegatePsiElement
import com.intellij.lang.ASTNode
import com.intellij.lang.documentation.DocumentationProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiDocCommentBase
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import java.util.function.Consumer

class AttributesDocumentationProvider : DocumentationProvider {
    override fun getUrlFor(element: PsiElement?, originalElement: PsiElement?): List<String>? {
//        if (element !is AttributesSchedule) return null
//        return listOf(AttributesGuruUtils.generateCrontabGuruUrl(element))
        return null
    }

    override fun getCustomDocumentationElement(
        editor: Editor,
        file: PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int
    ): PsiElement? {
        if (file !is AttributesFile) return null

        return PsiTreeUtil.findFirstParent(contextElement) { it is AttributesDefinition }
    }

    /**
     * Extracts the key, value, file and documentation comment of a Simple key/value entry and returns
     * a formatted representation of the information.
     */
    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? = null

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? = null

    override fun generateRenderedDoc(comment: PsiDocCommentBase) = markdownToHtml(comment.text, comment.project)

    fun markdownToHtml(string: String, project: Project): String {
        val markdownText = string
            .split("\n")
            .joinToString("\n") { it.replaceFirst(Regex("#+\\s+"), "") }

        return try {
            // Dynamically find the class
            val clazz = Class.forName("com.intellij.markdown.utils.doc.DocMarkdownToHtmlConverter")

            // Find the 'convert' method (assuming it's `static fun convert(project, text)`)
            val method = clazz.getMethod("convert", Project::class.java, String::class.java)

            // Invoke the static method
            method.invoke(null, project, markdownText) as String
        } catch (e: ClassNotFoundException) {
//            println("Markdown converter not available, returning plain text.")
            markdownText
        }
    }

    override fun findDocComment(file: PsiFile, range: TextRange): PsiDocCommentBase? {
        val element = file.findElementAt(range.startOffset) as? PsiComment ?: return null

        return object : PsiCommentDelegate(element), PsiDocCommentBase {
            override fun getOwner() = element
        }
    }

    override fun collectDocComments(file: PsiFile, sink: Consumer<in PsiDocCommentBase>) {
        buildList {
            addAll(PsiTreeUtil.findChildrenOfType(file, AttributesDefinition::class.java))
        }
            .forEach { expression ->
                val comment = AttributesDocumentationUtils.findContextualDocumentationElement(expression) ?: return@forEach
                sink.accept(object : PsiCommentDelegate(comment), PsiDocCommentBase {
                    override fun getOwner() = expression
                })
            }
    }

    override fun generateHoverDoc(element: PsiElement, originalElement: PsiElement?) =
        generateDoc(element, originalElement)
}

open class PsiCommentDelegate(val myComment: PsiComment) : PsiComment, ASTDelegatePsiElement() {
    override fun getTokenType() = myComment.tokenType

    override fun getParent(): PsiElement? = myComment.parent

    override fun getNode(): ASTNode = myComment.node
}