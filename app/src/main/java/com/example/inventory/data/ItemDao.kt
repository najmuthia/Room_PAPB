/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

// Mengimpor anotasi dan fungsi dari Room untuk mengatur data
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow // Mengimpor Flow untuk mengelola data secara asinkron

// Anotasi Dao untuk menandai interface sebagai DAO yang mengakses database
@Dao
interface ItemDao {
    // Menyisipkan item baru ke tabel, mengabaikan jika terjadi konflik
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    // Memperbarui data item yang ada
    @Update
    suspend fun update(item: Item)

    // Menghapus data item tertentu
    @Delete
    suspend fun delete(item: Item)

    // Mengambil data item berdasarkan ID dan mengembalikan dalam bentuk Flow
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    // Mengambil semua data item dan mengembalikan dalam urutan nama ascending
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}
