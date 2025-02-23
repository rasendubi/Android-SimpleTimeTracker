package com.example.util.simpletimetracker.data_local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.util.simpletimetracker.data_local.model.RecordTagDBO

@Dao
interface RecordTagDao {

    @Transaction
    @Query("select exists(select 1 from recordTags)")
    suspend fun isEmpty(): Long

    @Query("SELECT * FROM recordTags")
    suspend fun getAll(): List<RecordTagDBO>

    @Query("SELECT * FROM recordTags WHERE id = :id LIMIT 1")
    suspend fun get(id: Long): RecordTagDBO?

    @Query("SELECT * FROM recordTags WHERE name = :name LIMIT 1")
    suspend fun get(name: String): RecordTagDBO?

    @Query("SELECT * FROM recordTags WHERE icon_color_source = :typeId")
    suspend fun getByType(typeId: Long): List<RecordTagDBO>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tag: RecordTagDBO): Long

    @Query("UPDATE recordTags SET archived = 1 WHERE id = :id")
    suspend fun archive(id: Long)

    @Query("UPDATE recordTags SET archived = 0 WHERE id = :id")
    suspend fun restore(id: Long)

    @Query("DELETE FROM recordTags WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM recordTags")
    suspend fun clear()
}