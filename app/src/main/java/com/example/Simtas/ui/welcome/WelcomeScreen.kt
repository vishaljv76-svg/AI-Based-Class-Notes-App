package com.example.Simtas.ui.welcome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.theme.InnerCircleBg
import com.example.Simtas.ui.theme.PurplePrimary
import com.example.Simtas.ui.theme.SplashBackground
import com.example.Simtas.ui.theme.TextDarkPrimary
import com.example.Simtas.ui.theme.TextMuted
import com.example.Simtas.ui.theme.TextSubtitle

/**
 * Onboarding/Welcome screen following the MVVM pattern.
 */
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = remember { WelcomeViewModel() },
    onGetStartedClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    WelcomeScreenContent(
        modifier = modifier,
        onGetStartedClick = {
            viewModel.onGetStartedClicked()
            onGetStartedClick()
        },
        onLoginClick = {
            viewModel.onLoginClicked()
            onLoginClick()
        },
        onSignUpClick = {
            viewModel.onSignUpClicked()
            onSignUpClick()
        }
    )
}

@Composable
fun WelcomeScreenContent(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = SplashBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Top Header Bar with App Logo
                TopHeaderBar()

                Spacer(modifier = Modifier.height(20.dp))

                // Central Hero Card Illustration
                HeroIllustrationSection()

                Spacer(modifier = Modifier.height(28.dp))

                // Title & Subtitle
                Text(
                    text = "Be in class.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Not in your notebook.",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "AI-powered lecture notes that help you listen,\nunderstand and remember more.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextSubtitle,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                // 3 Feature Bullet Points
                FeatureListSection()

                Spacer(modifier = Modifier.height(28.dp))
            }

            // Bottom Action Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                // Get Started Button
                Button(
                    onClick = onGetStartedClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(27.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PurplePrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Canvas(modifier = Modifier.size(18.dp)) {
                            // Right arrow icon
                            drawRightArrow(Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Get Started",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Login Link
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Already have an account? ",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Text(
                        text = "Login",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary,
                        modifier = Modifier.clickable { onLoginClick() }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sign Up Link
                Text(
                    text = "New here? Sign Up",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }
        }
    }
}

/**
 * Top App Logo Bar
 */
@Composable
private fun TopHeaderBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(PurplePrimary)
        ) {
            Canvas(modifier = Modifier.size(18.dp)) {
                // Miniature Note Icon
                drawRoundRect(
                    color = Color.White,
                    topLeft = Offset(size.width * 0.15f, size.height * 0.15f),
                    size = Size(size.width * 0.7f, size.height * 0.7f),
                    cornerRadius = CornerRadius(3.dp.toPx()),
                    style = Stroke(width = 1.8.dp.toPx())
                )
                drawLine(
                    color = Color.White,
                    start = Offset(size.width * 0.35f, size.height * 0.4f),
                    end = Offset(size.width * 0.65f, size.height * 0.4f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawLine(
                    color = Color.White,
                    start = Offset(size.width * 0.35f, size.height * 0.6f),
                    end = Offset(size.width * 0.65f, size.height * 0.6f),
                    strokeWidth = 1.8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "SmartNotes AI",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkPrimary
        )
    }
}

/**
 * Hero Card Illustration in the center
 */
@Composable
private fun HeroIllustrationSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp)
    ) {
        // Soft Glow Circle
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(InnerCircleBg)
        )

        // Sparkle icon background left
        Canvas(
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 24.dp)
        ) {
            drawSparkleStar(PurplePrimary.copy(alpha = 0.5f), Offset(size.width / 2, size.height / 2), size.width / 2)
        }

        // Tilted Elevated White Card
        Box(
            modifier = Modifier
                .width(230.dp)
                .rotate(-4f)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(20.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.12f)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(22.dp)
                            .clip(CircleShape)
                            .background(InnerCircleBg)
                    ) {
                        Canvas(modifier = Modifier.size(12.dp)) {
                            drawSparkleStar(PurplePrimary, Offset(size.width / 2, size.height / 2), size.width / 2)
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "A little clarity.",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextDarkPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "A lot more learning.",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Accent line
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(InnerCircleBg)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.55f)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(InnerCircleBg.copy(alpha = 0.6f))
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Checkmark Item 1
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        drawCheckmarkCircle(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Key ideas, connected",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Checkmark Item 2
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        drawCheckmarkCircle(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ready when you are",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }
            }
        }

        // Floating Pill Badge at bottom right
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-8).dp, y = (-12).dp)
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = Color.Black.copy(alpha = 0.08f),
                    spotColor = Color.Black.copy(alpha = 0.12f)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFE8E2FF))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Waveform icon
                Canvas(modifier = Modifier.size(16.dp)) {
                    drawWaveform(PurplePrimary)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Lecture → notes",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }
        }
    }
}

