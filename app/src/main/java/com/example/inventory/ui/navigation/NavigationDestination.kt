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

package com.example.inventory.ui.navigation

/**
 * Interface ini digunakan untuk mendefinisikan tujuan navigasi pada aplikasi. Setiap tujuan (screen) yang ada di aplikasi harus mengimplementasikan interface ini untuk menyatakan rute dan judul layar.
 */
interface NavigationDestination {
    /**
     * Mendefinisikan rute unik untuk navigasi ke composable tertentu. Setiap screen di aplikasi akan memiliki rute yang digunakan untuk melakukan navigasi.
     */
    val route: String

    /**
     * Menyediakan ID sumber daya string yang berisi judul untuk tampilan layar tersebut.
     * Judul ini akan digunakan untuk ditampilkan di antarmuka pengguna, seperti di bar judul layar.
     */
    val titleRes: Int
}