package com.example.data.repository

import com.example.data.model.CefrLevel
import com.example.data.model.DayCurriculum
import com.example.data.model.DialogueLine
import com.example.data.model.GrammarPatternItem
import com.example.data.model.ListeningDialogue
import com.example.data.model.QuizQuestion
import com.example.data.model.SpeakingDrill
import com.example.data.model.WordItem

object CurriculumDataSource {

  fun getCurriculumForDay(day: Int): DayCurriculum {
    val level = CefrLevel.fromDay(day)
    return when (day) {
      1 -> createDay1()
      2 -> createDay2()
      3 -> createDay3()
      4 -> createDay4()
      5 -> createDay5()
      26 -> createDay26()
      27 -> createDay27()
      28 -> createDay28()
      51 -> createDay51()
      52 -> createDay52()
      76 -> createDay76()
      85 -> createDay85()
      90 -> createDay90()
      else -> generateSyntheticDay(day, level)
    }
  }

  private fun createDay1() = DayCurriculum(
    dayNumber = 1,
    level = CefrLevel.A1,
    titleUz = "Birinchi Tanishuv & Asosiy Salomlashish",
    objectiveUz = "Oʻzingizni tanishtirish, salomlashish va ingliz tilidagi birinchi jumlalarni hayajonsiz aytishni oʻrganamiz.",
    vocabList = listOf(
      WordItem("Introduce", "Tanishtirmoq", "/ˌɪntrəˈdjuːs/", "verb", "Allow me to introduce myself.", "Oʻzimni tanishtirishga ruxsat bering.", CefrLevel.A1, "introduce yourself"),
      WordItem("Pleasure", "Mamnuniyat", "/ˈpleʒə(r)/", "noun", "It is a pleasure to meet you.", "Siz bilan uchrashganimdan mamnunman.", CefrLevel.A1, "my pleasure"),
      WordItem("Origin", "Kelib chiqish", "/ˈɒrɪdʒɪn/", "noun", "Where are you from originally?", "Asli qayerdansiz?", CefrLevel.A1, "country of origin"),
      WordItem("Occupation", "Kasb-kor", "/ˌɒkjuˈpeɪʃn/", "noun", "What is your current occupation?", "Hozirgi kasbingiz nima?", CefrLevel.A1, "by occupation"),
      WordItem("Confident", "Oʻziga ishongan", "/ˈkɒnfɪdənt/", "adjective", "Speak in a confident voice.", "Ishonchli ovozda gapiring.", CefrLevel.A1, "feel confident"),
    ),
    listening = ListeningDialogue(
      titleEn = "Meeting a New Colleague",
      titleUz = "Yangi hamkasb bilan uchrashuv",
      situationUz = "Xalqaro ofisda ikki yangi xodim ilk bor bir-biri bilan salomlashmoqda.",
      lines = listOf(
        DialogueLine("Alex", "Hello there! My name is Alex. Welcome to our team.", "Salom! Mening ismim Aleks. Jamoamizga xush kelibsiz."),
        DialogueLine("Dilshod", "Hi Alex! Nice to meet you. I am Dilshod from Uzbekistan.", "Salom Aleks! Tanishganimdan xursandman. Men Dilshodman, Oʻzbekistondan."),
        DialogueLine("Alex", "Great to have you here! Is this your first week in the city?", "Bu yerda ekaningizdan xursandmiz! Bu sizning shahardagi birinchi haftangizmi?"),
        DialogueLine("Dilshod", "Yes, exactly. Everything is exciting and I want to improve my speaking.", "Ha, aynan shunday. Hammasi qiziq va men gapirishimni yaxshilamoqchiman."),
        DialogueLine("Alex", "You are doing great! Just relax, we are all friends here.", "Juda zoʻr gapiryapsiz! Shunchaki xotirjam boʻling, biz hammamiz doʻstlarmiz."),
      ),
      comprehensionTipUz = "Spikerning intonatsiyasiga eʼtibor bering: 'Hello there' iborasi doʻstona va samimiy ohangda aytiladi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Diyafragmani boʻshashtirish va tovushlarni ochish",
      tongueTwister = "Red lorry, yellow lorry, red lorry, yellow lorry.",
      tongueTwisterUz = "Qizil yuk mashinasi, sariq yuk mashinasi (R va L tovushlarini ravonlashtirish uchun).",
      roleplayPromptUz = "Oʻzingizni xorijlik mehmonga tanishtiring: Ismingiz, kasbingiz va qayerdan ekanligingizni ayting.",
      roleplayDialog = listOf(
        DialogueLine("Partner", "Good morning! Are you the new specialist?", "Xayrli tong! Siz yangi mutaxassismisiz?"),
        DialogueLine("You", "Yes, good morning! My name is [Your Name], and I am excited to work together.", "Ha, xayrli tong! Ismim [Ismingiz], siz bilan birga ishlashdan mamnunman."),
      ),
      impromptuTopicEn = "Tell about your hometown in 60 seconds",
      impromptuTopicUz = "Tugʻilgan shahringiz haqida 60 soniya toʻxtovsiz, xatolardan qoʻrqmasdan gapiring.",
      anxietyTipUz = "ESDA TUTING: Xato qilish – oʻrganishning tabiiy belgisi! Hech kim sizning grammatikangizni baholamaydi, asosiy maqsad – fikringizni yetkazish.",
    ),
    grammar = GrammarPatternItem(
      patternName = "To Be + Adjective / Noun (Oʻzini tanishtirish qolipi)",
      formula = "Subject + am/is/are + [Adjective/Noun]",
      explanationUz = "Ingliz tilida feʼlsiz gap boʻlmaydi. 'Men talabaman' deyish uchun 'I student' emas, 'I am a student' deyiladi.",
      sampleSentences = listOf(
        "I am ready to learn English." to "Men ingliz tilini oʻrganishga tayyorman.",
        "She is very friendly and supportive." to "U juda samimiy va qoʻllab-quvvatlovchi.",
        "We are here to speak without fear." to "Biz bu yerda qoʻrquvsiz gapirish uchun yigʻildik.",
      ),
      scrambledWords = listOf("to", "am", "ready", "I", "speak"),
      correctSentence = "I am ready to speak",
    ),
    quizQuestions = listOf(
      QuizQuestion("Qaysi jumla toʻgʻri tuzilgan?", listOf("I a student.", "I am a student.", "I be student.", "I is student."), 1, "'I' olmoshi bilan 'am' yordamchi feʼli ishlatiladi."),
      QuizQuestion("'Pleasure to meet you' iborasi qanday maʼnoni anglatadi?", listOf("Kechirasiz", "Siz bilan tanishganimdan mamnunman", "Xayr, koʻrishguncha", "Qayerdansiz?"), 1, "Bu rasmiy va doʻstona tanishuv iborasi."),
      QuizQuestion("Boʻsh joyni toʻldiring: 'She ___ from Uzbekistan.'", listOf("am", "are", "is", "be"), 2, "'She' uchinchi shaxs birlik uchun 'is' feʼli toʻgʻri keladi."),
      QuizQuestion("Nutq paytida hayajon kelganda eng samarali usul qaysi?", listOf("Gapirishdan toʻxtash", "Sekin chuqur nafas olib, fikrni sodda soʻzlar bilan davom ettirish", "Tezroq qochib ketish", "Jim oʻtirish"), 1, "4-7-8 nafas mashqi va sodda jumlalar hayajonni darhol tushiradi."),
    ),
  )

  private fun createDay2() = DayCurriculum(
    dayNumber = 2,
    level = CefrLevel.A1,
    titleUz = "Kundalik Ehtiyojlar & Savol Berish",
    objectiveUz = "Magazin, koʻcha va kundalik vaziyatlarda yordam soʻrash va oddiy savollarni berish.",
    vocabList = listOf(
      WordItem("Available", "Mavjud / Bor", "/əˈveɪləbl/", "adjective", "Is this seat available?", "Bu joy boʻshmi / mavjudmi?", CefrLevel.A1, "readily available"),
      WordItem("Assistance", "Yordam / Koʻmak", "/əˈsɪstəns/", "noun", "Do you need any assistance?", "Sizga yordam kerakmi?", CefrLevel.A1, "require assistance"),
      WordItem("Price", "Narx", "/praɪs/", "noun", "What is the total price?", "Umumiy narx qancha?", CefrLevel.A1, "reasonable price"),
      WordItem("Receipt", "Kvitansiya / Chek", "/rɪˈsiːt/", "noun", "Here is your receipt.", "Mana sizning chekingiz.", CefrLevel.A1, "keep the receipt"),
      WordItem("Recommend", "Tavsiya qilmoq", "/ˌrekəˈmend/", "verb", "Can you recommend a good book?", "Yaxshi kitob tavsiya qila olasizmi?", CefrLevel.A1, "highly recommend"),
    ),
    listening = ListeningDialogue(
      titleEn = "At a Coffee Shop",
      titleUz = "Qahvaxonada buyurtma berish",
      situationUz = "Xaridor qahva va yegulik tanlab buyurtma qilmoqda.",
      lines = listOf(
        DialogueLine("Barista", "Hi! What can I get started for you today?", "Salom! Bugun sizga nima tayyorlab beray?"),
        DialogueLine("Customer", "Hello! Could I please get a medium cappuccino with oat milk?", "Salom! Iltimos, joʻxori sutli oʻrtacha kapuchino bera olasizmi?"),
        DialogueLine("Barista", "Sure thing. Anything to eat with that?", "Albatta. Unga qoʻshib yeyishga biror narsa olasizmi?"),
        DialogueLine("Customer", "A chocolate croissant, please. How much is it?", "Shokoladli kruassan, iltimos. Narxi qancha?"),
        DialogueLine("Barista", "That will be six dollars total. Card or cash?", "Jami olti dollar boʻladi. Karta yoki naqd?"),
      ),
      comprehensionTipUz = "'Could I please get...' iborasi buyurtma berishda eng xushmuomala usul hisoblanadi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Lab va tovush chiqarish mashqlari",
      tongueTwister = "Fresh fried fish, fish fresh fried, fried fish fresh.",
      tongueTwisterUz = "F va SH tovushlarini aniq talaffuz qilish uchun tez aytish.",
      roleplayPromptUz = "Doʻkonda kerakli mahsulotni topolmayapsiz. Sotuvchidan yordam soʻrang.",
      roleplayDialog = listOf(
        DialogueLine("Clerk", "Can I help you find something?", "Nimadir topishga yordam beraymi?"),
        DialogueLine("You", "Yes, please. Where can I find drinking water?", "Ha, iltimos. Ichimlik suvini qayerdan topsam boʻladi?"),
      ),
      impromptuTopicEn = "What is your favorite food and why?",
      impromptuTopicUz = "Yoqtirgan taomingiz va nima uchun uni sevishingiz haqida 1 daqiqa gapiring.",
      anxietyTipUz = "Ovozingiz past chiqishidan tortinmang. Koʻkrakdan toʻliq ovoz chiqarib gapirish ishonchni 2 barobar oshiradi.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Could I have / Can I get...? (Xushmuomala soʻrov)",
      formula = "Could I + [verb/noun] + please?",
      explanationUz = "Buyruq ohangida 'Give me' (menga ber) demasdan, 'Could I have...' orqali madaniyatli soʻrov hosil qilinadi.",
      sampleSentences = listOf(
        "Could I get the bill, please?" to "Hisob-kitobni olsam boʻladimi, iltimos?",
        "Can I ask a quick question?" to "Tezkor bitta savol bersam maylimi?",
        "Could you speak a bit slower?" to "Biroz sekinroq gapira olasizmi?",
      ),
      scrambledWords = listOf("have", "Could", "water", "I", "some"),
      correctSentence = "Could I have some water",
    ),
    quizQuestions = listOf(
      QuizQuestion("Qaysi ibora eng muloyim buyurtma berish usuli?", listOf("Give me coffee!", "Could I please have a coffee?", "I want coffee now.", "Coffee here."), 1, "'Could I please have...' eng muloyim va toʻgʻri soʻrovdir."),
      QuizQuestion("'Receipt' soʻzining talaffuzida qaysi harf aytilmaydi?", listOf("R harfi", "P harfi (resiyt deb oʻqiladi)", "C harfi", "T harfi"), 1, "'P' harfi yoziladi, lekin talaffuz qilinmaydi: /rɪˈsiːt/."),
      QuizQuestion("Doʻkonda narxni qanday soʻrash toʻgʻri?", listOf("How many dollars?", "How much is this?", "What price money?", "How price is?"), 1, "'How much is this?' yagona eng tabiiy soʻrov."),
      QuizQuestion("Inglizcha gapirganda soʻz yodingizdan koʻtarilsa nima qilish kerak?", listOf("Suhbatni toʻxtatish", "'You know, that thing...' deb boshqa soʻz bilan tushuntirish", "Oʻzbekcha qichqirish", "Jim turish"), 1, "Parafrazlash (boshqa soʻz bilan tushuntirish) haqiqiy B2 darajasi mahoratidir!"),
    ),
  )

