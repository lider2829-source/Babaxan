package com.example.data.repository

import com.example.data.model.CefrLevel
import com.example.data.model.WordItem

object VocabularyBank {

  val allWords: List<WordItem> = listOf(
    // A1 Vocabulary
    WordItem("Ability", "Qobiliyat", "/əˈbɪləti/", "noun", "Everyone has the ability to learn languages.", "Har bir insonda til oʻrganish qobiliyati bor.", CefrLevel.A1, "natural ability"),
    WordItem("Achieve", "Erishmoq", "/əˈtʃiːv/", "verb", "You will achieve B2 level in 90 days.", "Siz 90 kunda B2 darajasiga erishasiz.", CefrLevel.A1, "achieve success"),
    WordItem("Action", "Harakat", "/ˈækʃn/", "noun", "Take action every single day.", "Har kuni harakat qiling.", CefrLevel.A1, "take action"),
    WordItem("Active", "Faol", "/ˈæktɪv/", "adjective", "Be an active listener in conversations.", "Suhbatlarda faol tinglovchi boʻling.", CefrLevel.A1, "active role"),
    WordItem("Advise", "Maslahat bermoq", "/ədˈvaɪz/", "verb", "I advise you to practice daily.", "Sizga har kuni mashq qilishni maslahat beraman.", CefrLevel.A1, "strongly advise"),
    WordItem("Afford", "Qurbi yetmoq", "/əˈfɔːd/", "verb", "I can afford this course.", "Bu kursga qurbim yetadi.", CefrLevel.A1, "can afford"),
    WordItem("Afraid", "Qoʻrqqan", "/əˈfreɪd/", "adjective", "Do not be afraid of making mistakes.", "Xato qilishdan qoʻrqmang.", CefrLevel.A1, "afraid of fear"),
    WordItem("Agreement", "Kelishuv", "/əˈɡriːmənt/", "noun", "We reached a mutual agreement.", "Biz oʻzaro kelishuvga erishdik.", CefrLevel.A1, "reach agreement"),
    WordItem("Aim", "Maqsad", "/eɪm/", "noun", "Our aim is complete fluency.", "Maqsadimiz – toʻliq ravonlik.", CefrLevel.A1, "primary aim"),
    WordItem("Allow", "Ruxsat bermoq", "/əˈlaʊ/", "verb", "Allow yourself to make mistakes and learn.", "Oʻzingizga xato qilish va oʻrganishga ruxsat bering.", CefrLevel.A1, "allow time"),
    WordItem("Ambitious", "Intiluvchan", "/æmˈbɪʃəs/", "adjective", "She is an ambitious student.", "U intiluvchan talaba.", CefrLevel.A1, "highly ambitious"),
    WordItem("Announce", "Eʼlon qilmoq", "/əˈnaʊns/", "verb", "They will announce the results tomorrow.", "Ertaga natijalarni eʼlon qilishadi.", CefrLevel.A1, "officially announce"),
    WordItem("Anxiety", "Xavotir / Hayajon", "/æŋˈzaɪəti/", "noun", "Breathing exercises calm your anxiety.", "Nafas mashqlari hayajoningizni bosadi.", CefrLevel.A1, "reduce anxiety"),
    WordItem("Appreciate", "Qadrlamoq", "/əˈpriːʃieɪt/", "verb", "I appreciate your help.", "Yordamingizni qadrlayman.", CefrLevel.A1, "greatly appreciate"),
    WordItem("Approach", "Yondashuv", "/əˈprəʊtʃ/", "noun", "We use a 150-minute intensive approach.", "Biz 150 daqiqalik intensiv yondashuvdan foydalanamiz.", CefrLevel.A1, "modern approach"),

    // A2 Vocabulary
    WordItem("Background", "Kelib chiqish / Tajriba", "/ˈbækɡraʊnd/", "noun", "Tell me about your educational background.", "Taʼlim tajribangiz haqida soʻzlab bering.", CefrLevel.A2, "cultural background"),
    WordItem("Balance", "Muvozanat", "/ˈbæləns/", "noun", "Work-life balance is essential.", "Ish va hayot muvozanati muhimdir.", CefrLevel.A2, "healthy balance"),
    WordItem("Benefit", "Foyda / Manfaat", "/ˈbenɪfɪt/", "noun", "The benefits of daily practice are huge.", "Kunlik amaliyotning foydasi juda katta.", CefrLevel.A2, "mutual benefit"),
    WordItem("Bilingual", "Ikki tilli", "/ˌbaɪˈlɪŋɡwəl/", "adjective", "Becoming bilingual opens global doors.", "Ikki tilli boʻlish dunyo eshiklarini ochadi.", CefrLevel.A2, "fluent bilingual"),
    WordItem("Boost", "Oshirmoq / Kuchaytirmoq", "/buːst/", "verb", "This exercise will boost your confidence.", "Ushbu mashq ishonchingizni oshiradi.", CefrLevel.A2, "boost confidence"),
    WordItem("Breathe", "Nafas olmoq", "/briːð/", "verb", "Breathe in for four seconds.", "Toʻrt soniya ichingizga nafas oling.", CefrLevel.A2, "breathe deeply"),
    WordItem("Brilliant", "Aql bovar qilmas / Aʼlo", "/ˈbrɪliənt/", "adjective", "You did a brilliant job today.", "Bugun aʼlo darajada bajardingiz.", CefrLevel.A2, "brilliant idea"),
    WordItem("Capable", "Qodir", "/ˈkeɪpəbl/", "adjective", "You are capable of mastering English.", "Siz ingliz tilini oʻrganishga toʻliq qodirsiz.", CefrLevel.A2, "highly capable"),
    WordItem("Celebrate", "Nishonlamoq", "/ˈselɪbreɪt/", "verb", "Celebrate every small win along the way.", "Yoʻldagi har bir kichik gʻalabani nishonlang.", CefrLevel.A2, "celebrate success"),
    WordItem("Certain", "Ishonchi komil", "/ˈsɜːtn/", "adjective", "I am certain that practice brings results.", "Amaliyot natija berishiga ishonchim komil.", CefrLevel.A2, "almost certain"),

    // B1 Vocabulary
    WordItem("Comprehend", "Chuqur tushunmoq", "/ˌkɒmprɪˈhend/", "verb", "I can comprehend native speakers now.", "Endi ona tilida soʻzlashuvchilarni tushuna olaman.", CefrLevel.B1, "fully comprehend"),
    WordItem("Crucial", "Oʻta muhim", "/ˈkruːʃl/", "adjective", "Consistency is crucial for fluency.", "Ravonlik uchun doimiylik oʻta muhim.", CefrLevel.B1, "crucial role"),
    WordItem("Dedication", "Fidoyilik", "/ˌdedɪˈkeɪʃn/", "noun", "Success requires genuine dedication.", "Muvaffaqiyat chinakam fidoyilikni talab qiladi.", CefrLevel.B1, "unstoppable dedication"),
    WordItem("Demonstrate", "Amalda koʻrsatmoq", "/ˈdemənstreɪt/", "verb", "Demonstrate your knowledge in real conversations.", "Bilimingizni jonli suhbatlarda koʻrsating.", CefrLevel.B1, "demonstrate ability"),
    WordItem("Determine", "Qatʼiy belgilamoq", "/dɪˈtɜːmɪn/", "verb", "Your daily habits determine your fluency.", "Kunlik odatlaringiz ravonligingizni belgilaydi.", CefrLevel.B1, "determine future"),
    WordItem("Discipline", "Intizom", "/ˈdɪsəplɪn/", "noun", "Discipline beats motivation every day.", "Intizom har kuni oddiy motivatsiyani yengadi.", CefrLevel.B1, "daily discipline"),
    WordItem("Elaborate", "Kengroq tushuntirmoq", "/ɪˈlæbəreɪt/", "verb", "Could you elaborate on that point?", "Ushbu fikrni kengroq tushuntirib bera olasizmi?", CefrLevel.B1, "elaborate further"),
    WordItem("Encounter", "Toʻqnash kelmoq / Uchratmoq", "/ɪnˈkaʊntə(r)/", "verb", "We encounter new idioms every day.", "Biz har kuni yangi idiyomalarga duch kelamiz.", CefrLevel.B1, "encounter difficulties"),
    WordItem("Enhance", "Yaxshilamoq / Rivojlantirmoq", "/ɪnˈhɑːns/", "verb", "Shadowing enhances your pronunciation.", "Shadowing talaffuzingizni rivojlantiradi.", CefrLevel.B1, "enhance skills"),
    WordItem("Evaluate", "Baholamoq", "/ɪˈvæljueɪt/", "verb", "Evaluate your daily progress honestly.", "Kunlik oʻsishingizni xolisona baholang.", CefrLevel.B1, "evaluate performance"),

    // B2 Vocabulary
    WordItem("Articulate", "Fikrni ravon va chiroyli ifodalamoq", "/ɑːˈtɪkjuleɪt/", "verb", "He can articulate complex concepts effortlessly.", "U murakkab tushunchalarni osonlikcha bayon eta oladi.", CefrLevel.B2, "articulate speaker"),
    WordItem("Breakthrough", "Yorib chiqish / Katta kashfiyot", "/ˈbreɪkθruː/", "noun", "Today was a breakthrough in my speaking.", "Bugun nutqimda katta burilish yuz berdi.", CefrLevel.B2, "major breakthrough"),
    WordItem("Captivating", "Maftunkor / Diqqatni jalb etuvchi", "/ˈkæptɪveɪtɪŋ/", "adjective", "Her speech was captivating and memorable.", "Uning nutqi maftunkor va esda qolarli edi.", CefrLevel.B2, "captivating story"),
    WordItem("Comprehensive", "Keng qamrovli / Toʻliq", "/ˌkɒmprɪˈhensɪv/", "adjective", "We follow a comprehensive 90-day system.", "Biz 90 kunlik keng qamrovli tizimga amal qilamiz.", CefrLevel.B2, "comprehensive guide"),
    WordItem("Eloquent", "Notiq / Soʻzamol", "/ˈeləkwənt/", "adjective", "He delivered an eloquent and moving address.", "U oʻta notiqlik bilan taʼsirli nutq soʻzladi.", CefrLevel.B2, "eloquent speaker"),
    WordItem("Exemplary", "Oʻrnak boʻlarli / Namunali", "/ɪɡˈzempləri/", "adjective", "Your daily effort is truly exemplary.", "Sizning kunlik harakatingiz chinakam ibratli.", CefrLevel.B2, "exemplary work"),
    WordItem("Fluency", "Ravonlik", "/ˈfluːənsi/", "noun", "Fluency is having no fear of hesitation.", "Ravonlik – ikkilanishdan qoʻrqmaslikdir.", CefrLevel.B2, "achieve fluency"),
    WordItem("Impromptu", "Tayyorgarliksiz / Ekspromt", "/ɪmˈprɒmptjuː/", "adjective", "Practice impromptu speaking for one minute.", "Bir daqiqa tayyorgarliksiz erkin gapirishni mashq qiling.", CefrLevel.B2, "impromptu speech"),
    WordItem("Invaluable", "Bebaho / Qimmatbaho", "/ɪnˈvæljuəbl/", "adjective", "The confidence you gain is invaluable.", "Qoʻlga kiritgan ishonchingiz bebaho boylikdir.", CefrLevel.B2, "invaluable asset"),
    WordItem("Perseverance", "Qatʼiyat / Chidamlilik", "/ˌpɜːsɪˈvɪərəns/", "noun", "Through perseverance, all goals are met.", "Qatʼiyat orqali barcha maqsadlarga erishiladi.", CefrLevel.B2, "unyielding perseverance"),
    WordItem("Resilience", "Moslashuvchanlik va sinmas iroda", "/rɪˈzɪliəns/", "noun", "Mental resilience eliminates speech anxiety.", "Ruhiy chidamlilik nutqdagi hayajonni butkul yoʻq qiladi.", CefrLevel.B2, "emotional resilience"),
    WordItem("Sophisticated", "Nafis / Murakkab va yuksak", "/səˈfɪstɪkeɪtɪd/", "adjective", "Use sophisticated sentence structures.", "Yuksak va nozik gap tuzilmalaridan foydalaning.", CefrLevel.B2, "sophisticated vocabulary"),
    WordItem("Spontaneous", "Oʻz-oʻzidan keladigan / Samimiy", "/spɒnˈteɪniəs/", "adjective", "Her English is spontaneous and fluid.", "Uning ingliz tili samimiy va oqar suvdek ravon.", CefrLevel.B2, "spontaneous laughter"),
    WordItem("Substantial", "Sezilarli / Katta", "/səbˈstænʃl/", "adjective", "You have made substantial progress.", "Siz sezilarli darajada katta oʻsishga erishdingiz.", CefrLevel.B2, "substantial improvement"),
    WordItem("Unstoppable", "Toʻxtatib boʻlmas", "/ʌnˈstɒpəbl/", "adjective", "A dedicated learner is unstoppable.", "Fidoyi oʻquvchini hech narsa toʻxtata olmaydi.", CefrLevel.B2, "unstoppable force"),

    // Additional Core Words (A1-B2 Expansion)
    WordItem("Believe", "Ishonmoq", "/bɪˈliːv/", "verb", "Believe in your ability to speak.", "Oʻz gapirish qobiliyatingizga ishoning.", CefrLevel.A1, "firmly believe"),
    WordItem("Change", "Oʻzgarish / Oʻzgartirmoq", "/tʃeɪndʒ/", "verb", "Small daily actions change everything.", "Kichik kunlik harakatlar hammasini oʻzgartiradi.", CefrLevel.A1, "positive change"),
    WordItem("Decision", "Qaror", "/dɪˈsɪʒn/", "noun", "Making the decision to study was crucial.", "Oʻrganishga qaror qilish muhim edi.", CefrLevel.A2, "make a decision"),
    WordItem("Effort", "Harakat / Kuch", "/ˈefət/", "noun", "Consistent effort beats natural talent.", "Doimiy harakat tugʻma isteʼdodni yengadi.", CefrLevel.A2, "put in effort"),
    WordItem("Focus", "Diqqatni qaratmoq", "/ˈfəʊkəs/", "verb", "Focus on 30 minutes of deep practice.", "30 daqiqalik chuqur mashqqa diqqat qiling.", CefrLevel.A2, "focus sharply"),
    WordItem("Goal", "Maqsad", "/ɡəʊl/", "noun", "My goal is B2 level in 90 days.", "Maqsadim – 90 kunda B2 darajasiga erishish.", CefrLevel.A1, "achieve a goal"),
    WordItem("Growth", "Oʻsish / Rivojlanish", "/ɡrəʊθ/", "noun", "Every mistake is an opportunity for growth.", "Har bir xato – bu oʻsish imkoniyati.", CefrLevel.B1, "personal growth"),
    WordItem("Knowledge", "Bilim", "/ˈnɒlɪdʒ/", "noun", "Knowledge becomes power when spoken.", "Bilim aytilgandagina kuchga aylanadi.", CefrLevel.A2, "gain knowledge"),
    WordItem("Mindset", "Fikrlash tarzi", "/ˈmaɪndset/", "noun", "Adopt a growth mindset toward learning.", "Oʻrganishga nisbatan oʻsuvchi fikrlashni qabul qiling.", CefrLevel.B1, "positive mindset"),
    WordItem("Opportunity", "Imkoniyat", "/ˌɒpəˈtjuːnəti/", "noun", "Speaking English creates global opportunities.", "Inglizcha gapirish global imkoniyatlar yaratadi.", CefrLevel.B1, "golden opportunity"),
    WordItem("Practice", "Amaliyot / Mashq", "/ˈpræktɪs/", "noun", "Daily practice makes speech second nature.", "Kunlik amaliyot nutqni tabiiy holga keltiradi.", CefrLevel.A1, "daily practice"),
    WordItem("Progress", "Rivoj / Oʻsish", "/ˈprəʊɡres/", "noun", "Celebrate your daily progress.", "Kunlik rivojlanishingizni nishonlang.", CefrLevel.A2, "make progress"),
    WordItem("Quality", "Sifat", "/ˈkwɒləti/", "noun", "Focus on the quality of your pronunciation.", "Talaffuz sifatiga eʼtibor bering.", CefrLevel.A2, "high quality"),
    WordItem("Result", "Natija", "/rɪˈzʌlt/", "noun", "Discipline guarantees outstanding results.", "Intizom ajoyib natijalarni kafolatlaydi.", CefrLevel.A1, "visible results"),
    WordItem("Success", "Muvaffaqiyat", "/səkˈses/", "noun", "Success belongs to the persistent.", "Muvaffaqiyat qatʼiyatlilarga tegishli.", CefrLevel.A1, "achieve success"),
    WordItem("Transformation", "Tub burilish / Oʻzgarish", "/ˌtrænsfəˈmeɪʃn/", "noun", "Witness your own speech transformation.", "Oʻz nutqingizdagi tub burilishga guvoh boʻling.", CefrLevel.B2, "complete transformation"),
    WordItem("Victory", "Gʻalaba", "/ˈvɪktəri/", "noun", "Completing all 5 blocks is a daily victory.", "Barcha 5 blokni bajarish – kunlik gʻalabadir.", CefrLevel.B1, "celebrate victory"),
    WordItem("Voice", "Ovoz / Fikr", "/vɔɪs/", "noun", "Find your authentic voice in English.", "Ingliz tilida oʻzingizning samimiy ovozingizni toping.", CefrLevel.A2, "confident voice"),
    WordItem("Wisdom", "Donolik / Tajriba", "/ˈwɪzdəm/", "noun", "Experience brings communication wisdom.", "Tajriba muloqot donoligini keltiradi.", CefrLevel.B2, "words of wisdom"),
    WordItem("Zeal", "Gʻayrat / Ishtiyoq", "/ziːl/", "noun", "He practices with unmatched zeal.", "U tengsiz gʻayrat bilan mashq qiladi.", CefrLevel.B2, "unwavering zeal"),
  )
}
