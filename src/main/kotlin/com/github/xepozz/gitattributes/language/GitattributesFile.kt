package com.github.xepozz.gitattributes.language

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.psi.FileViewProvider

class GitattributesFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, GitattributesLanguage.INSTANCE) {
    override fun getFileType() = GitattributesFileType.INSTANCE

    override fun toString() = "Gitattributes File"
}