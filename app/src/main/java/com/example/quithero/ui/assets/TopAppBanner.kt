package com.example.quithero.ui.assets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val banners = listOf(
    com.example.quithero.R.drawable.banner_1,
    com.example.quithero.R.drawable.banner_3,
    com.example.quithero.R.drawable.banner_2,
)

@Composable
fun TopAppBanner() {
    val pagerState = rememberPagerState(
        initialPage = 1,
        pageCount = { banners.size }
    )
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % banners.size
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),

        ) { page ->
            Image(
                painter = painterResource(id = banners[page]),
                contentDescription = "App Banner",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .padding(horizontal = 3.dp),
                contentScale = ContentScale.Crop
            )
        }

        PagerIndicator(
            size = banners.size,
            current = pagerState.currentPage
        )
    }
}

@Composable
fun PagerIndicator(
    size: Int,
    current: Int,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        repeat(size) { index ->
            val isSelected = index == current
            val indicatorSize = if (isSelected) 10.dp else 6.dp
            val color =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface

            Box(
                modifier = Modifier
                    .size(indicatorSize)
                    .clip(CircleShape)
                    .background(color)
            )

            if (index != size - 1) {
                Spacer(modifier = Modifier.width(6.dp))
            }

        }

    }
}