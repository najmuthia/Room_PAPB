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

package com.example.inventory

import android.app.Application
import com.example.inventory.data.AppContainer
import com.example.inventory.data.AppDataContainer

class InventoryApplication : Application() {

    /**
     * Instance dari AppContainer yang digunakan oleh kelas lain untuk memperoleh dependensi.
     * Ini akan menyediakan akses ke repository dan data container yang diperlukan dalam aplikasi.
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        // Menginisialisasi container dengan AppDataContainer, yang berfungsi untuk menyimpan dan menyediakan dependensi yang dibutuhkan selama lifecycle aplikasi.
        container = AppDataContainer(this)
    }
}
