package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.RewardBadge
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald

@Composable
fun DailyCompletionCelebrationDialog(
  dayNumber: Int,
  onDismiss: () -> Unit,
  onNextDay: () -> Unit,
) {
  // Pulsing scale animation for celebration badge
  val badgeScale = remember { Animatable(0.7f) }
  val glowAlpha = remember { Animatable(0.3f) }

  LaunchedEffect(Unit) {
    badgeScale.animateTo(
      targetValue = 1.15f,
      animationSpec = infiniteRepeatable(
        animation = tween(800, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse,
      ),
    )
  }

  LaunchedEffect(Unit) {
    glowAlpha.animateTo(
      targetValue = 0.8f,
      animationSpec = infiniteRepeatable(
        animation = tween(1000, easing = FastOutSlowInEasing),
        repeatMode = RepeatMode.Reverse,
      ),
    )
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(28.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("celebration_modal"),
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        // Glowing Star Badge
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(110.dp)
            .scale(badgeScale.value)
            .background(
              Brush.radialGradient(
                listOf(ConfidenceGold.copy(alpha = glowAlpha.value), Color.Transparent)
              ),
              CircleShape,
            ),
        ) {
          Surface(
            shape = CircleShape,
            color = ConfidenceGold.copy(alpha = 0.2f),
            modifier = Modifier.size(80.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = "🌟",
                fontSize = 42.sp,
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Title
        Text(
          text = "TABRIKLAYMIZ! 🎉",
          style = MaterialTheme.typography.headlineMedium,
          fontWeight = FontWeight.ExtraBold,
          color = ConfidenceGold,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "$dayNumber-KUNNING BARCHA 5 TA BLOKI BAJARILDI!",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "Bugungi 150 daqiqalik intensiv mashgʻulot toʻliq yakunlandi. Siz B2 darajasi sari yana bir katta qadam tashladingiz!",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Unlocked Badge Box
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = ConfidenceGold.copy(alpha = 0.12f),
          border = androidx.compose.foundation.BorderStroke(1.5.dp, ConfidenceGold),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Text(text = "🏆", fontSize = 28.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "Yangi Nishon Ochildi!",
                style = MaterialTheme.typography.labelSmall,
                color = ConfidenceGold,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = "Kunlik 150m Qahramoni",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = "+150 min • Intizomli Streak saqlandi",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Action Buttons
        Button(
          onClick = {
            onDismiss()
            if (dayNumber < 90) onNextDay()
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = FluencyEmerald),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("celebration_next_day_btn"),
        ) {
          Text(
            text = if (dayNumber < 90) "Ertangi Kunga Oʻtish (${dayNumber + 1}-kun)" else "90-Kun Yakuni!",
            fontWeight = FontWeight.Bold,
          )
          Spacer(modifier = Modifier.width(6.dp))
          Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
          onClick = onDismiss,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text("Yopish")
        }
      }
    }
  }
}

@Composable
fun RewardsBadgesSection(
  badges: List<RewardBadge>,
  modifier: Modifier = Modifier,
) {
  val unlockedCount = badges.count { it.isUnlocked }

  Column(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.EmojiEvents,
          contentDescription = null,
          tint = ConfidenceGold,
          modifier = Modifier.size(22.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Yutuqlar & Nishonlar (Rewards)",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
      }

      Surface(
        shape = RoundedCornerShape(8.dp),
        color = ConfidenceGold.copy(alpha = 0.15f),
      ) {
        Text(
          text = "$unlockedCount / ${badges.size} Ochildi",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.Bold,
          color = ConfidenceGold,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      items(badges) { badge ->
        BadgeItemCard(badge = badge)
      }
    }
  }
}

@Composable
fun BadgeItemCard(badge: RewardBadge) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (badge.isUnlocked) {
        MaterialTheme.colorScheme.surface
      } else {
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
      }
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (badge.isUnlocked) 2.dp else 0.dp),
    modifier = Modifier
      .width(170.dp)
      .then(
        if (badge.isUnlocked) {
          Modifier.border(1.5.dp, ConfidenceGold.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
        } else {
          Modifier
        }
      ),
  ) {
    Column(
      modifier = Modifier.padding(12.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(54.dp)
          .background(
            if (badge.isUnlocked) ConfidenceGold.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
            CircleShape,
          ),
      ) {
        Text(
          text = badge.emoji,
          fontSize = 28.sp,
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = badge.titleUz,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        maxLines = 2,
      )

      Text(
        text = badge.titleEn,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        maxLines = 1,
      )

      Spacer(modifier = Modifier.height(6.dp))

      Surface(
        shape = RoundedCornerShape(6.dp),
        color = if (badge.isUnlocked) FluencyEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant,
      ) {
        Text(
          text = if (badge.isUnlocked) "✓ Ochildi" else badge.progressText,
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          fontWeight = FontWeight.Bold,
          color = if (badge.isUnlocked) FluencyEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
        )
      }
    }
  }
}
