package com.github.xepozz.gitattributes.language

import com.github.xepozz.gitattributes.AttributesIcons
import com.intellij.openapi.fileTypes.LanguageFileType

class AttributesFileType private constructor() : LanguageFileType(AttributesLanguage.INSTANCE) {
    override fun getName() = "Gitattributes File"

    override fun getDescription() = "Gitattributes language file"

    override fun getDefaultExtension() = "gitattributes"

    override fun getIcon() = AttributesIcons.FILE

    companion object {
        val INSTANCE = AttributesFileType()
    }
}