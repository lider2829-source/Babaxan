package com.example.ui.screens.today

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.AssignmentTurnedIn
import androidx.compose.material.icons.filled.AutoFixHigh
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BadgeCatalog
import com.example.data.model.BlockType
import com.example.data.model.CefrLevel
import com.example.data.model.DayCurriculum
import com.example.ui.components.DailyCompletionCelebrationDialog
import com.example.ui.components.RewardsBadgesSection
import com.example.ui.theme.ConfidenceGold
import com.example.ui.theme.FluencyEmerald
import com.example.ui.theme.PrimaryBlueLight
import com.example.ui.viewmodel.Eng90ViewModel
import com.example.util.ReminderScheduler

@Composable
fun TodayScreen(
  viewModel: Eng90ViewModel,
  modifier: Modifier = Modifier,
) {
  val selectedDay by viewModel.selectedDay.collectAsState()
  val curriculum by viewModel.curriculum.collectAsState()
  val dayProgress by viewModel.dayProgress.collectAsState()
  val allProgressList by viewModel.allProgressList.collectAsState()
  val profile by viewModel.userProfile.collectAsState()
  val currentTtsSpeed by viewModel.ttsHelper.currentSpeed.collectAsState()

  val context = LocalContext.current

  // Daily notification reminder state via AlarmManager
  var isReminderOn by remember { mutableStateOf(ReminderScheduler.isReminderEnabled(context)) }
  val (savedHour, savedMinute) = remember { ReminderScheduler.getReminderTime(context) }
  var reminderHour by remember { mutableStateOf(savedHour) }
  var reminderMinute by remember { mutableStateOf(savedMinute) }
  var testNotificationSentMessage by remember { mutableStateOf<String?>(null) }

  val notificationPermissionLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
  ) { isGranted ->
    if (isGranted) {
      isReminderOn = true
      ReminderScheduler.saveAndScheduleReminder(context, true, reminderHour, reminderMinute)
    } else {
      isReminderOn = false
      ReminderScheduler.saveAndScheduleReminder(context, false, reminderHour, reminderMinute)
    }
  }

  val minutesSpent = dayProgress?.minutesSpent ?: 0
  val progressFraction = (minutesSpent / 150f).coerceIn(0f, 1f)

  val blockStatuses = listOf(
    dayProgress?.isBlock0Done == true,
    dayProgress?.isBlock1Done == true,
    dayProgress?.isBlock2Done == true,
    dayProgress?.isBlock3Done == true,
    dayProgress?.isBlock4Done == true,
  )
  val completedBlocksCount = blockStatuses.count { it }

  // Overall 90-day trajectory progress
  val completedDaysOverall = allProgressList.count { it.isDayCompleted }
  val overall90DayFraction = (selectedDay / 90f).coerceIn(0f, 1f)

  // Next incomplete block to recommend
  val nextIncompleteBlock = BlockType.entries.firstOrNull { block ->
    when (block.id) {
      0 -> dayProgress?.isBlock0Done != true
      1 -> dayProgress?.isBlock1Done != true
      2 -> dayProgress?.isBlock2Done != true
      3 -> dayProgress?.isBlock3Done != true
      4 -> dayProgress?.isBlock4Done != true
      else -> false
    }
  }
  val showCelebration by viewModel.showCelebrationDialog.collectAsState()

  val badges = remember(completedDaysOverall, profile, dayProgress) {
    BadgeCatalog.getBadges(
      completedDaysCount = completedDaysOverall,
      totalMinutes = profile?.totalMinutesLearned ?: 0,
      streakDays = profile?.streakDays ?: 1,
      confidenceScore = profile?.confidenceScore ?: 35,
      todayAll5Done = dayProgress?.isDayCompleted == true,
    )
  }

  if (showCelebration) {
    DailyCompletionCelebrationDialog(
      dayNumber = selectedDay,
      onDismiss = { viewModel.dismissCelebrationDialog() },
      onNextDay = {
        viewModel.dismissCelebrationDialog()
        if (selectedDay < 90) viewModel.selectDay(selectedDay + 1)
      },
    )
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .testTag("today_dashboard_screen"),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    // -------------------------------------------------------------
    // SECTION 1: HEADER & 90-DAY PROGRESS TRACKER
    // -------------------------------------------------------------
    item {
      Dashboard90DayTrackerCard(
        currentDay = selectedDay,
        totalCompletedDays = completedDaysOverall,
        level = curriculum.level,
        streakDays = profile?.streakDays ?: 1,
        confidenceScore = profile?.confidenceScore ?: 35,
        overallFraction = overall90DayFraction,
        onPrevDay = { if (selectedDay > 1) viewModel.selectDay(selectedDay - 1) },
        onNextDay = { if (selectedDay < 90) viewModel.selectDay(selectedDay + 1) },
      )
    }

    // -------------------------------------------------------------
    // SECTION 2: INTERACTIVE DAY TIMELINE STRIP (Scrollable 90 days)
    // -------------------------------------------------------------
    item {
      InteractiveDayTimelineStrip(
        selectedDay = selectedDay,
        allProgressList = allProgressList,
        onSelectDay = { viewModel.selectDay(it) },
      )
    }

    // -------------------------------------------------------------
    // SECTION 3: TODAY'S 150-MINUTE STUDY SESSION GAUGE
    // -------------------------------------------------------------
    item {
      Daily150MinuteGaugeCard(
        dayNumber = selectedDay,
        dayTitle = curriculum.titleUz,
        dayObjective = curriculum.objectiveUz,
        minutesSpent = minutesSpent,
        completedCount = completedBlocksCount,
        progressFraction = progressFraction,
        isDayDone = dayProgress?.isDayCompleted == true,
        blockStatuses = blockStatuses,
        nextBlock = nextIncompleteBlock,
        onStartBlock = { block -> viewModel.startBlockSession(block) },
      )
    }

    // -------------------------------------------------------------
    // SECTION 4: AUDIO SPEED & SPEECH CONTROLLER
    // -------------------------------------------------------------
    item {
      DashboardTtsSpeedBar(
        currentSpeed = currentTtsSpeed,
        onSpeedSelected = { viewModel.setTtsSpeed(it) },
      )
    }

    // -------------------------------------------------------------
    // SECTION 4.5: DAILY STUDY REMINDER (AlarmManager)
    // -------------------------------------------------------------
    item {
      DailyStudyReminderCard(
        isEnabled = isReminderOn,
        hour = reminderHour,
        minute = reminderMinute,
        feedbackMessage = testNotificationSentMessage,
        onToggleEnabled = { enabled ->
          if (enabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
              notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
              isReminderOn = true
              ReminderScheduler.saveAndScheduleReminder(context, true, reminderHour, reminderMinute)
            }
          } else {
            isReminderOn = false
            ReminderScheduler.saveAndScheduleReminder(context, false, reminderHour, reminderMinute)
          }
        },
        onTimeSelected = { h, m ->
          reminderHour = h
          reminderMinute = m
          if (isReminderOn) {
            ReminderScheduler.saveAndScheduleReminder(context, true, h, m)
          }
        },
        onTestNotification = {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
          }
          ReminderScheduler.triggerTestNotification(context)
          testNotificationSentMessage = "Test eslatma yuborildi! (Bildirishnomalarni tekshiring)"
        },
      )
    }

    // -------------------------------------------------------------
    // SECTION 5: THE 5 DAILY STUDY BLOCKS HEADER
    // -------------------------------------------------------------
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Column {
          Text(
            text = "5 Daily Study Blocks (150 min)",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
          )
          Text(
            text = "Har bir blok 30 daqiqa • Room bazasidagi doimiy holat",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (completedBlocksCount == 5) FluencyEmerald.copy(alpha = 0.18f) else MaterialTheme.colorScheme.secondaryContainer,
        ) {
          Text(
            text = "$completedBlocksCount / 5 Done",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = if (completedBlocksCount == 5) FluencyEmerald else MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
          )
        }
      }
    }

    // -------------------------------------------------------------
    // SECTION 6: THE 5 SPECIFIC DAILY STUDY BLOCKS (Room Connected)
    // -------------------------------------------------------------
    items(BlockType.entries) { block ->
      val isDone = when (block.id) {
        0 -> dayProgress?.isBlock0Done == true
        1 -> dayProgress?.isBlock1Done == true
        2 -> dayProgress?.isBlock2Done == true
        3 -> dayProgress?.isBlock3Done == true
        4 -> dayProgress?.isBlock4Done == true
        else -> false
      }

      StudyBlockCard(
        block = block,
        isCompleted = isDone,
        curriculum = curriculum,
        quizScorePercent = dayProgress?.quizScore ?: 0,
        onStartBlock = { viewModel.startBlockSession(block) },
        onToggleCompleted = { viewModel.toggleBlockStatus(selectedDay, block.id) },
      )
    }

    // -------------------------------------------------------------
    // SECTION 7: REWARDS & BADGES SHOWCASE
    // -------------------------------------------------------------
    item {
      RewardsBadgesSection(badges = badges)
    }

    // -------------------------------------------------------------
    // SECTION 8: ANTI-ANXIETY & PERSISTENCE BANNER
    // -------------------------------------------------------------
    item {
      DashboardPersistenceBanner(dayNumber = selectedDay)
    }

    // -------------------------------------------------------------
    // SECTION 9: CREATOR ATTRIBUTION
    // -------------------------------------------------------------
    item {
      CreatorFooter()
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

// ---------------------------------------------------------------------------
// 1. 90-DAY PROGRESS TRACKER CARD
// ---------------------------------------------------------------------------
@Composable
private fun Dashboard90DayTrackerCard(
  currentDay: Int,
  totalCompletedDays: Int,
  level: CefrLevel,
  streakDays: Int,
  confidenceScore: Int,
  overallFraction: Float,
  onPrevDay: () -> Unit,
  onNextDay: () -> Unit,
) {
  val animatedProgress by animateFloatAsState(targetValue = overallFraction, label = "progressAnim")

  Card(
    shape = RoundedCornerShape(24.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface,
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth().testTag("90_day_tracker_card"),
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      // Top row: Day title, navigation arrows, streak
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        IconButton(
          onClick = onPrevDay,
          enabled = currentDay > 1,
          modifier = Modifier
            .size(36.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape)
            .testTag("prev_day_btn"),
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Oldingi kun",
            modifier = Modifier.size(18.dp),
          )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "DAY $currentDay OF 90",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.ExtraBold,
              letterSpacing = 0.5.sp,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MaterialTheme.colorScheme.primaryContainer,
            ) {
              Text(
                text = level.code,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              )
            }
          }
          Text(
            text = level.titleUz,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        IconButton(
          onClick = onNextDay,
          enabled = currentDay < 90,
          modifier = Modifier
            .size(36.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), CircleShape)
            .testTag("next_day_btn"),
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Keyingi kun",
            modifier = Modifier.size(18.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 90-Day Trajectory Milestone Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Timeline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(18.dp),
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "90-Day Trajectory (0 dan B2)",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }

        Text(
          text = "${(animatedProgress * 100).toInt()}% B2 yoʻli",
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary,
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      LinearProgressIndicator(
        progress = { animatedProgress },
        modifier = Modifier
          .fillMaxWidth()
          .height(8.dp)
          .clip(RoundedCornerShape(4.dp)),
        color = MaterialTheme.colorScheme.primary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
      )

      Spacer(modifier = Modifier.height(6.dp))

      // 4 Milestone Markers: A1, A2, B1, B2
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        MilestoneLabel(code = "A1", range = "1–25", isActive = currentDay in 1..25)
        MilestoneLabel(code = "A2", range = "26–50", isActive = currentDay in 26..50)
        MilestoneLabel(code = "B1", range = "51–75", isActive = currentDay in 51..75)
        MilestoneLabel(code = "B2", range = "76–90", isActive = currentDay in 76..90)
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Badges: Streak + Confidence + Completed Days
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        DashboardBadge(
          icon = Icons.Default.LocalFireDepartment,
          tint = ConfidenceGold,
          title = "Streak",
          value = "$streakDays kun",
          modifier = Modifier.weight(1f),
        )
        DashboardBadge(
          icon = Icons.Default.Psychology,
          tint = FluencyEmerald,
          title = "Ishonch",
          value = "$confidenceScore%",
          modifier = Modifier.weight(1f),
        )
        DashboardBadge(
          icon = Icons.Default.School,
          tint = MaterialTheme.colorScheme.primary,
          title = "Tugatilgan",
          value = "$totalCompletedDays / 90 kun",
          modifier = Modifier.weight(1.3f),
        )
      }
    }
  }
}

