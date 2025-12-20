package com.example.quithero.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quithero.viewmodel.OnBoardingViewModel

// =================================================================
// OnboardingScreen اصلی (فقط تغییر Progress Indicator)
// =================================================================

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnBoardingViewModel,
    onFinished: () -> Unit,
) {
    val step by viewModel.currentStep.collectAsState()
    val name by viewModel.name.collectAsState()
    val price by viewModel.pricePerPack.collectAsState()
    val cpd by viewModel.cigarettesPerDay.collectAsState()
    val reason by viewModel.quitReason.collectAsState()
    val visible by viewModel.isOnboardingVisible.collectAsState()

    visible?.let {
        if (!it) {
            onFinished()
            return
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        // حذف شماره مراحل از اینجا برای مینیمال شدن
                        "گام آشنایی",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                navigationIcon = {
                    if (step > 0) {
                        IconButton(onClick = { viewModel.prevStep() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "قبلی",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Progress Indicator متفاوت و برجسته
            AnimatedLinearProgressIndicator(progress = (step + 1) / 5f)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AnimatedContent(
                    // ... (بخش AnimatedContent بدون تغییر)
                    targetState = step,
                    transitionSpec = {
                        if (targetState > initialState) {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> fullWidth },
                                animationSpec = tween(durationMillis = 350)
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            )
                        } else {
                            slideInHorizontally(
                                initialOffsetX = { fullWidth -> -fullWidth },
                                animationSpec = tween(durationMillis = 350)
                            ).togetherWith(
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(durationMillis = 350)
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { currentStep ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (currentStep) {
                            0 -> StepName(name, viewModel::onNameChange, viewModel::nextStep)
                            1 -> StepPrice(price, viewModel::onPriceChange, viewModel::nextStep)
                            2 -> StepCigarettes(cpd, viewModel::onCigarettesPerDayChange, viewModel::nextStep)
                            3 -> StepReason(reason, viewModel::onQuitReasonChange, viewModel::nextStep)
                            4 -> StepSummary(
                                name,
                                price,
                                cpd,
                                reason,
                                { viewModel.finish(onFinished) },
                                { viewModel.prevStep() }
                            )
                        }
                    }
                }
            }
        }
    }
}

// =================================================================
// کامپوزبل های سفارشی و متفاوت
// =================================================================

// Progress Indicator متفاوت
@Composable
private fun AnimatedLinearProgressIndicator(progress: Float) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        // شماره گام در مرکز
        Text(
            text = "گام ${(progress * 5).toInt()}/5",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp) // ضخیم‌تر
                .clip(RoundedCornerShape(8.dp)), // گوشه‌های گرد
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

// فیلد ورودی سفارشی و مینیمال
@Composable
private fun MinimalInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 1
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        ),
        keyboardOptions = keyboardOptions,
        singleLine = maxLines == 1,
        maxLines = maxLines,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant) // باکس متفاوت
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .onFocusChanged { isFocused = it.isFocused }
        ,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    if (value.isEmpty() && !isFocused) {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                            textAlign = TextAlign.Center
                        )
                    }
                    innerTextField()
                }
                if (trailingIcon != null) {
                    Spacer(Modifier.width(8.dp))
                    trailingIcon()
                }
            }
        }
    )
}

// دکمه سفارشی
@Composable
private fun CustomNextButton(
    text: String,
    onClick: () -> Unit,
    isEnabled: Boolean,
    modifier: Modifier = Modifier, // <--- اضافه کردن Modifier
    isSecondary: Boolean = false
) {
    Button(
        onClick = onClick,
        enabled = isEnabled,
        // استفاده از Modifier ورودی به جای fillMaxWidth ثابت
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        // ... (سایر تنظیمات بدون تغییر)
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSecondary) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.primary,
            contentColor = if (isSecondary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

// =================================================================
// OnboardingInputStep جدید
// =================================================================

@Composable
private fun OnboardingInputStep(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    trailingIcon: @Composable (() -> Unit)? = null,
    caption: @Composable (() -> Unit)? = null,
    isNextEnabled: Boolean = value.isNotBlank()
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // عنوان سوال
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(32.dp))

        // فیلد ورودی سفارشی
        MinimalInputField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            keyboardOptions = keyboardOptions,
            trailingIcon = trailingIcon,
            maxLines = maxLines
        )

        if (caption != null) {
            Spacer(Modifier.height(12.dp))
            caption()
        }

        Spacer(Modifier.height(64.dp)) // فاصله زیادتر برای تاکید

        // دکمه بعدی سفارشی
        CustomNextButton(
            text = "بعدی",
            onClick = onNext,
            isEnabled = isNextEnabled,
            isSecondary = false
        )
    }
}

