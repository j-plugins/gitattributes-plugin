package com.github.xepozz.gitattributes.ide

import com.github.xepozz.gitattributes.ide.documentation.AttributesDocumentationProvider
import com.github.xepozz.gitattributes.language.AttributesFileType
import com.github.xepozz.gitattributes.language.psi.AttributesDefinition
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AttributesDocumentationTest : BasePlatformTestCase() {
    fun testDefinitionDocumentationUsesPreviousComment() {
        val file = myFixture.configureByText(
            AttributesFileType.INSTANCE,
            """
                # Attribute documentation
                *.kt text
            """.trimIndent(),
        )
        val definition = PsiTreeUtil.findChildOfType(file, AttributesDefinition::class.java)

        val doc = AttributesDocumentationProvider().generateDoc(definition, definition)

        assertNotNull(doc)
        assertTrue(doc!!, doc.contains("Attribute documentation"))
        assertFalse(doc, doc.contains("# Attribute documentation"))
    }
}
