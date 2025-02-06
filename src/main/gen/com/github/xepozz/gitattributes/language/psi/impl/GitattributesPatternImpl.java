// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.xepozz.gitattributes.language.psi.GitattributesTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.xepozz.gitattributes.language.psi.*;

public class GitattributesPatternImpl extends ASTWrapperPsiElement implements GitattributesPattern {

  public GitattributesPatternImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull GitattributesVisitor visitor) {
    visitor.visitPattern(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof GitattributesVisitor) accept((GitattributesVisitor)visitor);
    else super.accept(visitor);
  }

}