  private fun createDay3() = DayCurriculum(
    dayNumber = 3,
    level = CefrLevel.A1,
    titleUz = "Oila, Yaqinlar & Shaxsiy Hayot",
    objectiveUz = "Oilangiz aʼzolari, ularning feʼl-atvori va qiziqishlari haqida erkin gapirish.",
    vocabList = listOf(
      WordItem("Sibling", "Aka-uka / Opa-singil", "/ˈsɪblɪŋ/", "noun", "I have two siblings.", "Mening ikkita aka-ukam bor.", CefrLevel.A1, "older sibling"),
      WordItem("Supportive", "Qoʻllab-quvvatlovchi", "/səˈpɔːtɪv/", "adjective", "My parents are very supportive.", "Ota-onam meni juda qoʻllab-quvvatlaydi.", CefrLevel.A1, "supportive family"),
      WordItem("Hobby", "Qiziqish / Xobbi", "/ˈhɒbi/", "noun", "What is your main hobby?", "Asosiy qiziqishingiz nima?", CefrLevel.A1, "pursue a hobby"),
      WordItem("Inspire", "Ilhomlantirmoq", "/ɪnˈspaɪə(r)/", "verb", "My father inspires me every day.", "Otam meni har kuni ilhomlantiradi.", CefrLevel.A1, "inspire others"),
      WordItem("Generous", "Saxiy / Bagʻrikeng", "/ˈdʒenərəs/", "adjective", "She is a generous person.", "U saxiy inson.", CefrLevel.A1, "generous heart"),
    ),
    listening = ListeningDialogue(
      titleEn = "Talking About Family",
      titleUz = "Oila haqida doʻstona suhbat",
      situationUz = "Ikki doʻst tushlik paytida oilalari va bolalik xotiralari haqida gaplashmoqda.",
      lines = listOf(
        DialogueLine("Sarah", "Do you have a big family, David?", "Devid, katta oilangiz bormi?"),
        DialogueLine("David", "Quite big! I have an older brother and two younger sisters.", "Anchagina katta! Katta akam va ikkita singlim bor."),
        DialogueLine("Sarah", "That sounds lively! Do you all live in the same city?", "Qiziqarli-ku! Hammasi bir shaharda yashaysizmi?"),
        DialogueLine("David", "No, my brother studies abroad, but we call each other every weekend.", "Yoʻq, akam chet elda oʻqiydi, lekin har dam olish kunlari gaplashib turamiz."),
      ),
      comprehensionTipUz = "'Do you all live...' iborasi bir guruh odamlar haqida soʻrashda qoʻllaniladi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Ovoz kuchi va intonatsiya erkinligi",
      tongueTwister = "A proper cup of coffee from a proper copper coffee pot.",
      tongueTwisterUz = "P va K tovushlarining artikulyatsiyasi uchun mashq.",
      roleplayPromptUz = "Doʻstingizga oilangizdagi eng sevimli insoningiz haqida aytib bering.",
      roleplayDialog = listOf(
        DialogueLine("Friend", "Who do you admire most in your family?", "Oilangizda eng koʻp kimga havas qilasiz?"),
        DialogueLine("You", "I admire my mother because she is hardworking and kind.", "Onamga havas qilaman, chunki u mehnatkash va mehribon."),
      ),
      impromptuTopicEn = "Describe a person who inspires you",
      impromptuTopicUz = "Sizni ilhomlantiradigan inson haqida 1 daqiqa hikoya qiling.",
      anxietyTipUz = "Gapirishdan oldin tabassum qiling. Tabassum miyaga xavfsizlik signallarini yuboradi va hayajonni bosadi.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Have / Has got & Possessives (Egalik ifodasi)",
      formula = "Subject + have/has + [noun]",
      explanationUz = "Biror narsaga ega ekanlikni aytishda 'I have' yoki 'He/She has' ishlatiladi.",
      sampleSentences = listOf(
        "I have a passion for languages." to "Menda tillarga nisbatan katta qiziqish bor.",
        "She has two lovely children." to "Uning ikkita yoqimtoy farzandi bor.",
        "They have a wonderful house." to "Ularning ajoyib uyi bor.",
      ),
      scrambledWords = listOf("have", "family", "I", "supportive", "a"),
      correctSentence = "I have a supportive family",
    ),
    quizQuestions = listOf(
      QuizQuestion("'Sibling' soʻzi kimlarni anglatadi?", listOf("Faqat amakilarni", "Aka-uka yoki opa-singilni", "Ota-onani", "Qoʻshnilarni"), 1, "'Sibling' – bu aka-uka yoki opa-singillar uchun umumiy soʻz."),
      QuizQuestion("'He ___ a very busy schedule' jumlasida boʻsh joyga nima qoʻyiladi?", listOf("have", "has", "having", "is have"), 1, "Uchinchi shaxs birlik (He/She/It) bilan 'has' ishlatiladi."),
      QuizQuestion("Saxiy insonni ingliz tilida qanday taʼriflaysiz?", listOf("Greedy", "Generous", "Lazy", "Angry"), 1, "'Generous' – saxiy va ochiqkoʻngil inson."),
      QuizQuestion("Oʻzingizga boʻlgan ishonchni oshirish uchun har kuni nima qilish lozim?", listOf("Hech kim bilan gaplashmaslik", "Ovozingizni diktofonga yozib, oʻzingiz eshitib koʻrish", "Grammatika qoidalarini yodlash", "Faqat film koʻrish"), 1, "Oʻz ovozini eshitish miyadagi nutq toʻsigʻini eng tez sindiradi!"),
    ),
  )

  private fun createDay4() = DayCurriculum(
    dayNumber = 4,
    level = CefrLevel.A1,
    titleUz = "Vaqt, Jadval & Odatiy Kun Tartibi",
    objectiveUz = "Kundalik 24 soatlik tartib, vaqtni aytish va odatlarni Present Simple orqali ifodalash.",
    vocabList = listOf(
      WordItem("Routine", "Kun tartibi / Odat", "/ruːˈtiːn/", "noun", "My daily routine starts at 6 AM.", "Kun tartibim ertalab soat 6 da boshlanadi.", CefrLevel.A1, "daily routine"),
      WordItem("Punctual", "Vaqtga qatʼiy rioya qiluvchi", "/ˈpʌŋktʃuəl/", "adjective", "He is always punctual.", "U har doim vaqtida keladi.", CefrLevel.A1, "stay punctual"),
      WordItem("Schedule", "Reja / Jadval", "/ˈʃedjuːl/", "noun", "I have a tight schedule today.", "Bugun menda tigʻiz jadval bor.", CefrLevel.A1, "busy schedule"),
      WordItem("Productive", "Samarali", "/prəˈdʌktɪv/", "adjective", "Today was a productive day.", "Bugun samarali kun boʻldi.", CefrLevel.A1, "productive morning"),
      WordItem("Habit", "Odat", "/ˈhæbɪt/", "noun", "Reading is a powerful habit.", "Kitob oʻqish – kuchli odat.", CefrLevel.A1, "build a habit"),
    ),
    listening = ListeningDialogue(
      titleEn = "Planning the Workday",
      titleUz = "Ish kunini rejalashtirish",
      situationUz = "Ikki hamkasb ertalabki rejalarni muhokama qilmoqda.",
      lines = listOf(
        DialogueLine("Manager", "Good morning! What does your schedule look like today?", "Xayrli tong! Bugungi rejangiz qanday?"),
        DialogueLine("Worker", "I have a team meeting at 10 AM, and then I focus on our project.", "Soat 10 da jamoaviy yigʻilishim bor, keyin loyihamizga diqqatimni qarataman."),
        DialogueLine("Manager", "Sounds good. Don't forget our 150-minute English session!", "Juda soz. 150 daqiqalik ingliz tili darsimizni unutmang!"),
        DialogueLine("Worker", "Never! Consistency is key to becoming fluent.", "Hech qachon! Doimiylik ravon gapirishning garovidir."),
      ),
      comprehensionTipUz = "'Sounds good' iborasi suhbatdoshning fikrini maʼqullashda keng qoʻllanadi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Diqqatni jamlash va sekin nafas chiqarish",
      tongueTwister = "Six sticky skeletons, six sticky skeletons.",
      tongueTwisterUz = "S va T tovushlari birikmasi artikulyatsiyasi.",
      roleplayPromptUz = "Ertalabdan kechgacha nima ishlar qilishingizni tartib bilan soʻzlab bering.",
      roleplayDialog = listOf(
        DialogueLine("Interviewer", "How do you start your morning?", "Ertalabni qanday boshlaysiz?"),
        DialogueLine("You", "I wake up early, drink water, and practice speaking English for 30 minutes.", "Ertalab erta uygʻonaman, suv ichaman va 30 daqiqa inglizcha nutq mashq qilaman."),
      ),
      impromptuTopicEn = "Walk me through your ideal productive day",
      impromptuTopicUz = "Oʻzingizning eng samarali ideal kuningizni 60 soniya davomida tasvirlang.",
      anxietyTipUz = "Gapirishdan oldin 4 soniya burun bilan nafas oling, 7 soniya ushlab turing va 8 soniya sekin chiqaring. Bu pulsni darhol meʼyorlashtiradi.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Present Simple for Routines (Kundalik odatlar)",
      formula = "I/You/We/They + verb | He/She/It + verb-s",
      explanationUz = "Har doim takrorlanadigan odatlar va haqiqatlarda Present Simple ishlatiladi. 'I work every day'.",
      sampleSentences = listOf(
        "I dedicate 150 minutes to English every single day." to "Men har kuni ingliz tiliga 150 daqiqa ajrataman.",
        "She wakes up at dawn with high energy." to "U tongda yuqori quvvat bilan uygʻonadi.",
        "Consistency creates real confidence." to "Doimiylik haqiqiy ishonchni yaratadi.",
      ),
      scrambledWords = listOf("English", "practice", "I", "daily"),
      correctSentence = "I practice English daily",
    ),
    quizQuestions = listOf(
      QuizQuestion("Doimiy takrorlanadigan harakatlarda qaysi zamon ishlatiladi?", listOf("Present Continuous", "Present Simple", "Past Simple", "Future Simple"), 1, "Kundalik odatlar uchun Present Simple zamoni javob beradi."),
      QuizQuestion("'Punctual' inson qanday inson?", listOf("Dangasa", "Har doim vaqtida yuradigan", "Kech qoluvchi", "Juda qoʻpol"), 1, "Punctual – vaqtga qatʼiy rioya qiluvchi inson."),
      QuizQuestion("'He ___ English very well.' boʻsh joyga nima qoʻyiladi?", listOf("speak", "speaks", "speaking", "is speak"), 1, "'He' bilan feʼl oxiriga -s qoʻshiladi: speaks."),
      QuizQuestion("Ravon gapirishga erishishning eng muhim omili nima?", listOf("Oyiga bir marta 10 soat oʻqish", "Har kuni muntazam mashq qilish (Consistency)", "Faqat lugʻat yodlash", "Gapirishdan qochish"), 1, "Har kungi doimiy intensiv mashq 90 kunda B2 darajasiga kafolat beradi."),
    ),
  )

