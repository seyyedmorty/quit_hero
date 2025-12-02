package com.example.quithero.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(nav: NavController) {
    NavigationBar(

    ) {
        NavigationBarItem(
            icon = { /* TODO: Add icon */ },
            label = { /* TODO: Add label */ },
            selected = false,
            onClick = { /* TODO: Handle navigation */ }
        )
    }
}