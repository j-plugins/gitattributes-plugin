package com.github.xepozz.gitattributes.language.parser;

import com.github.xepozz.gitattributes.language.psi.AttributesTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;

%%
%class AttributesLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

SINGLE_COMMENT=#[^\r\n]*

NUMBER=[0-9]+
WHITESPACE=[ \t]+
DASH="-"
COMMA=","
EQUALS_SIGN="="
NEWLINE=\r\n|\r|\n

QUOTED_PATTERN=\"([^\"\\\r\n]|\\[^\r\n])*(\"?)
TEXT=(\\[^\r\n]|[^ \t\r\n])+
ATTRIBUTE_TEXT=[^\-\s\=\n\r,][^\s\=\n\r,]*
ATTRIBUTE_VALUE_TEXT=[^\s\=\n\r,]+

%state PATTERN, ATTRIBUTES, ATTRIBUTE_VALUES
%%
<YYINITIAL> {
    {SINGLE_COMMENT}                             { return AttributesTypes.COMMENT; }
    {QUOTED_PATTERN}                             { yybegin(PATTERN); return AttributesTypes.TEXT; }
    {TEXT}                                       { yybegin(PATTERN); return AttributesTypes.TEXT; }
}
<PATTERN> {
    {QUOTED_PATTERN}                             { return AttributesTypes.TEXT; }
    {TEXT}                                       { return AttributesTypes.TEXT; }
    {WHITESPACE}                                 { yybegin(ATTRIBUTES); return TokenType.WHITE_SPACE; }
}

<ATTRIBUTES> {
    {DASH}                                       { return AttributesTypes.DASH; }
    {COMMA}                                      { return AttributesTypes.COMMA; }
    {EQUALS_SIGN}                                { yybegin(ATTRIBUTE_VALUES); return AttributesTypes.EQUALS_SIGN; }
    {ATTRIBUTE_TEXT}                             { return AttributesTypes.TEXT; }
}

<ATTRIBUTE_VALUES> {
    {WHITESPACE}                                 { yybegin(ATTRIBUTES); return TokenType.WHITE_SPACE; }
    {COMMA}                                      { return AttributesTypes.COMMA; }
    {EQUALS_SIGN}                                { return AttributesTypes.EQUALS_SIGN; }
    {ATTRIBUTE_VALUE_TEXT}                       { yybegin(ATTRIBUTES); return AttributesTypes.TEXT; }
}


{WHITESPACE}                                     { return TokenType.WHITE_SPACE; }
{NEWLINE}+                                       { yybegin(YYINITIAL); return AttributesTypes.EOL; }

[^]                                              { return TokenType.BAD_CHARACTER; }
//[^]                                              { throw new Error("Illegal character <"+yytext()+">"); }
