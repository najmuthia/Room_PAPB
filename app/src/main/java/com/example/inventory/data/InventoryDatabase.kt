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

// Mengimpor komponen Room yang diperlukan
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Anotasi Database untuk menentukan entitas dan versi database
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    // Fungsi abstrak untuk mendapatkan instance DAO Item
    abstract fun itemDao(): ItemDao

    // Object companion untuk menyediakan satu instance InventoryDatabase
    companion object {
        // Menandai instance sebagai volatile agar selalu konsisten di berbagai thread
        @Volatile
        private var Instance: InventoryDatabase? = null

        // Mengambil instance database, atau membuatnya jika belum ada
        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            // Menggunakan builder Room untuk membuat instance baru
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    .build().also { Instance = it } // Menyimpan instance ke dalam variabel singleton
            }
        }
    }
}

