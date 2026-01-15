package com.github.xepozz.gitattributes.ide.editor

import com.github.xepozz.gitattributes.language.AttributesFileType
import com.intellij.ide.BrowserUtil
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotificationProvider
import com.intellij.ui.EditorNotifications
import java.util.function.Function
import javax.swing.JComponent

private const val PROMO_RATE = "gitattributes.promo.rate"

class EditorNotificationProvider : EditorNotificationProvider, DumbAware {
    override fun collectNotificationData(
        project: Project,
        file: VirtualFile
    ): Function<in FileEditor, out JComponent?>? {
        if (file.fileType !is AttributesFileType) return null

        if (PropertiesComponent.getInstance().isTrueValue(PROMO_RATE)) return null

        return Function { editor ->
            val panel = EditorNotificationPanel(editor, EditorNotificationPanel.Status.Promo)
            panel.text = "GitAttributes: Rate plugin on the Marketplace, help it grow"
            panel.createActionLabel("Leave Review") {
                BrowserUtil.browse("https://plugins.jetbrains.com/plugin/26477--gitattributes-support/reviews/new")
                updateProperty(project)
            }
            panel.setCloseAction {
                thisLogger().info("Close action clicked")
                updateProperty(project)
            }
            panel
        }
    }

    private fun updateProperty(project: Project) {
        PropertiesComponent.getInstance().setValue(PROMO_RATE, true)
        EditorNotifications.getInstance(project).updateAllNotifications()
    }
}