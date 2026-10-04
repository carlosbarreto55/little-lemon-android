package com.example.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.data.AppDatabase
import com.example.littlelemon.data.MenuApi
import com.example.littlelemon.data.UserPreferences
import com.example.littlelemon.ui.theme.LittleLemonTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = UserPreferences(this)
        val dao = AppDatabase.get(this).menuItemDao()

        lifecycleScope.launch {
            if (dao.count() == 0) {
                runCatching { MenuApi.fetchMenu() }
                    .onSuccess { menu -> dao.insertAll(menu.map { it.toRoom() }) }
                    .onFailure { Log.e("LittleLemon", "Could not download menu", it) }
            }
        }

        setContent {
            LittleLemonTheme {
                AppNavigation(rememberNavController(), prefs, dao)
            }
        }
    }
}
