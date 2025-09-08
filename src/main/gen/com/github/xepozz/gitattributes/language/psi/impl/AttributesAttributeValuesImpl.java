// This is a generated file. Not intended for manual editing.
package com.github.xepozz.gitattributes.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.github.xepozz.gitattributes.language.psi.AttributesTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.github.xepozz.gitattributes.language.psi.*;

public class AttributesAttributeValuesImpl extends ASTWrapperPsiElement implements AttributesAttributeValues {

  public AttributesAttributeValuesImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull AttributesVisitor visitor) {
    visitor.visitAttributeValues(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof AttributesVisitor) accept((AttributesVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<AttributesAttributeValue> getAttributeValueList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, AttributesAttributeValue.class);
  }

}
