//package com.github.xepozz.gitattributes.language.psi.impl
//
//import com.github.xepozz.gitattributes.language.psi.GitattributesTimeRange
//import com.intellij.extapi.psi.ASTWrapperPsiElement
//import com.intellij.lang.ASTNode
//
//abstract class GitattributesTimeRangeBaseImpl : ASTWrapperPsiElement, GitattributesTimeRange {
//    constructor(node: ASTNode) : super(node)
//
//    override fun getText(): String {
//        return this.node.text
//    }
//}