/**
 * 3 Feature Bullet Points
 */
@Composable
private fun FeatureListSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        FeatureItemRow(
            icon = { MicrophoneIcon(PurplePrimary) },
            text = "Capture every important idea"
        )
        FeatureItemRow(
            icon = { SparkleIcon(PurplePrimary) },
            text = "Turn audio into clear, structured notes"
        )
        FeatureItemRow(
            icon = { BrainIcon(PurplePrimary) },
            text = "Make it stick with personalized quizzes"
        )
    }
}

@Composable
private fun FeatureItemRow(
    icon: @Composable () -> Unit,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(24.dp)
        ) {
            icon()
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = TextDarkPrimary
        )
    }
}

// --- Canvas Drawing Helpers for Icons ---

@Composable
private fun MicrophoneIcon(color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        // Mic body
        drawRoundRect(
            color = color,
            topLeft = Offset(size.width * 0.35f, size.height * 0.15f),
            size = Size(size.width * 0.3f, size.height * 0.45f),
            cornerRadius = CornerRadius(4.dp.toPx()),
            style = stroke
        )
        // Mic arc
        val path = Path().apply {
            moveTo(size.width * 0.2f, size.height * 0.45f)
            quadraticTo(
                size.width * 0.2f, size.height * 0.75f,
                size.width * 0.5f, size.height * 0.75f
            )
            quadraticTo(
                size.width * 0.8f, size.height * 0.75f,
                size.width * 0.8f, size.height * 0.45f
            )
        }
        drawPath(path = path, color = color, style = stroke)
        // Mic stand
        drawLine(
            color = color,
            start = Offset(size.width * 0.5f, size.height * 0.75f),
            end = Offset(size.width * 0.5f, size.height * 0.9f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun SparkleIcon(color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        drawSparkleStar(color, Offset(size.width / 2, size.height / 2), size.width / 2)
    }
}

@Composable
private fun BrainIcon(color: Color) {
    Canvas(modifier = Modifier.size(20.dp)) {
        val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        // Left hemisphere circle
        drawCircle(
            color = color,
            radius = size.width * 0.28f,
            center = Offset(size.width * 0.36f, size.height * 0.5f),
            style = stroke
        )
        // Right hemisphere circle
        drawCircle(
            color = color,
            radius = size.width * 0.28f,
            center = Offset(size.width * 0.64f, size.height * 0.5f),
            style = stroke
        )
        // Connecting line
        drawLine(
            color = color,
            start = Offset(size.width * 0.36f, size.height * 0.5f),
            end = Offset(size.width * 0.64f, size.height * 0.5f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawSparkleStar(
    color: Color,
    center: Offset,
    starRadius: Float
) {
    val path = Path().apply {
        val cx = center.x
        val cy = center.y
        val r = starRadius
        moveTo(cx, cy - r)
        quadraticTo(cx, cy, cx + r, cy)
        quadraticTo(cx, cy, cx, cy + r)
        quadraticTo(cx, cy, cx - r, cy)
        quadraticTo(cx, cy, cx, cy - r)
        close()
    }
    drawPath(path = path, color = color)
}

private fun DrawScope.drawCheckmarkCircle(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(
        color = color,
        radius = size.width * 0.42f,
        center = Offset(size.width / 2, size.height / 2),
        style = stroke
    )
    val checkPath = Path().apply {
        moveTo(size.width * 0.32f, size.height * 0.5f)
        lineTo(size.width * 0.46f, size.height * 0.64f)
        lineTo(size.width * 0.68f, size.height * 0.38f)
    }
    drawPath(path = checkPath, color = color, style = stroke)
}

private fun DrawScope.drawWaveform(color: Color) {
    val strokeWidth = 2.dp.toPx()
    val heights = listOf(0.4f, 0.8f, 0.5f, 0.9f, 0.3f)
    val step = size.width / (heights.size + 1)
    heights.forEachIndexed { index, h ->
        val x = step * (index + 1)
        val hPx = size.height * h
        val startY = (size.height - hPx) / 2
        drawLine(
            color = color,
            start = Offset(x, startY),
            end = Offset(x, startY + hPx),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawRightArrow(color: Color) {
    val strokeWidth = 2.dp.toPx()
    drawLine(
        color = color,
        start = Offset(size.width * 0.2f, size.height * 0.5f),
        end = Offset(size.width * 0.8f, size.height * 0.5f),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    val headPath = Path().apply {
        moveTo(size.width * 0.58f, size.height * 0.28f)
        lineTo(size.width * 0.82f, size.height * 0.5f)
        lineTo(size.width * 0.58f, size.height * 0.72f)
    }
    drawPath(
        path = headPath,
        color = color,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    AIBasedClassNotesAppTheme {
        WelcomeScreenContent()
    }
}
