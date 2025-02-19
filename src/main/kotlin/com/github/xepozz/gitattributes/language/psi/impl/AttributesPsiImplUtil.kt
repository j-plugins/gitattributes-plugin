package com.github.xepozz.gitattributes.language.psi.impl

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry

object AttributesPsiImplUtil {
    @JvmStatic
    fun getReferences(element: PsiElement): Array<PsiReference> =
        ReferenceProvidersRegistry.getReferencesFromProviders(element)
}