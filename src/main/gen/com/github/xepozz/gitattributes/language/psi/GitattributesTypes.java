// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.xepozz.gitattributes.language.psi.impl.*;

public interface GitattributesTypes {

  IElementType COMMENT = new GitattributesElementType("COMMENT");
  IElementType DEFINITION = new GitattributesElementType("DEFINITION");
  IElementType PARAMETER = new GitattributesElementType("PARAMETER");
  IElementType PATTERN = new GitattributesElementType("PATTERN");
  IElementType UNARY_DEFINITION = new GitattributesElementType("UNARY_DEFINITION");

  IElementType EOL = new GitattributesTokenType("EOL");
  IElementType SINGLE_COMMENT = new GitattributesTokenType("SINGLE_COMMENT");
  IElementType TEXT = new GitattributesTokenType("TEXT");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == COMMENT) {
        return new GitattributesCommentImpl(node);
      }
      else if (type == DEFINITION) {
        return new GitattributesDefinitionImpl(node);
      }
      else if (type == PARAMETER) {
        return new GitattributesParameterImpl(node);
      }
      else if (type == PATTERN) {
        return new GitattributesPatternImpl(node);
      }
      else if (type == UNARY_DEFINITION) {
        return new GitattributesUnaryDefinitionImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
