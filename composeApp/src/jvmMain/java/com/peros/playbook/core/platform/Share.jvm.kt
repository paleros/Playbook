package com.peros.playbook.core.platform

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JOptionPane

/**
 * Jatek megosztasi linkjenek megosztasa a platform megosztasi funkciojan keresztul
 * @param gameId a jatek azonositoja
 */
actual fun shareGameLink(gameId: String?) {
    val documentId = gameId?.substringAfterLast('/')
    println("Share this game link: https://playbook-ffca5.web.app/index.html?id=$documentId")
    // Vagolapra masolas
    val clipboard = Toolkit.getDefaultToolkit().systemClipboard
    val selection = StringSelection("https://playbook-ffca5.web.app/index.html?id=$documentId")
    clipboard.setContents(selection, selection)
    // Ertesites a vagolapra masolasrol
    JOptionPane.showMessageDialog(null, "Game link copied to clipboard!")
}
