package com.example.Simtas.ui.transcript

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

val MiniStripBg = Color(0xFFF1EDFE)

/**
 * Transcript Screen component adhering to MVVM architecture.
 */
@Composable
fun TranscriptScreen(
    modifier: Modifier = Modifier,
    viewModel: TranscriptViewModel = remember { TranscriptViewModel() },
    onBackClick: () -> Unit = {},
    onMoreOptionsClick: () -> Unit = {},
    onGenerateNotesClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    TranscriptScreenContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onMoreOptionsClick = onMoreOptionsClick,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onPlayPauseToggle = viewModel::togglePlayPause,
        onEditTextClick = viewModel::onEditTextClicked,
        onGenerateNotesClick = {
            viewModel.onGenerateNotesClicked()
            onGenerateNotesClick()
        }
    )
}

@Composable
fun TranscriptScreenContent(
    uiState: TranscriptUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onMoreOptionsClick: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    onPlayPauseToggle: () -> Unit = {},
    onEditTextClick: () -> Unit = {},
    onGenerateNotesClick: () -> Unit = {}
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
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Top Bar
                TopNavigationBar(
                    onBackClick = onBackClick,
                    onMoreOptionsClick = onMoreOptionsClick
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Lecture Title
                Text(
                    text = uiState.lectureTitle,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Language Chip & Metadata Row
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(InnerCircleBg)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = uiState.language,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PurplePrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = uiState.durationAndConfidence,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Search & Filter Input Bar
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = "Search transcript",
                            fontSize = 14.sp,
                            color = TextMuted
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color.Transparent,
                        focusedTextColor = TextDarkPrimary,
                        unfocusedTextColor = TextDarkPrimary
                    ),
                    leadingIcon = {
                        Canvas(modifier = Modifier.size(18.dp)) {
                            drawSearchIcon(TextMuted)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = {}) {
                            Canvas(modifier = Modifier.size(18.dp)) {
                                drawFilterSlidersIcon(PurplePrimary)
                            }
                        }
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Mini Audio Playback Control Strip
                MiniAudioControlStrip(
                    currentTimeText = uiState.currentTimeText,
                    totalTimeText = uiState.totalTimeText,
                    isPlaying = uiState.isPlaying,
                    onPlayPauseToggle = onPlayPauseToggle
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Transcript Card Container
                TranscriptCard(
                    paragraphs = uiState.paragraphs,
                    excerptFooterText = uiState.excerptFooterText,
                    onEditTextClick = onEditTextClick
                )

                Spacer(modifier = Modifier.height(28.dp))
            }

            // Bottom CTA Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                // Generate Notes Button
                Button(
                    onClick = onGenerateNotesClick,
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
                            drawSparkleStarIcon(Color.White)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Generate Notes",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Review names and technical terms before generating.",
                    fontSize = 11.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Top Navigation Bar
 */
@Composable
private fun TopNavigationBar(
    onBackClick: () -> Unit,
    onMoreOptionsClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(36.dp)
            ) {
                Canvas(modifier = Modifier.size(20.dp)) {
                    drawBackArrow(TextDarkPrimary)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Transcript",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkPrimary
            )
        }

        IconButton(
            onClick = onMoreOptionsClick,
            modifier = Modifier.size(36.dp)
        ) {
            Canvas(modifier = Modifier.size(20.dp)) {
                drawThreeDotsMenu(TextDarkPrimary)
            }
        }
    }
}

/**
 * Mini Audio Playback Control Strip
 */
@Composable
private fun MiniAudioControlStrip(
    currentTimeText: String,
    totalTimeText: String,
    isPlaying: Boolean,
    onPlayPauseToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MiniStripBg)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onPlayPauseToggle,
                    modifier = Modifier.size(24.dp)
                ) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        if (isPlaying) {
                            drawPauseIcon(PurplePrimary)
                        } else {
                            drawPlayIcon(PurplePrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "$currentTimeText / $totalTimeText",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary
                )
            }

            Canvas(modifier = Modifier.size(18.dp)) {
                drawSpeakerVolumeIcon(PurplePrimary)
            }
        }
    }
}

/**
 * Transcript Content Card Item
 */
