package com.github.xepozz.gitattributes.language

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class AttributesFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, AttributesLanguage.INSTANCE) {
    override fun getFileType() = AttributesFileType.INSTANCE

    override fun toString() = "Gitattributes File"
}