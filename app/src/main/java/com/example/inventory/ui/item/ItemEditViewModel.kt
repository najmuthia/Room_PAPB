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

package com.example.inventory.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.inventory.data.ItemsRepository

/**
 * ItemEditViewModel adalah kelas ViewModel yang digunakan untuk mengambil dan memperbarui data item dari sumber data yang dikelola oleh ItemsRepository. ViewModel ini bertugas menjaga state UI yang berhubungan dengan tampilan pengeditan item, termasuk validasi input yang dimasukkan oleh pengguna.
 */
class ItemEditViewModel(
    savedStateHandle: SavedStateHandle, // Menyimpan status yang dipertahankan setelah perubahan konfigurasi
) : ViewModel() {

    /**
     * itemUiState menyimpan state UI untuk item yang sedang diedit. Ini termasuk informasi seperti nama item, harga, dan kuantitas yang akan ditampilkan di layar.
     * State ini diubah menggunakan mutableStateOf, yang memungkinkan Compose untuk memantau dan merender UI ketika nilai berubah.
     */
    var itemUiState by mutableStateOf(ItemUiState()) // Default state adalah ItemUiState() yang kosong
        private set // Setter private berarti nilai ini hanya dapat dimodifikasi dalam ViewModel

    // itemId digunakan untuk mengambil informasi tentang item yang akan diedit. Nilai ini diperoleh dari argumen yang disimpan dalam SavedStateHandle.
    private val itemId: Int = checkNotNull(savedStateHandle[ItemEditDestination.itemIdArg]) // Argumen itemId diambil dari SavedStateHandle

    /**
     * Fungsi validateInput digunakan untuk memvalidasi apakah input yang dimasukkan oleh pengguna valid atau tidak.
     * Fungsi ini memeriksa apakah nilai dari nama, harga, dan kuantitas tidak kosong atau blank.
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        // Memeriksa apakah nama, harga, dan kuantitas item tidak kosong
        return with(uiState) {
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank() // Mengembalikan true jika semua input valid
        }
    }
}