  private fun createDay5() = DayCurriculum(
    dayNumber = 5,
    level = CefrLevel.A1,
    titleUz = "His-tuygʻular, Kayfiyat & Motivatsiya",
    objectiveUz = "Oʻzingizni qanday his qilayotganingiz, energiya darajangiz va ijobiy kayfiyatni inglizcha ifodalash.",
    vocabList = listOf(
      WordItem("Motivated", "Motivatsiyaga toʻla", "/ˈməʊtɪveɪtɪd/", "adjective", "I feel motivated to succeed.", "Muvaffaqiyatga erishishga ishtiyoqim baland.", CefrLevel.A1, "highly motivated"),
      WordItem("Exhausted", "Juda charchagan", "/ɪɡˈzɔːstɪd/", "adjective", "I was exhausted after a long run.", "Uzoq yugurishdan soʻng holdan toydim.", CefrLevel.A1, "feel exhausted"),
      WordItem("Grateful", "Minnatdor", "/ˈɡreɪtfl/", "adjective", "I am grateful for this opportunity.", "Bu imkoniyat uchun minnatdorman.", CefrLevel.A1, "truly grateful"),
      WordItem("Overcome", "Yengib oʻtmoq", "/ˌəʊvəˈkʌm/", "verb", "We can overcome any fear.", "Biz har qanday qoʻrquvni yenga olamiz.", CefrLevel.A1, "overcome obstacles"),
      WordItem("Courage", "Jasorat", "/ˈkʌrɪdʒ/", "noun", "It takes courage to speak in public.", "Omma oldida gapirish jasorat talab qiladi.", CefrLevel.A1, "have the courage"),
    ),
    listening = ListeningDialogue(
      titleEn = "Encouraging a Friend",
      titleUz = "Doʻstni ruhlantirish",
      situationUz = "Inglizcha gapirishdan qoʻrqayotgan doʻstga ruhiy madad berilmoqda.",
      lines = listOf(
        DialogueLine("Mentor", "You seemed a bit nervous during the presentation. Are you okay?", "Taqdimotda biroz hayajonlangandek koʻrindingiz. Hammasi joyidami?"),
        DialogueLine("Student", "Honestly, I was terrified of making grammatical mistakes.", "Rostini aytsam, grammatik xato qilishdan juda qoʻrqdim."),
        DialogueLine("Mentor", "Listen, mistakes are proof that you are trying. Your message was clear!", "Eshiting, xatolar – siz harakat qilayotganingizning isboti. Fikringiz tushunarli edi!"),
        DialogueLine("Student", "Thank you, that gives me courage to keep practicing.", "Rahmat, bu menga mashqni davom ettirishga jasorat beradi."),
      ),
      comprehensionTipUz = "'Proof that you are trying' – harakat qilayotganingizning dalili maʼnosida qoʻllanadi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Ijobiy tasavvur va ovoz erkinligi",
      tongueTwister = "I wish to wash my Irish wristwatch.",
      tongueTwisterUz = "W va SH tovushlarining toʻgʻri hosil boʻlishi.",
      roleplayPromptUz = "Oʻzingizga bugungi yutuqlaringiz uchun minnatdorchilik bildiring va ovoz chiqarib ayting.",
      roleplayDialog = listOf(
        DialogueLine("Inner Critic", "What if you sound imperfect?", "Agar notoʻgʻri talaffuz qilsang-chi?"),
        DialogueLine("You", "Progress is better than perfection. I am proud of my effort!", "Mukammallikdan koʻra rivojlanish afzalroq. Men oʻz mehnatim bilan faxrlanaman!"),
      ),
      impromptuTopicEn = "What makes you genuinely happy in life?",
      impromptuTopicUz = "Hayotda sizni nimalar chin dildan baxtli qilishi haqida 1 daqiqa gapiring.",
      anxietyTipUz = "Oʻzingizni boshqalar bilan solishtirmang. Sizning yagona raqibingiz – kechagi oʻzingiz!",
    ),
    grammar = GrammarPatternItem(
      patternName = "Feel + Adjective & I am grateful for...",
      formula = "I feel + [Adjective] / I am grateful for + [Noun/Gerund]",
      explanationUz = "Hissiyotlarni ifodalashda 'feel' feʼlidan soʻng sifat keladi: 'I feel energetic'.",
      sampleSentences = listOf(
        "I feel confident about my English journey." to "Ingliz tili yoʻlimda oʻzimga ishonch sezyapman.",
        "I am grateful for my progress." to "Oʻz rivojimdan minnatdorman.",
        "She feels excited about the new challenge." to "U yangi chaqiruvdan hayajonda.",
      ),
      scrambledWords = listOf("feel", "confident", "I", "today"),
      correctSentence = "I feel confident today",
    ),
    quizQuestions = listOf(
      QuizQuestion("'Exhausted' soʻzining sinonimi qaysi?", listOf("Very happy", "Very tired", "Very angry", "Very fast"), 1, "Exhausted – juda charchagan (very tired)."),
      QuizQuestion("Nutq davomida xato qilishdan qoʻrqishni qanday yengish mumkin?", listOf("Gapirmay qoʻyish", "Xatoni tabiiy jarayon deb qabul qilib, fikrni davom ettirish", "Faqat yozma mashq qilish", "Boshqalarni tanqid qilish"), 1, "Xatolar – oʻsishning asosiy poydevori."),
      QuizQuestion("'Grateful' soʻzi qanday maʼnoni bildiradi?", listOf("Qaysar", "Minnatdor", "Qoʻrqoq", "Gʻazablangan"), 1, "Grateful – minnatdor, shukr qiluvchi."),
      QuizQuestion("5 kunlik mashgʻulotdan soʻng nimani sezasiz?", listOf("Hech narsani", "Miyadagi inglizcha toʻsiq sekin-asta yorilib, til erkinlashadi", "Hammasini unutish", "Qiyinlashish"), 1, "Muntazamlik miyada yangi neyron aloqalarini barpo qiladi!"),
    ),
  )

  private fun createDay26() = DayCurriculum(
    dayNumber = 26,
    level = CefrLevel.A2,
    titleUz = "A2 Bosqichiga Xush Kelibsiz! Oʻtgan Zamon Xotiralari",
    objectiveUz = "Oʻtgan zamon (Past Simple), oʻtgan haftada nima qilganingiz va shaxsiy tajribalarni hikoya qilish.",
    vocabList = listOf(
      WordItem("Memorable", "Unutilmas / Esda qolarli", "/ˈmemərəbl/", "adjective", "It was a memorable journey.", "Bu unutilmas sayohat edi.", CefrLevel.A2, "memorable experience"),
      WordItem("Achieve", "Erishmoq", "/əˈtʃiːv/", "verb", "I achieved my weekly goal.", "Men haftalik maqsadimga erishdim.", CefrLevel.A2, "achieve success"),
      WordItem("Decide", "Qaror qilmoq", "/dɪˈsaɪd/", "verb", "I decided to master English.", "Men ingliz tilini mukammal oʻrganishga qaror qildim.", CefrLevel.A2, "decide firmly"),
      WordItem("Challenging", "Qiyin, ammo qiziqarli", "/ˈtʃælɪndʒɪŋ/", "adjective", "The task was challenging but fun.", "Vazifa qiyin, lekin qiziqarli edi.", CefrLevel.A2, "challenging task"),
      WordItem("Improve", "Yaxshilamoq", "/ɪmˈpruːv/", "verb", "My pronunciation improved a lot.", "Talaffuzim ancha yaxshilandi.", CefrLevel.A2, "improve rapidly"),
    ),
    listening = ListeningDialogue(
      titleEn = "A Weekend Trip",
      titleUz = "Dam olish kunlari sayohati",
      situationUz = "Doʻstlar oʻtgan dam olish kunlarida qayerga borganliklarini muhokama qilmoqda.",
      lines = listOf(
        DialogueLine("Emma", "How was your weekend, Lucas?", "Dam olish kunlaring qanday oʻtdi, Lukas?"),
        DialogueLine("Lucas", "It was fantastic! I went hiking in the mountains with my family.", "Ajoyib oʻtdi! Oilam bilan togʻga piyoda sayrga chiqdim."),
        DialogueLine("Emma", "Did you take any good photos?", "Yaxshi suratlar oldingizmi?"),
        DialogueLine("Lucas", "Yes, plenty! The air was so fresh, and we really relaxed.", "Ha, juda koʻp! Havo shunchalik toza ediki, miriqib dam oldik."),
      ),
      comprehensionTipUz = "'How was your weekend?' – dushanba kuni suhbatni boshlash uchun eng ommabop ibora.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Oʻtgan zamon feʼllarining -ed qoʻshimchasini toʻgʻri talaffuz qilish (/t/, /d/, /ɪd/)",
      tongueTwister = "She stopped, walked, and decided to succeed.",
      tongueTwisterUz = "-ed qoʻshimchasining turli tovushlarini mustahkamlash.",
      roleplayPromptUz = "Oʻtgan yilgi eng unutilmas voqea haqida doʻstingizga aytib bering.",
      roleplayDialog = listOf(
        DialogueLine("Friend", "What did you do last summer?", "Oʻtgan yozda nima ish qildingiz?"),
        DialogueLine("You", "Last summer, I started learning coding and read ten great books.", "Oʻtgan yozda dasturlashni oʻrgana boshladim va oʻnta ajoyib kitob oʻqidim."),
      ),
      impromptuTopicEn = "Tell a story of a day you will never forget",
      impromptuTopicUz = "Hech qachon esingizdan chiqmaydigan bir kun haqida 1 daqiqa hikoya qiling.",
      anxietyTipUz = "Gapirayotganda pauza boʻlib qolsa, 'Let me see...' yoki 'Well...' kabi tabiiy soʻzlar bilan vaqt yuting. Bu tabiiy eshitiladi.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Past Simple (Doimiy va notoʻgʻri feʼllar)",
      formula = "Subject + Verb-2 / -ed",
      explanationUz = "Oʻtgan zamonda vaqt aniq boʻlsa (yesterday, last week, in 2020), Past Simple ishlatiladi.",
      sampleSentences = listOf(
        "I decided to change my life 26 days ago." to "Men 26 kun oldin hayotimni oʻzgartirishga qaror qildim.",
        "We spoke English for two hours yesterday." to "Biz kecha ikki soat inglizcha gaplashdik.",
        "He achieved an outstanding result." to "U ajoyib natijaga erishdi.",
      ),
      scrambledWords = listOf("spoke", "yesterday", "We", "English"),
      correctSentence = "We spoke English yesterday",
    ),
    quizQuestions = listOf(
      QuizQuestion("'Decide' feʼlining oʻtgan zamon shakli qaysi?", listOf("Decided", "Deciding", "Deciden", "Decideded"), 0, "Toʻgʻri feʼllarga -ed qoʻshiladi: decided."),
      QuizQuestion("'Go' feʼlining oʻtgan zamon shakli (Past Simple) qaysi?", listOf("Goed", "Gone", "Went", "Going"), 2, "Go notoʻgʻri feʼl boʻlib, oʻtgan zamoni 'went' boʻladi."),
      QuizQuestion("'Memorable' soʻzining maʼnosi nima?", listOf("Unutilgan", "Esda qolarli / Unutilmas", "Zerikarli", "Qimmat"), 1, "Memorable – esda qolarli."),
      QuizQuestion("Suhbat paytida toʻxtab qolsangiz qaysi iborani ishlatish tabiiy?", listOf("I don't know anything!", "Well, let me think for a moment...", "Be quiet!", "Shut up!"), 1, "'Well, let me think...' ravon maʼruzachilarning sevimli usuli."),
    ),
  )

