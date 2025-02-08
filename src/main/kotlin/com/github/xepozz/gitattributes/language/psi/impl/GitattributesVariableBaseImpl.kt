//package com.github.xepozz.gitattributes.language.psi.impl
//
//import com.github.xepozz.gitattributes.language.psi.AttributesVariableDefinition
//import com.intellij.extapi.psi.ASTWrapperPsiElement
//import com.intellij.icons.AllIcons
//import com.intellij.lang.ASTNode
//
//abstract class GitattributesVariableBaseImpl :
//    ASTWrapperPsiElement,
//    GitattributesVariableDefinition {
//    constructor(node: ASTNode) : super(node)
//
//    override fun getText() = this.node.text
//
//    override fun getIcon(flags: Int) = AllIcons.Nodes.Variable
//}