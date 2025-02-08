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

SINGLE_COMMENT=#[^\n]*

NUMBER=[0-9]+
WHITESPACE=[ \t]+
DASH="-"
EQUALS_SIGN="="
NEWLINE=\r|\n|\r\n

TEXT=(\\\s|[^\s])+

%state PATTERN, ATTRIBUTES
%%
<YYINITIAL> {
    {SINGLE_COMMENT}({NEWLINE}{SINGLE_COMMENT})* { return AttributesTypes.COMMENT; }
    {TEXT}                                       { yypushback(0); yybegin(PATTERN); return AttributesTypes.TEXT; }
}
<PATTERN> {
    {TEXT}                                       { return AttributesTypes.TEXT; }
    {WHITESPACE}                                 { yybegin(ATTRIBUTES); return TokenType.WHITE_SPACE; }
}

<ATTRIBUTES> {
    {DASH}                                       { return AttributesTypes.DASH; }
    {EQUALS_SIGN}                                { return AttributesTypes.EQUALS_SIGN; }
    [^\-\s\=\n][^\s\=\n]*                        { return AttributesTypes.TEXT; }
}


{WHITESPACE}                                     { return TokenType.WHITE_SPACE; }
{NEWLINE}+                                       { yybegin(YYINITIAL); return AttributesTypes.EOL; }

[^]                                              { return TokenType.BAD_CHARACTER; }
//[^]                                              { throw new Error("Illegal character <"+yytext()+">"); }