  private fun createDay27() = DayCurriculum(
    dayNumber = 27,
    level = CefrLevel.A2,
    titleUz = "Sayohat, Aeroport & Yoʻnalish Soʻrash",
    objectiveUz = "Chet elda adashib qolmaslik: yoʻnalish soʻrash, aeroportda registratsiyadan oʻtish va xaritalar bilan ishlash.",
    vocabList = listOf(
      WordItem("Destination", "Manzil", "/ˌdestɪˈneɪʃn/", "noun", "What is your final destination?", "Yakuniy manzilingiz qayer?", CefrLevel.A2, "holiday destination"),
      WordItem("Boarding pass", "Samolyotga chiqish taloni", "/ˈbɔːdɪŋ pɑːs/", "noun", "Please show your boarding pass.", "Iltimos, samolyotga chiqish taloningizni koʻrsating.", CefrLevel.A2, "print boarding pass"),
      WordItem("Direction", "Yoʻnalish", "/daɪˈrekʃn/", "noun", "Could you give me directions to the station?", "Vokzalga borish yoʻnalishini tushuntira olasizmi?", CefrLevel.A2, "ask for directions"),
      WordItem("Convenient", "Qulay", "/kənˈviːniənt/", "adjective", "The subway is very convenient.", "Metro juda qulay.", CefrLevel.A2, "convenient location"),
      WordItem("Luggage", "Yuk / Bagaj", "/ˈlʌɡɪdʒ/", "noun", "Do you have any checked luggage?", "Topshiradigan yukingiz bormi?", CefrLevel.A2, "carry-on luggage"),
    ),
    listening = ListeningDialogue(
      titleEn = "Airport Check-in Desk",
      titleUz = "Aeroportda roʻyxatdan oʻtish",
      situationUz = "Yoʻlovchi London reysiga bagaj topshirib, talab qilingan joyni tanlamoqda.",
      lines = listOf(
        DialogueLine("Agent", "Good afternoon. Passport and ticket, please.", "Xayrli kun. Pasport va chiptangizni bering, iltimos."),
        DialogueLine("Passenger", "Here you go. Can I get a window seat, please?", "Marhamat. Deraza yonidagi oʻrindiqni olsam boʻladimi?"),
        DialogueLine("Agent", "Let me check. Yes, seat 14A is open. Place your bag on the scale.", "Tekshirib koʻray. Ha, 14A oʻrindiq boʻsh. Yukingizni taroziga qoʻying."),
        DialogueLine("Passenger", "Sure. What gate is it boarding at?", "Albatta. Qaysi chiqish eshigi (gate) orqali chiqiladi?"),
        DialogueLine("Agent", "Gate 22. Boarding starts at 3:30 PM. Have a pleasant flight!", "22-chi eshik. Samolyotga chiqish 15:30 da boshlanadi. Parvozingiz yoqimli oʻtsin!"),
      ),
      comprehensionTipUz = "'Here you go' – biror narsani birovga uzatganda aytiladigan eng xushmuomala ibora."),
    speaking = SpeakingDrill(
      warmUpTitle = "Yoʻnalish koʻrsatish intonatsiyasi (Turn left, turn right, go straight)",
      tongueTwister = "Travelers travel to tremendous territories tirelessly.",
      tongueTwisterUz = "T va R tovushlarini ravonlashtirish.",
      roleplayPromptUz = "Begona shaharda muzeyni qidiryapsiz. Yoʻlovchidan yoʻl soʻrang.",
      roleplayDialog = listOf(
        DialogueLine("Pedestrian", "Excuse me, do you need help?", "Kechirasiz, sizga yordam kerakmi?"),
        DialogueLine("You", "Yes, excuse me. How can I get to the National Museum?", "Ha, kechirasiz. Milliy muzeyga qanday borsam boʻladi?"),
      ),
      impromptuTopicEn = "If you could travel anywhere tomorrow, where would you go?",
      impromptuTopicUz = "Ertaga istalgan mamlakatga borish imkoningiz boʻlsa, qayerga borardingiz va nega?",
      anxietyTipUz = "Chet elda gapirayotganda odamlar sizning chet ellik ekanligingizni bilishadi va sizga yordam berishga intilishadi. Hech kim sizni sinovdan oʻtkazmaydi!",
    ),
    grammar = GrammarPatternItem(
      patternName = "Imperatives & Giving Directions (Yoʻl koʻrsatish)",
      formula = "Go straight / Turn left / Cross the street",
      explanationUz = "Yoʻnalish koʻrsatishda toʻgʻridan-toʻgʻri feʼl bilan boshlanadi: 'Go straight and turn right at the traffic lights'.",
      sampleSentences = listOf(
        "Go straight ahead for two blocks." to "Ikkita kvartal toʻgʻriga yuring.",
        "Turn right opposite the bank." to "Bank qarshisidan oʻngga buriling.",
        "Take the subway line two." to "Metroning ikkinchi liniyasiga chiqing.",
      ),
      scrambledWords = listOf("ahead", "Go", "straight", "please"),
      correctSentence = "Go straight ahead please",
    ),
    quizQuestions = listOf(
      QuizQuestion("Samolyotda deraza yonidagi oʻrindiq qanday ataladi?", listOf("Aisle seat", "Window seat", "Middle seat", "Back seat"), 1, "Window seat – deraza yonidagi joy."),
      QuizQuestion("'Convenient' soʻzining oʻzbekcha maʼnosi nima?", listOf("Qulay", "Xavfli", "Tor", "Qimmat"), 0, "Convenient – qulay va maʼqul."),
      QuizQuestion("Yoʻldan oʻtayotganda 'Kechirasiz' deb gap boshlash uchun qaysi ibora toʻgʻri?", listOf("Hey you!", "Excuse me", "Sorry to myself", "Stop there!"), 1, "Begona kishiga murojaat qilishda har doim 'Excuse me' ishlatiladi."),
      QuizQuestion("Chet elda sayohat paytida tilni eng tez ochish usuli qaysi?", listOf("Mehmonxonada oʻtirish", "Kafeda, doʻkonda va koʻchada mahalliy odamlar bilan suhbatlashish", "Faqat onlayn tarjimondan foydalanish", "Uxlash"), 1, "Jonli muloqot miyani tezkor reaksiya qilishga oʻrgatadi."),
    ),
  )

