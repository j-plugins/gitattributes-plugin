package com.github.xepozz.gitattributes

import com.github.xepozz.gitattributes.language.AttributesFileType
import com.github.xepozz.gitattributes.language.parser.AttributesLexerAdapter
import com.github.xepozz.gitattributes.language.psi.AttributesDefinition
import com.github.xepozz.gitattributes.language.psi.AttributesTypes
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.BasePlatformTestCase

class AttributesParsingTest : BasePlatformTestCase() {
    fun testValidGitAttributeEdgeCasesHaveNoParserErrors() {
        val file = myFixture.configureByText(
            AttributesFileType.INSTANCE,
            """
                foo.txt custom=-bar
                foo.txt empty=
                foo.txt empty= text
                "dir with space/a.txt" text
            """.trimIndent(),
        )

        assertNoPsiErrors(file)

        val definitions = PsiTreeUtil.findChildrenOfType(file, AttributesDefinition::class.java).toList()
        assertEquals(4, definitions.size)
        val customAttribute = definitions[0].attributeList.attributeList.single()
        assertEquals("custom", customAttribute.attributeName.text)
        assertEquals("-bar", customAttribute.attributeValues?.text)

        val emptyAttribute = definitions[1].attributeList.attributeList.single()
        assertEquals("empty", emptyAttribute.attributeName.text)
        assertNull(emptyAttribute.attributeValues)

        val separatedAttributes = definitions[2].attributeList.attributeList
        assertEquals("empty", separatedAttributes[0].attributeName.text)
        assertNull(separatedAttributes[0].attributeValues)
        assertEquals("text", separatedAttributes[1].attributeName.text)

        assertEquals("\"dir with space/a.txt\"", definitions[3].pattern.text)
    }

    fun testEscapedNewlineIsStillALineBreak() {
        val lexer = AttributesLexerAdapter()
        lexer.start("foo\\\nbar custom\n")

        assertEquals(AttributesTypes.TEXT, lexer.tokenType)
        assertEquals("foo\\", lexer.tokenText)

        lexer.advance()
        assertEquals(AttributesTypes.EOL, lexer.tokenType)
        assertEquals("\n", lexer.tokenText)
    }

    fun testLexerStateAfterEqualsSurvivesRestart() {
        val lexer = AttributesLexerAdapter()
        lexer.start("foo.txt custom=-bar")
        while (lexer.tokenType != AttributesTypes.EQUALS_SIGN) {
            lexer.advance()
        }
        lexer.advance()
        assertEquals(AttributesTypes.TEXT, lexer.tokenType)
        val valueState = lexer.state

        val restartedLexer = AttributesLexerAdapter()
        restartedLexer.start("-bar", 0, "-bar".length, valueState)

        assertEquals(AttributesTypes.TEXT, restartedLexer.tokenType)
        assertEquals("-bar", restartedLexer.tokenText)
    }

    private fun assertNoPsiErrors(file: com.intellij.psi.PsiFile) {
        val errors = PsiTreeUtil.findChildrenOfType(file, PsiErrorElement::class.java)
        assertTrue(
            errors.joinToString("\n") { "${it.errorDescription}: `${it.text}`" },
            errors.isEmpty(),
        )
    }
}
