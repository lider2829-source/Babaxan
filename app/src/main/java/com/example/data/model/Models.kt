package com.example.data.model

enum class CefrLevel(
  val code: String,
  val titleUz: String,
  val descriptionUz: String,
  val dayStart: Int,
  val dayEnd: Int,
) {
  A1("A1", "A1 - Elementary", "1–25 kunlar: Noldan asoslar va kundalik iboralar", 1, 25),
  A2("A2", "A2 - Pre-Intermediate", "26–50 kunlar: Oʻtgan/kelasi zamon va erkin muloqot", 26, 50),
  B1("B1", "B1 - Intermediate", "51–75 kunlar: Fikrni erkin ifodalash va ravonlik", 51, 75),
  B2("B2", "B2 - Upper-Intermediate", "76–90 kunlar: Chuqur tahlil, idiyomalar va spontan nutq", 76, 90);

  companion object {
    fun fromDay(day: Int): CefrLevel =
      when (day) {
        in 1..25 -> A1
        in 26..50 -> A2
        in 51..75 -> B1
        else -> B2
      }
  }
}

enum class BlockType(
  val id: Int,
  val nameEn: String,
  val titleUz: String,
  val subtitleUz: String,
  val durationMinutes: Int,
  val descriptionUz: String,
) {
  VOCABULARY(
    id = 0,
    nameEn = "Vocabulary",
    titleUz = "Lugʻat & Iboralar",
    subtitleUz = "30 daq • Kontekstdagi yangi soʻzlar va collocation",
    durationMinutes = 30,
    descriptionUz = "Soʻzlarni shunchaki yodlamasdan, ularni iboralar va audio talaffuzi bilan birga oʻrganing.",
  ),
  LISTENING(
    id = 1,
    nameEn = "Listening",
    titleUz = "Eshitish & Shadowing",
    subtitleUz = "30 daq • Audio dialog va spiker ketidan takrorlash",
    durationMinutes = 30,
    descriptionUz = "Ingliz tili ohangiga quloqni oʻrgating va audio tezligini oʻzingizga moslang.",
  ),
  SPEAKING(
    id = 2,
    nameEn = "Speaking",
    titleUz = "Nutq & Hayajonni Yengish",
    subtitleUz = "30 daq • 4-7-8 nafas, artikulyatsiya va ekspromt nutq",
    durationMinutes = 30,
    descriptionUz = "Xavotir va tildagi qotishni yengish uchun maxsus psixologik mashqlar va jonli suhbat.",
  ),
  GRAMMAR(
    id = 3,
    nameEn = "Grammar",
    titleUz = "Jonli Gap Qoliplari",
    subtitleUz = "30 daq • Qoidalarsiz jonli gap tuzilmalari",
    durationMinutes = 30,
    descriptionUz = "Quruq grammatik formulalardan voz kechib, nutqda darhol ishlatiladigan patternlar.",
  ),
  QUIZ(
    id = 4,
    nameEn = "Test",
    titleUz = "Kunlik Test & Sinov",
    subtitleUz = "30 daq • 150 daqiqalik darsni xulosalash",
    durationMinutes = 30,
    descriptionUz = "Bugungi oʻrganilgan material boʻyicha amaliy sinov va natijalarni mustahkamlash.",
  );

  companion object {
    fun fromId(id: Int): BlockType = entries.find { it.id == id } ?: VOCABULARY
  }
}

data class WordItem(
  val english: String,
  val uzbek: String,
  val transcription: String,
  val partOfSpeech: String,
  val exampleEn: String,
  val exampleUz: String,
  val level: CefrLevel,
  val collocation: String = "",
)

data class DialogueLine(
  val speaker: String,
  val textEn: String,
  val textUz: String,
)

data class ListeningDialogue(
  val titleEn: String,
  val titleUz: String,
  val situationUz: String,
  val lines: List<DialogueLine>,
  val comprehensionTipUz: String,
)

data class SpeakingDrill(
  val warmUpTitle: String,
  val tongueTwister: String,
  val tongueTwisterUz: String,
  val roleplayPromptUz: String,
  val roleplayDialog: List<DialogueLine>,
  val impromptuTopicEn: String,
  val impromptuTopicUz: String,
  val anxietyTipUz: String,
)

data class GrammarPatternItem(
  val patternName: String,
  val formula: String,
  val explanationUz: String,
  val sampleSentences: List<Pair<String, String>>, // English, Uzbek
  val scrambledWords: List<String>,
  val correctSentence: String,
)

data class QuizQuestion(
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanationUz: String,
)

data class DayCurriculum(
  val dayNumber: Int,
  val level: CefrLevel,
  val titleUz: String,
  val objectiveUz: String,
  val vocabList: List<WordItem>,
  val listening: ListeningDialogue,
  val speaking: SpeakingDrill,
  val grammar: GrammarPatternItem,
  val quizQuestions: List<QuizQuestion>,
)
