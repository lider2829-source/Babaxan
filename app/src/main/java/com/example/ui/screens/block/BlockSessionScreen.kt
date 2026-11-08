package com.example.ui.screens.block

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
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
import com.example.data.model.BlockType
import com.example.data.model.DialogueLine
import com.example.data.model.GrammarPatternItem
import com.example.data.model.ListeningDialogue
import com.example.data.model.QuizQuestion
import com.example.data.model.SpeakingDrill
import com.example.data.model.WordItem
import com.example.ui.theme.CalmingTeal
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.viewmodel.BreathingPhase
import com.example.ui.viewmodel.Eng90ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockSessionScreen(
  viewModel: Eng90ViewModel,
  block: BlockType,
  onExit: () -> Unit,
  modifier: Modifier = Modifier,
) {
  BackHandler { onExit() }

  val curriculum by viewModel.curriculum.collectAsState()
  val timerSeconds by viewModel.timerSeconds.collectAsState()
  val isTimerRunning by viewModel.isTimerRunning.collectAsState()
  val favoriteWords by viewModel.favoriteWords.collectAsState()

  val minutes = timerSeconds / 60
  val seconds = timerSeconds % 60
  val timerText = String.format("%02d:%02d", minutes, seconds)

  Scaffold(
    modifier = modifier.fillMaxSize().testTag("block_session_screen"),
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "${block.id + 1}. ${block.nameEn} • ${block.titleUz}",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = "${curriculum.dayNumber}-Kun: ${curriculum.level.titleUz}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onExit, modifier = Modifier.testTag("exit_block_btn")) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Chiqish",
            )
          }
        },
        actions = {
          // Timer Widget in TopAppBar
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(end = 8.dp),
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
              Text(
                text = timerText,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
              )
              IconButton(
                onClick = {
                  if (isTimerRunning) viewModel.pauseBlockTimer() else viewModel.startBlockTimer()
                },
                modifier = Modifier.size(28.dp).testTag("timer_toggle_btn"),
              ) {
                Icon(
                  imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                  contentDescription = "Taymer",
                  tint = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.size(18.dp),
                )
              }
            }
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface,
        ),
      )
    },
    bottomBar = {
      Surface(
        tonalElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Button(
            onClick = { viewModel.completeCurrentBlock() },
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp)
              .testTag("complete_block_btn"),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = FluencyEmerald),
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Blokni Yakunlash (+30 daqiqa)",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    },
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      // Content based on Block Type
      when (block) {
        BlockType.VOCABULARY -> {
          item {
            BlockHeaderCard(
              title = "Kontekstdagi 5 ta Muhim Soʻz",
              desc = "Talaffuzni eshiting, birikmani oʻrganing va gap ichida qoʻllang.",
            )
          }
          items(curriculum.vocabList) { word ->
            val isFav = favoriteWords.any { it.english.equals(word.english, ignoreCase = true) }
            WordItemCard(
              word = word,
              isFavorite = isFav,
              onSpeak = { viewModel.speakText(word.english) },
              onSpeakExample = { viewModel.speakText(word.exampleEn) },
              onToggleFavorite = { viewModel.toggleFavorite(word, isFav) },
            )
          }
        }

        BlockType.LISTENING -> {
          item {
            ListeningBlockSection(
              dialogue = curriculum.listening,
              onSpeakLine = { viewModel.speakText(it) },
              onSpeakAll = {
                val fullText = curriculum.listening.lines.joinToString(". ") { "${it.speaker} says: ${it.textEn}" }
                viewModel.speakText(fullText)
              },
            )
          }
        }

        BlockType.SPEAKING -> {
          item {
            SpeakingBlockSection(
              speaking = curriculum.speaking,
              viewModel = viewModel,
            )
          }
        }

        BlockType.GRAMMAR -> {
          item {
            GrammarBlockSection(
              grammar = curriculum.grammar,
              onSpeakSentence = { viewModel.speakText(it) },
            )
          }
        }

        BlockType.QUIZ -> {
          item {
            QuizBlockSection(
              questions = curriculum.quizQuestions,
              onSubmit = { scorePercent -> viewModel.submitQuiz(scorePercent) },
            )
          }
        }
      }

      item {
        Spacer(modifier = Modifier.height(32.dp))
      }
    }
  }
}