  private fun createDay28() = DayCurriculum(
    dayNumber = 28,
    level = CefrLevel.A2,
    titleUz = "Restoran, Taomlar & Maxsus Istaklar",
    objectiveUz = "Menyuni erkin tushunish, allergiyalar haqida ogohlantirish va hisobni toʻlash.",
    vocabList = listOf(
      WordItem("Delicious", "Juda mazali", "/dɪˈlɪʃəs/", "adjective", "This soup is absolutely delicious.", "Bu shoʻrva haqiqatan ham juda mazali.", CefrLevel.A2, "taste delicious"),
      WordItem("Allergy", "Allergiya", "/ˈælədʒi/", "noun", "I have an allergy to peanuts.", "Menda yeryongʻoqqa allergiya bor.", CefrLevel.A2, "food allergy"),
      WordItem("Beverage", "Ichimlik", "/ˈbevərɪdʒ/", "noun", "Would you like a cold beverage?", "Muzdek ichimlik xohlaysizmi?", CefrLevel.A2, "hot beverage"),
      WordItem("Split the bill", "Hisobni boʻlib toʻlamoq", "/splɪt ðə bɪl/", "phrase", "Can we split the bill?", "Hisobni oʻzaro teng boʻlib toʻlasak boʻladimi?", CefrLevel.A2, "let's split"),
      WordItem("Tip", "Choychaqa", "/tɪp/", "noun", "Did you leave a tip for the waiter?", "Ofitsiantga choychaqa qoldirdingizmi?", CefrLevel.A2, "leave a generous tip"),
    ),
    listening = ListeningDialogue(
      titleEn = "Dinner at an Italian Bistro",
      titleUz = "Italiya restoranida kechki ovqat",
      situationUz = "Doʻstlar kechki ovqat paytida taom tanlab, uning tarkibini soʻramoqda.",
      lines = listOf(
        DialogueLine("Waiter", "Are you ready to order, or do you need a few more minutes?", "Buyurtma berishga tayyormisiz yoki yana bir necha daqiqa kerakmi?"),
        DialogueLine("Guest", "We are ready. Does the seafood pasta contain garlic?", "Biz tayyormiz. Dengiz mahsulotli pastada sarimsoq bormi?"),
        DialogueLine("Waiter", "Yes, it does. But our chef can prepare it without garlic for you.", "Ha, bor. Lekin oshpazimiz uni siz uchun sarimsoqsiz tayyorlab berishi mumkin."),
        DialogueLine("Guest", "That would be wonderful! And a sparkling water, please.", "Bu ajoyib boʻlardi! Va gazlangan suv ham bering, iltimos."),
      ),
      comprehensionTipUz = "'That would be wonderful' – taklifni bajonidil qabul qilishda ajoyib ifoda.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Taʼm va hislarni ifodalovchi ohangdorlik",
      tongueTwister = "Betty Botter bought some butter, but she said the butter's bitter.",
      tongueTwisterUz = "B va T tovushlari ustida ishlash.",
      roleplayPromptUz = "Restoranda ofitsiantdan taom tavsiya qilishni va hisob-kitob olib kelishni soʻrang.",
      roleplayDialog = listOf(
        DialogueLine("Waiter", "Can I bring you anything else?", "Yana biror narsa keltiraymi?"),
        DialogueLine("You", "No, thank you. Could we have the bill, please?", "Yoʻq, rahmat. Hisobni olib kelsangiz, iltimos."),
      ),
      impromptuTopicEn = "What is your traditional national dish and how is it made?",
      impromptuTopicUz = "Oʻzbek milliy taomingiz (masalan, palov) va uni qanday tayyorlanishi haqida 1 daqiqa gapiring.",
      anxietyTipUz = "Restoranda yoki hayotda gapirayotganda xatolaringizga emas, boshqalarga yoqimli taassurot qoldirayotganingizga ishoning.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Would like + Noun / To + Verb (Madaniy xohish)",
      formula = "I would like + [noun] / I would like to + [verb]",
      explanationUz = "'I want' oʻrniga 'I would like' (I'd like) ishlatish madaniyatli va hurmatli muloqot belgisidir.",
      sampleSentences = listOf(
        "I would like to try the specialty of the house." to "Restoranning maxsus taomini tatib koʻrmoqchiman.",
        "We would like a table near the window." to "Deraza yonidagi stolni xohlardik.",
        "Would you like anything to drink?" to "Ichishga biror narsa xohlaysizmi?",
      ),
      scrambledWords = listOf("like", "water", "would", "I", "some"),
      correctSentence = "I would like some water",
    ),
    quizQuestions = listOf(
      QuizQuestion("'Split the bill' iborasi nimani anglatadi?", listOf("Bitta odam hammasini toʻlashi", "Hisobni oʻzaro teng boʻlib toʻlash", "Pul toʻlamasdan ketish", "Chipta sotib olish"), 1, "Split the bill – hisobni boʻlib toʻlash."),
      QuizQuestion("'I want coffee' jumlasining eng xushmuomala varianti qaysi?", listOf("Give coffee!", "I would like a coffee, please.", "Coffee right now!", "Bring me coffee."), 1, "'I would like a coffee, please' – eng toʻgʻri va muloyim variant."),
      QuizQuestion("Taom juda mazali ekanligini aytish uchun qaysi soʻz mos keladi?", listOf("Horrible", "Delicious", "Bitter", "Cold"), 1, "Delicious – juda mazali."),
      QuizQuestion("Ingliz tilida milliy taomlarni tushuntirish nimaga yordam beradi?", listOf("Hech narsaga", "Xorijliklar bilan suhbatda mavzu topish va oʻz madaniyatini gʻurur bilan soʻzlashga", "Charchashga", "Pul tejashga"), 1, "Oʻz madaniyati haqida gapirish nutq ravonligini oshirishning eng yaxshi usulidir."),
    ),
  )

  private fun createDay51() = DayCurriculum(
    dayNumber = 51,
    level = CefrLevel.B1,
    titleUz = "B1 Intermediate: Oʻz Fikrini Erkin Himoya Qilish",
    objectiveUz = "Munozaralarda qatnashish, oʻz nuqtai nazarini 'In my opinion' va 'From my perspective' orqali asoslash.",
    vocabList = listOf(
      WordItem("Perspective", "Nuqtai nazar / Qarash", "/pəˈspektɪv/", "noun", "From my perspective, hard work pays off.", "Mening nuqtai nazarimdan, tinimsiz mehnat oʻz mevasini beradi.", CefrLevel.B1, "broad perspective"),
      WordItem("Convincing", "Ishonarli", "/kənˈvɪnsɪŋ/", "adjective", "Her argument was totally convincing.", "Uning dalili mutlaqo ishonarli edi.", CefrLevel.B1, "convincing evidence"),
      WordItem("Consequence", "Oqibat / Natija", "/ˈkɒnsɪkwəns/", "noun", "Every decision has a consequence.", "Har bir qarorning oʻz oqibati bor.", CefrLevel.B1, "face consequences"),
      WordItem("Significant", "Muhim / Ahamiyatli", "/sɪɡˈnɪfɪkənt/", "adjective", "This is a significant milestone for us.", "Bu biz uchun muhim marra.", CefrLevel.B1, "significant impact"),
      WordItem("Articulate", "Fikrni ravon ifoda etmoq", "/ɑːˈtɪkjuleɪt/", "verb", "He articulated his ideas clearly.", "U oʻz gʻoyalarini aniq va ravon bayon qildi.", CefrLevel.B1, "articulate thoughts"),
    ),
    listening = ListeningDialogue(
      titleEn = "Debating Remote vs Office Work",
      titleUz = "Masofaviy va ofisdagi ish bahsi",
      situationUz = "Ikki mutaxassis masofadan ishlashning afzalliklari va kamchiliklarini tahlil qilmoqda.",
      lines = listOf(
        DialogueLine("Farhod", "In my opinion, remote work saves hours of commuting every week.", "Mening fikrimcha, masofadan ishlash har hafta yoʻldagi soatlarni tejaydi."),
        DialogueLine("Sophie", "I see your point, but on the other hand, in-person collaboration sparks more creative ideas.", "Fikringizni tushunaman, lekin boshqa tomondan, yuzma-yuz hamkorlik koʻproq ijodiy gʻoyalarni uygʻotadi."),
        DialogueLine("Farhod", "That is true to some extent, but a hybrid model provides the perfect balance.", "Bu maʼlum darajada toʻgʻri, lekin gibrid model mukammal muvozanatni taʼminlaydi."),
        DialogueLine("Sophie", "I totally agree with that conclusion!", "Men bu xulosaga toʻliq qoʻshilaman!"),
      ),
      comprehensionTipUz = "'I see your point, but on the other hand...' – bu qarshi fikrni muloyim bildirishning B1 andozasidir.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Argumenlar ohangi va urgʻu bilan gapirish",
      tongueTwister = "Specifically speaking, sophisticated speakers simplify speech.",
      tongueTwisterUz = "S va P tovushlarining nutqdagi aniqligi.",
      roleplayPromptUz = "Doʻstingiz kitob oʻqishdan koʻra faqat video koʻrish yaxshiroq deydi. Oʻz fikringizni bildiring.",
      roleplayDialog = listOf(
        DialogueLine("Friend", "Videos are much faster than reading books, right?", "Videolar kitob oʻqishdan ancha tezroq, shunday emasmi?"),
        DialogueLine("You", "While videos are visual, reading develops deeper critical thinking and imagination.", "Videolar koʻrgazmali boʻlsa-da, mutolaa chuqur tanqidiy fikrlash va tasavvurni rivojlantiradi."),
      ),
      impromptuTopicEn = "Should schools teach financial literacy and public speaking?",
      impromptuTopicUz = "Maktablarda moliyaviy savodxonlik va omma oldida gapirish oʻqitilishi kerakmi? 1 daqiqa asoslang.",
      anxietyTipUz = "Fikringizga qarshi chiqishsa, bu sizga hujum emas, balki qiziqarli munozara. Oʻzingizni erkin va xotirjam tuting.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Expressing Opinions & Concession (While / Although)",
      formula = "Although / While + [Clause 1], [Clause 2]",
      explanationUz = "Ikki xil qarashni bir gapda chiroyli bogʻlash uchun 'Although' (garchi ... boʻlsa ham) ishlatiladi.",
      sampleSentences = listOf(
        "Although English seemed difficult at first, I am enjoying it now." to "Garchi ingliz tili boshida qiyin koʻringan boʻlsa-da, hozir undan zavqlanmoqdaman.",
        "While technology is helpful, human interaction is irreplaceable." to "Texnologiya foydali boʻlsa-da, insoniy muloqotning oʻrnini bosa olmaydi.",
        "In my honest opinion, daily practice is non-negotiable." to "Mening samimiy fikrimcha, kunlik mashgʻulot bahslashilmaydigan zaruratdir.",
      ),
      scrambledWords = listOf("opinion", "my", "practice", "In", "matters"),
      correctSentence = "In my opinion practice matters",
    ),
    quizQuestions = listOf(
      QuizQuestion("'In my opinion' iborasi qachon ishlatiladi?", listOf("Vaqtni soʻrashda", "Oʻz shaxsiy fikrini bildirishda", "Kechirim soʻrashda", "Salomlashishda"), 1, "Oʻz fikrini bildirishda 'In my opinion' klassik vositadir."),
      QuizQuestion("Boshqa odamning fikrini tushunib, lekin boshqacha qarash bildirish uchun nima deyiladi?", listOf("You are wrong!", "I see your point, but...", "Shut up!", "No way!"), 1, "'I see your point, but...' – eng madaniyatli va diplomatik ifoda."),
      QuizQuestion("'Significant' soʻzining maʼnosi nima?", listOf("Arzimas", "Juda muhim va ahamiyatli", "Kulgili", "Eski"), 1, "Significant – muhim ahamiyatga ega."),
      QuizQuestion("B1 darajasida eng asosiy eʼtibor nimaga qaratilishi kerak?", listOf("Yolgʻiz soʻzlarni yodlashga", "Fikrlarni qoʻshma gaplar orqali ravon va ulab ifodalashga", "Jim turishga", "Imtihon topshirmaslikka"), 1, "Murakkab fikrlarni ravon bogʻlash B1 darajasining yuragi hisoblanadi."),
    ),
  )

