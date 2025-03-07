package com.github.xepozz.gitattributes.language

import com.github.xepozz.gitattributes.language.psi.AttributesAttribute
import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.psi.PsiElement

class AttributesAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is AttributesPattern -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element.textRange)
                    .textAttributes(PATTERN_HIGHLIGHT)
                    .create()
            }

            is AttributesAttribute -> {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element.textRange)
                    .textAttributes(PARAMETERS_HIGHLIGHT)
                    .create()
            }

//            is GitattributesVariableValue -> {
//                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//                    .range(element.textRange)
//                    .textAttributes(STRING_HIGHLIGHT)
//                    .create()
//            }

//            is GitattributesCommand -> {
//                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//                    .range(element.textRange)
//                    .textAttributes(COMMAND_HIGHLIGHT)
//                    .create()
//            }
        }
    }

    companion object {
        val PATTERN_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
            "GITATTRIBUTES_PATTERN",
            DefaultLanguageHighlighterColors.STRING,
        )
        private val PARAMETERS_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
            "GITATTRIBUTES_IDENTIFIER",
            DefaultLanguageHighlighterColors.KEYWORD,
        )
//        private val STRING_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
//            "GITATTRIBUTES_STRING",
//            DefaultLanguageHighlighterColors.STRING,
//        )
//        private val OPERATION_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
//            "GITATTRIBUTES_OPERATION",
//            DefaultLanguageHighlighterColors.OPERATION_SIGN,
//        )
//        private val COMMAND_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
//            "GITATTRIBUTES_COMMAND",
//            DefaultLanguageHighlighterColors.STRING,
//        )
    }
}