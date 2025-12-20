package com.example.quithero.ui.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.quithero.R

// مدل داده برای هر پله از مسیر
data class BadgeStep(
    val title: String,
    val daysRequired: Int,
    val imageRes: Int
)

// لیست بج‌ها بر اساس روز
val badgeRoadmap = listOf(
    BadgeStep("یک هفته پاکی", 7, R.drawable.week_1),
    BadgeStep("یک ماه پایداری", 30, R.drawable.month_1),
    BadgeStep("سه ماه قهرمانی", 90, R.drawable.month_3),
    BadgeStep("شش ماه قدرت", 180, R.drawable.month_6),
    BadgeStep("یک سال رهایی کامل", 365, R.drawable.year_1)
)

@Composable
fun BadgeIcon(days: Int, onClick: () -> Unit) {
    val badgeResource = getBadgePic(days)
    Card(
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = androidx.compose.ui.graphics.Color.Transparent),
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .height(250.dp)
    ) {
        Image(
            painter = painterResource(id = badgeResource),
            contentDescription = "بج فعلی",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun BadgeRoadmapList(currentDays: Int) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "نقشه راه قهرمانی تو",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Box(modifier = Modifier.fillMaxWidth()) {

                VerticalDivider(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 31.dp, top = 20.dp, bottom = 20.dp)
                        .height(450.dp), // ارتفاع تقریبی بر اساس تعداد بج‌ها
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant
                )

                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    badgeRoadmap.forEach { step ->
                        BadgeRow(
                            step = step,
                            isUnlocked = currentDays >= step.daysRequired
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun BadgeRow(step: BadgeStep, isUnlocked: Boolean) {
    val grayScaleMatrix = remember { ColorMatrix().apply { setToSaturation(0f) } }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ۱. دایره یا تصویر نشان
        Box(
            modifier = Modifier.size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = step.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                colorFilter = if (isUnlocked) null else ColorFilter.colorMatrix(grayScaleMatrix)
            )
        }

        // ۲. متون توضیحات
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = step.title,
                style = MaterialTheme.typography.titleMedium,
                color = if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
            )
            Text(
                text = "${step.daysRequired} روز پاکی",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

fun getBadgePic(days: Int): Int {
    return when {
        days in 7..29 -> R.drawable.week_1
        days in 30..89 -> R.drawable.month_1
        days in 90..179 -> R.drawable.month_3
        days in 180..364 -> R.drawable.month_6
        days >= 365 -> R.drawable.year_1
        else -> R.drawable.default_badge
    }
}