  private fun createDay52() = DayCurriculum(
    dayNumber = 52,
    level = CefrLevel.B1,
    titleUz = "Ish Intervyusi & Kasbiy Rezyume Taqdimoti",
    objectiveUz = "Xalqaro kompaniyalarda ish intervyusidan muvaffaqiyatli oʻtish, kuchli tomonlar va tajribani tushuntirish.",
    vocabList = listOf(
      WordItem("Strengths", "Kuchli tomonlar", "/streŋkθs/", "noun", "One of my greatest strengths is problem-solving.", "Mening eng kuchli tomonlarimdan biri – muammolarni hal qila olishim.", CefrLevel.B1, "core strengths"),
      WordItem("Collaborate", "Hamkorlik qilmoq", "/kəˈlæbəreɪt/", "verb", "I collaborate well with diverse teams.", "Men turli xil jamoalar bilan aʼlo darajada hamkorlik qilaman.", CefrLevel.B1, "collaborate closely"),
      WordItem("Accomplish", "Muvaffaqiyatli bajarmoq", "/əˈkʌmplɪʃ/", "verb", "What did you accomplish in your previous role?", "Oldingi vazifangizda qanday yutuqlarga erishgansiz?", CefrLevel.B1, "accomplish a mission"),
      WordItem("Adaptable", "Moslashuvchan", "/əˈdæptəbl/", "adjective", "I am adaptable in fast-changing environments.", "Men tez oʻzgaruvchan muhitda moslashuvchanman.", CefrLevel.B1, "highly adaptable"),
      WordItem("Opportunity", "Imkoniyat", "/ˌɒpəˈtjuːnəti/", "noun", "Thank you for this career opportunity.", "Ushbu kasbiy imkoniyat uchun tashakkur.", CefrLevel.B1, "golden opportunity"),
    ),
    listening = ListeningDialogue(
      titleEn = "Job Interview at a Global Firm",
      titleUz = "Xalqaro kompaniyada ish suhbati",
      situationUz = "Kompaniya rahbari nomzodning tajribasi va jamoada ishlash qobiliyatini tekshirmoqda.",
      lines = listOf(
        DialogueLine("Interviewer", "Tell me about a time you handled a difficult project.", "Menga qiyin loyihani uddalagan vaqtingiz haqida aytib bering."),
        DialogueLine("Candidate", "In my last role, we had a tight deadline. I reorganized our workflow and motivated the team.", "Oxirgi ishimda muddat juda qisqa edi. Men ish oqimini qayta tashkil qildim va jamoani ruhlantirdim."),
        DialogueLine("Interviewer", "Impressive. How do you handle constructive criticism?", "Ajoyib. Konstruktiv tanqidni qanday qabul qilasiz?"),
        DialogueLine("Candidate", "I view feedback as an opportunity for continuous improvement.", "Men fikr-mulohazalarni doimiy oʻsish imkoniyati deb bilaman."),
      ),
      comprehensionTipUz = "'Constructive criticism' – xatolarni toʻgʻrilashga yoʻnaltirilgan sogʻlom tanqid.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Ishonchli va professional nutq ohangi (Deep chest voice)",
      tongueTwister = "Professional personnel produce purposeful proposals promptly.",
      tongueTwisterUz = "P va R tovushlarini ravon professional talaffuz qilish.",
      roleplayPromptUz = "Oʻzingizning eng katta yutugʻingiz va qaysi sohada kuchli ekanligingizni intervyuda gapirib bering.",
      roleplayDialog = listOf(
        DialogueLine("Recruiter", "Why should we hire you for this position?", "Nima uchun aynan sizni bu lavozimga qabul qilishimiz kerak?"),
        DialogueLine("You", "Because I bring strong technical skills, dedication, and an eagerness to deliver measurable results.", "Chunki men kuchli texnik koʻnikmalar, fidoyilik va oʻlchanadigan natijalar berish ishtiyoqini olib kelaman."),
      ),
      impromptuTopicEn = "Where do you see yourself professionally in three years?",
      impromptuTopicUz = "Uch yildan soʻng oʻzingizni professional sohada qayerda koʻrasiz? 1 daqiqa gapiring.",
      anxietyTipUz = "Intervyuda savol berilganda darhol shoshib javob bermang. 2 soniya oʻylab, 'That's a thoughtful question' deb boshlasangiz, miya tinchlanadi.",
    ),
    grammar = GrammarPatternItem(
      patternName = "Present Perfect with Experience (Tajribani koʻrsatish)",
      formula = "I have + [Past Participle] + for / since / in my career",
      explanationUz = "Hayotiy yoki kasbiy tajribani aytishda vaqt aniq aytilmaganda Present Perfect ishlatiladi: 'I have led multiple teams'.",
      sampleSentences = listOf(
        "I have worked in international environments for five years." to "Men besh yil xalqaro muhitda ishlaganman.",
        "She has successfully completed all certifications." to "U barcha sertifikatlarni muvaffaqiyatli yakunlagan.",
        "We have developed modern software solutions." to "Biz zamonaviy dasturiy yechimlarni ishlab chiqqanmiz.",
      ),
      scrambledWords = listOf("skills", "developed", "have", "I", "strong"),
      correctSentence = "I have developed strong skills",
    ),
    quizQuestions = listOf(
      QuizQuestion("Ish intervyusida 'Why should we hire you?' savoliga qanday javob berish toʻgʻri?", listOf("Chunki menga pul kerak", "Oʻz kuchli tomonlaringiz va kompaniyaga qanday foyda keltirishingizni asoslab", "Bilmayman, oʻzingiz bilasiz", "Boshqa ish topolmadim"), 1, "Kompaniyaga keltiradigan aniq foydangizni koʻrsatish eng toʻgʻri yoʻldir."),
      QuizQuestion("'Adaptable' xodim qanday xislatga ega?", listOf("Oʻzgarishlardan qoʻrqadigan", "Yangi sharoitga tez va samarali moslashadigan", "Doim kech qoladigan", "Ishni tashlab ketadigan"), 1, "Adaptable – moslashuvchan va chidamli."),
      QuizQuestion("Kasbiy tajribani ifodalashda qaysi grammatik zamon afzal?", listOf("Present Perfect", "Past Continuous", "Future Continuous", "Imperative"), 0, "Present Perfect yutuqlar va tajribani koʻrsatishda idealdir."),
      QuizQuestion("Intervyuda hayajonni yengishning siri nima?", listOf("Yodlangan javoblarni robotdek aytish", "Suhbatdoshga teng huquqli professional hamkor sifatida qarab muloqot qilish", "Koʻzni yumib olish", "Gapirmaslik"), 1, "Intervyu – imtihon emas, ikki tomonlama doʻstona biznes muloqotidir."),
    ),
  )

  private fun createDay76() = DayCurriculum(
    dayNumber = 76,
    level = CefrLevel.B2,
    titleUz = "B2 Upper-Intermediate: Debatlar, Idiyomalar & Jonli Sleng",
    objectiveUz = "Idiyomalarni nutqda tabiiy ishlatish, nozik maʼnolarni ilgʻash va murakkab gʻoyalarni ravon himoya qilish.",
    vocabList = listOf(
      WordItem("Compelling", "Oʻziga rom etuvchi / Rad etib boʻlmas", "/kəmˈpelɪŋ/", "adjective", "He presented a compelling argument.", "U oʻziga rom etuvchi kuchli dalil keltirdi.", CefrLevel.B2, "compelling evidence"),
      WordItem("Bite the bullet", "Qiyin vaziyatga mardona chidamoq", "/baɪt ðə ˈbʊlɪt/", "idiom", "I had to bite the bullet and give the speech.", "Men noiloj mardona chidab nutq soʻzlashimga toʻgʻri keldi.", CefrLevel.B2, "time to bite the bullet"),
      WordItem("Resilience", "Qatʼiyat / Sinmas iroda", "/rɪˈzɪliəns/", "noun", "Mental resilience is essential for growth.", "Ruhiy qatʼiyat oʻsish uchun juda muhim.", CefrLevel.B2, "build resilience"),
      WordItem("Spontaneous", "Oʻz-oʻzidan paydo boʻladigan / Samimiy", "/spɒnˈteɪniəs/", "adjective", "Her speech was spontaneous and natural.", "Uning nutqi tabiiy va samimiy edi.", CefrLevel.B2, "spontaneous applause"),
      WordItem("Break the ice", "Suhbatdagi begonasirashni yoʻqotmoq", "/breɪk ði aɪs/", "idiom", "He told a joke to break the ice.", "U muzni eritish va suhbatni boshlash uchun hazil qildi.", CefrLevel.B2, "break the ice"),
    ),
    listening = ListeningDialogue(
      titleEn = "Leadership and Public Perception",
      titleUz = "Yetakchilik va omma oldidagi obroʻ",
      situationUz = "Ikki tadbirkor zamonaviy yetakchilikda hissiy aql (EQ) va ravon notiqlikning oʻrni haqida bahslashmoqda.",
      lines = listOf(
        DialogueLine("Arthur", "Technical competence gets you in the door, but articulate communication makes you a leader.", "Texnik bilim sizga eshikni ochadi, lekin ravon muloqot sizni yetakchiga aylantiradi."),
        DialogueLine("Elena", "Precisely. If you cannot convey your vision, nobody will rally behind it.", "Aynan shunday. Agar oʻz tasavvuringizni yetkaza olmasangiz, hech kim ortingizdan ergashmaydi."),
        DialogueLine("Arthur", "And genuine resilience shines through especially when obstacles arise.", "Va haqiqiy iroda toʻsiqlar chiqqanda yanada yorqinroq koʻrinadi."),
        DialogueLine("Elena", "Well said. It is all about authenticity and staying composed under pressure.", "Juda toʻgʻri aytildi. Asosiysi – samimiylik va bosim ostida ham xotirjamlikni saqlash."),
      ),
      comprehensionTipUz = "'Rally behind' – biror gʻoya yoki yetakchi atrofida birlashish maʼnosida keladi.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Tezkor spontan reaksiya va intonatsiya oʻyinlari",
      tongueTwister = "The thirty-three thieves thought that they thrilled the throne throughout Thursday.",
      tongueTwisterUz = "TH tovushining tishlar orasidan ravon chiqishi.",
      roleplayPromptUz = "Kutilmagan vaziyatda jamoaga 1 daqiqalik motivatsion nutq soʻzlang.",
      roleplayDialog = listOf(
        DialogueLine("Colleague", "Our main server crashed! What should we do?", "Asosiy serverimiz quladi! Nima qilamiz?"),
        DialogueLine("You", "Stay calm, everyone. We have solid backups. Let's execute our disaster protocol immediately.", "Hammasi xotirjam boʻlsin. Bizda mustahkam nusxalar bor. Keling, favqulodda rejamizni darhol ishga tushiramiz."),
      ),
      impromptuTopicEn = "Is failure the most powerful catalyst for long-term success?",
      impromptuTopicUz = "Magʻlubiyat uzoq muddatli muvaffaqiyatning eng kuchli omilimi? 1 daqiqa ichida oʻz mulohazangizni bildiring.",
      anxietyTipUz = "B2 darajasida siz tildan instrument sifatida foydalanasiz. Oʻzingizni til oʻrganuvchi emas, balki inglizcha soʻzlashuvchi deb his qiling!",
    ),
    grammar = GrammarPatternItem(
      patternName = "Conditionals Type 2 & 3 (Gipotetik vaziyatlar)",
      formula = "If + Past Simple, would + Verb | If + Past Perfect, would have + V3",
      explanationUz = "Amalga oshishi dargumon yoki oʻtib ketgan imkoniyatlarni tahlil qilishda Conditionals qoʻllaniladi.",
      sampleSentences = listOf(
        "If I had not practiced daily, I would not speak so fluently now." to "Agar har kuni mashq qilmaganimda, hozir bunchalik ravon gapirmagan boʻlardim.",
        "If we embraced uncertainty, fear would lose its power." to "Agar noaniqlikni qabul qilsak, qoʻrquv oʻz kuchini yoʻqotadi.",
        "What would you do if failure were impossible?" to "Agar yutqazish imkonsiz boʻlsa, nima qilgan boʻlardingiz?",
      ),
      scrambledWords = listOf("would", "fear", "vanish", "Confidence", "make"),
      correctSentence = "Confidence would make fear vanish",
    ),
    quizQuestions = listOf(
      QuizQuestion("'Bite the bullet' idiyomasi nimani anglatadi?", listOf("Oʻqni tishlab olish", "Qiyin, lekin muqarrar ishni mardona yengib oʻtish", "Tez yugurish", "Uxlab qolish"), 1, "Bite the bullet – qiyinchilikka chidab, vazifani bajarish."),
      QuizQuestion("'Break the ice' iborasi qaysi holatda mos keladi?", listOf("Muzlatgichni tozalashda", "Yangi tanishuvda noqulay sukunatni yoʻqotishda", "Qishda sayr qilishda", "Urush boshlashda"), 1, "Break the ice – dastlabki begonasirash va noqulaylikni yoʻqotish."),
      QuizQuestion("'Spontaneous' soʻzining antonimi qaysi?", listOf("Rejalashtirilgan (Planned)", "Tabiiy", "Qiziq", "Ochiq"), 0, "Spontaneous – kutilmagan, tabiiy; aksi esa planned – oldindan rejalashtirilgan."),
      QuizQuestion("B2 darajasidagi notiq qanday xislatga ega?", listOf("Faqat oddiy gaplar aytadi", "Oʻz fikrini ravon, idiyomalar va aniq intonatsiya bilan yetkaza oladi", "Xato qilsa yigʻlaydi", "Hech gapirmaydi"), 1, "B2 darajasi erkin, ishonchli va badiiy nutq demakdir."),
    ),
  )

