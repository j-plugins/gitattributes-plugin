package com.github.xepozz.gitattributes.language.psi

import com.intellij.psi.tree.TokenSet

object GitattributesTokenSets {
    val EMPTY_SET = TokenSet.EMPTY

    val COMMENTS = TokenSet.create(GitattributesTypes.COMMENT)
    val STRING_LITERALS = TokenSet.create(GitattributesTypes.PATTERN)
    val WHITESPACES = TokenSet.WHITE_SPACE
}