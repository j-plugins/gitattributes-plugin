package com.github.xepozz.gitattributes.language

//import com.github.xepozz.gitattributes.language.psi.AttributesCommand
import com.intellij.lang.Language
import com.intellij.lang.injection.MultiHostInjector
import com.intellij.lang.injection.MultiHostRegistrar
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLanguageInjectionHost

class AttributesLanguageInjector : MultiHostInjector {
    val shellLanguage = Language.findLanguageByID("Shell Script")
    override fun getLanguagesToInject(registrar: MultiHostRegistrar, element: PsiElement) {
        if (shellLanguage == null) return

        when (element) {
//            is GitattributesCommand -> {
//                if (element.textLength > 0) {
//                    registrar.startInjecting(shellLanguage)
//                        .addPlace(null, null, element, TextRange(0, element.textLength))
//                        .doneInjecting()
//                }
//            }
        }
    }

    override fun elementsToInjectIn(): List<Class<out PsiElement>> {
        return listOf(PsiLanguageInjectionHost::class.java)
    }
}