  private fun createDay85() = DayCurriculum(
    dayNumber = 85,
    level = CefrLevel.B2,
    titleUz = "Omma Oldida Nutq Soʻzlash & Notiqlik Mahorati",
    objectiveUz = "TED-talk uslubida nutq tuzish, tinglovchilar diqqatini ushlab turish va sahna hayajonini quvvatga aylantirish.",
    vocabList = listOf(
      WordItem("Charisma", "Joziba / Notiqlik sehri", "/kəˈrɪzmə/", "noun", "Great leaders lead with authenticity and charisma.", "Buyuk yetakchilar samimiylik va joziba bilan yetakchilik qiladi.", CefrLevel.B2, "natural charisma"),
      WordItem("Captivate", "Maftun etmoq / Diqqatni tortmoq", "/ˈkæptɪveɪt/", "verb", "Her story captivated the whole auditorium.", "Uning hikoyasi butun zalni maftun etdi.", CefrLevel.B2, "captivate audience"),
      WordItem("Rhetorical", "Ritorik / Notiqlik sanʼatiga oid", "/rɪˈtɒrɪkl/", "adjective", "He used a rhetorical question to provoke thought.", "U fikrlashga undash uchun ritorik savoldan foydalandi.", CefrLevel.B2, "rhetorical question"),
      WordItem("Unwavering", "Qatʼiy / Tebranmas", "/ʌnˈweɪvərɪŋ/", "adjective", "Her dedication was unwavering.", "Uning fidoyiligi qatʼiy va tebranmas edi.", CefrLevel.B2, "unwavering focus"),
      WordItem("Metaphor", "Metofora / Oʻxshatish", "/ˈmetəfə(r)/", "noun", "Language is a bridge between different cultures.", "Til – turli madaniyatlar oʻrtasidagi koʻprikdir.", CefrLevel.B2, "powerful metaphor"),
    ),
    listening = ListeningDialogue(
      titleEn = "Mastering the Stage",
      titleUz = "Sahnani zabt etish sirlari",
      situationUz = "Xalqaro spiker yosh notiqlarga sahnada hayajonni kuchga aylantirish boʻyicha mahorat darsi oʻtmoqda.",
      lines = listOf(
        DialogueLine("Speaker", "Stage fright is adrenaline. Do not fight it; channel it into enthusiasm!", "Sahna qoʻrquvi – bu adrenalin. U bilan kurashmang; uni ishtiyoqqa aylantiring!"),
        DialogueLine("Trainee", "How do you recover if you lose your train of thought?", "Agar fikringiz chalkashib ketsa, qanday qilib oʻzingizga kelasiz?"),
        DialogueLine("Speaker", "Take a slow breath, smile, and ask the audience a thought-provoking question.", "Sekin nafas oling, jilmaying va tinglovchilarga chuqur oʻyga toldiruvchi savol bering."),
        DialogueLine("Trainee", "That transforms vulnerability into an interactive moment!", "Bu zaiflikni interaktiv va jonli lahzaga aylantiradi!"),
      ),
      comprehensionTipUz = "'Lose your train of thought' – fikr ipini yoʻqotib qoʻyish maʼnosidagi chiroyli ibora.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Diyafragma, koʻkrak qafasi va nigohni tik tutish",
      tongueTwister = "Unique New York, unique New York, you know you need unique New York.",
      tongueTwisterUz = "Notiqlar uchun klassik artikulyatsiya mashqi.",
      roleplayPromptUz = "Dunyo boʻylab ingliz tili oʻrganayotgan yoshlarga 1 daqiqalik ilhomlantiruvchi TED-talk nutqi soʻzlang.",
      roleplayDialog = listOf(
        DialogueLine("Host", "Please welcome our keynote speaker to the stage!", "Iltimos, asosiy spikerimizni sahnaga taklif eting!"),
        DialogueLine("You", "Ladies and gentlemen, fear is merely an illusion. When you speak from the heart, words find their own way.", "Xonimlar va janoblar, qoʻrquv – shunchaki xayol. Yurakdan gapirsangiz, soʻzlar oʻz yoʻlini topadi."),
      ),
      impromptuTopicEn = "What is the single most important lesson life has taught you?",
      impromptuTopicUz = "Hayot sizga bergan eng muhim bitta saboq qaysi? 60 soniya davomida taʼsirchan qilib ayting.",
      anxietyTipUz = "Siz zaldagi hamma insonlar sizning muvaffaqiyat qozonishingizni xohlashini bilasizmi? Ular sizga muxlislik qilmoqda, dushman emas!",
    ),
    grammar = GrammarPatternItem(
      patternName = "Inversion for Dramatic Emphasis (Notiqlik uslubi)",
      formula = "Never before / Seldom + Auxiliary + Subject + Verb",
      explanationUz = "Nutqqa kuchli taʼsir va joziba berish uchun inkor iboralari gap boshida kelib, inversiya hosil qiladi.",
      sampleSentences = listOf(
        "Never have I seen such remarkable dedication." to "Hech qachon bunday ajoyib fidoyilikni koʻrmaganman.",
        "Not only did we practice, but we also conquered our hesitation." to "Biz nafaqat mashq qildik, balki ikkilanishlarimizni ham yengdik.",
        "Seldom does an opportunity like this present itself." to "Bunday imkoniyat kamdan-kam holatda oʻzini namoyon qiladi.",
      ),
      scrambledWords = listOf("have", "Never", "I", "felt", "so", "confident"),
      correctSentence = "Never have I felt so confident",
    ),
    quizQuestions = listOf(
      QuizQuestion("Nutq soʻzlashda 'Metaphor' nimaga xizmat qiladi?", listOf("Vaqtni choʻzishga", "Murakkab fikrni tushunarli oʻxshatish orqali tinglovchi qalbiga yetkazishga", "Grammatikani buzishga", "Ovozni pasaytirishga"), 1, "Metafora – fikrni yorqin va unutilmas qiluvchi badiiy vosita."),
      QuizQuestion("Sahnada toʻsatdan fikr yoʻqolib qolsa eng yaxshi chora nima?", listOf("Sahnadan qochib ketish", "Xotirjam nafas olib, tinglovchiga savol berish yoki mavzuga qaytish", "Yigʻlash", "Jim turish"), 1, "Xotirjamlik va pauza tinglovchi oldida ishonchni yanada oshiradi."),
      QuizQuestion("'Charisma' soʻzi nimani anglatadi?", listOf("Qoʻrquv", "Kuchli shaxsiy joziba va ilhomlantirish qobiliyati", "Beparvolik", "Jismoniy quvvat"), 1, "Charisma – kishilarni oʻziga tortuvchi ichki joziba."),
      QuizQuestion("85-kunda erishilgan eng katta natija nima?", listOf("Faqat yangi kitob sotib olish", "Ingliz tilida fikrlash, erkin gapirish va har qanday hayajonni oʻz foydasiga aylantirish", "Hech qanday oʻzgarish yoʻqligi", "Grammatikani yodlab olish"), 1, "Hayajonsiz va erkin fikrlash – haqiqiy B2 darajasi gʻalabasidir!"),
    ),
  )