@Composable
private fun BlockHeaderCard(title: String, desc: String) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    ),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = desc,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}

// ----------------- VOCABULARY VIEW -----------------
@Composable
private fun WordItemCard(
  word: WordItem,
  isFavorite: Boolean,
  onSpeak: () -> Unit,
  onSpeakExample: () -> Unit,
  onToggleFavorite: () -> Unit,
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth().testTag("word_card_${word.english}"),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = word.english,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
          )
          Spacer(modifier = Modifier.width(8.dp))
          IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp)) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Talaffuz",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp),
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
          ) {
            Text(
              text = word.partOfSpeech,
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            )
          }
          IconButton(onClick = onToggleFavorite, modifier = Modifier.size(32.dp)) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
              contentDescription = "Saqlash",
              tint = if (isFavorite) ConfidenceGold else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(20.dp),
            )
          }
        }
      }

      Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
          text = word.transcription,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
          text = "•  ${word.uzbek}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.SemiBold,
        )
      }

      if (word.collocation.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = FluencyEmerald.copy(alpha = 0.12f),
        ) {
          Text(
            text = "Birikma: ${word.collocation}",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = FluencyEmerald,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Example sentence container
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxWidth().clickable { onSpeakExample() },
      ) {
        Row(
          modifier = Modifier.padding(10.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp),
          )
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = word.exampleEn,
              style = MaterialTheme.typography.bodyMedium,
              fontWeight = FontWeight.Medium,
            )
            Text(
              text = word.exampleUz,
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }
  }
}

// ----------------- LISTENING & SHADOWING VIEW -----------------
@Composable
private fun ListeningBlockSection(
  dialogue: ListeningDialogue,
  onSpeakLine: (String) -> Unit,
  onSpeakAll: () -> Unit,
) {
  Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = dialogue.titleEn,
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = dialogue.titleUz,
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
          Button(
            onClick = onSpeakAll,
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Hammasi", style = MaterialTheme.typography.labelMedium)
          }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = "Vaziyat: ${dialogue.situationUz}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }

    // Shadowing instruction tip
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = FluencyEmerald.copy(alpha = 0.12f),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        Text("🎙️", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "Shadowing Qoidasi: Har bir jumla audio ijro etilgach, xuddi shu intonatsiya va tezlikda baland ovozda takrorlang!",
          style = MaterialTheme.typography.bodySmall,
          fontWeight = FontWeight.SemiBold,
          color = FluencyEmerald,
        )
      }
    }

    // Dialogue Lines
    dialogue.lines.forEach { line ->
      DialogueBubble(line = line, onSpeak = { onSpeakLine(line.textEn) })
    }
  }
}

@Composable
private fun DialogueBubble(line: DialogueLine, onSpeak: () -> Unit) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.Top,
    ) {
      Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.size(36.dp),
      ) {
        Box(contentAlignment = Alignment.Center) {
          Text(
            text = line.speaker.take(1),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
          )
        }
      }

      Spacer(modifier = Modifier.width(12.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = line.speaker,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
          )
          IconButton(onClick = onSpeak, modifier = Modifier.size(28.dp)) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Ovoz chiqarish",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(18.dp),
            )
          }
        }
        Text(
          text = line.textEn,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = line.textUz,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }
  }
}

