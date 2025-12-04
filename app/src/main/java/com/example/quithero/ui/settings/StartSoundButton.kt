package com.example.quithero.ui.settings

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quithero.viewmodel.StartSoundViewModel
import com.example.quithero.viewmodel.ThemeViewModel

@Composable
fun StartSoundButton() {

    val ctx = LocalContext.current
    val startSoundVM: StartSoundViewModel = viewModel()
    val isEnabled by startSoundVM.isStartSoundEnabled.collectAsState()


    Card(
        modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(30.dp))
            .fillMaxWidth()
            .height(40.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .border(
                        BorderStroke(3.dp, MaterialTheme.colorScheme.primary),
                        RoundedCornerShape(30.dp)
                    )
                    .height(40.dp)
                    .width(90.dp)
                    .clickable {
                        startSoundVM.setStartSound(!isEnabled)
                        Toast.makeText(ctx,
                            if (!isEnabled) "صدای شروع اپلیکیشن فعال شد" else "صدای شروع اپلیکیشن غیرفعال شد",
                            Toast.LENGTH_SHORT
                        ).show()
                    },


                contentAlignment = Alignment.CenterStart
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Sound On",
                        tint = if (isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                    Icon(
                        Icons.AutoMirrored.Filled.VolumeOff,
                        contentDescription = "Sound Off",
                        tint = if (!isEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }


            Text(
                text = "صدای شروع اپلیکیشن",
                modifier = Modifier.padding(end = 16.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = androidx.compose.ui.text.style.TextAlign.End
            )
        }
    }
}