package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MainScreen
import com.example.ui.theme.EnglishIntensiveTheme
import com.example.ui.viewmodel.Eng90ViewModel

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      EnglishIntensiveTheme {
        val viewModel: Eng90ViewModel = viewModel()
        MainScreen(viewModel = viewModel)
      }
    }
  }
}
