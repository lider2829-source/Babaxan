package com.example.ui.screens.vocab

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.CefrLevel
import com.example.data.model.WordItem
import com.example.data.repository.VocabularyBank
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.viewmodel.Eng90ViewModel

@Composable
fun VocabVaultScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  var searchQuery by remember { mutableStateOf("") }
  var selectedFilter by remember { mutableStateOf("ALL") }

  val favoriteEntities by viewModel.favoriteWords.collectAsState()

  val allWords = VocabularyBank.allWords

  val filteredWords = remember(searchQuery, selectedFilter, favoriteEntities) {
    allWords.filter { word ->
      val matchesSearch = if (searchQuery.isBlank()) true else {
        word.english.contains(searchQuery, ignoreCase = true) ||
          word.uzbek.contains(searchQuery, ignoreCase = true) ||
          word.collocation.contains(searchQuery, ignoreCase = true)
      }

      val matchesCategory = when (selectedFilter) {
        "FAV" -> favoriteEntities.any { it.english.equals(word.english, ignoreCase = true) }
        "A1" -> word.level == CefrLevel.A1
        "A2" -> word.level == CefrLevel.A2
        "B1" -> word.level == CefrLevel.B1
        "B2" -> word.level == CefrLevel.B2
        else -> true
      }

      matchesSearch && matchesCategory
    }
  }

  LazyColumn(
    modifier = modifier.fillMaxSize().testTag("vocab_vault_screen"),
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp),
  ) {
    // 1. Search Bar
    item {
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier.fillMaxWidth().testTag("vocab_search_input"),
        placeholder = { Text("Soʻz, tarjima yoki iborani qidiring...") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }) {
              Icon(Icons.Default.Clear, contentDescription = "Tozalash")
            }
          }
        },
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
      )
    }

    // 2. Filter Chips
    item {
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth(),
      ) {
        item {
          FilterChip(
            selected = (selectedFilter == "ALL"),
            onClick = { selectedFilter = "ALL" },
            label = { Text("Barchasi (${allWords.size})") },
          )
        }
        item {
          FilterChip(
            selected = (selectedFilter == "FAV"),
            onClick = { selectedFilter = "FAV" },
            label = { Text("⭐ Sevimlilar (${favoriteEntities.size})") },
          )
        }
        listOf("A1", "A2", "B1", "B2").forEach { lvl ->
          item {
            FilterChip(
              selected = (selectedFilter == lvl),
              onClick = { selectedFilter = lvl },
              label = { Text(lvl) },
            )
          }
        }
      }
    }

    // 3. Results count
    item {
      Text(
        text = "${filteredWords.size} ta soʻz topildi",
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }

    // 4. Word List
    if (filteredWords.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
          ),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(text = "🔍", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = "Hech qanday soʻz topilmadi",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = "Boshqa soʻz qidiring yoki filterni oʻzgartiring.",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    } else {
      items(filteredWords) { word ->
        val isFav = favoriteEntities.any { it.english.equals(word.english, ignoreCase = true) }
        VaultWordCard(
          word = word,
          isFavorite = isFav,
          onSpeak = { viewModel.speakText(word.english) },
          onSpeakExample = { viewModel.speakText(word.exampleEn) },
          onToggleFav = { viewModel.toggleFavorite(word, isFav) },
        )
      }
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun VaultWordCard(
  word: WordItem,
  isFavorite: Boolean,
  onSpeak: () -> Unit,
  onSpeakExample: () -> Unit,
  onToggleFav: () -> Unit,
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth().testTag("vault_card_${word.english}"),
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = word.english,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
          )
          Spacer(modifier = Modifier.width(6.dp))
          IconButton(onClick = onSpeak, modifier = Modifier.size(32.dp)) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Ovoz chiqarish",
              tint = MaterialTheme.colorScheme.primary,
              modifier = Modifier.size(20.dp),
            )
          }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            shape = RoundedCornerShape(6.dp),
            color = MaterialTheme.colorScheme.primaryContainer,
          ) {
            Text(
              text = word.level.code,
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onPrimaryContainer,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            )
          }
          Spacer(modifier = Modifier.width(4.dp))
          IconButton(onClick = onToggleFav, modifier = Modifier.size(32.dp)) {
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
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
          text = "• ${word.uzbek}",
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.SemiBold,
        )
      }

      if (word.collocation.isNotEmpty()) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = FluencyEmerald.copy(alpha = 0.12f),
        ) {
          Text(
            text = "Birikma: ${word.collocation}",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = FluencyEmerald,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
        modifier = Modifier.fillMaxWidth().clickable { onSpeakExample() },
      ) {
        Column(modifier = Modifier.padding(8.dp)) {
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
