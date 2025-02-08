package com.github.xepozz.gitattributes.language.psi

import com.intellij.psi.tree.TokenSet

object AttributesTokenSets {
    val EMPTY_SET = TokenSet.EMPTY

    val COMMENTS = TokenSet.create(AttributesTypes.COMMENT)
    val STRING_LITERALS = TokenSet.create(AttributesTypes.PATTERN)
    val WHITESPACES = TokenSet.WHITE_SPACE
}