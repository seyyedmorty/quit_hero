package com.example.quithero.ui.screens

import android.annotation.SuppressLint
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.quithero.data.Profile
import com.example.quithero.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(nav: NavController) {
    val ctx = LocalContext.current
    val viewModel: ProfileViewModel = viewModel()
    val profile by viewModel.profileState.collectAsState()
    val scrollState = rememberScrollState()

    // استیت‌ها
    var name by remember { mutableStateOf(profile?.name ?: "") }
    var cigarettesPerDay by remember { mutableStateOf(profile?.cigarettesPerDay?.toString() ?: "") }
    var pricePerPack by remember { mutableStateOf(profile?.pricePerPack?.toString() ?: "") }
    var quitReason by remember { mutableStateOf(profile?.quitReason ?: "") }

    LaunchedEffect(profile) {
        profile?.let {
            name = it.name
            cigarettesPerDay = it.cigarettesPerDay.toString()
            pricePerPack = it.pricePerPack.toString()
            quitReason = it.quitReason
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(

        ) { pad ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
                    .verticalScroll(scrollState)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // کارت اطلاعات شخصی
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "اطلاعات کاربری",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        CustomTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "نام",
                            icon = Icons.Default.Person
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // کارت آمار سیگار
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "آمار مصرف و هزینه",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        CustomTextField(
                            value = cigarettesPerDay,
                            onValueChange = { cigarettesPerDay = it },
                            label = "تعداد نخ در روز",
                            icon = Icons.Default.SmokingRooms,
                            isNumber = true
                        )

                        Spacer(Modifier.height(12.dp))

                        CustomTextField(
                            value = pricePerPack,
                            onValueChange = { pricePerPack = it },
                            label = "قیمت هر پاکت (تومان)",
                            icon = Icons.Default.Payments,
                            isNumber = true
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                // کارت انگیزه
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "هدف شما",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        CustomTextField(
                            value = quitReason,
                            onValueChange = { quitReason = it },
                            label = "علت اصلی تغییر",
                            icon = Icons.Default.Favorite,
                            singleLine = false
                        )
                    }
                }

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        val cigs = cigarettesPerDay.toIntOrNull() ?: 0
                        val price = pricePerPack.toFloatOrNull() ?: 0f

                        if (name.isBlank() || cigs <= 0 || price <= 0f) {
                            Toast.makeText(
                                ctx,
                                "لطفاً تمامی فیلدها را به درستی پر کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            val updatedProfile = Profile(
                                id = profile?.id ?: 0,
                                name = name,
                                cigarettesPerDay = cigs,
                                pricePerPack = price,
                                quitReason = quitReason,
                                isReasonAsked = true
                            )
                            viewModel.updateProfile(updatedProfile)
                            Toast.makeText(ctx, "تغییرات با موفقیت ذخیره شد", Toast.LENGTH_SHORT)
                                .show()
                            nav.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(Icons.Default.Check, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("ذخیره تغییرات", style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isNumber: Boolean = false,
    singleLine: Boolean = true
) {
    var isFirstChangeAfterFocus by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (isFirstChangeAfterFocus) {
                val char = newValue.lastOrNull()?.toString() ?: ""
                onValueChange(char)
                isFirstChangeAfterFocus = false
            } else {
                onValueChange(newValue)
            }
        },
        label = { Text(label) },
        leadingIcon = {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    isFirstChangeAfterFocus = true
                } else {
                    isFirstChangeAfterFocus = false
                }
            },
        shape = RoundedCornerShape(12.dp),
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumber) KeyboardType.Number else KeyboardType.Text
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
        )
    )
}