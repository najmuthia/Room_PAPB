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

package com.example.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventory.InventoryApplication
import com.example.inventory.ui.home.HomeViewModel
import com.example.inventory.ui.item.ItemDetailsViewModel
import com.example.inventory.ui.item.ItemEditViewModel
import com.example.inventory.ui.item.ItemEntryViewModel

/**
 * Objek ini menyediakan factory untuk membuat instance dari ViewModel yang akan digunakan di seluruh aplikasi Inventory.
 * Factory ini memanfaatkan `viewModelFactory` untuk menginisialisasi ViewModel dan menyediakannya dalam aplikasi.
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer untuk ItemEditViewModel
        initializer {
            // Membuat instance ItemEditViewModel dengan menggunakan SavedStateHandle
            ItemEditViewModel(
                this.createSavedStateHandle()
            )
        }
        // Initializer untuk ItemEntryViewModel
        initializer {
            // Membuat instance ItemEntryViewModel dengan mengambil ItemsRepository dari InventoryApplication
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        // Initializer untuk ItemDetailsViewModel
        initializer {
            // Membuat instance ItemDetailsViewModel dengan menggunakan SavedStateHandle
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        // Initializer untuk HomeViewModel
        initializer {
            // Membuat instance HomeViewModel
            HomeViewModel()
        }
    }
}

/**
  * Fungsi ekstensi ini digunakan untuk mendapatkan instance dari [InventoryApplication] dengan mengakses objek [Application] yang disediakan oleh [CreationExtras].
 * Ini memungkinkan kita untuk mengakses komponen lain yang ada di dalam aplikasi, seperti repository, melalui `InventoryApplication`.
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)