package com.example.ui.screens.speaking

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ui.theme.CalmingTeal
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.viewmodel.BreathingPhase
import com.example.ui.viewmodel.Eng90ViewModel

@Composable
fun SpeakingLabScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  val breathingPhase by viewModel.breathingPhase.collectAsState()
  val breathingSecondsLeft by viewModel.breathingSecondsLeft.collectAsState()
  val impromptuSecondsLeft by viewModel.impromptuSecondsLeft.collectAsState()
  val isImpromptuRunning by viewModel.isImpromptuRunning.collectAsState()
  val userProfile by viewModel.userProfile.collectAsState()

  // Animated scale for breathing circle
  val circleScale = remember { Animatable(1f) }

  LaunchedEffect(breathingPhase) {
    when (breathingPhase) {
      BreathingPhase.INHALE -> {
        circleScale.animateTo(1.4f, animationSpec = tween(4000, easing = FastOutSlowInEasing))
      }
      BreathingPhase.HOLD -> {
        // keep scale
      }
      BreathingPhase.EXHALE -> {
        circleScale.animateTo(1f, animationSpec = tween(8000, easing = FastOutSlowInEasing))
      }
      BreathingPhase.IDLE -> {
        circleScale.animateTo(1f, animationSpec = tween(500))
      }
    }
  }

  val randomPrompts = listOf(
    "Describe your most memorable life experience in detail." to "Hayotingizdagi eng unutilmas voqeani batafsil tasvirlang.",
    "If you could have dinner with anyone in history, who would it be?" to "Tarixdagi istalgan inson bilan tushlik qilish imkoni boʻlsa, kimni tanlardingiz?",
    "Why is learning English important for your future career?" to "Ingliz tilini oʻrganish kelajakdagi faoliyatingiz uchun nima uchun muhim?",
    "What are three habits that make a person truly successful?" to "Insonni chinakam muvaffaqiyatli qiluvchi uchta odat qaysi?",
    "Describe your country Uzbekistan to a foreign tourist." to "Chet ellik sayyohga Oʻzbekistonni ingliz tilida tasvirlab bering.",
  )
  var currentPromptIndex by remember { mutableIntStateOf(0) }
  val activePrompt = randomPrompts[currentPromptIndex]

  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("speaking_lab_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    // 1. Header Banner
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Row(modifier = Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(52.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = Icons.Default.RecordVoiceOver,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(28.dp),
              )
            }
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column {
            Text(
              text = "Nutq & Hayajonni Yengish",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = "Xavotirni bosish, artikulyatsiyani ochish va ravon gapirish laboratoriyasi.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
            )
          }
        }
      }
    }

    // 2. 4-7-8 Breathing Visualizer Card
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().testTag("breathing_card"),
      ) {
        Column(
          modifier = Modifier.padding(20.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(Icons.Default.SelfImprovement, contentDescription = null, tint = CalmingTeal)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "4-7-8 Tinchlantiruvchi Nafas Tizimi",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "Parasimpatik asab tizimini uygʻotib, yurak urishini normallashtiradi.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )

          Spacer(modifier = Modifier.height(24.dp))

          // Animated Visual Breathing Circle
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(160.dp)
              .scale(circleScale.value)
              .background(
                color = when (breathingPhase) {
                  BreathingPhase.INHALE -> CalmingTeal.copy(alpha = 0.35f)
                  BreathingPhase.HOLD -> ConfidenceGold.copy(alpha = 0.35f)
                  BreathingPhase.EXHALE -> FluencyEmerald.copy(alpha = 0.35f)
                  BreathingPhase.IDLE -> MaterialTheme.colorScheme.surfaceVariant
                },
                shape = CircleShape,
              ),
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(110.dp)
                .background(
                  color = when (breathingPhase) {
                    BreathingPhase.INHALE -> CalmingTeal
                    BreathingPhase.HOLD -> ConfidenceGold
                    BreathingPhase.EXHALE -> FluencyEmerald
                    BreathingPhase.IDLE -> MaterialTheme.colorScheme.primary
                  },
                  shape = CircleShape,
                ),
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (breathingPhase == BreathingPhase.IDLE) {
                  Text(
                    text = "Boshlash",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                  )
                } else {
                  Text(
                    text = "$breathingSecondsLeft",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                  )
                  Text(
                    text = when (breathingPhase) {
                      BreathingPhase.INHALE -> "Oling"
                      BreathingPhase.HOLD -> "Ushlang"
                      BreathingPhase.EXHALE -> "Chiqaring"
                      else -> ""
                    },
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(20.dp))

          Text(
            text = breathingPhase.labelUz,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
          )

          Spacer(modifier = Modifier.height(14.dp))

          if (breathingPhase == BreathingPhase.IDLE) {
            Button(
              onClick = { viewModel.startBreathingExercise() },
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(containerColor = CalmingTeal),
              modifier = Modifier.fillMaxWidth().testTag("start_breathing_btn"),
            ) {
              Text("Nafas Mashqini Boshlash (3 sikl)")
            }
          } else {
            OutlinedButton(
              onClick = { viewModel.stopBreathingExercise() },
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier.fillMaxWidth(),
            ) {
              Text("Toʻxtatish")
            }
          }
        }
      }
    }

    // 3. 60-Second Impromptu Speech Card
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth().testTag("impromptu_card"),
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Psychology, contentDescription = null, tint = ConfidenceGold)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "60-Soniya Ekspromt Nutq",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
              )
            }

            Surface(
              shape = CircleShape,
              color = if (isImpromptuRunning) ConfidenceGold else MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.padding(2.dp),
            ) {
              Text(
                text = "${impromptuSecondsLeft}s",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isImpromptuRunning) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth(),
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Text(
                text = activePrompt.first,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = activePrompt.second,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
              onClick = {
                currentPromptIndex = (currentPromptIndex + 1) % randomPrompts.size
              },
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f),
            ) {
              Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("Boshqa Mavzu")
            }

            Button(
              onClick = {
                if (isImpromptuRunning) viewModel.stopImpromptuSpeech() else viewModel.startImpromptuSpeech()
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isImpromptuRunning) MaterialTheme.colorScheme.error else ConfidenceGold
              ),
              modifier = Modifier.weight(1f).testTag("impromptu_toggle_btn"),
            ) {
              Text(
                text = if (isImpromptuRunning) "Toʻxtatish" else "Boshlash (60s)",
                color = if (isImpromptuRunning) Color.White else Color.Black,
                fontWeight = FontWeight.Bold,
              )
            }
          }
        }
      }
    }

    // 4. Tongue Twisters for Articulation
    item {
      Text(
        text = "Artikulyatsiya va Tilni Ravonlashtirish",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
      )
    }

    val tongueTwisters = listOf(
      "She sells seashells on the seashore." to "S va SH tovushlari artikulyatsiyasi.",
      "Peter Piper picked a peck of pickled peppers." to "P va K tovushlari bilan lab mashqi.",
      "Red lorry, yellow lorry, red lorry, yellow lorry." to "R va L tovushlarini ravonlashtirish.",
      "The thirty-three thieves thought that they thrilled the throne." to "TH tovushining ravon chiqishi.",
    )

    items(tongueTwisters) { (twister, desc) ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = twister,
              style = MaterialTheme.typography.bodyLarge,
              fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
              text = desc,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
          IconButton(onClick = { viewModel.speakText(twister) }) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Eshitish",
              tint = MaterialTheme.colorScheme.primary,
            )
          }
        }
      }
    }

    // 5. Psychological Confidence Tracker
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth().testTag("confidence_tracker_card"),
      ) {
        Column(modifier = Modifier.padding(18.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(Icons.Default.Shield, contentDescription = null, tint = FluencyEmerald)
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Oʻzingizga Ishonch Darajasi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
              )
            }
            Text(
              text = "${userProfile?.confidenceScore ?: 40}%",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = FluencyEmerald,
            )
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Bugungi mashgʻulotlardan soʻng gapirishga ishonchingiz qanday? Koʻrsatkichni belgilang:",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )

          Slider(
            value = (userProfile?.confidenceScore ?: 40).toFloat(),
            onValueChange = { viewModel.setConfidenceScore(it.toInt()) },
            valueRange = 0f..100f,
            steps = 9,
            modifier = Modifier.fillMaxWidth(),
          )
        }
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
