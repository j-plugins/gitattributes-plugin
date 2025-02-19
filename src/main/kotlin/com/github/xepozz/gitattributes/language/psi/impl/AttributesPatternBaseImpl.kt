package com.github.xepozz.gitattributes.language.psi.impl

import com.github.xepozz.gitattributes.language.psi.AttributesAttribute
import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode

abstract class AttributesPatternBaseImpl : ASTWrapperPsiElement, AttributesPattern {
    constructor(node: ASTNode) : super(node)

    override fun getText() = this.node.text

    override fun getPresentation() = PresentationData(text, null, getIcon(0), null)

    override fun getIcon(flags: Int) = AllIcons.Nodes.Console
}