@Composable
private fun TranscriptCard(
    paragraphs: List<TranscriptParagraph>,
    excerptFooterText: String,
    onEditTextClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.04f),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(shape)
            .background(Color.White)
            .padding(18.dp)
    ) {
        Column {
            // Header Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Lecture transcript",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onEditTextClick() }
                ) {
                    Canvas(modifier = Modifier.size(14.dp)) {
                        drawPencilEditIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Edit text",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Paragraphs List
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                paragraphs.forEach { paragraph ->
                    Column {
                        Text(
                            text = "${paragraph.timestamp} · ${paragraph.speaker}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = PurplePrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        val textColor = if (paragraph.isHighlighted) PurplePrimary else TextDarkPrimary
                        Text(
                            text = paragraph.text,
                            fontSize = 13.sp,
                            fontWeight = if (paragraph.isHighlighted) FontWeight.Medium else FontWeight.Normal,
                            color = textColor,
                            lineHeight = 19.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = excerptFooterText,
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}

// --- Custom Canvas Graphics Helpers ---

private fun DrawScope.drawSearchIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(
        color = color,
        radius = size.width * 0.32f,
        center = Offset(size.width * 0.4f, size.height * 0.4f),
        style = stroke
    )
    drawLine(
        color = color,
        start = Offset(size.width * 0.62f, size.height * 0.62f),
        end = Offset(size.width * 0.85f, size.height * 0.85f),
        strokeWidth = 1.8.dp.toPx(),
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawFilterSlidersIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.15f, size.height * 0.3f), end = Offset(size.width * 0.85f, size.height * 0.3f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.15f, size.height * 0.7f), end = Offset(size.width * 0.85f, size.height * 0.7f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(color = color, radius = 2.5.dp.toPx(), center = Offset(size.width * 0.35f, size.height * 0.3f))
    drawCircle(color = color, radius = 2.5.dp.toPx(), center = Offset(size.width * 0.65f, size.height * 0.7f))
}

private fun DrawScope.drawPauseIcon(color: Color) {
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.2f), end = Offset(size.width * 0.35f, size.height * 0.8f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.65f, size.height * 0.2f), end = Offset(size.width * 0.65f, size.height * 0.8f), strokeWidth = 2.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawPlayIcon(color: Color) {
    val path = Path().apply {
        moveTo(size.width * 0.3f, size.height * 0.2f)
        lineTo(size.width * 0.8f, size.height * 0.5f)
        lineTo(size.width * 0.3f, size.height * 0.8f)
        close()
    }
    drawPath(path = path, color = color)
}

private fun DrawScope.drawSpeakerVolumeIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val hornPath = Path().apply {
        moveTo(size.width * 0.15f, size.height * 0.35f)
        lineTo(size.width * 0.35f, size.height * 0.35f)
        lineTo(size.width * 0.55f, size.height * 0.2f)
        lineTo(size.width * 0.55f, size.height * 0.8f)
        lineTo(size.width * 0.35f, size.height * 0.65f)
        lineTo(size.width * 0.15f, size.height * 0.65f)
        close()
    }
    drawPath(path = hornPath, color = color)
    drawArc(color = color, startAngle = -45f, sweepAngle = 90f, useCenter = false, topLeft = Offset(size.width * 0.62f, size.height * 0.3f), size = Size(size.width * 0.25f, size.height * 0.4f), style = stroke)
}

private fun DrawScope.drawPencilEditIcon(color: Color) {
    drawLine(color = color, start = Offset(size.width * 0.2f, size.height * 0.8f), end = Offset(size.width * 0.8f, size.height * 0.2f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawSparkleStarIcon(color: Color) {
    val path = Path().apply {
        val cx = size.width / 2
        val cy = size.height / 2
        val r = size.width / 2.2f
        moveTo(cx, cy - r)
        quadraticTo(cx, cy, cx + r, cy)
        quadraticTo(cx, cy, cx, cy + r)
        quadraticTo(cx, cy, cx - r, cy)
        quadraticTo(cx, cy, cx, cy - r)
        close()
    }
    drawPath(path = path, color = color)
}

private fun DrawScope.drawThreeDotsMenu(color: Color) {
    drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(size.width * 0.25f, size.height * 0.5f))
    drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(size.width * 0.5f, size.height * 0.5f))
    drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(size.width * 0.75f, size.height * 0.5f))
}

private fun DrawScope.drawBackArrow(color: Color) {
    val stroke = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
    drawLine(
        color = color,
        start = Offset(size.width * 0.85f, size.height * 0.5f),
        end = Offset(size.width * 0.15f, size.height * 0.5f),
        strokeWidth = 2.dp.toPx(),
        cap = StrokeCap.Round
    )
    val path = Path().apply {
        moveTo(size.width * 0.45f, size.height * 0.2f)
        lineTo(size.width * 0.15f, size.height * 0.5f)
        lineTo(size.width * 0.45f, size.height * 0.8f)
    }
    drawPath(path = path, color = color, style = stroke)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TranscriptScreenPreview() {
    AIBasedClassNotesAppTheme {
        TranscriptScreenContent(uiState = TranscriptUiState())
    }
}
