package com.github.xepozz.gitattributes.ide

import com.intellij.lang.Commenter

class GitattributesCommenter : Commenter {
    override fun getLineCommentPrefix() = "#"

    override fun getBlockCommentPrefix() = null

    override fun getBlockCommentSuffix() = null

    override fun getCommentedBlockCommentPrefix() = null

    override fun getCommentedBlockCommentSuffix() = null
}