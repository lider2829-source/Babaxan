package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.FavoriteWordEntity
import com.example.data.local.UserDayProgressEntity
import com.example.data.local.UserProfileEntity
import com.example.data.model.BlockType
import com.example.data.model.CefrLevel
import com.example.data.model.DayCurriculum
import com.example.data.model.WordItem
import com.example.data.repository.EnglishCurriculumRepository
import com.example.util.TtsHelper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab {
  TODAY,
  ROADMAP,
  SPEAKING_LAB,
  VOCAB_VAULT,
  PROFILE,
}

enum class BreathingPhase(val labelUz: String, val seconds: Int) {
  IDLE("Boshlashga tayyor", 0),
  INHALE("Nafas oling (Burun bilan chuqur)", 4),
  HOLD("Nafasni ushlab turing", 7),
  EXHALE("Sekin ogʻizdan chiqaring", 8),
}

class Eng90ViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: EnglishCurriculumRepository
  val ttsHelper: TtsHelper = TtsHelper(application)

  init {
    val database = AppDatabase.getDatabase(application)
    repository = EnglishCurriculumRepository(database.progressDao())
    viewModelScope.launch {
      repository.ensureProfileExists()
    }
  }

  // Navigation state
  private val _currentTab = MutableStateFlow(MainTab.TODAY)
  val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

  // Selected Day (defaults to user active day, but user can view any day 1..90)
  private val _selectedDay = MutableStateFlow(1)
  val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

  // Active Block Session (null if browsing main screens)
  private val _activeSessionBlock = MutableStateFlow<BlockType?>(null)
  val activeSessionBlock: StateFlow<BlockType?> = _activeSessionBlock.asStateFlow()

  // User Profile
  val userProfile: StateFlow<UserProfileEntity?> = repository.getUserProfile()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

  // Current Day Curriculum
  private val _curriculum = MutableStateFlow(repository.getCurriculumForDay(1))
  val curriculum: StateFlow<DayCurriculum> = _curriculum.asStateFlow()

  // Day Progress for currently selected day
  val dayProgress: StateFlow<UserDayProgressEntity?> = _selectedDay
    .flatMapLatest { day -> repository.getDayProgress(day) }
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

  // All 90 days progress list
  val allProgressList: StateFlow<List<UserDayProgressEntity>> = repository.getAllDayProgress()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Favorites
  val favoriteWords: StateFlow<List<FavoriteWordEntity>> = repository.getAllFavorites()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // --- Block 30-Minute Timer ---
  private val _timerSeconds = MutableStateFlow(30 * 60)
  val timerSeconds: StateFlow<Int> = _timerSeconds.asStateFlow()

  private val _isTimerRunning = MutableStateFlow(false)
  val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

  private var timerJob: Job? = null

  // --- 4-7-8 Breathing State ---
  private val _breathingPhase = MutableStateFlow(BreathingPhase.IDLE)
  val breathingPhase: StateFlow<BreathingPhase> = _breathingPhase.asStateFlow()

  private val _breathingSecondsLeft = MutableStateFlow(0)
  val breathingSecondsLeft: StateFlow<Int> = _breathingSecondsLeft.asStateFlow()

  private var breathingJob: Job? = null

  // --- Impromptu 60-Second Challenge ---
  private val _impromptuSecondsLeft = MutableStateFlow(60)
  val impromptuSecondsLeft: StateFlow<Int> = _impromptuSecondsLeft.asStateFlow()

  private val _isImpromptuRunning = MutableStateFlow(false)
  val isImpromptuRunning: StateFlow<Boolean> = _isImpromptuRunning.asStateFlow()

  private var impromptuJob: Job? = null

  fun setTab(tab: MainTab) {
    _currentTab.value = tab
    _activeSessionBlock.value = null
  }

  fun selectDay(day: Int) {
    val clamped = day.coerceIn(1, 90)
    _selectedDay.value = clamped
    _curriculum.value = repository.getCurriculumForDay(clamped)
  }

  fun startBlockSession(block: BlockType) {
    _activeSessionBlock.value = block
    resetBlockTimer(block.durationMinutes)
    startBlockTimer()
  }

  fun exitBlockSession() {
    pauseBlockTimer()
    _activeSessionBlock.value = null
  }

  fun startBlockTimer() {
    if (_isTimerRunning.value) return
    _isTimerRunning.value = true
    timerJob?.cancel()
    timerJob = viewModelScope.launch {
      while (_isTimerRunning.value && _timerSeconds.value > 0) {
        delay(1000)
        _timerSeconds.value = (_timerSeconds.value - 1).coerceAtLeast(0)
      }
      _isTimerRunning.value = false
    }
  }

  fun pauseBlockTimer() {
    _isTimerRunning.value = false
    timerJob?.cancel()
  }

  fun resetBlockTimer(minutes: Int = 30) {
    pauseBlockTimer()
    _timerSeconds.value = minutes * 60
  }

  private val _showCelebrationDialog = MutableStateFlow(false)
  val showCelebrationDialog: StateFlow<Boolean> = _showCelebrationDialog.asStateFlow()

  fun dismissCelebrationDialog() {
    _showCelebrationDialog.value = false
  }

  fun triggerCelebration() {
    _showCelebrationDialog.value = true
  }

  fun completeCurrentBlock() {
    val block = _activeSessionBlock.value ?: return
    viewModelScope.launch {
      val isAllDayDone = repository.markBlockCompleted(_selectedDay.value, block.id, block.durationMinutes)
      exitBlockSession()
      if (isAllDayDone) {
        _showCelebrationDialog.value = true
      }
    }
  }

  fun toggleBlockStatus(day: Int, blockId: Int) {
    viewModelScope.launch {
      val isAllDayDone = repository.toggleBlockStatus(day, blockId)
      if (isAllDayDone) {
        _showCelebrationDialog.value = true
      }
    }
  }

  fun submitQuiz(scorePercent: Int) {
    viewModelScope.launch {
      val isAllDayDone = repository.submitQuizScore(_selectedDay.value, scorePercent)
      if (isAllDayDone) {
        _showCelebrationDialog.value = true
      }
    }
  }

  fun toggleFavorite(word: WordItem, isFav: Boolean) {
    viewModelScope.launch {
      repository.toggleFavoriteWord(word, isFav)
    }
  }

  // --- 4-7-8 Breathing Logic ---
  fun startBreathingExercise() {
    breathingJob?.cancel()
    breathingJob = viewModelScope.launch {
      // 3 cycles of 4-7-8 breathing
      for (cycle in 1..3) {
        // Inhale 4s
        _breathingPhase.value = BreathingPhase.INHALE
        for (sec in 4 downTo 1) {
          _breathingSecondsLeft.value = sec
          delay(1000)
        }
        // Hold 7s
        _breathingPhase.value = BreathingPhase.HOLD
        for (sec in 7 downTo 1) {
          _breathingSecondsLeft.value = sec
          delay(1000)
        }
        // Exhale 8s
        _breathingPhase.value = BreathingPhase.EXHALE
        for (sec in 8 downTo 1) {
          _breathingSecondsLeft.value = sec
          delay(1000)
        }
      }
      _breathingPhase.value = BreathingPhase.IDLE
      _breathingSecondsLeft.value = 0
    }
  }

  fun stopBreathingExercise() {
    breathingJob?.cancel()
    _breathingPhase.value = BreathingPhase.IDLE
    _breathingSecondsLeft.value = 0
  }

  // --- Impromptu Speech Logic ---
  fun startImpromptuSpeech() {
    _impromptuSecondsLeft.value = 60
    _isImpromptuRunning.value = true
    impromptuJob?.cancel()
    impromptuJob = viewModelScope.launch {
      while (_impromptuSecondsLeft.value > 0 && _isImpromptuRunning.value) {
        delay(1000)
        _impromptuSecondsLeft.value -= 1
      }
      _isImpromptuRunning.value = false
    }
  }

  fun stopImpromptuSpeech() {
    _isImpromptuRunning.value = false
    impromptuJob?.cancel()
  }

  // --- Audio / TTS Actions ---
  fun speakText(text: String) {
    ttsHelper.speak(text)
  }

  fun setTtsSpeed(speed: Float) {
    ttsHelper.setSpeed(speed)
  }

  fun setConfidenceScore(score: Int) {
    viewModelScope.launch {
      repository.updateConfidenceScore(score)
    }
  }

  fun setDayAsCurrent(day: Int) {
    selectDay(day)
    viewModelScope.launch {
      repository.setCurrentActiveDay(day)
    }
  }

  override fun onCleared() {
    super.onCleared()
    timerJob?.cancel()
    breathingJob?.cancel()
    impromptuJob?.cancel()
    ttsHelper.shutdown()
  }
}
