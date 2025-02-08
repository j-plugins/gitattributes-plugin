package com.github.xepozz.gitattributes.language.psi.impl

import com.github.xepozz.gitattributes.language.psi.GitattributesAttribute
import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.icons.AllIcons
import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode

abstract class AttributesAttributeBaseImpl : ASTWrapperPsiElement, GitattributesAttribute {
    constructor(node: ASTNode) : super(node)

    override fun getText() = this.node.text

    override fun getPresentation() = PresentationData(text, null, getIcon(0), null)

    override fun getIcon(flags: Int) = AllIcons.Nodes.Console
}