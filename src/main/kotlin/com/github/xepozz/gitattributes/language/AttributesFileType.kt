package com.github.xepozz.gitattributes.language

import com.github.xepozz.gitattributes.AttributesIcons
import com.intellij.openapi.fileTypes.LanguageFileType

class AttributesFileType private constructor() : LanguageFileType(AttributesLanguage.INSTANCE) {
    override fun getName() = "Git Attributes File"

    override fun getDescription() = "Git Attributes language"

    override fun getDefaultExtension() = "gitattributes"

    override fun getIcon() = AttributesIcons.FILE

    companion object {
        @JvmStatic
        val INSTANCE = AttributesFileType()
    }
}