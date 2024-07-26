package com.ndc.neostore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ndc.neostore.ui.navigation.SetupNavHost
import com.ndc.neostore.ui.theme.NeoStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navHostController = rememberNavController()
            NeoStoreTheme {
                SetupNavHost(navHostController = navHostController)
            }
        }
    }
}