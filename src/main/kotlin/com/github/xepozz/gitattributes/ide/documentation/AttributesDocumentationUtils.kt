package com.github.xepozz.gitattributes.ide.documentation

import com.github.xepozz.gitattributes.language.psi.AttributesTypes
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.elementType

object AttributesDocumentationUtils {
    fun findCrontabElementDocumentation(element: PsiElement?): String =
        findContextualDocumentationElement(element)?.text?.replaceFirst("[# ]+", "") ?: ""

    fun findContextualDocumentationElement(element: PsiElement?): PsiComment? {
        if (element == null) return null

        var element = element.prevSibling
        while (element is PsiComment || element is PsiWhiteSpace || element.elementType == AttributesTypes.EOL) {
            if (element is PsiComment) {
                return element
            }
            element = element.prevSibling
        }

        return null
    }
}