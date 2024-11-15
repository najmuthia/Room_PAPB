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

import android.content.Context

/**
 * AppContainer adalah interface untuk Dependency Injection, menyediakan instance dari ItemsRepository
 */
interface AppContainer {
    val itemsRepository: ItemsRepository
}

/**
 * Implementasi AppContainer yang menyediakan instance dari OfflineItemsRepository
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Mendapatkan instance dari ItemsRepository menggunakan metode lazy initialization
     */
    override val itemsRepository: ItemsRepository by lazy {
        /**
         * Menginisialisasi OfflineItemsRepository dengan mengambil DAO dari InventoryDatabase
         */
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).itemDao())
    }
}
