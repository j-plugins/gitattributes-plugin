package com.github.xepozz.gitattributes.language.psi

import com.github.xepozz.gitattributes.language.AttributesLanguage
import com.intellij.psi.tree.IElementType

class AttributesTokenType(debugName: String) : IElementType(debugName, AttributesLanguage.INSTANCE) {
    override fun toString() = "AttributesTokenType." + super.toString()
}