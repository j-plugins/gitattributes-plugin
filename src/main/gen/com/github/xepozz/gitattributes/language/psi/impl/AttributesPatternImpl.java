// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.xepozz.gitattributes.language.psi.AttributesTypes.*;
import com.github.xepozz.gitattributes.language.psi.*;
import com.intellij.psi.PsiReference;

public class AttributesPatternImpl extends AttributesPatternBaseImpl implements AttributesPattern {

  public AttributesPatternImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull AttributesVisitor visitor) {
    visitor.visitPattern(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof AttributesVisitor) accept((AttributesVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  public @NotNull PsiReference @NotNull [] getReferences() {
    return AttributesPsiImplUtil.getReferences(this);
  }

}
