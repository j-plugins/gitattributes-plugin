package com.github.xepozz.gitattributes.language

import com.intellij.lang.Language

class GitattributesLanguage : Language("Gitattributes") {
    companion object {
        val INSTANCE = GitattributesLanguage();
    }
}