package com.github.xepozz.gitattributes.language.psi

import com.github.xepozz.gitattributes.language.GitattributesLanguage
import com.intellij.psi.tree.IElementType

class GitattributesElementType(debugName: String) : IElementType("GitattributesElementType($debugName)", GitattributesLanguage.INSTANCE)
