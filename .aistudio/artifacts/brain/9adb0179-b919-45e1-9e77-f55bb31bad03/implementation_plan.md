# 90-Kunlik Ingliz Tili Intensiv: 0 dan B2 gacha (Kuniga 150 daqiqa)

Noldan (A0) boshlab erkin va ishonchli B2 darajasiga olib chiquvchi, kunlik 150 daqiqalik tuzilgan va hayajonni yengishga moʻljallangan mobil intensiv oʻquv platformasi.

## Foydalanuvchi tanlovi va muhim qarorlar

> [!IMPORTANT]
> Foydalanuvchi tomonidan tasdiqlangan parametrlar boʻyicha reja shakllantirildi:
> - **Kunlik vaqt taqsimoti:** Har biri 30 daqiqadan iborat 5 ta intensiv blok (Jami: 150 daqiqa).
> - **Nutq va hayajonni yengish:** Real hayotiy dialoglar, audio eshittirish (TTS), psixologik nafas olish/tayyorgarlik texnikalari va ovozli takrorlash mashqlari.
> - **Dizayn uslubi:** Zamonaviy, motivatsion va koʻzga yoqimli Material 3 Dark / Light premium interfeys.

- **Tasdiqlangan reja bloklari (150 daqiqa/kun):**
  1. `Lugʻat & Iboralar` (30 daqiqa) – kontekstdagi soʻzlar, collocation va intervalli takrorlash (SRS).
  2. `Eshitish & Shadowing` (30 daqiqa) – audio nutq, tezlikni boshqarish va ketidan takrorlash.
  3. `Nutq & Hayajonni yengish` (30 daqiqa) – 4-7-8 nafas olish, tildagi toʻsiqni yechish, ekspromt gapirish mavzulari.
  4. `Jonli Grammatika & Qoliplar` (30 daqiqa) – qoidalar emas, balki jonli gap tuzish patternlari.
  5. `Kunlik Test & Mustahkamlash` (30 daqiqa) – interaktiv quiz, xatolar tahlili va kunlik yutuq.

---

## 1. Umumiy koʻrinish va asosiy konsepsiya

- **Ilovaning maqsadi:** Foydalanuvchini 90 kun ichida noldan B2 darajasiga intensiv, tizimli va psixologik toʻsiqlarsiz olib chiqish.
- **Maqsadli auditoriya:** Ingliz tilini tez oʻrganishni istagan, soʻz yodlab lekin gapirishda hayajonlanib qoladigan, qatʼiy intizom va natijaga intiluvchi oʻquvchilar.
- **Asosiy qiymat:** Kunlik 150 daqiqani parchalangan holda unumli sarflash (Pomodoro uslubidagi 30 daqiqalik fokus taymerlari), har bir kun uchun aniq maqsad, audio-talaffuz va gapirishdagi qoʻrquvni bartaraf etuvchi amaliy vositalar.

---

## 2. Foydalanuvchi tajribasi (UX) va vizual dizayn

### Asosiy oqimlar:
1. **Bosh ekran (Bugungi kun markazi):**
   - 90 kunlik marshrut xaritasi (Bosqichlar: A1 Elementary [1-25 kun], A2 Pre-Intermediate [26-50 kun], B1 Intermediate [51-75 kun], B2 Upper-Intermediate [76-90 kun]).
   - Kunlik 150 daqiqalik progress halqasi va 5 ta modul holati (Bajarildi / Jarayonda / Qulflangan).
   - Motivatsion iqtibos va oʻrganish seriyasi (Streak counter).

2. **5 ta 30 daqiqalik intensiv blok ekrani:**
   - Har bir blokda interaktiv taymer (30:00 daqiqalik teskari hisoblash, toʻxtatish, davom ettirish).
   - Ovozli eshitish (Text-To-Speech) imkoniyati bilan soʻz va jumlalar talaffuzi.
   - Tezkor mashqlar va kartochkalar.

3. **Speaking & Hayajonni yengish laboratoriyasi (Anti-Anxiety Speaking Lab):**
   - **4-7-8 Tinchlantiruvchi nafas mashqi:** Nutqdan oldin yurak urishini sokinlashtiruvchi vizual animatsiya.
   - **Vocal Warm-up (Artikulyatsiya mashqlari):** Til va jagʻ mushaklarini boʻshashtiruvchi tez aytishlar (Tongue twisters).
   - **Real Hayot Dialoglari:** Kafe, aeroport, ish intervyusi, doʻstlar suhbati boʻyicha rol oʻynash.
   - **Shadowing & Self-Practice:** Spiker ketidan bir xil intonatsiyada takrorlash, taymer bilan 1 daqiqa toʻxtovsiz gapirish sinovi.

4. **Lugʻat va Grammatik Qoliplar bazasi (Vocabulary & Pattern Bank):**
   - Mavzulashtirilgan va darajalar boʻyicha ajratilgan 3000+ eng kerakli soʻz va barqaror iboralar.
   - Qidiruv, saralash, "Sevimlilar"ga saqlash va ovozli talaffuz.