  private fun createDay90() = DayCurriculum(
    dayNumber = 90,
    level = CefrLevel.B2,
    titleUz = "90-Kun: Gʻalaba! B2 Master Sertifikati & Toʻliq Ravonlik",
    objectiveUz = "90 kunlik intensiv yoʻlni muvaffaqiyatli xulosalash, oʻz yutuqlarini sarhisob qilish va umrbod ingliz tilida erkin yashash.",
    vocabList = listOf(
      WordItem("Milestone", "Katta marra / Muhim bekat", "/ˈmaɪlstəʊn/", "noun", "We have reached a historic milestone.", "Biz tarixiy marraga yetib keldik.", CefrLevel.B2, "major milestone"),
      WordItem("Triumph", "Ulkan zafar / Gʻalaba", "/ˈtraɪʌmf/", "noun", "Consistency always ends in triumph.", "Doimiylik har doim gʻalaba bilan tugaydi.", CefrLevel.B2, "celebrate triumph"),
      WordItem("Fluency", "Ravonlik", "/ˈfluːənsi/", "noun", "Fluency is freedom of thought in another tongue.", "Ravonlik – boshqa tilda fikrlash erkinligidir.", CefrLevel.B2, "achieve fluency"),
      WordItem("Unstoppable", "Toʻxtatib boʻlmas", "/ʌnˈstɒpəbl/", "adjective", "With discipline, you become unstoppable.", "Intizom bilan sizni toʻxtatib boʻlmaydi.", CefrLevel.B2, "unstoppable drive"),
      WordItem("Lifelong", "Umrbod / Hayotiy", "/ˈlaɪflɒŋ/", "adjective", "English is now a lifelong superpower.", "Ingliz tili endi sizning umrbod super-kuchingizdir.", CefrLevel.B2, "lifelong learner"),
    ),
    listening = ListeningDialogue(
      titleEn = "Graduation and the New Beginning",
      titleUz = "90 kunlik marshrut xulosasi",
      situationUz = "Murabbiy va oʻquvchi 90 kun avvalgi 0 darajadan bugungi B2 darajasigacha bosib oʻtilgan yoʻlni sarhisob qilmoqda.",
      lines = listOf(
        DialogueLine("Mentor", "Dilshod, do you remember Day 1? You hesitated to say 'Hello there'.", "Dilshod, 1-kuningiz esingizdami? 'Hello there' deyishga ham ikkilangandingiz."),
        DialogueLine("Graduate", "Yes! I was so afraid of making mistakes. But now I think and express myself effortlessly in English.", "Ha! Xato qilishdan shunchalik qoʻrqardimki. Hozir esa inglizcha bemalol fikrlayman va soʻzlayman."),
        DialogueLine("Mentor", "150 minutes every day for 90 days. You proved that discipline conquers all barriers.", "90 kun davomida har kuni 150 daqiqa. Siz intizom barcha toʻsiqlarni yengishini isbotladingiz."),
        DialogueLine("Graduate", "This is not the finish line; it is my ticket to the global world!", "Bu marra chizigʻi emas; bu mening global dunyoga kirish chiptamdir!"),
      ),
      comprehensionTipUz = "'Ticket to the global world' – dunyo bilan erkin muloqot qilish ramzidir.",
    ),
    speaking = SpeakingDrill(
      warmUpTitle = "Gʻalaba nafas mashqi va cheksiz ishonch",
      tongueTwister = "Bravo, brilliant, brave bilingual breakthrough!",
      tongueTwisterUz = "B va R tovushlari bilan zafar shiori.",
      roleplayPromptUz = "Oʻzingizning 90 kunlik oʻsishingiz haqida ingliz tilida gʻalaba nutqi soʻzlang.",
      roleplayDialog = listOf(
        DialogueLine("World", "Who are you now?", "Endi siz kimsiz?"),
        DialogueLine("You", "I am a confident, fluent English speaker, ready to seize global opportunities!", "Men global imkoniyatlarni zabt etishga tayyor, oʻziga ishongan ravon ingliz tili sohibiman!"),
      ),
      impromptuTopicEn = "What is your next big dream now that you speak English fluently?",
      impromptuTopicUz = "Ingliz tilida ravon gapira olishingiz bilan keyingi eng katta maqsadingiz nima? 1 daqiqa soʻzlang.",
      anxietyTipUz = "Siz 90 kun ichida qoʻrquvni yengib, yangi shaxsga aylandingiz. Bu gʻalaba endi umrbod sizniki!",
    ),
    grammar = GrammarPatternItem(
      patternName = "Mastery Reflection (Future Continuous & Perfect)",
      formula = "I will be + V-ing | I will have achieved + Noun",
      explanationUz = "Kelajakda davom etadigan va yakunlanadigan ulkan rejalarni ifodalash sanʼati.",
      sampleSentences = listOf(
        "I will be using English in my global career every single day." to "Men global faoliyatimda har kuni ingliz tilidan foydalanib yashayman.",
        "I have officially unlocked the B2 Upper-Intermediate level." to "Men rasman B2 Upper-Intermediate darajasini qoʻlga kiritdim.",
        "Dedication always yields extraordinary transformations." to "Fidoyilik har doim ajoyib oʻzgarishlarni keltiradi.",
      ),
      scrambledWords = listOf("have", "achieved", "I", "my", "B2", "goal"),
      correctSentence = "I have achieved my B2 goal",
    ),
    quizQuestions = listOf(
      QuizQuestion("90 kunlik intensiv dasturning eng asosiy yutugʻi nima?", listOf("Faqat lugʻat toʻplash", "0 dan B2 gacha koʻtarilib, hayajonsiz va erkin ravon gapirish qobiliyatiga ega boʻlish", "Vaqt oʻtkazish", "Daftar toʻldirish"), 1, "Hayajonsiz, erkin va ravon nutq – dasturning oliy maqsadi."),
      QuizQuestion("'Milestone' soʻzining maʼnosi nima?", listOf("Tosh", "Katta tarixiy marra / Yutuq bekat", "Xato", "Tanaffus"), 1, "Milestone – katta tarixiy marra."),
      QuizQuestion("Ingliz tilini butun umr saqlab qolish uchun nima qilish kerak?", listOf("Tilni unutish", "Kundalik hayotda kitob oʻqish, podkastlar eshitish va gapirishni davom ettirish", "Hech narsa qilmaslik", "Faqat oʻzbekcha gapirish"), 1, "Til – tirik organizm, u bilan har kuni yashash kerak!"),
      QuizQuestion("Oʻzingizga boʻlgan ishonch qayerdan keladi?", listOf("Kutilmaganda", "Kunlik 150 daqiqalik intizomli mehnat va qilingan amaliyotdan", "Boshqalarning gapidan", "Omaddan"), 1, "Intizom har doim haqiqiy ishonchni tugʻdiradi!"),
    ),
  )

  private fun generateSyntheticDay(day: Int, level: CefrLevel): DayCurriculum {
    val levelPrefix = when (level) {
      CefrLevel.A1 -> "A1 Asoslar"
      CefrLevel.A2 -> "A2 Muloqot"
      CefrLevel.B1 -> "B1 Erkinlik"
      CefrLevel.B2 -> "B2 Mahorat"
    }

    val topics = listOf(
      "Kasbiy Muloqot & Hamkorlik",
      "Sogʻliq, Sport & Sogʻlom Turmush",
      "Texnologiya & Innovatsiyalar",
      "Doʻstlik & Jamiyat Hayoti",
      "Madaniyat, Sanʼat & Musiqa",
      "Biznes & Moliyaviy Qarorlar",
      "Muammolarni Hal Qilish Mahorati",
      "Oʻz-oʻzini Rivojlantirish & Kitobxonlik",
    )
    val topicTitle = topics[(day - 1) % topics.size]

    return DayCurriculum(
      dayNumber = day,
      level = level,
      titleUz = "$day-Kun: $topicTitle ($levelPrefix)",
      objectiveUz = "Bugungi 150 daqiqalik mashgʻulotda $topicTitle mavzusida soʻz boyligini kengaytiramiz, nutqni charxlaymiz va hayajonni yengamiz.",
      vocabList = listOf(
        WordItem("Determine", "Belgilamoq / Qatʼiy qaror qilmoq", "/dɪˈtɜːmɪn/", "verb", "Your habits determine your future.", "Odatlaringiz kelajagingizni belgilaydi.", level, "determine success"),
        WordItem("Efficient", "Samarali / Tejamkor", "/ɪˈfɪʃnt/", "adjective", "Find the most efficient solution.", "Eng samarali yechimni toping.", level, "highly efficient"),
        WordItem("Insight", "Chuqur tushuncha / Fikr", "/ˈɪnsaɪt/", "noun", "Thank you for sharing your valuable insight.", "Qimmatli fikringizni baham koʻrganingiz uchun rahmat.", level, "gain insight"),
        WordItem("Maintain", "Saqlab turmoq", "/meɪnˈteɪn/", "verb", "Maintain consistent focus throughout the day.", "Kun boʻyi doimiy diqqatni saqlab turing.", level, "maintain progress"),
        WordItem("Transform", "Butunlay oʻzgartirmoq", "/trænsˈfɔːm/", "verb", "Daily English practice transforms your thinking.", "Har kungi ingliz tili mashgʻuloti fikrlashingizni butunlay oʻzgartiradi.", level, "transform life"),
      ),
      listening = ListeningDialogue(
        titleEn = "Daily Life and Discussion",
        titleUz = "$topicTitle boʻyicha amaliy muloqot",
        situationUz = "Xalqaro forumda ikki ishtirokchi $topicTitle mavzusida fikr almashmoqda.",
        lines = listOf(
          DialogueLine("Alex", "How do you view today's topic on $topicTitle?", "Bugungi $topicTitle mavzusiga qanday qaraysiz?"),
          DialogueLine("You", "I believe consistent dedication is the real catalyst for breakthrough results.", "Menimcha, doimiy fidoyilik chinakam yorib chiqish natijalarining asosiy omilidir."),
          DialogueLine("Alex", "Well put! Small daily steps compound into massive achievements.", "Juda toʻgʻri! Kichik kunlik qadamlar ulkan yutuqlarga aylanadi."),
        ),
        comprehensionTipUz = "Spikerning soʻz urgʻulariga diqqat qiling va intonatsiyani takrorlang.",
      ),
      speaking = SpeakingDrill(
        warmUpTitle = "Diyafragmali 4-7-8 nafas va ohang erkinligi",
        tongueTwister = "Practice passionately, produce prosperity promptly.",
        tongueTwisterUz = "P va S tovushlarining artikulyatsiyasi.",
        roleplayPromptUz = "Oʻrtoqlaringizga $topicTitle haqida oʻz tajribangizni soʻzlab bering.",
        roleplayDialog = listOf(
          DialogueLine("Host", "What did you discover today?", "Bugun oʻzingiz uchun nimani kashf qildingiz?"),
          DialogueLine("You", "I realized that consistent practice brings unmatched fluency and self-confidence.", "Doimiy amaliyot tengsiz ravonlik va oʻziga ishonch keltirishini angladim."),
        ),
        impromptuTopicEn = "Speak about $topicTitle for 60 seconds without hesitation",
        impromptuTopicUz = "$topicTitle mavzusida 60 soniya davomida toʻxtamasdan, qoʻrquvsiz gapiring.",
        anxietyTipUz = "Xato qilib qoʻyishdan sira qoʻrqmang. Har bir yangi jumla sizni B2 darajasiga yaqinlashtiradi!",
      ),
      grammar = GrammarPatternItem(
        patternName = "Pattern: The more..., the more... (Qanchalik koʻp..., shunchalik...)",
        formula = "The + comparative, the + comparative",
        explanationUz = "Biror harakatning ortishi bilan natija ham oʻzgarishini ifodalash andozasi.",
        sampleSentences = listOf(
          "The more you speak, the more confident you become." to "Qanchalik koʻp gapirsangiz, shunchalik oʻzingizga ishonchingiz ortadi.",
          "The earlier you begin, the faster you achieve fluency." to "Qanchalik erta boshlasangiz, shunchalik tez ravonlikka erishasiz.",
          "The calmer your mind, the clearer your voice." to "Aql qanchalik sokin boʻlsa, ovoz shunchalik jarangdor boʻladi.",
        ),
        scrambledWords = listOf("more", "speak", "The", "you", "learn", "the", "more"),
        correctSentence = "The more you speak the more you learn",
      ),
      quizQuestions = listOf(
        QuizQuestion("Bugungi darsning asosiy maqsadi nima?", listOf("Hech narsa qilmaslik", "$topicTitle mavzusida ravon va ishonchli gapirish", "Yolgʻon gapirish", "Kech qolish"), 1, "Mavzu boʻyicha ravonlik va ishonchni mustahkamlash."),
        QuizQuestion("'Maintain' soʻzining maʼnosi nima?", listOf("Tashlab ketish", "Saqlab turmoq / Davom ettirmoq", "Buzmoq", "Yigʻlamoq"), 1, "Maintain – barqaror saqlab turmoq."),
        QuizQuestion("'The more you speak, the ___ you learn.' boʻsh joyga nima mos keladi?", listOf("most", "more", "better", "best"), 1, "'The more..., the more...' qolipiga asosan 'more' ishlatiladi."),
        QuizQuestion("Kunlik 150 daqiqalik intensiv reja qanday yakunlanadi?", listOf("Charchoq bilan", "Muvaffaqiyatli test va mustahkamlangan yutuq bilan", "Hammasini oʻchirib", "Bahslashuv bilan"), 1, "Har bir kun gʻalaba va oʻsish bilan xulosalanadi!"),
      ),
    )
  }
}
