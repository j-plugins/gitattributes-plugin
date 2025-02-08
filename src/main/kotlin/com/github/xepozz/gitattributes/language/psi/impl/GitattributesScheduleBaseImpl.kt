//package com.github.xepozz.gitattributes.language.psi.impl
//
//import com.github.xepozz.gitattributes.language.AttributesAnnotator
//import com.github.xepozz.gitattributes.language.psi.AttributesSchedule
//import com.intellij.extapi.psi.ASTWrapperPsiElement
//import com.intellij.icons.AllIcons
//import com.intellij.ide.projectView.PresentationData
//import com.intellij.lang.ASTNode
//
//abstract class GitattributesScheduleBaseImpl : ASTWrapperPsiElement, GitattributesSchedule {
//    constructor(node: ASTNode) : super(node)
//
//    override fun getText() = this.node.text
//
//    override fun getPresentation() = PresentationData(text, null, getIcon(0), AttributesAnnotator.SCHEDULE_HIGHLIGHT)
//
//    override fun getIcon(flags: Int) = AllIcons.Nodes.DataTables
//}