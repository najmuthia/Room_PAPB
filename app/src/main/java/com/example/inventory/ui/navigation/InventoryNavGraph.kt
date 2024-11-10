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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.inventory.ui.home.HomeDestination
import com.example.inventory.ui.home.HomeScreen
import com.example.inventory.ui.item.ItemDetailsDestination
import com.example.inventory.ui.item.ItemDetailsScreen
import com.example.inventory.ui.item.ItemEditDestination
import com.example.inventory.ui.item.ItemEditScreen
import com.example.inventory.ui.item.ItemEntryDestination
import com.example.inventory.ui.item.ItemEntryScreen

/**
 * Fungsi ini bertanggung jawab untuk menyediakan struktur navigasi untuk aplikasi.
 * Menggunakan NavHost yang mengelola berbagai rute (routes) dan pengalihan antar layar menggunakan NavController.
 * Setiap rute yang ditentukan akan merujuk ke tampilan/tugas tertentu di aplikasi.
 *  */
@Composable
fun InventoryNavHost(
    navController: NavHostController,  // Kontroler untuk mengelola navigasi antar tampilan.
    modifier: Modifier = Modifier,     // Modifier opsional untuk penyesuaian UI.
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,  // Tampilan awal saat aplikasi dimulai.
        modifier = modifier // Modifier UI opsional.
    ) {
        // Rute untuk tampilan utama (HomeScreen), dengan navigasi ke item entry dan update.
        composable(route = HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },  // Menuju ItemEntryScreen.
                navigateToItemUpdate = { navController.navigate("${ItemDetailsDestination.route}/${it}") }  // Menuju ItemDetailsScreen dengan ID.
            )
        }

        // Rute untuk menambah item baru (ItemEntryScreen).
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = { navController.popBackStack() },  // Kembali ke layar sebelumnya.
                onNavigateUp = { navController.navigateUp() }     // Menggunakan tombol "Up" untuk kembali.
            )
        }

        // Rute untuk melihat detail item, membawa argumen itemId.
        composable(
            route = ItemDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemDetailsDestination.itemIdArg) {
                type = NavType.IntType  // itemId integer.
            })
        ) {
            ItemDetailsScreen(
                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") }, // Menuju ItemEditScreen.
                navigateBack = { navController.navigateUp() } // Kembali ke layar sebelumnya.
            )
        }

        // Rute untuk mengedit item, membawa argumen itemId.
        composable(
            route = ItemEditDestination.routeWithArgs,
            arguments = listOf(navArgument(ItemEditDestination.itemIdArg) {
                type = NavType.IntType  // Argumen itemId bertipe Integer.
            })
        ) {
            ItemEditScreen(
                navigateBack = { navController.popBackStack() },  // Kembali ke layar sebelumnya setelah edit.
                onNavigateUp = { navController.navigateUp() }     // Menggunakan tombol "Up" untuk kembali.
            )
        }
    }
}
