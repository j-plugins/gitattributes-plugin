package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileSystemItem

class AttributesLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun getLineMarkerInfo(element: PsiElement): RelatedItemLineMarkerInfo<*>? {
        if (element !is AttributesPattern) return null

        val target = element.references.firstNotNullOfOrNull { it.resolve() } as? PsiFileSystemItem ?: return null

        val builder = NavigationGutterIconBuilder.create(target.getIcon(0) ?: AllIcons.Nodes.Target)
            .setTargets(target)
            .setTooltipText("Navigate to ${target.name}")

        return builder.createLineMarkerInfo(element.firstChild ?: element)
    }
}
