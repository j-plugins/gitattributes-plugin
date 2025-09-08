// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.xepozz.gitattributes.language.psi.AttributesTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class AttributesParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return attributesFile(b, l + 1);
  }

  /* ********************************************************** */
  // DASH? attribute_name (EQUALS_SIGN attribute_values)?
  public static boolean attribute(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute")) return false;
    if (!nextTokenIs(b, "<attribute>", DASH, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE, "<attribute>");
    r = attribute_0(b, l + 1);
    r = r && attribute_name(b, l + 1);
    r = r && attribute_2(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // DASH?
  private static boolean attribute_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_0")) return false;
    consumeToken(b, DASH);
    return true;
  }

  // (EQUALS_SIGN attribute_values)?
  private static boolean attribute_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_2")) return false;
    attribute_2_0(b, l + 1);
    return true;
  }

  // EQUALS_SIGN attribute_values
  private static boolean attribute_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, EQUALS_SIGN);
    r = r && attribute_values(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attribute+
  public static boolean attributeList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributeList")) return false;
    if (!nextTokenIs(b, "<attribute list>", DASH, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ATTRIBUTE_LIST, "<attribute list>");
    r = attribute(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!attribute(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attributeList", c)) break;
    }
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // TEXT
  public static boolean attribute_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_name")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    exit_section_(b, m, ATTRIBUTE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // TEXT (EQUALS_SIGN TEXT)?
  public static boolean attribute_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_value")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    r = r && attribute_value_1(b, l + 1);
    exit_section_(b, m, ATTRIBUTE_VALUE, r);
    return r;
  }

  // (EQUALS_SIGN TEXT)?
  private static boolean attribute_value_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_value_1")) return false;
    attribute_value_1_0(b, l + 1);
    return true;
  }

  // EQUALS_SIGN TEXT
  private static boolean attribute_value_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_value_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, EQUALS_SIGN, TEXT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // attribute_value (COMMA attribute_value)*
  public static boolean attribute_values(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_values")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = attribute_value(b, l + 1);
    r = r && attribute_values_1(b, l + 1);
    exit_section_(b, m, ATTRIBUTE_VALUES, r);
    return r;
  }

  // (COMMA attribute_value)*
  private static boolean attribute_values_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_values_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!attribute_values_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attribute_values_1", c)) break;
    }
    return true;
  }

  // COMMA attribute_value
  private static boolean attribute_values_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attribute_values_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && attribute_value(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean attributesFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "attributesFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "attributesFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // pattern attributeList
  public static boolean definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pattern(b, l + 1);
    r = r && attributeList(b, l + 1);
    exit_section_(b, m, DEFINITION, r);
    return r;
  }

  /* ********************************************************** */
  // definition | COMMENT | EOL
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = definition(b, l + 1);
    if (!r) r = consumeToken(b, COMMENT);
    if (!r) r = consumeToken(b, EOL);
    return r;
  }

  /* ********************************************************** */
  // TEXT
  public static boolean pattern(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "pattern")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    exit_section_(b, m, PATTERN, r);
    return r;
  }

}
