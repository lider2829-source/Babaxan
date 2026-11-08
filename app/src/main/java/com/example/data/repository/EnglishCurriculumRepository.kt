package com.example.data.repository

import com.example.data.local.FavoriteWordEntity
import com.example.data.local.ProgressDao
import com.example.data.local.UserDayProgressEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.DayCurriculum
import com.example.data.model.WordItem
import kotlinx.coroutines.flow.Flow

class EnglishCurriculumRepository(
  private val dao: ProgressDao,
) {

  fun getCurriculumForDay(day: Int): DayCurriculum {
    val clampedDay = day.coerceIn(1, 90)
    return CurriculumDataSource.getCurriculumForDay(clampedDay)
  }

  fun getDayProgress(day: Int): Flow<UserDayProgressEntity?> {
    return dao.getProgressForDay(day)
  }

  fun getAllDayProgress(): Flow<List<UserDayProgressEntity>> {
    return dao.getAllDayProgress()
  }

  fun getUserProfile(): Flow<UserProfileEntity?> {
    return dao.getUserProfile()
  }

  suspend fun ensureProfileExists() {
    val profile = dao.getUserProfileSync()
    if (profile == null) {
      dao.saveUserProfile(
        UserProfileEntity(
          id = 1,
          currentActiveDay = 1,
          streakDays = 1,
          totalMinutesLearned = 0,
          confidenceScore = 30,
        )
      )
    }
  }

  suspend fun markBlockCompleted(day: Int, blockId: Int, durationMinutes: Int = 30): Boolean {
    val current = dao.getProgressForDaySync(day) ?: UserDayProgressEntity(dayNumber = day)
    val b0 = if (blockId == 0) true else current.isVocabularyCompleted
    val b1 = if (blockId == 1) true else current.isListeningCompleted
    val b2 = if (blockId == 2) true else current.isSpeakingCompleted
    val b3 = if (blockId == 3) true else current.isGrammarCompleted
    val b4 = if (blockId == 4) true else current.isTestCompleted
    val allDone = b0 && b1 && b2 && b3 && b4

    val updatedMinutes = current.totalMinutesSpent + durationMinutes
    val updated = current.copy(
      isVocabularyCompleted = b0,
      isListeningCompleted = b1,
      isSpeakingCompleted = b2,
      isGrammarCompleted = b3,
      isTestCompleted = b4,
      totalMinutesSpent = updatedMinutes,
      isDayCompleted = allDone,
      completedTimestamp = if (allDone && current.completedTimestamp == 0L) System.currentTimeMillis() else current.completedTimestamp,
      lastUpdated = System.currentTimeMillis(),
    )
    dao.insertOrUpdateDayProgress(updated)

    // Update user profile minutes & streak
    val profile = dao.getUserProfileSync() ?: UserProfileEntity()
    val newTotalMinutes = profile.totalMinutesLearned + durationMinutes
    val newConfidence = (profile.confidenceScore + 1).coerceAtMost(100)
    dao.saveUserProfile(
      profile.copy(
        totalMinutesLearned = newTotalMinutes,
        confidenceScore = newConfidence,
      )
    )
    return allDone
  }

  suspend fun toggleBlockStatus(day: Int, blockId: Int): Boolean {
    val current = dao.getProgressForDaySync(day) ?: UserDayProgressEntity(dayNumber = day)
    val b0 = if (blockId == 0) !current.isVocabularyCompleted else current.isVocabularyCompleted
    val b1 = if (blockId == 1) !current.isListeningCompleted else current.isListeningCompleted
    val b2 = if (blockId == 2) !current.isSpeakingCompleted else current.isSpeakingCompleted
    val b3 = if (blockId == 3) !current.isGrammarCompleted else current.isGrammarCompleted
    val b4 = if (blockId == 4) !current.isTestCompleted else current.isTestCompleted
    val allDone = b0 && b1 && b2 && b3 && b4

    val completedCount = listOf(b0, b1, b2, b3, b4).count { it }
    val newMinutes = completedCount * 30

    val updated = current.copy(
      isVocabularyCompleted = b0,
      isListeningCompleted = b1,
      isSpeakingCompleted = b2,
      isGrammarCompleted = b3,
      isTestCompleted = b4,
      totalMinutesSpent = newMinutes,
      isDayCompleted = allDone,
      completedTimestamp = if (allDone && current.completedTimestamp == 0L) System.currentTimeMillis() else if (!allDone) 0L else current.completedTimestamp,
      lastUpdated = System.currentTimeMillis(),
    )
    dao.insertOrUpdateDayProgress(updated)

    val profile = dao.getUserProfileSync() ?: UserProfileEntity()
    val allDays = dao.getAllDayProgressSync()
    val totalMins = allDays.sumOf { it.totalMinutesSpent }
    dao.saveUserProfile(
      profile.copy(
        totalMinutesLearned = totalMins,
      )
    )
    return allDone
  }

  suspend fun submitQuizScore(day: Int, scorePercent: Int): Boolean {
    val current = dao.getProgressForDaySync(day) ?: UserDayProgressEntity(dayNumber = day)
    val isAllDone = current.isVocabularyCompleted && current.isListeningCompleted && current.isSpeakingCompleted && current.isGrammarCompleted
    val updated = current.copy(
      isTestCompleted = true,
      testScorePercent = scorePercent,
      totalMinutesSpent = current.totalMinutesSpent + 30,
      isDayCompleted = isAllDone,
      completedTimestamp = if (isAllDone && current.completedTimestamp == 0L) System.currentTimeMillis() else current.completedTimestamp,
      lastUpdated = System.currentTimeMillis(),
    )
    dao.insertOrUpdateDayProgress(updated)

    val profile = dao.getUserProfileSync() ?: UserProfileEntity()
    val newConfidence = (profile.confidenceScore + 2).coerceAtMost(100)
    dao.saveUserProfile(
      profile.copy(
        confidenceScore = newConfidence,
        totalMinutesLearned = profile.totalMinutesLearned + 30,
      )
    )
    return isAllDone
  }

  suspend fun setCurrentActiveDay(day: Int) {
    val profile = dao.getUserProfileSync() ?: UserProfileEntity()
    dao.saveUserProfile(profile.copy(currentActiveDay = day.coerceIn(1, 90)))
  }

  suspend fun updateConfidenceScore(score: Int) {
    val profile = dao.getUserProfileSync() ?: UserProfileEntity()
    dao.saveUserProfile(profile.copy(confidenceScore = score.coerceIn(0, 100)))
  }

  fun getAllFavorites(): Flow<List<FavoriteWordEntity>> {
    return dao.getAllFavorites()
  }

  suspend fun toggleFavoriteWord(word: WordItem, isCurrentlyFavorite: Boolean) {
    if (isCurrentlyFavorite) {
      dao.deleteFavorite(word.english)
    } else {
      dao.insertFavorite(
        FavoriteWordEntity(
          english = word.english,
          uzbek = word.uzbek,
          transcription = word.transcription,
          partOfSpeech = word.partOfSpeech,
          levelCode = word.level.code,
        )
      )
    }
  }

  fun isWordFavorite(english: String): Flow<Boolean> {
    return dao.isWordFavorite(english)
  }
}