5. **Kunlik Test va Statistika:**
   - Interaktiv testlar (Toʻgʻri/notoʻgʻri, soʻzlardan gap yigʻish, audioni eshitib yozish).
   - Kun yakunida tahlil va progressni saqlash.

### Vizual identifikatsiya va uslub:
- **Ranglar palitrasi:** Chuqur premium toʻq indigo/koʻk fon (`#0B0F19`), quvvatlantiruvchi zumrad yashil muvaffaqiyat rangi (`#10B981`), zamonaviy neon koʻk asosiy aksent (`#3B82F6`), va quyosh energiyasini beruvchi oltin aksent (`#F59E0B`).
- **M3 Dark & Light rejimlari:** Tizim sozlamasiga yoki foydalanuvchi tanloviga qarab qulay oʻtish.
- **Tipografiya:** Aniq oʻqiladigan zamonaviy shriftlar, katta jozibali sarlavhalar va qulay masofalar.

---

## 3. Asosiy mahsulot qarorlari va afzalliklari

- **Qaror 1: Maʼlumotlar saqlanishi (Offline-First Local Room Database):**
  - *Yondashuv:* Android Room SQLite bazasi orqali 90 kunlik reja, darsliklar, lugʻat bazasi va foydalanuvchining progressini toʻliq oflayn saqlash.
  - *Sabab:* Oʻquvchi internet yoʻq joyda ham mashgʻulotlarni 150 daqiqa toʻliq bajara olishi va hech qanday kechikishsiz ishlashi shart.
- **Qaror 2: Android Text-To-Speech (TTS) dvigateli:**
  - *Yondashuv:* Android-ning ichki `android.speech.tts.TextToSpeech` imkoniyatidan foydalanish (ingliz tili talaffuzi uchun).
  - *Sabab:* Har qanday soʻz va dialoglarni toʻgʻri talaffuzda eshitish, tashqi qimmat serverlarga bogʻlanib qolmaslik va oflayn rejimda ham ishlash.
- **Qaror 3: 5 ta 30 daqiqalik aniq struktura:**
  - *Yondashuv:* 150 daqiqalik yagona dars oʻrniga 30 daqiqalik 5 ta blok.
  - *Sabab:* Miya 150 daqiqa uzluksiz charchaydi. 5 ta alohida yoʻnalish (Soʻz, Eshitish, Gapirish, Grammatika, Test) miyada turli qismlarni faollashtiradi va charchoqni kamaytiradi.

---

## 4. Texnik arxitektura va komponentlar diagrammasi

```
┌─────────────────────────────────────────────────────────────────┐
│                       Jetpack Compose UI                        │
├───────────────┬─────────────────┬───────────────┬───────────────┤
│ Bosh Ekran    │ 5 Blok Mashgʻul.│ Speaking Lab  │ Lugʻat & Qidir│
│ (90 kun xarita│ (30 min Taymer, │ (Nafas, Audio │ (3000+ soʻz,  │
│  va Progress) │  Interaktiv)    │  Shadowing)   │  Sevimlilar)  │
└───────┬───────┴────────┬────────┴───────┬───────┴───────┬───────┘
        │                │                │               │
        ▼                ▼                ▼               ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Eng90ViewModel & UI State                   │
│   (StateFlow<DayPlanUiState>, TTS Controller, Timer Manager)    │
└────────────────────────────────┬────────────────────────────────┘
                                 │
                                 ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Eng90Repository Layer                       │
└───────┬─────────────────────────────────────────────────┬───────┘
        │                                                 │
        ▼                                                 ▼
┌───────────────────────────────┐       ┌─────────────────────────┐
│      Room Local Database      │       │ Android TextToSpeech    │
│ (Progress, Kunlik holatlar,   │       │ Engine (Talaffuz,       │
│  Lugʻat, Test natijalari)     │       │  Dialog audio ijrosi)   │
└───────────────────────────────┘       └─────────────────────────┘
```

### Amalga oshirish bosqichlari (Reja tasdiqlangach):
1. **1-qadam:** `metadata.json` va `strings.xml` fayllariga ilovaning rasmiy nomini ("Ingliz Tili 90 Kunlik Intensiv") oʻrnatish.
2. **2-qadam:** Room entity, DAO va Repository qatlamini yaratish (90 kunlik oʻquv rejasi, 5 ta blok modeli, 3000+ soʻzlar bazasi, foydalanuvchi kuni va ballari).
3. **3-qadam:** Text-To-Speech (TTS) yordamchisi va 30 daqiqalik blok taymer boshqaruvchisini sozlash.
4. **4-qadam:** Speaking & Hayajonni yengish moduli (4-7-8 nafas mashqi, artikulyatsiya, real stsenariylar) UI-sini ishlab chiqish.
5. **5-qadam:** Bosh ekran (90 kunlik progress), Kunlik 5 blok dars ekrani, Lugʻat qidiruvi va Test interfeyslarini zamonaviy M3 dizaynida tayyorlash.
6. **6-qadam:** `compile_applet` orqali toʻliq kompilyatsiyadan oʻtkazish va tekshirish.
