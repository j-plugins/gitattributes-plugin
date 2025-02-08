package com.github.xepozz.gitattributes.language.psi

import com.github.xepozz.gitattributes.language.AttributesLanguage
import com.intellij.psi.tree.IElementType

class AttributesElementType(debugName: String) : IElementType("AttributesElementType($debugName)", AttributesLanguage.INSTANCE)
