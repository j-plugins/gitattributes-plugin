// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.NavigatablePsiElement;

public class GitattributesVisitor extends PsiElementVisitor {

  public void visitComment(@NotNull GitattributesComment o) {
    visitPsiElement(o);
  }

  public void visitAttribute(@NotNull GitattributesAttribute o) {
    visitNavigatablePsiElement(o);
  }

  public void visitAttributeList(@NotNull GitattributesAttributeList o) {
    visitPsiElement(o);
  }

  public void visitDefinition(@NotNull GitattributesDefinition o) {
    visitPsiElement(o);
  }

  public void visitPattern(@NotNull GitattributesPattern o) {
    visitPsiElement(o);
  }

  public void visitUnaryDefinition(@NotNull GitattributesUnaryDefinition o) {
    visitPsiElement(o);
  }

  public void visitNavigatablePsiElement(@NotNull NavigatablePsiElement o) {
    visitElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
