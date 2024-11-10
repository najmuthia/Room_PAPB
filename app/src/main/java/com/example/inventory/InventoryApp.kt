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

import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inventory.R.string
import com.example.inventory.ui.navigation.InventoryNavHost

/**
 * Fungsi ini akan menginisialisasi dan menampilkan navigasi antar layar di aplikasi.
 */
@Composable
fun InventoryApp(navController: NavHostController = rememberNavController()) {
    // Menampilkan graf navigasi menggunakan InventoryNavHost
    InventoryNavHost(navController = navController)
}

/**
 * Fungsi untuk membuat AppBar yang menampilkan judul dan tombol navigasi mundur jika diperlukan.
 * AppBar ini digunakan untuk memberikan kontrol navigasi di bagian atas layar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryTopAppBar(
    title: String, // Judul AppBar
    canNavigateBack: Boolean, // Menampilkan tombol mundur jika true
    modifier: Modifier = Modifier, // Modifier untuk kustomisasi AppBar
    scrollBehavior: TopAppBarScrollBehavior? = null, // Perilaku saat scroll
    navigateUp: () -> Unit = {} // Fungsi navigasi saat tombol mundur ditekan
) {
    CenterAlignedTopAppBar(
        title = { Text(title) }, // Menampilkan teks judul
        modifier = modifier, // Modifier yang diterapkan pada AppBar
        scrollBehavior = scrollBehavior, // Menentukan perilaku scroll pada AppBar
        navigationIcon = { // Menampilkan tombol navigasi mundur jika diperlukan
            if (canNavigateBack) { // Jika memungkinkan untuk kembali, tampilkan tombol mundur
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Filled.ArrowBack, // Icon
                        contentDescription = stringResource(string.back_button) // Deskripsi icon
                    )
                }
            }
        }
    )
}