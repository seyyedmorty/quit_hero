package com.example.quithero.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton


import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

import kotlinx.coroutines.delay

@Composable
fun FAButton(modifier: Modifier = Modifier) {
    // 1. حالت اصلی باز/بسته شدن
    var expand by remember { mutableStateOf(false) }

    // 2. یک لیست حالت برای هر اکشن (برای stagger)
    val itemVisible = remember { mutableStateListOf(false, false) }

    // 3. هنگام تغییر expand، با تاخیر کوتاه هر آیتم را نشان/مخفی کن
    LaunchedEffect(key1 = expand) {
        if (expand) {
            // باز شدن: نشان دادن به ترتیب با تاخیر کوچک
            itemVisible[0] = true
            delay(60) // stagger
            itemVisible[1] = true
        } else {
            // بستن: مخفی کردن به ترتیب معکوس برای حس خوب
            itemVisible[1] = false
            delay(40)
            itemVisible[0] = false
        }
    }

    // 4. ساختار: Box که محتوا را در BottomCenter قرار می‌دهد.
    Box(
        modifier = modifier.wrapContentSize(), // اندازه را به محتوا محدود کن
        contentAlignment = Alignment.BottomCenter
    ) {
        // ---- دکمه‌های فرعی (نمایش بالا سر FAB) ----
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier

                .offset(y = (-72).dp)
        ) {
            // Action 1
            AnimatedVisibility(
                visible = itemVisible[0],
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut() + slideOutVertically { it / 2 }
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        expand = false
                    },
                  containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "کشیدم",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            // Action 2
            AnimatedVisibility(
                visible = itemVisible[1],
                enter = fadeIn() + slideInVertically { it / 2 },
                exit = fadeOut() + slideOutVertically { it / 2 }
            ) {
                SmallFloatingActionButton(
                    onClick = {
                        expand = false
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "میخوام بکشم",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }

        // ---- دکمهٔ اصلی (FAB) ----
        FloatingActionButton(
            onClick = { expand = !expand },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = if (expand) Icons.Filled.Close else Icons.Filled.Add,
                contentDescription = "FAB Icon"
            )
        }
    }
}
