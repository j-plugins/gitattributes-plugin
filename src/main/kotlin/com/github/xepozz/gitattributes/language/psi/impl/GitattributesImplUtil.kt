//package com.github.xepozz.gitattributes.language.psi.impl
//
//import com.github.xepozz.gitattributes.language.psi.AttributesTimeRange
//
//object GitattributesImplUtil {
//    @JvmStatic
//    fun getFirst(element: GitattributesTimeRange) = element.node.firstChildNode.text.toIntOrNull() ?: 0
//
//    @JvmStatic
//    fun getSecond(element: GitattributesTimeRange): Int = element.node.lastChildNode.text.toIntOrNull() ?: 0
//
//    @JvmStatic
//    fun getIntRange(element: GitattributesTimeRange): IntRange = IntRange(element.first, element.second)
//}
