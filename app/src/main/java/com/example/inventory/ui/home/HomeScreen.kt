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

package com.example.inventory.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.inventory.InventoryTopAppBar
import com.example.inventory.R
import com.example.inventory.data.Item
import com.example.inventory.ui.item.formatedPrice
import com.example.inventory.ui.navigation.NavigationDestination
import com.example.inventory.ui.theme.InventoryTheme

// Objek untuk tujuan navigasi layar Home
object HomeDestination : NavigationDestination {
    override val route = "home" // Rute navigasi ke layar Home
    override val titleRes = R.string.app_name // ID sumber daya untuk judul Home
}

/**
 * Fungsi utama untuk menampilkan layar Home
 */
@OptIn(ExperimentalMaterial3Api::class) // Anotasi untuk menandai penggunaan API eksperimental
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit, // Fungsi navigasi untuk menambah item baru
    navigateToItemUpdate: (Int) -> Unit, // Fungsi navigasi untuk memperbarui item berdasarkan ID
    modifier: Modifier = Modifier // Modifier opsional untuk komposisi
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior() // Mengatur perilaku scroll untuk TopAppBar

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), // Menghubungkan scroll TopAppBar dengan Scaffold
        topBar = {
            InventoryTopAppBar(
                title = stringResource(HomeDestination.titleRes), // Mengambil judul dari resource
                canNavigateBack = false, // Tidak ada navigasi kembali di layar Home
                scrollBehavior = scrollBehavior // Mengatur scroll
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry, // Navigasi ke entri item baru ketika diklik
                shape = MaterialTheme.shapes.medium, // Menggunakan bentuk medium untuk FAB
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)) // Mengatur padding FAB
            ) {
                Icon(
                    imageVector = Icons.Default.Add, // Menggunakan ikon tambah dari material icons
                    contentDescription = stringResource(R.string.item_entry_title) // Deskripsi konten dari ikon
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            itemList = listOf(), // Data item kosong, untuk ditampilkan dalam HomeBody
            onItemClick = navigateToItemUpdate, // Mengatur navigasi pada klik item
            modifier = modifier.fillMaxSize(), // Memperluas ukuran ke seluruh layar
            contentPadding = innerPadding, // Mengatur padding dari Scaffold
        )
    }
}

// Fungsi untuk menampilkan isi dari layar Home
@Composable
private fun HomeBody(
    itemList: List<Item>, // Daftar item untuk ditampilkan
    onItemClick: (Int) -> Unit, // Fungsi navigasi ketika item diklik
    modifier: Modifier = Modifier, // Modifier opsional
    contentPadding: PaddingValues = PaddingValues(0.dp), // Padding untuk konten, default 0dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, // Mengatur isi secara horizontal ke tengah
        modifier = modifier,
    ) {
        if (itemList.isEmpty()) { // Jika daftar item kosong
            Text(
                text = stringResource(R.string.no_item_description), // Menampilkan teks tidak ada item
                textAlign = TextAlign.Center, // Mengatur teks rata tengah
                style = MaterialTheme.typography.titleLarge, // Menggunakan gaya teks besar
                modifier = Modifier.padding(contentPadding), // Mengatur padding konten
            )
        } else {
            InventoryList(
                itemList = itemList, // Daftar item yang akan ditampilkan
                onItemClick = { onItemClick(it.id) }, // Panggilan fungsi klik dengan ID item
                contentPadding = contentPadding, // Padding untuk konten daftar
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small)) // Padding horizontal daftar
            )
        }
    }
}

// Fungsi untuk menampilkan daftar item dengan LazyColumn
@Composable
private fun InventoryList(
    itemList: List<Item>, // Daftar item yang ditampilkan
    onItemClick: (Item) -> Unit, // Fungsi navigasi ketika item diklik
    contentPadding: PaddingValues, // Padding untuk konten
    modifier: Modifier = Modifier // Modifier opsional
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = itemList, key = { it.id }) { item -> // Mengidentifikasi item berdasarkan ID
            InventoryItem(item = item,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small)) // Padding untuk setiap item
                    .clickable { onItemClick(item) }) // Fungsi ketika item diklik
        }
    }
}

// Komponen untuk menampilkan item individual dalam kartu
@Composable
private fun InventoryItem(
    item: Item, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp) // Ketinggian kartu dengan elevasi 2dp
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)), // Padding untuk konten dalam kartu
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)) // Jarak antar elemen
        ) {
            Row(
                modifier = Modifier.fillMaxWidth() // Mengisi lebar penuh kartu
            ) {
                Text(
                    text = item.name, // Nama item
                    style = MaterialTheme.typography.titleLarge, // Gaya teks besar
                )
                Spacer(Modifier.weight(1f)) // Spacer untuk membuat jarak di antara teks
                Text(
                    text = item.formatedPrice(), // Harga item yang sudah diformat
                    style = MaterialTheme.typography.titleMedium // Gaya teks medium
                )
            }
            Text(
                text = stringResource(R.string.in_stock, item.quantity), // Menampilkan jumlah stok
                style = MaterialTheme.typography.titleMedium // Gaya teks medium
            )
        }
    }
}

// Fungsi preview untuk melihat tampilan HomeBody dengan daftar item contoh
@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    InventoryTheme {
        HomeBody(listOf(
            Item(1, "Game", 100.0, 20), Item(2, "Pen", 200.0, 30), Item(3, "TV", 300.0, 50)
        ), onItemClick = {})
    }
}

// Fungsi preview untuk melihat tampilan HomeBody ketika daftar item kosong
@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    InventoryTheme {
        HomeBody(listOf(), onItemClick = {})
    }
}

// Fungsi preview untuk melihat tampilan satu item dalam kartu
@Preview(showBackground = true)
@Composable
fun InventoryItemPreview() {
    InventoryTheme {
        InventoryItem(
            Item(1, "Game", 100.0, 20),
        )
    }
}