@Composable
private fun MilestoneLabel(code: String, range: String, isActive: Boolean) {
  Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text(
      text = code,
      style = MaterialTheme.typography.labelSmall,
      fontWeight = if (isActive) FontWeight.ExtraBold else FontWeight.Medium,
      color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Text(
      text = range,
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
      color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
    )
  }
}

@Composable
private fun DashboardBadge(
  icon: ImageVector,
  tint: Color,
  title: String,
  value: String,
  modifier: Modifier = Modifier,
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = tint.copy(alpha = 0.12f),
    modifier = modifier,
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
      Spacer(modifier = Modifier.width(6.dp))
      Column {
        Text(text = title, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(text = value, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = tint)
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 2. INTERACTIVE DAY TIMELINE STRIP
// ---------------------------------------------------------------------------
@Composable
private fun InteractiveDayTimelineStrip(
  selectedDay: Int,
  allProgressList: List<com.example.data.local.UserDayProgressEntity>,
  onSelectDay: (Int) -> Unit,
) {
  val listState = rememberLazyListState()

  // Auto-scroll to near selected day
  LaunchedEffect(selectedDay) {
    val targetIndex = (selectedDay - 3).coerceAtLeast(0)
    listState.animateScrollToItem(targetIndex)
  }

  Column {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = "Kunlar Xaritasi (Tezkor oʻtish)",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
      )
      Text(
        text = "Kunni tanlang",
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    LazyRow(
      state = listState,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth().testTag("day_timeline_row"),
    ) {
      items(90) { index ->
        val dayNum = index + 1
        val isSelected = (dayNum == selectedDay)
        val isCompleted = allProgressList.any { it.dayNumber == dayNum && it.isDayCompleted }

        val backgroundBrush = if (isSelected) {
          Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primaryContainer)
          )
        } else if (isCompleted) {
          Brush.verticalGradient(
            colors = listOf(FluencyEmerald.copy(alpha = 0.2f), FluencyEmerald.copy(alpha = 0.1f))
          )
        } else {
          Brush.verticalGradient(
            colors = listOf(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f), MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f))
          )
        }

        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(width = 48.dp, height = 62.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(backgroundBrush)
            .clickable { onSelectDay(dayNum) }
            .then(
              if (isSelected) {
                Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(14.dp))
              } else {
                Modifier
              }
            )
            .testTag("timeline_day_$dayNum"),
        ) {
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
          ) {
            Text(
              text = "KUN",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp),
              fontWeight = FontWeight.Bold,
              color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
              text = "$dayNum",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            )
            if (isCompleted) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (isSelected) Color.White else FluencyEmerald,
                modifier = Modifier.size(12.dp),
              )
            }
          }
        }
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 3. DAILY 150-MINUTE STUDY SESSION GAUGE CARD
// ---------------------------------------------------------------------------
@Composable
private fun Daily150MinuteGaugeCard(
  dayNumber: Int,
  dayTitle: String,
  dayObjective: String,
  minutesSpent: Int,
  completedCount: Int,
  progressFraction: Float,
  isDayDone: Boolean,
  blockStatuses: List<Boolean>,
  nextBlock: BlockType?,
  onStartBlock: (BlockType) -> Unit,
) {
  val blockNames = listOf("Vocab", "Listen", "Speak", "Grammar", "Test")

  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth().testTag("150m_gauge_card"),
  ) {
    Column(modifier = Modifier.padding(18.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = dayTitle,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = dayObjective,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
          )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // 150m Circular Gauge
        Box(contentAlignment = Alignment.Center) {
          CircularProgressIndicator(
            progress = { progressFraction },
            modifier = Modifier.size(72.dp),
            strokeWidth = 7.dp,
            color = if (isDayDone) FluencyEmerald else MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
          )
          Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
              text = "$minutesSpent",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            Text(
              text = "/150m",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      // 5-Block Status Segment Bar
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
      ) {
        blockStatuses.forEachIndexed { index, isDone ->
          val segmentColor = if (isDone) FluencyEmerald else MaterialTheme.colorScheme.surfaceVariant
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = segmentColor.copy(alpha = if (isDone) 1f else 0.4f),
            modifier = Modifier
              .weight(1f)
              .height(28.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Text(
                text = blockNames[index],
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                fontWeight = if (isDone) FontWeight.Bold else FontWeight.Normal,
                color = if (isDone) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action: Continue or Celebrate
      if (isDayDone) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = FluencyEmerald.copy(alpha = 0.15f),
          modifier = Modifier.fillMaxWidth(),
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = FluencyEmerald)
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "🎉 Bugungi 150 daqiqa toʻliq bajarildi!",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = FluencyEmerald,
              )
              Text(
                text = "Ajoyib natija! Ertangi kunga oʻtishingiz mumkin.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
              )
            }
          }
        }
      } else if (nextBlock != null) {
        Button(
          onClick = { onStartBlock(nextBlock) },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
          modifier = Modifier.fillMaxWidth().height(48.dp).testTag("continue_study_btn"),
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "Darsni davom ettirish: ${nextBlock.nameEn} (+30 min)",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 4. TTS SPEED CONTROLLER
// ---------------------------------------------------------------------------
@Composable
private fun DashboardTtsSpeedBar(
  currentSpeed: Float,
  onSpeedSelected: (Float) -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
        contentDescription = "Ovoz",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(18.dp),
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = "Nutq tezligi:",
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.SemiBold,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
      listOf(
        0.75f to "0.75x (Sekin)",
        1.0f to "1.0x (Oddiy)",
        1.25f to "1.25x (Tez)",
      ).forEach { (speed, label) ->
        FilterChip(
          selected = (currentSpeed == speed),
          onClick = { onSpeedSelected(speed) },
          label = { Text(label, style = MaterialTheme.typography.labelSmall) },
          modifier = Modifier.testTag("speed_${speed}"),
        )
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 4.5. DAILY STUDY REMINDER CARD (AlarmManager)
// ---------------------------------------------------------------------------
@Composable
private fun DailyStudyReminderCard(
  isEnabled: Boolean,
  hour: Int,
  minute: Int,
  feedbackMessage: String?,
  onToggleEnabled: (Boolean) -> Unit,
  onTimeSelected: (Int, Int) -> Unit,
  onTestNotification: () -> Unit,
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isEnabled) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
      else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth().testTag("daily_reminder_card"),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.weight(1f),
        ) {
          Surface(
            shape = CircleShape,
            color = if (isEnabled) ConfidenceGold.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(42.dp),
          ) {
            Box(contentAlignment = Alignment.Center) {
              Icon(
                imageVector = if (isEnabled) Icons.Default.NotificationsActive else Icons.Default.Notifications,
                contentDescription = "Eslatma",
                tint = if (isEnabled) ConfidenceGold else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp),
              )
            }
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "Kunlik Dars Eslatmasi",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            val timeFormatted = String.format("%02d:%02d", hour, minute)
            Text(
              text = if (isEnabled) "Har kuni $timeFormatted da (AlarmManager orqali)"
                     else "150 daqiqalik darsni oʻtkazib yubormaslik uchun yoqing",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }

        Switch(
          checked = isEnabled,
          onCheckedChange = onToggleEnabled,
          colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = MaterialTheme.colorScheme.primary,
          ),
          modifier = Modifier.testTag("reminder_switch"),
        )
      }

      if (isEnabled) {
        Spacer(modifier = Modifier.height(14.dp))
        Text(
          text = "Qulay eslatma vaqtini tanlang:",
          style = MaterialTheme.typography.labelSmall,
          fontWeight = FontWeight.SemiBold,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(6.dp))

        val presetTimes = listOf(
          Pair(8, 0) to "08:00 (Ertalab)",
          Pair(13, 0) to "13:00 (Tushlik)",
          Pair(18, 0) to "18:00 (Ishdan soʻng)",
          Pair(20, 0) to "20:00 (Optimal)",
          Pair(21, 30) to "21:30 (Kechki)",
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          presetTimes.take(3).forEach { (timePair, label) ->
            val isSelected = (hour == timePair.first && minute == timePair.second)
            FilterChip(
              selected = isSelected,
              onClick = { onTimeSelected(timePair.first, timePair.second) },
              label = { Text(label.substringBefore(" "), style = MaterialTheme.typography.labelSmall) },
              modifier = Modifier.weight(1f),
            )
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          presetTimes.drop(3).forEach { (timePair, label) ->
            val isSelected = (hour == timePair.first && minute == timePair.second)
            FilterChip(
              selected = isSelected,
              onClick = { onTimeSelected(timePair.first, timePair.second) },
              label = { Text(label, style = MaterialTheme.typography.labelSmall) },
              modifier = Modifier.weight(1f),
            )
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          OutlinedButton(
            onClick = onTestNotification,
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.testTag("test_notification_btn"),
          ) {
            Icon(Icons.Default.Alarm, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Eslatmani Hozir Sinash", style = MaterialTheme.typography.labelSmall)
          }

          if (feedbackMessage != null) {
            Text(
              text = "✅ Eslatma yuborildi",
              style = MaterialTheme.typography.labelSmall,
              color = FluencyEmerald,
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 5. THE 5 DAILY STUDY BLOCKS CARDS (Room Database Connected)
// ---------------------------------------------------------------------------
@Composable
private fun StudyBlockCard(
  block: BlockType,
  isCompleted: Boolean,
  curriculum: DayCurriculum,
  quizScorePercent: Int,
  onStartBlock: () -> Unit,
  onToggleCompleted: () -> Unit,
) {
  val (icon: ImageVector, accentColor: Color) = when (block) {
    BlockType.VOCABULARY -> Icons.AutoMirrored.Filled.MenuBook to PrimaryBlueLight
    BlockType.LISTENING -> Icons.Default.Headphones to Color(0xFF6366F1)
    BlockType.SPEAKING -> Icons.Default.RecordVoiceOver to FluencyEmerald
    BlockType.GRAMMAR -> Icons.Default.AutoFixHigh to Color(0xFF8B5CF6)
    BlockType.QUIZ -> Icons.Default.AssignmentTurnedIn to ConfidenceGold
  }

  val previewContent = when (block) {
    BlockType.VOCABULARY -> {
      "Bugungi soʻzlar: " + curriculum.vocabList.joinToString(", ") { it.english }
    }
    BlockType.LISTENING -> {
      "Dialog: ${curriculum.listening.titleEn} (${curriculum.listening.titleUz}) • ${curriculum.listening.situationUz}"
    }
    BlockType.SPEAKING -> {
      "Nutq: ${curriculum.speaking.warmUpTitle} • ${curriculum.speaking.roleplayPromptUz}"
    }
    BlockType.GRAMMAR -> {
      "Qoida: ${curriculum.grammar.patternName} (${curriculum.grammar.formula})"
    }
    BlockType.QUIZ -> {
      if (isCompleted && quizScorePercent > 0) {
        "Kunlik 4 ta sinov savoli • Natija: $quizScorePercent% muvaffaqiyatli"
      } else {
        "Kunlik 4 ta sinov savoli • Barcha bloklarni tekshirish"
      }
    }
  }

  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isCompleted) FluencyEmerald.copy(alpha = 0.06f) else MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.cardElevation(defaultElevation = if (isCompleted) 1.dp else 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .testTag("block_card_${block.id}"),
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Top header row: Icon, Title, Status badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        // Icon Box
        Surface(
          shape = RoundedCornerShape(14.dp),
          color = if (isCompleted) FluencyEmerald.copy(alpha = 0.15f) else accentColor.copy(alpha = 0.15f),
          modifier = Modifier.size(48.dp),
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = if (isCompleted) Icons.Default.CheckCircle else icon,
              contentDescription = block.nameEn,
              tint = if (isCompleted) FluencyEmerald else accentColor,
              modifier = Modifier.size(24.dp),
            )
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "${block.id + 1}. ${block.nameEn}",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "• 30 min",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
          Text(
            text = block.titleUz,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Status Badge retrieved from Room
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = if (isCompleted) FluencyEmerald.copy(alpha = 0.18f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(
              imageVector = if (isCompleted) Icons.Default.Check else Icons.Default.AccessTime,
              contentDescription = null,
              tint = if (isCompleted) FluencyEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.size(12.dp),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = if (isCompleted) "Bajarildi (+30m)" else "Kutilmoqda",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              fontWeight = FontWeight.Bold,
              color = if (isCompleted) FluencyEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Preview description box
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f),
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(
          text = previewContent,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Interactive Action Row: Direct Toggle in Room OR Start 30m Session
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        // Direct toggle Room state button
        OutlinedButton(
          onClick = onToggleCompleted,
          shape = RoundedCornerShape(10.dp),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
          modifier = Modifier.testTag("toggle_block_${block.id}"),
        ) {
          Icon(
            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isCompleted) FluencyEmerald else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp),
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isCompleted) "Bajarildi ✓" else "Belgilash",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
          )
        }

        // Main action: Enter 30-minute interactive session
        Button(
          onClick = onStartBlock,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = if (isCompleted) MaterialTheme.colorScheme.surfaceVariant else accentColor,
            contentColor = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else Color.White,
          ),
          contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
          modifier = Modifier.testTag("start_block_${block.id}"),
        ) {
          Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (isCompleted) "Qayta Mashq" else "Darsni Boshlash (30m)",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    }
  }
}

// ---------------------------------------------------------------------------
// 6. DASHBOARD PERSISTENCE BANNER
// ---------------------------------------------------------------------------
@Composable
private fun DashboardPersistenceBanner(dayNumber: Int) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f)
    ),
    modifier = Modifier.fillMaxWidth().testTag("dashboard_persistence_banner"),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(text = "🧠", style = MaterialTheme.typography.headlineMedium)
      Spacer(modifier = Modifier.width(12.dp))
      Column {
        Text(
          text = "90 Kunlik Intensiv Qoida: 5x30 daqiqa",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = "Miya har 30 daqiqadan soʻng yangi yoʻnalishga oʻtsa (Lugʻat -> Eshitish -> Nutq -> Grammatika -> Test), charchoq 70% ga kamayadi va xotira 3 barobar mustahkamlanadi.",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.85f),
        )
      }
    }
  }
}

@Composable
private fun CreatorFooter() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 12.dp)
      .testTag("creator_footer"),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Text(
      text = "Created by B. Sherjonov",
      style = MaterialTheme.typography.labelLarge,
      fontWeight = FontWeight.Bold,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = "English 90-Day Intensive • 0 to B2 Fluency",
      style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
      color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
    )
  }
}
