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

import kotlinx.coroutines.flow.Flow

// Interface untuk menyediakan operasi utama (CRUD) dalam tabel Item di database aplikasi
// Interface ini bertindak sebagai kontrak yang menentukan fungsi-fungsi CRUD yang wajib diimplementasikan
interface ItemsRepository {
    // Fungsi untuk mengambil seluruh daftar item dari database. Data ini dikembalikan dalam bentuk Flow, yang memungkinkan aplikasi menerima pembaruan data secara real-time saat ada perubahan pada tabel Item
    fun getAllItemsStream(): Flow<List<Item>>

    // Fungsi untuk mengambil data satu item berdasarkan ID-nya.
    // Menggunakan Flow agar aplikasi dapat mengamati perubahan spesifik pada item tersebut di database
    fun getItemStream(id: Int): Flow<Item?>

    // Fungsi untuk menambahkan item baru ke tabel database.
    // Fungsi ini bersifat suspend karena harus dijalankan pada coroutine, memastikan proses input/output berjalan efisien tanpa mengganggu UI
    suspend fun insertItem(item: Item)

    // Fungsi untuk menghapus item tertentu dari tabel database.
    // Suspend function digunakan karena operasi ini juga berpotensi membutuhkan akses input/output yang lebih lambat
    suspend fun deleteItem(item: Item)

    // Fungsi untuk memperbarui data item yang ada di database.
    // Fungsi ini memungkinkan perubahan pada item yang sudah tersimpan sebelumnya dan merupakan suspend function untuk efisiensi
    suspend fun updateItem(item: Item)
}

