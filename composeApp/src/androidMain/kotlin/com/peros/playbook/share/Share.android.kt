package com.peros.playbook.share

import android.content.Intent
import com.peros.playbook.database.appContext
import playbook.composeapp.generated.resources.Res
import playbook.composeapp.generated.resources.check_out_this_game

/**
 * Jatek megosztasi linkjenek megosztasa a platform megosztasi funkciojan keresztul
 * @param gameId a jatek azonositoja
 */
actual fun shareGameLink(gameId: String?) {
    val documentId = gameId?.substringAfterLast('/')
    val context = appContext
    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT,
            "${Res.string.check_out_this_game}: https://playbook-ffca5.web.app/index.html?id=$documentId")
        type = "text/plain"
    }
    val chooser = Intent.createChooser(shareIntent, "Share game via")
    chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(chooser)
}