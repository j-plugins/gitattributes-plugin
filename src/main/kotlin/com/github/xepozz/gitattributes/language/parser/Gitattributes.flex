package com.github.xepozz.gitattributes.language.parser;

import com.github.xepozz.gitattributes.language.psi.GitattributesTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.TokenType;

%%
%class GitattributesLexer
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
    {SINGLE_COMMENT}({NEWLINE}{SINGLE_COMMENT})* { return GitattributesTypes.COMMENT; }
    {TEXT}                                       { yypushback(0); yybegin(PATTERN); return GitattributesTypes.TEXT; }
}
<PATTERN> {
    {TEXT}                                       { return GitattributesTypes.TEXT; }
    {WHITESPACE}                                 { yybegin(ATTRIBUTES); return TokenType.WHITE_SPACE; }
}

<ATTRIBUTES> {
    {DASH}                                       { return GitattributesTypes.DASH; }
    {EQUALS_SIGN}                                { return GitattributesTypes.EQUALS_SIGN; }
    [^\-\s\=]+                                     { return GitattributesTypes.TEXT; }
}


{WHITESPACE}                                     { return TokenType.WHITE_SPACE; }
{NEWLINE}+                                       { yybegin(YYINITIAL); return GitattributesTypes.EOL; }

[^]                                              { return TokenType.BAD_CHARACTER; }
//[^]                                              { throw new Error("Illegal character <"+yytext()+">"); }