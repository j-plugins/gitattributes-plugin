package com.github.xepozz.gitattributes.language.parser

import com.github.xepozz.gitattributes.language.GitattributesFile
import com.github.xepozz.gitattributes.language.GitattributesLanguage
import com.github.xepozz.gitattributes.language.psi.GitattributesTokenSets
import com.github.xepozz.gitattributes.language.psi.GitattributesTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

internal class GitattributesParserDefinition : ParserDefinition {
    override fun createLexer(project: Project) = GitattributesLexerAdapter()

    override fun getCommentTokens() = GitattributesTokenSets.COMMENTS

    override fun getWhitespaceTokens(): TokenSet = GitattributesTokenSets.WHITESPACES

    override fun getStringLiteralElements(): TokenSet = GitattributesTokenSets.STRING_LITERALS

    override fun createParser(project: Project?) = GitattributesParser()

    override fun getFileNodeType() = FILE

    override fun createFile(viewProvider: FileViewProvider) = GitattributesFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = GitattributesTypes.Factory.createElement(node)

    companion object {
        val FILE = IFileElementType(GitattributesLanguage.INSTANCE)
    }
}