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

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

/**
 * ItemDetailsDestination adalah objek yang berfungsi untuk mendefinisikan rute navigasi dan argumen yang diperlukan untuk menuju layar detail item.
 */
object ItemDetailsDestination : NavigationDestination {
    override val route = "item_details" // Rute untuk navigasi ke layar detail item
    override val titleRes = R.string.item_detail_title // Resource string untuk judul layar
    const val itemIdArg = "itemId" // Nama argumen untuk ID item
    val routeWithArgs = "$route/{$itemIdArg}" // Rute lengkap yang menyertakan argumen itemId
}

/**
 * ItemDetailsScreen adalah Composable untuk menampilkan detail item, termasuk UI seperti AppBar, tombol aksi, dan konten utama dari item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailsScreen(
    navigateToEditItem: (Int) -> Unit, // Fungsi untuk menavigasi ke layar edit item
    navigateBack: () -> Unit, // Fungsi untuk menavigasi kembali
    modifier: Modifier = Modifier // Modifier opsional untuk kustomisasi layout
) {
    Scaffold(
        topBar = { // Menampilkan AppBar di bagian atas
            InventoryTopAppBar(
                title = stringResource(ItemDetailsDestination.titleRes), // Menampilkan judul
                canNavigateBack = true, // Menentukan bahwa pengguna dapat menavigasi kembali
                navigateUp = navigateBack // Menangani aksi kembali
            )
        },
        floatingActionButton = { // Tombol aksi terapung (FAB) untuk mengedit item
            FloatingActionButton(
                onClick = { navigateToEditItem(0) }, // Menavigasi ke layar edit item
                shape = MaterialTheme.shapes.medium, // Bentuk tombol FAB
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)) // Padding untuk FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Edit, // Ikon edit
                    contentDescription = stringResource(R.string.edit_item_title) // Deskripsi konten untuk aksesibilitas
                )
            }
        }, modifier = modifier // Modifier untuk tampilan keseluruhan
    ) { innerPadding -> // Inner padding untuk konten utama
        ItemDetailsBody(
            itemDetailsUiState = ItemDetailsUiState(), // Menyediakan status UI untuk detail item
            onSellItem = { }, // Fungsi placeholder untuk aksi jual
            onDelete = { }, // Fungsi placeholder untuk aksi hapus
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState()) // Membuat konten dapat digulir secara vertikal
        )
    }
}

/**
 * ItemDetailsBody adalah Composable untuk menampilkan konten detail item termasuk tombol untuk aksi jual dan hapus.
 */
@Composable
private fun ItemDetailsBody(
    itemDetailsUiState: ItemDetailsUiState, // Status UI yang berisi detail item
    onSellItem: () -> Unit, // Fungsi untuk aksi jual
    onDelete: () -> Unit, // Fungsi untuk aksi hapus
    modifier: Modifier = Modifier // Modifier opsional untuk kustomisasi layout
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)), // Padding untuk kolom
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)) // Jarak antar elemen vertikal
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) } // Menyimpan status konfirmasi hapus

        ItemDetails(
            item = itemDetailsUiState.itemDetails.toItem(), // Menampilkan detail item
            modifier = Modifier.fillMaxWidth() // Membuat tampilan lebar penuh
        )
        Button(
            onClick = onSellItem, // Tombol untuk aksi jual
            modifier = Modifier.fillMaxWidth(), // Membuat tombol lebar penuh
            shape = MaterialTheme.shapes.small, // Bentuk tombol
            enabled = true // Menyediakan tombol yang selalu aktif
        ) {
            Text(stringResource(R.string.sell)) // Teks tombol untuk aksi jual
        }
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true }, // Tombol untuk mengkonfirmasi hapus
            shape = MaterialTheme.shapes.small, // Bentuk tombol
            modifier = Modifier.fillMaxWidth() // Membuat tombol lebar penuh
        ) {
            Text(stringResource(R.string.delete)) // Teks tombol untuk aksi hapus
        }
        // Menampilkan dialog konfirmasi hapus jika deleteConfirmationRequired true
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false // Reset status konfirmasi setelah hapus
                    onDelete() // Panggil fungsi hapus item
                },
                onDeleteCancel = { deleteConfirmationRequired = false }, // Reset status jika dibatalkan
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium)) // Padding untuk dialog
            )
        }
    }
}

/**
 * ItemDetails adalah Composable untuk menampilkan detail dari item tertentu dalam format card.
 */
@Composable
fun ItemDetails(
    item: Item, modifier: Modifier = Modifier // Menyediakan item dan modifier opsional
) {
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Warna latar belakang card
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Warna teks pada card
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth() // Membuat kolom lebar penuh
                .padding(dimensionResource(id = R.dimen.padding_medium)), // Padding untuk konten dalam card
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium)) // Jarak antar elemen
        ) {
            // Menampilkan detail item menggunakan baris
            ItemDetailsRow(
                labelResID = R.string.item, // Label untuk nama item
                itemDetail = item.name, // Detail nama item
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemDetailsRow(
                labelResID = R.string.quantity_in_stock, // Label untuk kuantitas
                itemDetail = item.quantity.toString(), // Kuantitas item
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            ItemDetailsRow(
                labelResID = R.string.price, // Label untuk harga
                itemDetail = item.formatedPrice(), // Harga item dalam format tertentu
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }

    }
}

/**
 * ItemDetailsRow adalah Composable untuk menampilkan baris label dan detail item.
 */
@Composable
private fun ItemDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier // Label dan detail item
) {
    Row(modifier = modifier) {
        Text(stringResource(labelResID)) // Menampilkan label
        Spacer(modifier = Modifier.weight(1f)) // Spacer untuk memberi jarak antara label dan detail
        Text(text = itemDetail, fontWeight = FontWeight.Bold) // Menampilkan detail dengan teks tebal
    }
}

/**
 * DeleteConfirmationDialog adalah Composable untuk menampilkan dialog konfirmasi penghapusan item.
 */
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier // Fungsi konfirmasi dan pembatalan
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(stringResource(R.string.attention)) }, // Judul dialog
        text = { Text(stringResource(R.string.delete_question)) }, // Teks pertanyaan konfirmasi
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) { // Tombol untuk membatalkan penghapusan
                Text(stringResource(R.string.no)) // Teks tombol "Tidak"
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) { // Tombol untuk mengonfirmasi penghapusan
                Text(stringResource(R.string.yes)) // Teks tombol "Ya"
            }
        })
}

/**
 * Preview untuk ItemDetailsScreen yang menampilkan UI contoh dengan data mock.
 */
@Preview(showBackground = true)
@Composable
fun ItemDetailsScreenPreview() {
    InventoryTheme {
        ItemDetailsBody(
            ItemDetailsUiState(
                outOfStock = true, // Status item habis stok
                itemDetails = ItemDetails(1, "Pen", "$100", "10") // Data contoh item
            ),
            onSellItem = {},
            onDelete = {}
        )
    }
}
