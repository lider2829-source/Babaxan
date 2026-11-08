package com.example.data.model

enum class BadgeCategory(val titleUz: String) {
  DAILY("Kunlik Gʻalabalar"),
  STREAK("Intizom & Streak"),
  CEFR("CEFR Bosqichlari"),
  MASTERY("Nutq & Mahorat"),
}

data class RewardBadge(
  val id: String,
  val titleUz: String,
  val titleEn: String,
  val emoji: String,
  val descriptionUz: String,
  val category: BadgeCategory,
  val isUnlocked: Boolean,
  val progressText: String,
)

object BadgeCatalog {

  fun getBadges(
    completedDaysCount: Int,
    totalMinutes: Int,
    streakDays: Int,
    confidenceScore: Int,
    todayAll5Done: Boolean,
  ): List<RewardBadge> {
    return listOf(
      // Daily & Today
      RewardBadge(
        id = "daily_champion",
        titleUz = "Kunlik 150m Qahramoni",
        titleEn = "Daily 150m Champion",
        emoji = "🌟",
        descriptionUz = "Bugungi 5 ta 30 daqiqalik dars blokini toʻliq yakunlang.",
        category = BadgeCategory.DAILY,
        isUnlocked = todayAll5Done,
        progressText = if (todayAll5Done) "Bugun bajarildi!" else "5 blokdan kamida bittasi qolgan",
      ),
      RewardBadge(
        id = "first_step",
        titleUz = "Birinchi Qadam",
        titleEn = "First Step Taken",
        emoji = "🚀",
        descriptionUz = "90 kunlik intensiv dasturning ilk kunini muvaffaqiyatli tugating.",
        category = BadgeCategory.DAILY,
        isUnlocked = completedDaysCount >= 1,
        progressText = if (completedDaysCount >= 1) "Ochildi!" else "$completedDaysCount / 1 kun",
      ),

      // Streak Badges
      RewardBadge(
        id = "streak_3",
        titleUz = "3-Kunlik Ishtiyoq",
        titleEn = "3-Day Flame",
        emoji = "🔥",
        descriptionUz = "Ketma-ket 3 kun davomida har kuni 150 daqiqa dars qiling.",
        category = BadgeCategory.STREAK,
        isUnlocked = streakDays >= 3 || completedDaysCount >= 3,
        progressText = if (streakDays >= 3 || completedDaysCount >= 3) "Ochildi!" else "${streakDays.coerceAtLeast(completedDaysCount)} / 3 kun",
      ),
      RewardBadge(
        id = "streak_7",
        titleUz = "1 Haftalik Temir Iroda",
        titleEn = "7-Day Iron Will",
        emoji = "⚡",
        descriptionUz = "1 hafta uzluksiz intensiv rejaga amal qiling.",
        category = BadgeCategory.STREAK,
        isUnlocked = streakDays >= 7 || completedDaysCount >= 7,
        progressText = if (streakDays >= 7 || completedDaysCount >= 7) "Ochildi!" else "${streakDays.coerceAtLeast(completedDaysCount)} / 7 kun",
      ),
      RewardBadge(
        id = "streak_21",
        titleUz = "21 Kun: Mustahkam Odat",
        titleEn = "Habit Master",
        emoji = "💎",
        descriptionUz = "Ingliz tilida fikrlash va gapirishni doimiy odatga aylantiring.",
        category = BadgeCategory.STREAK,
        isUnlocked = completedDaysCount >= 21,
        progressText = "$completedDaysCount / 21 kun",
      ),

      // CEFR Level Badges
      RewardBadge(
        id = "cefr_a1",
        titleUz = "A1 Elementary Zafari",
        titleEn = "A1 Milestone Conqueror",
        emoji = "🌱",
        descriptionUz = "Dastlabki 25 kunni yakunlab, A1 darajasiga erishing.",
        category = BadgeCategory.CEFR,
        isUnlocked = completedDaysCount >= 25,
        progressText = "$completedDaysCount / 25 kun",
      ),
      RewardBadge(
        id = "cefr_a2",
        titleUz = "A2 Muloqot Sohibi",
        titleEn = "A2 Conversationalist",
        emoji = "🌿",
        descriptionUz = "50-kunga yetib, xaridlar, sayohat va erkin dialoglarni zabt eting.",
        category = BadgeCategory.CEFR,
        isUnlocked = completedDaysCount >= 50,
        progressText = "$completedDaysCount / 50 kun",
      ),
      RewardBadge(
        id = "cefr_b1",
        titleUz = "B1 Mustaqil Notiq",
        titleEn = "B1 Intermediate Thinker",
        emoji = "🌲",
        descriptionUz = "75-kunda oʻz fikrini ravon asoslash va ish intervyulari mahoratiga ega boʻling.",
        category = BadgeCategory.CEFR,
        isUnlocked = completedDaysCount >= 75,
        progressText = "$completedDaysCount / 75 kun",
      ),
      RewardBadge(
        id = "cefr_b2",
        titleUz = "B2 Master Sertifikati",
        titleEn = "B2 Fluency Legend",
        emoji = "👑",
        descriptionUz = "90 kunlik marshrutni toʻliq zabt etib, mutlaq erkinlikka erishing!",
        category = BadgeCategory.CEFR,
        isUnlocked = completedDaysCount >= 90,
        progressText = "$completedDaysCount / 90 kun",
      ),

      // Mastery & Speaking Badges
      RewardBadge(
        id = "anxiety_breaker",
        titleUz = "Hayajonni Yenguvchi",
        titleEn = "Fearless Speaker",
        emoji = "🛡️",
        descriptionUz = "4-7-8 nafas mashqi va ekspromt nutq orqali nutqiy ishonchni 50% dan oshiring.",
        category = BadgeCategory.MASTERY,
        isUnlocked = confidenceScore >= 50,
        progressText = "$confidenceScore% / 50%",
      ),
      RewardBadge(
        id = "time_titan_10h",
        titleUz = "10 Soatlik Marafonchi",
        titleEn = "10 Hours of Deep Focus",
        emoji = "⏳",
        descriptionUz = "Jami 600 daqiqa (10 soat) intensiv ingliz tili amaliyoti.",
        category = BadgeCategory.MASTERY,
        isUnlocked = totalMinutes >= 600,
        progressText = "${totalMinutes / 60} / 10 soat",
      ),
    )
  }
}
