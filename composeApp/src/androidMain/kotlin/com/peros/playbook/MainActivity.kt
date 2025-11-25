package com.peros.playbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.peros.playbook.features.game.data.DatabaseDriverFactory
import com.peros.playbook.features.game.data.local.GameLocalRepository
import com.peros.playbook.features.game.data.remote.GameRemoteRepository
import com.peros.playbook.features.game.domain.GameUseCases
import com.peros.playbook.core.platform.initNetworkUtils
import com.peros.playbook.core.ui.theme.AppTheme

/**
 * A fo Activity, ami elindul az alkalmazas indulasakor
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                App {
                    val driver = DatabaseDriverFactory(this).createDriver()
                    val repository = GameLocalRepository(driver)
                    val remoteRepository = GameRemoteRepository()
                    initNetworkUtils(this)
                    GameUseCases(repository, remoteRepository)
                }
            }
        }
    }
}