// =================================================================
// مراحل خاص (فقط پارامترها تغییر می‌کنند)
// =================================================================

@Composable
private fun StepName(name: String, onValueChange: (String) -> Unit, onNext: () -> Unit) {
    OnboardingInputStep(
        title = "اسمت چیه؟",
        value = name,
        onValueChange = onValueChange,
        placeholder = "محمد",
        onNext = onNext,
        isNextEnabled = name.isNotBlank()
    )
}

@Composable
private fun StepPrice(price: String, onValueChange: (String) -> Unit, onNext: () -> Unit) {
    val isValid = price.isBlank() || price.toFloatOrNull() != null

    OnboardingInputStep(
        title = "قیمت تقریبی یک پاکت چنده؟",
        value = price,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() || it == '.' }) {
                onValueChange(newValue)
            }
        },
        placeholder = "۷۰۰۰۰",
        onNext = onNext,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

        trailingIcon = {
            Icon(
                Icons.Filled.Info,
                contentDescription = "اطلاعات",
                tint = MaterialTheme.colorScheme.primary
            )
        },

        caption = {
            Text(
                "مقدار را به تومان وارد کنید.",
                style = MaterialTheme.typography.bodyLarge, // فونت بزرگتر
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        isNextEnabled = isValid
    )
}

@Composable
private fun StepCigarettes(cpd: String, onValueChange: (String) -> Unit, onNext: () -> Unit) {
    val isValid = cpd.isBlank() || cpd.toIntOrNull() != null

    OnboardingInputStep(
        title = "روزانه چند نخ می‌کشی؟",
        value = cpd,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                onValueChange(newValue)
            }
        },
        placeholder = "۱۰",
        onNext = onNext,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isNextEnabled = isValid
    )
}

@Composable
private fun StepReason(reason: String, onValueChange: (String) -> Unit, onNext: () -> Unit) {
    OnboardingInputStep(
        title = "چرا میخوایی ترک کنی؟",
        value = reason,
        onValueChange = onValueChange,
        placeholder = "سلامت، پس انداز، خانواده...",
        onNext = onNext,
        maxLines = 3,
        isNextEnabled = true
    )
}


// =================================================================
// مرحله خلاصه (Card Glassmorphism-like)
// =================================================================

@Composable
private fun StepSummary(
    name: String,
    price: String,
    cpd: String,
    reason: String,
    onConfirm: () -> Unit,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "مرور نهایی اطلاعات",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp), // گوشه‌های گردتر
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), // شفافیت ملایم (Glassmorphism-like)
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                val data = listOf(
                    "👤 اسم" to if (name.isBlank()) "ناشناس" else name,
                    "💰 قیمت پاکت" to if (price.isBlank()) "-" else "$price تومان",
                    "🚬 نخ در روز" to if (cpd.isBlank()) "-" else "$cpd نخ",
                    "❤️ دلیل ترک" to if (reason.isBlank()) "وارد نشده" else reason
                )

                data.forEachIndexed { index, pair ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            pair.first,
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            pair.second,
                            style = MaterialTheme.typography.titleLarge, // داده‌ها بزرگتر
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary // برجسته سازی
                        )
                    }
                    if (index < data.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(64.dp))

        // دکمه‌های کنترلی
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // دکمه ویرایش (Secondary)
            CustomNextButton(
                text = "ویرایش",
                onClick = onEdit,
                isEnabled = true,
                isSecondary = true,
                modifier = Modifier.weight(1f) // <--- وزن دهی به جای fillMaxWidth(1f)
            )
            Spacer(Modifier.width(16.dp))

// دکمه تایید (Primary)
            CustomNextButton(
                text = "وقتشه",
                onClick = onConfirm,
                isEnabled = true,
                isSecondary = false,
                modifier = Modifier.weight(1f) // <--- وزن دهی به جای fillMaxWidth(1f)
            )
        }
    }
}

