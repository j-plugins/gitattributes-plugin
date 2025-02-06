// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class GitattributesVisitor extends PsiElementVisitor {

  public void visitComment(@NotNull GitattributesComment o) {
    visitPsiElement(o);
  }

  public void visitDefinition(@NotNull GitattributesDefinition o) {
    visitPsiElement(o);
  }

  public void visitParameter(@NotNull GitattributesParameter o) {
    visitPsiElement(o);
  }

  public void visitPattern(@NotNull GitattributesPattern o) {
    visitPsiElement(o);
  }

  public void visitUnaryDefinition(@NotNull GitattributesUnaryDefinition o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
