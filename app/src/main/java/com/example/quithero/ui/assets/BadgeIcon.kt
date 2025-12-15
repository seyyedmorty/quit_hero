package com.example.quithero.ui.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.quithero.R

@Composable
fun BadgeIcon(days: Int) {
    val badgeResource = getBadgePic(days)
    Image(
        painter = painterResource(id = badgeResource),
        contentDescription = "Badge",
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .widthIn(max = 400.dp)
            .fillMaxHeight()
            .heightIn(max = 400.dp),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
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