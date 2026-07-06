package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.language.psi.AttributesPattern
import com.intellij.psi.PsiFileSystemItem
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AttributesReferenceTest : BasePlatformTestCase() {
    fun testPatternReferencesUseGitAttributesGlobSemantics() {
        myFixture.addFileToProject("Main.kt", "")
        myFixture.addFileToProject("src/Main.kt", "")
        myFixture.addFileToProject("src/Nested/Test.kt", "")
        myFixture.addFileToProject("docs/readme.md", "")
        myFixture.addFileToProject("dir with space/a.txt", "")
        myFixture.addFileToProject("hash#name.txt", "")
        val file = myFixture.addFileToProject(
            ".gitattributes",
            """
                *.kt text
                docs/** text
                "dir with space/a.txt" text
                dir\ with\ space/a.txt text
                **/Main.kt text
                src/**/Test.kt text
                hash\#name.txt text
            """.trimIndent(),
        )

        val patterns = PsiTreeUtil.findChildrenOfType(file, AttributesPattern::class.java).toList()

        assertContainsElements(patterns[0].resolveTargetNames(), "Main.kt")
        assertEquals(listOf("readme.md"), patterns[1].resolveTargetNames())
        assertEquals(listOf("a.txt"), patterns[2].resolveTargetNames())
        assertEquals(listOf("a.txt"), patterns[3].resolveTargetNames())
        assertContainsElements(patterns[4].resolveTargetNames(), "Main.kt")
        assertEquals(listOf("Test.kt"), patterns[5].resolveTargetNames())
        assertEquals(listOf("hash#name.txt"), patterns[6].resolveTargetNames())
    }

    private fun AttributesPattern.resolveTargetNames(): List<String> =
        references
            .flatMap { reference ->
                if (reference is com.intellij.psi.PsiPolyVariantReference) {
                    reference.multiResolve(false).mapNotNull { it.element as? PsiFileSystemItem }
                } else {
                    listOfNotNull(reference.resolve() as? PsiFileSystemItem)
                }
            }
            .map { it.name }
            .sorted()
}
