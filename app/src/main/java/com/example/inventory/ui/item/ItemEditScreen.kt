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

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.ui.AppViewModelProvider
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

/**
 * ItemEditDestination adalah objek yang mendefinisikan rute dan argumen untuk layar pengeditan item dalam navigasi aplikasi.
 */
object ItemEditDestination : NavigationDestination {
    override val route = "item_edit" // Rute navigasi untuk layar pengeditan item
    override val titleRes = R.string.edit_item_title // Judul layar pengeditan item
    const val itemIdArg = "itemId" // Nama argumen untuk itemId
    val routeWithArgs = "$route/{$itemIdArg}" // Rute lengkap yang menyertakan argumen itemId
}

/**
 * ItemEditScreen adalah Composable yang menampilkan layar pengeditan item, termasuk UI untuk form entri item dan tombol untuk menyimpan perubahan.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemEditScreen(
    navigateBack: () -> Unit, // Fungsi untuk menavigasi kembali
    onNavigateUp: () -> Unit, // Fungsi untuk menangani tombol kembali
    modifier: Modifier = Modifier, // Modifier untuk kustomisasi layout
    viewModel: ItemEditViewModel = viewModel(factory = AppViewModelProvider.Factory) // ViewModel untuk pengelolaan state
) {
    // Scaffold digunakan untuk menyediakan struktur layar dengan topBar dan konten utama
    Scaffold(
        topBar = {
            // Menampilkan AppBar dengan judul dan tombol untuk navigasi
            InventoryTopAppBar(
                title = stringResource(ItemEditDestination.titleRes), // Mengambil judul dari resource string
                canNavigateBack = true, // Menyediakan navigasi kembali
                navigateUp = onNavigateUp // Menangani aksi navigasi
            )
        },
        modifier = modifier
    ) { innerPadding ->
        // Body utama yang menampilkan form untuk memasukkan data item
        ItemEntryBody(
            itemUiState = viewModel.itemUiState, // Status UI item dari ViewModel
            onItemValueChange = { }, // Fungsi placeholder untuk perubahan nilai item
            onSaveClick = { }, // Fungsi placeholder untuk menyimpan perubahan
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current), // Padding untuk sisi kiri
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current), // Padding untuk sisi kanan
                    top = innerPadding.calculateTopPadding() // Padding untuk bagian atas
                )
                .verticalScroll(rememberScrollState()) // Menambahkan kemampuan scroll vertikal pada konten
        )
    }
}

/**
 * ItemEditScreenPreview adalah Composable untuk preview tampilan ItemEditScreen.
 * Digunakan untuk melihat bagaimana tampilan akan terlihat pada UI editor.
 */
@Preview(showBackground = true)
@Composable
fun ItemEditScreenPreview() {
    InventoryTheme {
        // Menampilkan layar pengeditan item dengan tindakan kosong untuk navigasi
        ItemEditScreen(navigateBack = { /*Do nothing*/ }, onNavigateUp = { /*Do nothing*/ })
    }
}