package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.language.AttributesFileType
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AttributesCompletionTest : BasePlatformTestCase() {
    fun testKeywordCompletionInsideAttributeName() {
        myFixture.configureByText(AttributesFileType.INSTANCE, "foo.txt <caret>")

        myFixture.completeBasic()

        assertContainsElements(myFixture.lookupElementStrings ?: emptyList(), "text", "binary", "eol")
    }

    fun testKeywordCompletionIsNotOfferedInsideAttributeValue() {
        myFixture.configureByText(AttributesFileType.INSTANCE, "foo.txt eol=<caret>")

        myFixture.completeBasic()

        assertDoesntContain(myFixture.lookupElementStrings ?: emptyList(), "text", "binary", "eol")
    }
}
