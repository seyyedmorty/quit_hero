package com.example.quithero.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


data class BenefitStage(
    val day: Int,
    val title: String,
)

@Composable
fun QuitBenefitsScreen(currentDay: Int) {

    // Full list of benefits
    val benefits = listOf(
        BenefitStage(0, "ضربان نرمال (20دقیقه)"),
        BenefitStage(1, "پاک‌سازی از CO"),
        BenefitStage(2, "حس بویایی بهتر"),
        BenefitStage(3, "تنفس راحت‌تر"),
        BenefitStage(7, "انرژی بیشتر"),
        BenefitStage(14, "گردش خون بهتر"),
        BenefitStage(30, "پوست شفاف‌تر"),
        BenefitStage(60, "تنفس پایدارتر"),
        BenefitStage(90, "عملکرد ریه بهتر"),
        BenefitStage(180, "اعصاب آرام‌تر"),
        BenefitStage(365, "ریسک بیماری‌های قلبی ، نصف"),
    )


    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(benefits) { item ->

                    val isReached = currentDay >= item.day
                    val bg =
                        if (isReached) Color(0xFF4CAF50) else MaterialTheme.colorScheme.surface
                    val textColor = if (isReached) Color.White else Color.Black

                    CompositionLocalProvider(
                        LocalLayoutDirection provides LayoutDirection.Rtl
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = bg)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center // Card وسط چین میشه
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp) // فاصله ثابت بین Text ها
                                ) {
                                    Text(
                                        text = "${item.day} روز",
                                        color = if (isReached) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )

                                    Text(
                                        text = "→ ${item.title}",
                                        color = if (isReached) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        style = MaterialTheme.typography.bodyLarge,
                                    )
                                }
                            }
                        }


                    }

                }

            }
        }
    }
}
