// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.github.xepozz.gitattributes.language.psi.impl.*;

public interface AttributesTypes {

  IElementType ATTRIBUTE = new AttributesElementType("ATTRIBUTE");
  IElementType ATTRIBUTE_LIST = new AttributesElementType("ATTRIBUTE_LIST");
  IElementType ATTRIBUTE_NAME = new AttributesElementType("ATTRIBUTE_NAME");
  IElementType ATTRIBUTE_VALUE = new AttributesElementType("ATTRIBUTE_VALUE");
  IElementType ATTRIBUTE_VALUES = new AttributesElementType("ATTRIBUTE_VALUES");
  IElementType DEFINITION = new AttributesElementType("DEFINITION");
  IElementType PATTERN = new AttributesElementType("PATTERN");

  IElementType COMMA = new AttributesTokenType("COMMA");
  IElementType COMMENT = new AttributesTokenType("COMMENT");
  IElementType DASH = new AttributesTokenType("DASH");
  IElementType EOL = new AttributesTokenType("EOL");
  IElementType EQUALS_SIGN = new AttributesTokenType("EQUALS_SIGN");
  IElementType TEXT = new AttributesTokenType("TEXT");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ATTRIBUTE) {
        return new AttributesAttributeImpl(node);
      }
      else if (type == ATTRIBUTE_LIST) {
        return new AttributesAttributeListImpl(node);
      }
      else if (type == ATTRIBUTE_NAME) {
        return new AttributesAttributeNameImpl(node);
      }
      else if (type == ATTRIBUTE_VALUE) {
        return new AttributesAttributeValueImpl(node);
      }
      else if (type == ATTRIBUTE_VALUES) {
        return new AttributesAttributeValuesImpl(node);
      }
      else if (type == DEFINITION) {
        return new AttributesDefinitionImpl(node);
      }
      else if (type == PATTERN) {
        return new AttributesPatternImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
