// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.NavigatablePsiElement;

public class AttributesVisitor extends PsiElementVisitor {

  public void visitComment(@NotNull AttributesComment o) {
    visitPsiElement(o);
  }

  public void visitAttribute(@NotNull AttributesAttribute o) {
    visitNavigatablePsiElement(o);
  }

  public void visitAttributeList(@NotNull AttributesAttributeList o) {
    visitPsiElement(o);
  }

  public void visitDefinition(@NotNull AttributesDefinition o) {
    visitPsiElement(o);
  }

  public void visitPattern(@NotNull AttributesPattern o) {
    visitPsiElement(o);
  }

  public void visitNavigatablePsiElement(@NotNull NavigatablePsiElement o) {
    visitElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
