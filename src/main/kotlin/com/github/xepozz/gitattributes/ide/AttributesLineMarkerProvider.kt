package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileSystemItem
import kotlin.collections.firstNotNullOfOrNull

class AttributesLineMarkerProvider : RelatedItemLineMarkerProvider() {
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        when (element) {
            is AttributesPattern -> {
                val target = element.references.firstNotNullOfOrNull { it.resolve() } as? PsiFileSystemItem ?: return
//                println("target: $target")

                val builder = NavigationGutterIconBuilder.create(target.getIcon(0) ?: AllIcons.Nodes.Target)
                    .setTargets(target)
                    .setTooltipText("Navigate to ${target.name}")

                result.add(builder.createLineMarkerInfo(element))
            }
        }
    }
}