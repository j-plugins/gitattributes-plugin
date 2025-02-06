package com.github.xepozz.gitattributes.language

import com.github.xepozz.gitattributes.GitattributesIcons
import com.intellij.openapi.fileTypes.LanguageFileType

class GitattributesFileType private constructor() : LanguageFileType(GitattributesLanguage.INSTANCE) {
    override fun getName() = "Gitattributes File"

    override fun getDescription() = "Gitattributes language file"

    override fun getDefaultExtension() = "gitattributes"

    override fun getIcon() = GitattributesIcons.FILE

    companion object {
        val INSTANCE = GitattributesFileType()
    }
}