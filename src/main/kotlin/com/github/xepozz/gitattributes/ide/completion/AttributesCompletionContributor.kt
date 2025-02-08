package com.github.xepozz.gitattributes.ide.completion

import com.github.xepozz.gitattributes.AttributesIcons
import com.github.xepozz.gitattributes.language.AttributesFile
import com.github.xepozz.gitattributes.language.psi.AttributesAttribute
import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.project.DumbAware
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import kotlin.collections.filter
import kotlin.collections.forEach
import kotlin.jvm.java
import kotlin.text.startsWith
import kotlin.text.substring

class AttributesCompletionContributor : CompletionContributor(), DumbAware {
    val keywords = listOf(
        "text",
        "binary",
        "export-ignore",
        "merge",
        "eol",
        "crlf",
        "working-tree-encoding",
        "ident",
        "filter",
        "diff",
        "whitespace",
        "export-subst",
        "delta",
        "encoding"
    )

    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement()
                .withParent(PlatformPatterns.psiElement(AttributesAttribute::class.java)),
            object : CompletionProvider<CompletionParameters>(), DumbAware {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    val leafElement = parameters.position
                    val attribute = leafElement.parent as? AttributesAttribute ?: return
                    val count = attribute.children.count()
//                    println("leaft: $leafElement attr: $attribute, count: $count, text ${leafElement.prevSibling?.text}")
                    if (count == 1 || leafElement.prevSibling?.text != "=") {
                        keywords.forEach { keyword ->
                            result.addElement(
                                LookupElementBuilder.create(keyword)
                                    .withIcon(AttributesIcons.FILE)
                            )
                        }
                    }
                }
            }
        )
    }
}