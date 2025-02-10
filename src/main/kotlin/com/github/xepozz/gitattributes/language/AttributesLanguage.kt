package com.github.xepozz.gitattributes.language

import com.intellij.lang.Language

class AttributesLanguage : Language("Gitattributes") {
    companion object {
        @JvmStatic
        val INSTANCE = AttributesLanguage();
    }
}