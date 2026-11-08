package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RecordVoiceOver
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.example.ui.screens.block.BlockSessionScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.roadmap.RoadmapScreen
import com.example.ui.screens.speaking.SpeakingLabScreen
import com.example.ui.screens.today.TodayScreen
import com.example.ui.screens.vocab.VocabVaultScreen
import com.example.ui.viewmodel.Eng90ViewModel
import com.example.ui.viewmodel.MainTab

data class NavTabItem(
  val tab: MainTab,
  val labelUz: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String,
)

@Composable
fun MainScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  val currentTab by viewModel.currentTab.collectAsState()
  val activeBlock by viewModel.activeSessionBlock.collectAsState()

  // If a 30-min block session is active, display it full-screen
  if (activeBlock != null) {
    BlockSessionScreen(
      viewModel = viewModel,
      block = activeBlock!!,
      onExit = { viewModel.exitBlockSession() },
    )
    return
  }

  val navItems = listOf(
    NavTabItem(
      tab = MainTab.TODAY,
      labelUz = "Bugun",
      selectedIcon = Icons.Filled.DateRange,
      unselectedIcon = Icons.Outlined.DateRange,
      testTag = "nav_today",
    ),
    NavTabItem(
      tab = MainTab.ROADMAP,
      labelUz = "90 Kun",
      selectedIcon = Icons.Filled.Map,
      unselectedIcon = Icons.Outlined.Map,
      testTag = "nav_roadmap",
    ),
    NavTabItem(
      tab = MainTab.SPEAKING_LAB,
      labelUz = "Nutq Lab",
      selectedIcon = Icons.Filled.RecordVoiceOver,
      unselectedIcon = Icons.Outlined.RecordVoiceOver,
      testTag = "nav_speaking_lab",
    ),
    NavTabItem(
      tab = MainTab.VOCAB_VAULT,
      labelUz = "Lugʻat",
      selectedIcon = Icons.AutoMirrored.Filled.MenuBook,
      unselectedIcon = Icons.AutoMirrored.Outlined.MenuBook,
      testTag = "nav_vocab",
    ),
    NavTabItem(
      tab = MainTab.PROFILE,
      labelUz = "Profil",
      selectedIcon = Icons.Filled.Person,
      unselectedIcon = Icons.Outlined.Person,
      testTag = "nav_profile",
    ),
  )

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .windowInsetsPadding(WindowInsets.safeDrawing),
    bottomBar = {
      NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = Modifier.testTag("bottom_nav_bar"),
      ) {
        navItems.forEach { item ->
          val isSelected = (currentTab == item.tab)
          NavigationBarItem(
            selected = isSelected,
            onClick = { viewModel.setTab(item.tab) },
            icon = {
              Icon(
                imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                contentDescription = item.labelUz,
              )
            },
            label = {
              Text(
                text = item.labelUz,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              )
            },
            colors = NavigationBarItemDefaults.colors(
              selectedIconColor = MaterialTheme.colorScheme.primary,
              selectedTextColor = MaterialTheme.colorScheme.primary,
              indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = Modifier.testTag(item.testTag),
          )
        }
      }
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
    ) {
      AnimatedContent(
        targetState = currentTab,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "tab_transition",
      ) { tab ->
        when (tab) {
          MainTab.TODAY -> TodayScreen(viewModel = viewModel)
          MainTab.ROADMAP -> RoadmapScreen(viewModel = viewModel)
          MainTab.SPEAKING_LAB -> SpeakingLabScreen(viewModel = viewModel)
          MainTab.VOCAB_VAULT -> VocabVaultScreen(viewModel = viewModel)
          MainTab.PROFILE -> ProfileScreen(viewModel = viewModel)
        }
      }
    }
  }
}
