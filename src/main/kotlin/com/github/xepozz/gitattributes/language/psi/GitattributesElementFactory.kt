package com.github.xepozz.gitattributes.language.psi

import com.github.xepozz.gitattributes.language.GitattributesFile
import com.github.xepozz.gitattributes.language.GitattributesFileType
import com.intellij.openapi.project.Project
import com.intellij.psi.util.PsiTreeUtil

object GitattributesElementFactory {
//    fun createGitattributesTimeList(project: Project, values: List<String>): GitattributesTimeList {
//        val file = createFile(project, values.joinToString(","))
//
//        return PsiTreeUtil.findChildOfType(file, GitattributesTimeList::class.java) as GitattributesTimeList
//    }
//
//    fun createGitattributesTimeRange(project: Project, first: Int, last: Int): GitattributesTimeRange {
//        val file = createFile(project, "$first-$last")
//
//        return PsiTreeUtil.findChildOfType(file, GitattributesTimeRange::class.java) as GitattributesTimeRange
//    }
//    fun createGitattributesTimeExact(project: Project, value: Int): GitattributesTimeExact {
//        val file = createFile(project, "$value")
//
//        return PsiTreeUtil.findChildOfType(file, GitattributesTimeExact::class.java) as GitattributesTimeExact
//    }

    fun createFile(project: Project, text: String): GitattributesFile {
        val name = "dummy.gitattributes"
        return com.intellij.psi.PsiFileFactory.getInstance(project)
            .createFileFromText(name, GitattributesFileType.INSTANCE, text) as GitattributesFile
    }
}