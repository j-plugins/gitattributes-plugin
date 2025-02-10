package com.github.xepozz.gitattributes.language.parser

import com.github.xepozz.gitattributes.language.AttributesFile
import com.github.xepozz.gitattributes.language.AttributesLanguage
import com.github.xepozz.gitattributes.language.psi.AttributesTokenSets
import com.github.xepozz.gitattributes.language.psi.AttributesTypes
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

internal class AttributesParserDefinition : ParserDefinition {
    override fun createLexer(project: Project) = AttributesLexerAdapter()

    override fun getCommentTokens() = AttributesTokenSets.COMMENTS

    override fun getWhitespaceTokens(): TokenSet = AttributesTokenSets.WHITESPACES

    override fun getStringLiteralElements(): TokenSet = AttributesTokenSets.STRING_LITERALS

    override fun createParser(project: Project?) = AttributesParser()

    override fun getFileNodeType() = FILE

    override fun createFile(viewProvider: FileViewProvider) = AttributesFile(viewProvider)

    override fun createElement(node: ASTNode): PsiElement = AttributesTypes.Factory.createElement(node)

    companion object {
        @JvmStatic
        val FILE = IFileElementType(AttributesLanguage.INSTANCE)
    }
}