package com.github.xepozz.gitattributes.language.parser

import com.intellij.lexer.FlexAdapter

class AttributesLexerAdapter : FlexAdapter(AttributesLexer(null))