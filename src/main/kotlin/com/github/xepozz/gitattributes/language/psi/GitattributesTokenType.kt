package com.github.xepozz.gitattributes.language.psi

import com.github.xepozz.gitattributes.language.GitattributesLanguage
import com.intellij.psi.tree.IElementType

class GitattributesTokenType(debugName: String) : IElementType(debugName, GitattributesLanguage.INSTANCE) {
    override fun toString() = "GitattributesTokenType." + super.toString()
}