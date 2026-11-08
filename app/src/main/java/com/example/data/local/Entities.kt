package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room Database Schema storing progress for each of the 5 daily study blocks across 90 days:
 * 1. Vocabulary (Lugʻat & Iboralar - 30 min)
 * 2. Listening & Shadowing (Eshitish & Shadowing - 30 min)
 * 3. Speaking & Anti-Anxiety (Nutq & Hayajonni Yengish - 30 min)
 * 4. Grammar & Patterns (Jonli Gap Qoliplari - 30 min)
 * 5. Test & Review (Kunlik Test & Sinov - 30 min)
 */
@Entity(tableName = "user_day_progress")
data class UserDayProgressEntity(
  @PrimaryKey
  @ColumnInfo(name = "day_number")
  val dayNumber: Int,

  // 1. Vocabulary Block (30 min)
  @ColumnInfo(name = "is_vocabulary_done")
  val isVocabularyCompleted: Boolean = false,

  // 2. Listening & Shadowing Block (30 min)
  @ColumnInfo(name = "is_listening_done")
  val isListeningCompleted: Boolean = false,

  // 3. Speaking & Anti-Anxiety Block (30 min)
  @ColumnInfo(name = "is_speaking_done")
  val isSpeakingCompleted: Boolean = false,

  // 4. Grammar & Patterns Block (30 min)
  @ColumnInfo(name = "is_grammar_done")
  val isGrammarCompleted: Boolean = false,

  // 5. Test & Review Block (30 min)
  @ColumnInfo(name = "is_test_done")
  val isTestCompleted: Boolean = false,

  // Total study time spent on this day (target: 150 minutes)
  @ColumnInfo(name = "minutes_spent")
  val totalMinutesSpent: Int = 0,

  // Daily test score percentage (0..100)
  @ColumnInfo(name = "test_score_percent")
  val testScorePercent: Int = 0,

  // True when all 5 blocks (150 minutes) are completed
  @ColumnInfo(name = "is_day_completed")
  val isDayCompleted: Boolean = false,

  @ColumnInfo(name = "completed_timestamp")
  val completedTimestamp: Long = 0L,

  @ColumnInfo(name = "last_updated")
  val lastUpdated: Long = System.currentTimeMillis(),
) {
  // Backward-compatible and convenience aliases for UI & ViewModel
  val isBlock0Done: Boolean get() = isVocabularyCompleted
  val isBlock1Done: Boolean get() = isListeningCompleted
  val isBlock2Done: Boolean get() = isSpeakingCompleted
  val isBlock3Done: Boolean get() = isGrammarCompleted
  val isBlock4Done: Boolean get() = isTestCompleted
  val minutesSpent: Int get() = totalMinutesSpent
  val quizScore: Int get() = testScorePercent

  val completedBlocksCount: Int
    get() = listOf(
      isVocabularyCompleted,
      isListeningCompleted,
      isSpeakingCompleted,
      isGrammarCompleted,
      isTestCompleted,
    ).count { it }

  fun isBlockDone(blockId: Int): Boolean = when (blockId) {
    0 -> isVocabularyCompleted
    1 -> isListeningCompleted
    2 -> isSpeakingCompleted
    3 -> isGrammarCompleted
    4 -> isTestCompleted
    else -> false
  }
}

@Entity(tableName = "favorite_words")
data class FavoriteWordEntity(
  @PrimaryKey
  @ColumnInfo(name = "english")
  val english: String,

  @ColumnInfo(name = "uzbek")
  val uzbek: String,

  @ColumnInfo(name = "transcription")
  val transcription: String,

  @ColumnInfo(name = "part_of_speech")
  val partOfSpeech: String,

  @ColumnInfo(name = "level_code")
  val levelCode: String,

  @ColumnInfo(name = "added_timestamp")
  val addedTimestamp: Long = System.currentTimeMillis(),
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
  @PrimaryKey
  @ColumnInfo(name = "id")
  val id: Int = 1,

  @ColumnInfo(name = "current_active_day")
  val currentActiveDay: Int = 1,

  @ColumnInfo(name = "streak_days")
  val streakDays: Int = 1,

  @ColumnInfo(name = "total_minutes_learned")
  val totalMinutesLearned: Int = 0,

  @ColumnInfo(name = "confidence_score")
  val confidenceScore: Int = 35, // 0..100

  @ColumnInfo(name = "last_active_date")
  val lastActiveDate: String = "",
)
