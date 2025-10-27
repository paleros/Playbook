package com.peros.playbook.share

/**
 * Jatek megosztasi linkjenek megosztasa a platform megosztasi funkciojan keresztul
 * @param gameId a jatek azonositoja
 */
actual fun shareGameLink(gameId: String?) {
    val documentId = gameId?.substringAfterLast('/')
    println("Share this game link: https://playbook-peros.web.app/index.html?id=$documentId")
    // Vagolapra masolas
    val clipboard = java.awt.Toolkit.getDefaultToolkit().systemClipboard
    val selection = java.awt.datatransfer.StringSelection("https://playbook-peros.web.app/index.html?id=$documentId")
    clipboard.setContents(selection, selection)
    // Ertesites a vagolapra masolasrol
    javax.swing.JOptionPane.showMessageDialog(null, "Game link copied to clipboard!")
}
