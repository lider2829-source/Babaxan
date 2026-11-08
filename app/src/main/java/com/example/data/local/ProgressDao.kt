package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgressDao {

  @Query("SELECT * FROM user_day_progress ORDER BY day_number ASC")
  fun getAllDayProgress(): Flow<List<UserDayProgressEntity>>

  @Query("SELECT * FROM user_day_progress ORDER BY day_number ASC")
  suspend fun getAllDayProgressSync(): List<UserDayProgressEntity>

  @Query("SELECT * FROM user_day_progress WHERE day_number = :day LIMIT 1")
  fun getProgressForDay(day: Int): Flow<UserDayProgressEntity?>

  @Query("SELECT * FROM user_day_progress WHERE day_number = :day LIMIT 1")
  suspend fun getProgressForDaySync(day: Int): UserDayProgressEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateDayProgress(progress: UserDayProgressEntity)

  // 90-Day Progress Analytics Queries
  @Query("SELECT COUNT(*) FROM user_day_progress WHERE is_day_completed = 1")
  fun getCompletedDaysCount(): Flow<Int>

  @Query("SELECT SUM(minutes_spent) FROM user_day_progress")
  fun getTotalMinutesFromProgress(): Flow<Int?>

  @Query("SELECT * FROM user_day_progress WHERE day_number BETWEEN :startDay AND :endDay ORDER BY day_number ASC")
  fun getProgressForRange(startDay: Int, endDay: Int): Flow<List<UserDayProgressEntity>>

  // Favorite Words
  @Query("SELECT * FROM favorite_words ORDER BY added_timestamp DESC")
  fun getAllFavorites(): Flow<List<FavoriteWordEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertFavorite(word: FavoriteWordEntity)

  @Query("DELETE FROM favorite_words WHERE english = :english")
  suspend fun deleteFavorite(english: String)

  @Query("SELECT EXISTS(SELECT 1 FROM favorite_words WHERE english = :english)")
  fun isWordFavorite(english: String): Flow<Boolean>

  // User Profile
  @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
  fun getUserProfile(): Flow<UserProfileEntity?>

  @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
  suspend fun getUserProfileSync(): UserProfileEntity?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun saveUserProfile(profile: UserProfileEntity)
}
