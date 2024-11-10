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

package com.example.inventory.ui.home

import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item

/**
 * Kelas ViewModel untuk mengambil semua item yang disimpan dalam database Room.
 * ViewModel ini digunakan oleh HomeScreen untuk menjaga data tetap bertahan selama life cycle UI.
 */
class HomeViewModel() : ViewModel() {
    // Objek companion untuk menyimpan konstanta yang dapat diakses dari luar kelas
    companion object {
        // TIMEOUT_MILLIS menentukan waktu tunggu (5 detik) untuk operasi yang membutuhkan pembatasan waktu
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

/**
 * Data kelas HomeUiState menyimpan UI state untuk HomeScreen.
 * Kelas ini mengelola daftar item yang ditampilkan di layar Home.
 */
data class HomeUiState(val itemList: List<Item> = listOf())
