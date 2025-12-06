package com.example.quithero.ui.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

@Composable
fun ReasonDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
) {
    var reason = remember { mutableStateOf("") }

    CompositionLocalProvider(LocalLayoutDirection.provides(LayoutDirection.Rtl)) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "فدا سرت! دوباره تلاش میکنیم",
                style = MaterialTheme.typography.titleLarge) },
            text = {
                TextField(
                    value = reason.value,
                    onValueChange = { reason.value = it },
                    placeholder = { Text("چرا کشیدم...") }
                )
            },
            confirmButton = {
                Text(
                    text = "کشیدم",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable {
                            onConfirm(reason.value)
                            onDismiss()
                        }
                )
            },
            dismissButton = {
                Text(
                    text = "نکشیدم",
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onDismiss() }
                )
            },

            )
    }
}