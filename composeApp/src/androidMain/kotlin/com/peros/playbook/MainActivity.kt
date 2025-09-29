package com.peros.playbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.peros.playbook.database.DatabaseDriverFactory
import com.peros.playbook.database.GameRepository
import com.peros.playbook.database.GameUseCases
import com.peros.playbook.theme.AppTheme

/**
 * A fo Activity, ami elindul az alkalmazas indulasakor
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val driver = DatabaseDriverFactory(this).createDriver()
        val repository = GameRepository(driver)
        val gameUseCases = GameUseCases(repository)

        setContent {
            AppTheme {
                App(gameUseCases)
            }
        }
    }
}