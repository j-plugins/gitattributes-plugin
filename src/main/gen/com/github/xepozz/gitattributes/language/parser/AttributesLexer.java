package com.github.xepozz.gitattributes.language.parser;

import com.github.xepozz.gitattributes.language.psi.AttributesTypes;
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

class AttributesLexer implements FlexLexer {
  public static final int YYINITIAL = 0;
  public static final int PATTERN = 2;
  public static final int ATTRIBUTES = 4;
  public static final int ATTRIBUTE_VALUES = 6;

  private CharSequence buffer = "";
  private int endOffset;
  private int position;
  private int tokenStart;
  private int tokenEnd;
  private int state = YYINITIAL;

  AttributesLexer(java.io.Reader in) {
  }

  @Override
  public void reset(CharSequence buffer, int start, int end, int initialState) {
    this.buffer = buffer;
    this.position = start;
    this.tokenStart = start;
    this.tokenEnd = start;
    this.endOffset = end;
    this.state = initialState;
  }

  @Override
  public IElementType advance() {
    if (position >= endOffset) {
      tokenStart = tokenEnd = endOffset;
      return null;
    }

    tokenStart = position;

    char current = buffer.charAt(position);
    if (isNewLineStart(current)) {
      yybegin(YYINITIAL);
      scanNewLines();
      return AttributesTypes.EOL;
    }

    if (isHorizontalWhitespace(current)) {
      if (state == PATTERN) {
        yybegin(ATTRIBUTES);
      } else if (state == ATTRIBUTE_VALUES) {
        yybegin(ATTRIBUTES);
      }
      scanHorizontalWhitespace();
      return TokenType.WHITE_SPACE;
    }

    if (state == YYINITIAL && current == '#') {
      scanComment();
      return AttributesTypes.COMMENT;
    }

    if (state == YYINITIAL || state == PATTERN) {
      scanPattern();
      yybegin(PATTERN);
      return AttributesTypes.TEXT;
    }

    if (state == ATTRIBUTE_VALUES) {
      if (current == ',') {
        position++;
        tokenEnd = position;
        return AttributesTypes.COMMA;
      }

      if (current == '=') {
        position++;
        tokenEnd = position;
        return AttributesTypes.EQUALS_SIGN;
      }

      scanAttributeText();
      yybegin(ATTRIBUTES);
      return AttributesTypes.TEXT;
    }

    if (current == '=') {
      position++;
      tokenEnd = position;
      yybegin(ATTRIBUTE_VALUES);
      return AttributesTypes.EQUALS_SIGN;
    }

    if (current == ',') {
      position++;
      tokenEnd = position;
      return AttributesTypes.COMMA;
    }

    if (current == '-') {
      position++;
      tokenEnd = position;
      return AttributesTypes.DASH;
    }

    scanAttributeText();
    return AttributesTypes.TEXT;
  }

  @Override
  public int getTokenStart() {
    return tokenStart;
  }

  @Override
  public int getTokenEnd() {
    return tokenEnd;
  }

  @Override
  public void yybegin(int newState) {
    state = newState;
  }

  @Override
  public int yystate() {
    return state;
  }

  private void scanPattern() {
    if (buffer.charAt(position) == '"') {
      scanQuotedPattern();
      return;
    }

    while (position < endOffset) {
      char c = buffer.charAt(position);
      if (isNewLineStart(c) || isHorizontalWhitespace(c)) {
        break;
      }
      if (c == '\\' && position + 1 < endOffset && !isNewLineStart(buffer.charAt(position + 1))) {
        position += 2;
        continue;
      }
      position++;
    }
    tokenEnd = position;
  }

  private void scanQuotedPattern() {
    position++;
    while (position < endOffset) {
      char c = buffer.charAt(position);
      if (isNewLineStart(c)) {
        break;
      }
      if (c == '\\' && position + 1 < endOffset && !isNewLineStart(buffer.charAt(position + 1))) {
        position += 2;
        continue;
      }
      position++;
      if (c == '"') {
        break;
      }
    }
    tokenEnd = position;
  }

  private void scanAttributeText() {
    while (position < endOffset) {
      char c = buffer.charAt(position);
      if (isNewLineStart(c) || isHorizontalWhitespace(c) || c == '=' || c == ',') {
        break;
      }
      position++;
    }

    if (position == tokenStart) {
      position++;
    }
    tokenEnd = position;
  }

  private void scanComment() {
    while (position < endOffset && !isNewLineStart(buffer.charAt(position))) {
      position++;
    }
    tokenEnd = position;
  }

  private void scanHorizontalWhitespace() {
    while (position < endOffset && isHorizontalWhitespace(buffer.charAt(position))) {
      position++;
    }
    tokenEnd = position;
  }

  private void scanNewLines() {
    while (position < endOffset && isNewLineStart(buffer.charAt(position))) {
      if (buffer.charAt(position) == '\r' && position + 1 < endOffset && buffer.charAt(position + 1) == '\n') {
        position += 2;
      } else {
        position++;
      }
    }
    tokenEnd = position;
  }

  private static boolean isHorizontalWhitespace(char c) {
    return c == ' ' || c == '\t';
  }

  private static boolean isNewLineStart(char c) {
    return c == '\n' || c == '\r';
  }
}
