package com.example.ui.screens.roadmap

import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.data.model.CefrLevel
import com.example.data.repository.CurriculumDataSource
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.viewmodel.Eng90ViewModel
import com.example.ui.viewmodel.MainTab

@Composable
fun RoadmapScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  val selectedDay by viewModel.selectedDay.collectAsState()
  val allProgress by viewModel.allProgressList.collectAsState()
  val profile by viewModel.userProfile.collectAsState()

  var selectedFilter by remember { mutableStateOf<CefrLevel?>(null) }

  val completedDaysCount = allProgress.count { it.isDayCompleted }
  val totalDaysProgress = completedDaysCount / 90f

  val days = remember(selectedFilter) {
    if (selectedFilter != null) {
      (selectedFilter!!.dayStart..selectedFilter!!.dayEnd).toList()
    } else {
      (1..90).toList()
    }
  }

  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("roadmap_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    // 1. Overall Trajectory Summary
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Column {
              Text(
                text = "90 Kunlik Marshrut Xaritasi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = "0 dan B2 gacha: 4 ta asosiy CEFR bosqichi",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = MaterialTheme.colorScheme.primaryContainer,
            ) {
              Text(
                text = "$completedDaysCount / 90 Kun",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
              )
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          LinearProgressIndicator(
            progress = { totalDaysProgress },
            modifier = Modifier
              .fillMaxWidth()
              .height(8.dp)
              .clip(RoundedCornerShape(4.dp)),
            color = FluencyEmerald,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
          )
        }
      }
    }

    // 2. CEFR Level Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        item {
          FilterChip(
            selected = (selectedFilter == null),
            onClick = { selectedFilter = null },
            label = { Text("Barchasi (1-90)") },
          )
        }
        items(CefrLevel.entries) { level ->
          FilterChip(
            selected = (selectedFilter == level),
            onClick = { selectedFilter = level },
            label = { Text("${level.code} (${level.dayStart}-${level.dayEnd})") },
          )
        }
      }
    }

    // 3. Days list
    items(days) { dayNum ->
      val level = CefrLevel.fromDay(dayNum)
      val dayData = remember(dayNum) { CurriculumDataSource.getCurriculumForDay(dayNum) }
      val progress = allProgress.find { it.dayNumber == dayNum }
      val isCompleted = progress?.isDayCompleted == true
      val isCurrent = (dayNum == selectedDay)
      val minutes = progress?.minutesSpent ?: 0

      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (isCurrent) {
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
          } else {
            MaterialTheme.colorScheme.surface
          }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable {
            viewModel.selectDay(dayNum)
            viewModel.setTab(MainTab.TODAY)
          }
          .testTag("roadmap_day_$dayNum"),
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          // Day number badge
          Surface(
            shape = CircleShape,
            color = when {
              isCompleted -> FluencyEmerald
              isCurrent -> MaterialTheme.colorScheme.primary
              else -> MaterialTheme.colorScheme.surfaceVariant
            },
            modifier = Modifier.size(44.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              if (isCompleted) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(22.dp),
                )
              } else {
                Text(
                  text = "$dayNum",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = if (isCurrent) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                )
              }
            }
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = dayData.titleUz,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
              )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
              Surface(
                shape = RoundedCornerShape(6.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
              ) {
                Text(
                  text = level.code,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = if (isCompleted) "Bajarildi (150m)" else "$minutes/150 daqiqa",
                style = MaterialTheme.typography.bodySmall,
                color = if (isCompleted) FluencyEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }

          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Oʻtish",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp),
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
