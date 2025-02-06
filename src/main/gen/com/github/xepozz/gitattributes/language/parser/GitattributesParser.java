// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.github.xepozz.gitattributes.language.psi.GitattributesTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class GitattributesParser implements PsiParser, LightPsiParser {

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
    return gitattributesFile(b, l + 1);
  }

  /* ********************************************************** */
  // SINGLE_COMMENT
  public static boolean COMMENT(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "COMMENT")) return false;
    if (!nextTokenIs(b, SINGLE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, SINGLE_COMMENT);
    exit_section_(b, m, COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // unary_definition
  public static boolean definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "definition")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = unary_definition(b, l + 1);
    exit_section_(b, m, DEFINITION, r);
    return r;
  }

  /* ********************************************************** */
  // item_*
  static boolean gitattributesFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "gitattributesFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!item_(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "gitattributesFile", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // definition | COMMENT | EOL
  static boolean item_(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "item_")) return false;
    boolean r;
    r = definition(b, l + 1);
    if (!r) r = COMMENT(b, l + 1);
    if (!r) r = consumeToken(b, EOL);
    return r;
  }

  /* ********************************************************** */
  // TEXT
  public static boolean parameter(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "parameter")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, TEXT);
    exit_section_(b, m, PARAMETER, r);
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

  /* ********************************************************** */
  // pattern parameter+
  public static boolean unary_definition(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_definition")) return false;
    if (!nextTokenIs(b, TEXT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = pattern(b, l + 1);
    r = r && unary_definition_1(b, l + 1);
    exit_section_(b, m, UNARY_DEFINITION, r);
    return r;
  }

  // parameter+
  private static boolean unary_definition_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "unary_definition_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = parameter(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!parameter(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "unary_definition_1", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

}
