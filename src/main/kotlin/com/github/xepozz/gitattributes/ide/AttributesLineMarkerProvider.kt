package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileSystemItem

class AttributesLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun getLineMarkerInfo(element: PsiElement) = when {
        element is AttributesPattern -> {
            val target = element.references.firstNotNullOfOrNull { it.resolve() } as? PsiFileSystemItem ?: return null
//            println("target: $target")

            val builder = NavigationGutterIconBuilder.create(target.getIcon(0) ?: AllIcons.Nodes.Target)
                .setTargets(target)
                .setTooltipText("Navigate to ${target.name}")

            builder.createLineMarkerInfo(element.firstChild)
        }

        else -> null
    }
}