// ----------------- SPEAKING & ANTI-ANXIETY VIEW -----------------
@Composable
private fun SpeakingBlockSection(
  speaking: SpeakingDrill,
  viewModel: Eng90ViewModel,
) {
  val breathingPhase by viewModel.breathingPhase.collectAsState()
  val breathingSec by viewModel.breathingSecondsLeft.collectAsState()
  val impromptuSec by viewModel.impromptuSecondsLeft.collectAsState()
  val isImpromptuRunning by viewModel.isImpromptuRunning.collectAsState()

  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
    // 1. 4-7-8 Breathing Card
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = CalmingTeal.copy(alpha = 0.12f)),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(Icons.Default.SelfImprovement, contentDescription = null, tint = CalmingTeal)
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "4-7-8 Tinchlantiruvchi Nafas Mashqi",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = CalmingTeal,
          )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = "Nutq oldidan hayajon va pulsni meʼyorlashtirish uchun 3 marta 4-7-8 siklini bajaring.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (breathingPhase != BreathingPhase.IDLE) {
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = CalmingTeal,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.padding(12.dp),
            ) {
              Text(
                text = breathingPhase.labelUz,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
              )
              Text(
                text = "$breathingSec soniya",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
              )
              Spacer(modifier = Modifier.height(6.dp))
              OutlinedButton(
                onClick = { viewModel.stopBreathingExercise() },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
              ) {
                Text("Toʻxtatish")
              }
            }
          }
        } else {
          Button(
            onClick = { viewModel.startBreathingExercise() },
            colors = ButtonDefaults.buttonColors(containerColor = CalmingTeal),
            modifier = Modifier.fillMaxWidth(),
          ) {
            Text("Nafas Mashqini Boshlash (4-7-8)")
          }
        }
      }
    }

    // 2. Tongue Twister Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "Artikulyatsiya (Tez Aytish)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
          )
          IconButton(onClick = { viewModel.speakText(speaking.tongueTwister) }) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Eshitish",
              tint = MaterialTheme.colorScheme.primary,
            )
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = speaking.tongueTwister,
          style = MaterialTheme.typography.headlineSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = speaking.tongueTwisterUz,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }

    // 3. 60-Second Impromptu Speaking Challenge
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = ConfidenceGold.copy(alpha = 0.12f)),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = "60 Soniya Toʻxtovsiz Gapirish",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = ConfidenceGold,
          )
          Surface(
            shape = CircleShape,
            color = ConfidenceGold,
            modifier = Modifier.padding(4.dp),
          ) {
            Text(
              text = "${impromptuSec}s",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = Color.Black,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "Mavzu: ${speaking.impromptuTopicEn}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = speaking.impromptuTopicUz,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Button(
            onClick = {
              if (isImpromptuRunning) viewModel.stopImpromptuSpeech() else viewModel.startImpromptuSpeech()
            },
            colors = ButtonDefaults.buttonColors(containerColor = ConfidenceGold),
            modifier = Modifier.weight(1f),
          ) {
            Text(
              text = if (isImpromptuRunning) "Toʻxtatish" else "Taymerni Boshlash (60s)",
              color = Color.Black,
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    }

    // 4. Anxiety-Busting Psychological Tip
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.Top) {
        Text("💡", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "Hayajonni Yengish Tavsiyasi:",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
          )
          Spacer(modifier = Modifier.height(2.dp))
          Text(
            text = speaking.anxietyTipUz,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }
      }
    }
  }
}

