package com.example.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BadgeCatalog
import com.example.data.model.CefrLevel
import com.example.ui.components.RewardsBadgesSection
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.viewmodel.Eng90ViewModel
import com.example.ui.viewmodel.MainTab

@Composable
fun ProfileScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  val profile by viewModel.userProfile.collectAsState()
  val allProgress by viewModel.allProgressList.collectAsState()
  val selectedDay by viewModel.selectedDay.collectAsState()

  val totalMinutes = profile?.totalMinutesLearned ?: 0
  val totalHours = String.format("%.1f", totalMinutes / 60.0)
  val completedDays = allProgress.count { it.isDayCompleted }
  val streak = profile?.streakDays ?: 1
  val confidence = profile?.confidenceScore ?: 40
  val currentLevel = CefrLevel.fromDay(selectedDay)

  var showJumpDialog by remember { mutableStateOf(false) }

  val badges = remember(completedDays, totalMinutes, streak, confidence, selectedDay, allProgress) {
    val todayProgress = allProgress.find { it.dayNumber == selectedDay }
    BadgeCatalog.getBadges(
      completedDaysCount = completedDays,
      totalMinutes = totalMinutes,
      streakDays = streak,
      confidenceScore = confidence,
      todayAll5Done = todayProgress?.isDayCompleted == true,
    )
  }

  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("profile_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    // 1. User Avatar & Level Badge
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.size(72.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(38.dp),
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = "90 Kunlik Intensiv Oʻquvchi",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
          )

          Spacer(modifier = Modifier.height(4.dp))

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = FluencyEmerald.copy(alpha = 0.15f),
          ) {
            Text(
              text = "Hozirgi Bosqich: ${currentLevel.titleUz}",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = FluencyEmerald,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            )
          }
        }
      }
    }

    // 2. Metric Grid
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        StatMetricCard(
          icon = Icons.Default.HourglassTop,
          iconTint = MaterialTheme.colorScheme.primary,
          title = "Sarflangan Vaqt",
          value = "$totalHours soat",
          sub = "$totalMinutes daqiqa",
          modifier = Modifier.weight(1f),
        )

        StatMetricCard(
          icon = Icons.Default.LocalFireDepartment,
          iconTint = ConfidenceGold,
          title = "Ketma-ketlik",
          value = "$streak kun",
          sub = "Intizomli streak",
          modifier = Modifier.weight(1f),
        )
      }
    }

    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        StatMetricCard(
          icon = Icons.Default.EmojiEvents,
          iconTint = FluencyEmerald,
          title = "Yakunlangan Kunlar",
          value = "$completedDays / 90",
          sub = "${(completedDays * 100) / 90}% tayyor",
          modifier = Modifier.weight(1f),
        )

        StatMetricCard(
          icon = Icons.Default.Psychology,
          iconTint = Color(0xFF8B5CF6),
          title = "Nutq Ishonchi",
          value = "$confidence%",
          sub = "Hayajonsiz muloqot",
          modifier = Modifier.weight(1f),
        )
      }
    }

    // 3. 90-Day Trajectory Progress
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(
              text = "B2 Sertifikati Sari Yoʻl",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = "$selectedDay / 90-kun",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.primary,
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          LinearProgressIndicator(
            progress = { (selectedDay / 90f).coerceIn(0f, 1f) },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
          )

          Spacer(modifier = Modifier.height(14.dp))

          Button(
            onClick = { showJumpDialog = true },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().testTag("jump_day_btn"),
          ) {
            Text("Boshqa Kunga Oʻtish (1..90)")
          }
        }
      }
    }

    // 4. Rewards & Badges Gallery
    item {
      RewardsBadgesSection(badges = badges)
    }

    // 5. Intensiv Tizim Qoidalari
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "⚡ 150 Daqiqalik Intensiv Dastur Metodikasi:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "1. 30 daqiqa Lugʻat: Soʻzlarni kontekst va birikmalar bilan eslab qolish.\n" +
              "2. 30 daqiqa Eshitish: Audio eshittirish va Shadowing (ohangni takrorlash).\n" +
              "3. 30 daqiqa Nutq: 4-7-8 nafas mashqi orqali hayajonni bosish va 60s ekspromt nutq.\n" +
              "4. 30 daqiqa Grammatika: Qoidalar emas, jonli gap qoliplari va tartiblash.\n" +
              "5. 30 daqiqa Test: Kunlik bilimlarni mustahkamlash.",
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 20.sp,
          )
        }
      }
    }

    // 6. Creator Credit Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        ),
        modifier = Modifier.fillMaxWidth().testTag("creator_profile_card"),
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "Created by B. Sherjonov",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = "English 90-Day Intensive (0 to B2 Fluency Program)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }

  // Jump to Day Dialog
  if (showJumpDialog) {
    AlertDialog(
      onDismissRequest = { showJumpDialog = false },
      title = { Text("Qaysi kunga oʻtmoqchisiz?") },
      text = {
        Column {
          Text("90 kun ichidan istalgan kunni tanlang:")
          Spacer(modifier = Modifier.height(12.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            listOf(1, 25, 50, 75, 90).forEach { targetDay ->
              OutlinedButton(
                onClick = {
                  viewModel.setDayAsCurrent(targetDay)
                  showJumpDialog = false
                  viewModel.setTab(MainTab.TODAY)
                },
                modifier = Modifier.weight(1f),
              ) {
                Text("$targetDay")
              }
            }
          }
        }
      },
      confirmButton = {
        TextButton(onClick = { showJumpDialog = false }) {
          Text("Bekor qilish")
        }
      },
    )
  }
}

@Composable
private fun StatMetricCard(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  iconTint: Color,
  title: String,
  value: String,
  sub: String,
  modifier: Modifier = Modifier,
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier,
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Surface(
        shape = CircleShape,
        color = iconTint.copy(alpha = 0.15f),
        modifier = Modifier.size(36.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = sub,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
      )
    }
  }
}
