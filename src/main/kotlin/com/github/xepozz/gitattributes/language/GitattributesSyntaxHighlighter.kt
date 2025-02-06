package com.github.xepozz.gitattributes.language

import com.github.xepozz.gitattributes.language.parser.GitattributesLexerAdapter
import com.github.xepozz.gitattributes.language.psi.GitattributesTypes
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType

class GitattributesSyntaxHighlighter : SyntaxHighlighterBase() {
    override fun getHighlightingLexer() = GitattributesLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType) = when (tokenType) {
//        GitattributesTypes.SCHEDULE -> COMMENT_KEYS
//        GitattributesTypes.TIME_POINTER -> CONSTANT_KEYS
//        GitattributesTypes.COMMAND -> KEYWORD_KEYS
//        GitattributesTypes.EQUAL_SIGN -> OPERATION_KEYS

        GitattributesTypes.COMMENT -> COMMENT_KEYS
        TokenType.BAD_CHARACTER -> BAD_CHAR_KEYS
        else -> EMPTY_KEYS
    }

    companion object {
        private val BAD_CHAR_KEYS = arrayOf(
            HighlighterColors.BAD_CHARACTER,
        )

        private val COMMENT_KEYS = arrayOf(
            DefaultLanguageHighlighterColors.DOC_COMMENT
        )
        private val OPERATION_KEYS = arrayOf(
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )
        private val EMPTY_KEYS = emptyArray<TextAttributesKey>()
    }
}