// ----------------- GRAMMAR & PATTERNS VIEW -----------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GrammarBlockSection(
  grammar: GrammarPatternItem,
  onSpeakSentence: (String) -> Unit,
) {
  var selectedWords by remember { mutableStateOf(listOf<String>()) }
  var isChecked by remember { mutableStateOf(false) }
  var isCorrect by remember { mutableStateOf(false) }

  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
    // Formula Card
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = grammar.patternName,
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = MaterialTheme.colorScheme.primaryContainer,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(
            text = grammar.formula,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.padding(10.dp),
          )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = grammar.explanationUz,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }

    // Sample Sentences
    Text(
      text = "Jonli Misollar (Eshiting va Takrorlang):",
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
    )

    grammar.sampleSentences.forEach { (en, uz) ->
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier.fillMaxWidth().clickable { onSpeakSentence(en) },
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp),
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(text = en, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text(text = uz, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
    }

    // Interactive Sentence Builder (Scramble)
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Interaktiv Mashq: Gapni Toʻgʻri Yigʻing",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Selected Words Box
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = MaterialTheme.colorScheme.surface,
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(10.dp)),
        ) {
          Row(
            modifier = Modifier
              .fillMaxSize()
              .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            if (selectedWords.isEmpty()) {
              Text(
                text = "Pastdagi soʻzlarni ketma-ket bosing...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
              )
            } else {
              Text(
                text = selectedWords.joinToString(" "),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Scrambled Chips
        FlowRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          grammar.scrambledWords.forEach { word ->
            val countInScrambled = grammar.scrambledWords.count { it == word }
            val countInSelected = selectedWords.count { it == word }
            val isEnabled = countInSelected < countInScrambled

            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
              modifier = Modifier.clickable(enabled = isEnabled) {
                selectedWords = selectedWords + word
                isChecked = false
              },
            ) {
              Text(
                text = word,
                style = MaterialTheme.typography.labelLarge,
                color = if (isEnabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedButton(
            onClick = {
              selectedWords = emptyList()
              isChecked = false
            },
            modifier = Modifier.weight(1f),
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("Tozalash")
          }

          Button(
            onClick = {
              isChecked = true
              val result = selectedWords.joinToString(" ").trim()
              isCorrect = result.equals(grammar.correctSentence.trim(), ignoreCase = true)
            },
            enabled = selectedWords.isNotEmpty(),
            modifier = Modifier.weight(1f),
          ) {
            Text("Tekshirish")
          }
        }

        if (isChecked) {
          Spacer(modifier = Modifier.height(8.dp))
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (isCorrect) FluencyEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.errorContainer,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Text(
              text = if (isCorrect) "🎉 Toʻgʻri yigʻildi! Ajoyib!" else "Qayta urinib koʻring: '${grammar.correctSentence}'",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
              color = if (isCorrect) FluencyEmerald else MaterialTheme.colorScheme.onErrorContainer,
              modifier = Modifier.padding(10.dp),
            )
          }
        }
      }
    }
  }
}

// ----------------- QUIZ VIEW -----------------
@Composable
private fun QuizBlockSection(
  questions: List<QuizQuestion>,
  onSubmit: (Int) -> Unit,
) {
  val selectedAnswers = remember { mutableStateMapOf<Int, Int>() }
  var isSubmitted by remember { mutableStateOf(false) }
  var correctCount by remember { mutableIntStateOf(0) }

  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
      ),
      modifier = Modifier.fillMaxWidth(),
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "Kunlik Bilim Sinovi",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
        )
        Text(
          text = "Bugungi 150 daqiqalik dars natijasini sinab, B2 sari intiling.",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    }

    questions.forEachIndexed { qIndex, q ->
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "${qIndex + 1}. ${q.question}",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
          )

          Spacer(modifier = Modifier.height(10.dp))

          q.options.forEachIndexed { optIndex, optionText ->
            val isSelected = selectedAnswers[qIndex] == optIndex
            val isAnswerCorrect = (optIndex == q.correctIndex)

            val backgroundColor = when {
              !isSubmitted && isSelected -> MaterialTheme.colorScheme.primaryContainer
              isSubmitted && isAnswerCorrect -> FluencyEmerald.copy(alpha = 0.2f)
              isSubmitted && isSelected && !isAnswerCorrect -> MaterialTheme.colorScheme.errorContainer
              else -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
            }

            Surface(
              shape = RoundedCornerShape(10.dp),
              color = backgroundColor,
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .clickable(enabled = !isSubmitted) {
                  selectedAnswers[qIndex] = optIndex
                },
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Text(
                  text = "${'A' + optIndex}. $optionText",
                  style = MaterialTheme.typography.bodyMedium,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                )
              }
            }
          }

          if (isSubmitted) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Tushuntirish: ${q.explanationUz}",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }

    if (!isSubmitted) {
      Button(
        onClick = {
          var count = 0
          questions.forEachIndexed { idx, q ->
            if (selectedAnswers[idx] == q.correctIndex) count++
          }
          correctCount = count
          isSubmitted = true
          val percent = if (questions.isNotEmpty()) (count * 100) / questions.size else 100
          onSubmit(percent)
        },
        enabled = selectedAnswers.size == questions.size,
        modifier = Modifier.fillMaxWidth().height(50.dp),
      ) {
        Text("Testni Yakunlash (${selectedAnswers.size}/${questions.size} belgilandi)")
      }
    } else {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = FluencyEmerald.copy(alpha = 0.15f)),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
        ) {
          Text(
            text = "🎉 Natijangiz: $correctCount / ${questions.size} toʻgʻri javob!",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = FluencyEmerald,
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "Ballaringiz saqlandi va B2 darajangiz hisoblandi.",
            style = MaterialTheme.typography.bodyMedium,
          )
        }
      }
    }
  }
}
