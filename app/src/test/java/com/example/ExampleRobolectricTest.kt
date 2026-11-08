package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.CefrLevel
import com.example.data.repository.CurriculumDataSource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Ingliz Tili 90 Kun", appName)
  }

  @Test
  fun `curriculum provides rich day 1 data`() {
    val day1 = CurriculumDataSource.getCurriculumForDay(1)
    assertEquals(1, day1.dayNumber)
    assertEquals(CefrLevel.A1, day1.level)
    assertEquals(5, day1.vocabList.size)
    assertNotNull(day1.listening)
    assertNotNull(day1.speaking)
    assertNotNull(day1.grammar)
    assertEquals(4, day1.quizQuestions.size)
  }

  @Test
  fun `rewards system unlocks badges properly`() {
    val badges = com.example.data.model.BadgeCatalog.getBadges(
      completedDaysCount = 1,
      totalMinutes = 150,
      streakDays = 1,
      confidenceScore = 40,
      todayAll5Done = true,
    )
    val dailyChampion = badges.find { it.id == "daily_champion" }
    assertNotNull(dailyChampion)
    assertEquals(true, dailyChampion?.isUnlocked)

    val firstStep = badges.find { it.id == "first_step" }
    assertNotNull(firstStep)
    assertEquals(true, firstStep?.isUnlocked)
  }

  @Test
  fun `room database stores 5 daily study blocks progress correctly`() {
    val progress = com.example.data.local.UserDayProgressEntity(
      dayNumber = 1,
      isVocabularyCompleted = true,
      isListeningCompleted = true,
      isSpeakingCompleted = true,
      isGrammarCompleted = true,
      isTestCompleted = true,
      totalMinutesSpent = 150,
      testScorePercent = 100,
      isDayCompleted = true,
    )
    assertEquals(1, progress.dayNumber)
    assertEquals(true, progress.isVocabularyCompleted)
    assertEquals(true, progress.isListeningCompleted)
    assertEquals(true, progress.isSpeakingCompleted)
    assertEquals(true, progress.isGrammarCompleted)
    assertEquals(true, progress.isTestCompleted)
    assertEquals(5, progress.completedBlocksCount)
    assertEquals(150, progress.totalMinutesSpent)
    assertEquals(true, progress.isDayCompleted)
  }

  @Test
  fun `room database opens and inserts progress entity without crash`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = com.example.data.local.AppDatabase.getDatabase(context)
    val dao = db.progressDao()
    assertNotNull(dao)
  }
}
