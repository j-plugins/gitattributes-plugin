package com.github.xepozz.gitattributes.language.parser

import com.intellij.lexer.FlexAdapter

class GitattributesLexerAdapter : FlexAdapter(GitattributesLexer(null))