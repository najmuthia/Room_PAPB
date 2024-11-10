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
import androidx.lifecycle.ViewModel
import com.example.inventory.data.Item
import com.example.inventory.data.ItemsRepository
import java.text.NumberFormat

/**
 * ViewModel untuk memvalidasi dan menyimpan item ke dalam database Room.
 */
class ItemEntryViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    /**
     * Menyimpan status UI item yang sedang diedit. State ini mencakup detail item (nama, harga, jumlah) dan validitas entri.
     */
    var itemUiState by mutableStateOf(ItemUiState())
        private set

    /**
     * Memperbarui [itemUiState] dengan data item yang baru. Fungsi ini juga melakukan validasi terhadap input pengguna.
     */
    fun updateUiState(itemDetails: ItemDetails) {
        // Memperbarui status UI dengan item details baru dan memvalidasi entri
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    /**
     * Fungsi untuk menyimpan item ke dalam database jika valid.
     * Fungsi ini dipanggil saat tombol "Simpan" ditekan.
     * Validasi dilakukan terlebih dahulu sebelum data disimpan.
     */
    suspend fun saveItem() {
        if (validateInput()) { // Pastikan data valid
            // Menyimpan item ke dalam repository (database)
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    /**
     * Validasi input dari pengguna.
     * Memeriksa apakah nama, harga, dan jumlah sudah diisi dengan benar.
     */
    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            // Memastikan bahwa semua field tidak kosong
            name.isNotBlank() && price.isNotBlank() && quantity.isNotBlank()
        }
    }
}

/**
 * Representasi status UI untuk item.
 * Menyimpan detail item dan status validitas entri.
 */
data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false // Status validitas entri
)

data class ItemDetails(
    val id: Int = 0, // ID unik item, biasanya otomatis di-generate
    val name: String = "", // Nama item
    val price: String = "", // Harga item dalam format string (untuk input)
    val quantity: String = "", // Jumlah item dalam format string (untuk input)
)

/**
 * Fungsi ekstensi untuk mengkonversi [ItemDetails] ke [Item] yang dapat disimpan dalam database.
 * Jika harga atau jumlah tidak valid, maka nilai default (0.0 untuk harga, 0 untuk jumlah) akan digunakan.
 */
fun ItemDetails.toItem(): Item = Item(
    id = id, // Menyertakan ID item
    name = name, // Nama item
    price = price.toDoubleOrNull() ?: 0.0, // Konversi harga ke Double, default 0.0 jika tidak valid
    quantity = quantity.toIntOrNull() ?: 0 // Konversi jumlah ke Int, default 0 jika tidak valid
)

/**
 * Fungsi ekstensi untuk memformat harga item menjadi format mata uang yang sesuai dengan lokal pengguna.
 */
fun Item.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(price) // Format harga dalam bentuk mata uang
}

/**
 * Fungsi ekstensi untuk mengkonversi objek [Item] ke dalam [ItemUiState].
 * Untuk memperbarui UI dengan status validitas entri.
 */
fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(), // Mengonversi item ke ItemDetails untuk UI
    isEntryValid = isEntryValid // Menyertakan status validitas entri
)

/**
 * Fungsi ekstensi untuk mengkonversi objek [Item] ke dalam [ItemDetails].
 * Untuk menampilkan detail item di UI dan memungkinkan input oleh pengguna.
 */
fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id, // ID item
    name = name, // Nama item
    price = price.toString(), // Harga item dalam format String
    quantity = quantity.toString() // Jumlah item dalam format String
)
