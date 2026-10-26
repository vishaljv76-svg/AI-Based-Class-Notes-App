package com.example.Simtas.ui.home

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
import androidx.compose.material3.IconButton
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

val ToolkitPurpleBg = Color(0xFFF1EDFE)
val ToolkitBlueBg = Color(0xFFEEF3FF)

/**
 * Home Dashboard Screen adhering to MVVM architecture.
 */
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = remember { DashboardViewModel() },
    onNotificationClick: () -> Unit = {},
    onRecordLectureClick: () -> Unit = {},
    onUploadAudioClick: () -> Unit = {},
    onMyNotesClick: () -> Unit = {},
    onAiQuizClick: () -> Unit = {},
    onViewAllNotesClick: () -> Unit = {},
    onNoteClick: (String) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    DashboardScreenContent(
        uiState = uiState,
        modifier = modifier,
        onNotificationClick = onNotificationClick,
        onRecordLectureClick = {
            viewModel.onRecordLectureClicked()
            onRecordLectureClick()
        },
        onUploadAudioClick = {
            viewModel.onUploadAudioClicked()
            onUploadAudioClick()
        },
        onMyNotesClick = {
            viewModel.onMyNotesClicked()
            onMyNotesClick()
        },
        onAiQuizClick = {
            viewModel.onAiQuizClicked()
            onAiQuizClick()
        },
        onViewAllNotesClick = onViewAllNotesClick,
        onNoteClick = { noteId ->
            viewModel.onNoteItemClicked(noteId)
            onNoteClick(noteId)
        },
        onTabSelect = viewModel::selectTab
    )
}

@Composable
fun DashboardScreenContent(
    uiState: DashboardUiState,
    modifier: Modifier = Modifier,
    onNotificationClick: () -> Unit = {},
    onRecordLectureClick: () -> Unit = {},
    onUploadAudioClick: () -> Unit = {},
    onMyNotesClick: () -> Unit = {},
    onAiQuizClick: () -> Unit = {},
    onViewAllNotesClick: () -> Unit = {},
    onNoteClick: (String) -> Unit = {},
    onTabSelect: (HomeBottomTab) -> Unit = {}
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = SplashBackground
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 80.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Top Header Bar with App Title & Bell Icon
                TopDashboardHeader(onNotificationClick = onNotificationClick)

                Spacer(modifier = Modifier.height(20.dp))

                // User Greeting & Avatar Row
                UserGreetingRow(
                    userName = uiState.userName,
                    dateText = uiState.dateText,
                    userInitials = uiState.userInitials
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Metrics Overview Card
                MetricsOverviewCard(
                    totalNotes = uiState.totalNotes,
                    subjectsCount = uiState.subjectsCount,
                    quizzesCount = uiState.quizzesCount
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Your Study Toolkit Grid
                Text(
                    text = "Your study toolkit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                ToolkitGridSection(
                    onRecordLectureClick = onRecordLectureClick,
                    onUploadAudioClick = onUploadAudioClick,
                    onMyNotesClick = onMyNotesClick,
                    onAiQuizClick = onAiQuizClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Pick Up Where You Left Off Header Row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Pick up where you left off",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkPrimary
                    )

                    Text(
                        text = "View all",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary,
                        modifier = Modifier.clickable { onViewAllNotesClick() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Recent Notes List
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    uiState.recentNotes.forEach { note ->
                        RecentNoteCardItem(
                            note = note,
                            onClick = { onNoteClick(note.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }

            // Bottom Navigation Bar
            HomeBottomNavigationBar(
                selectedTab = uiState.selectedTab,
                onTabSelect = onTabSelect,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}

/**
 * Top Dashboard Bar with Title & Bell Notification Icon
 */
@Composable
private fun TopDashboardHeader(onNotificationClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "SmartNotes AI",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkPrimary
        )

        IconButton(
            onClick = onNotificationClick,
            modifier = Modifier.size(36.dp)
        ) {
            Canvas(modifier = Modifier.size(20.dp)) {
                drawBellIcon(TextDarkPrimary)
            }
        }
    }
}

/**
 * User Greeting & Profile Initials Badge
 */
@Composable
private fun UserGreetingRow(
    userName: String,
    dateText: String,
    userInitials: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(
                text = dateText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Hey $userName, let's learn.",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkPrimary
            )
        }

        // Profile Avatar Badge Pill
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(InnerCircleBg)
        ) {
            Text(
                text = userInitials,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PurplePrimary
            )
        }
    }
}

/**
 * Stats Overview Metrics Card
 */
@Composable
private fun MetricsOverviewCard(
    totalNotes: Int,
    subjectsCount: Int,
    quizzesCount: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.08f)
            )
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(vertical = 16.dp, horizontal = 20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Metric 1: Total Notes
            MetricItemColumn(
                number = String.format("%02d", totalNotes),
                label = "Total notes",
                icon = { drawNoteIconSmall(PurplePrimary) }
            )

            // Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(36.dp)
                    .background(Color(0xFFEEEEEE))
            )

            // Metric 2: Subjects
            MetricItemColumn(
                number = String.format("%02d", subjectsCount),
                label = "Subjects",
                icon = { drawSubjectsIconSmall(PurplePrimary) }
            )

            // Divider
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(36.dp)
                    .background(Color(0xFFEEEEEE))
            )

            // Metric 3: Quizzes
            MetricItemColumn(
                number = String.format("%02d", quizzesCount),
                label = "Quizzes",
                icon = { drawQuizIconSmall(PurplePrimary) }
            )
        }
    }
}

@Composable
private fun MetricItemColumn(
    number: String,
    label: String,
    icon: DrawScope.() -> Unit
) {
    Column(horizontalAlignment = Alignment.Start) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = number,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkPrimary
            )
            Spacer(modifier = Modifier.width(6.dp))
            Canvas(modifier = Modifier.size(16.dp)) {
                icon()
            }
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextMuted
        )
    }
}

/**
 * 2x2 Toolkit Grid Section
 */
@Composable
private fun ToolkitGridSection(
    onRecordLectureClick: () -> Unit,
    onUploadAudioClick: () -> Unit,
    onMyNotesClick: () -> Unit,
    onAiQuizClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ToolkitCard(
                title = "Record Lecture",
                subtitle = "Capture in the moment",
                bgColor = ToolkitPurpleBg,
                modifier = Modifier.weight(1f),
                onClick = onRecordLectureClick,
                icon = { drawMicrophoneIcon(PurplePrimary) }
            )

            ToolkitCard(
                title = "Upload Audio",
                subtitle = "Bring a recording",
                bgColor = ToolkitBlueBg,
                modifier = Modifier.weight(1f),
                onClick = onUploadAudioClick,
                icon = { drawUploadIcon(PurplePrimary) }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            ToolkitCard(
                title = "My Notes",
                subtitle = "All your ideas, sorted",
                bgColor = ToolkitBlueBg,
                modifier = Modifier.weight(1f),
                onClick = onMyNotesClick,
                icon = { drawNotePadIcon(PurplePrimary) }
            )

            ToolkitCard(
                title = "AI Quiz",
                subtitle = "Put knowledge to work",
                bgColor = ToolkitPurpleBg,
                modifier = Modifier.weight(1f),
                onClick = onAiQuizClick,
                icon = { drawSparkleStarIcon(PurplePrimary) }
            )
        }
    }
}

@Composable
private fun ToolkitCard(
    title: String,
    subtitle: String,
    bgColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    icon: DrawScope.() -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Canvas(modifier = Modifier.size(24.dp)) {
                icon()
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextDarkPrimary
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = subtitle,
                fontSize = 11.sp,
                color = TextSubtitle
            )
        }
    }
}

/**
 * Recent Note Item Row Card
 */
@Composable
private fun RecentNoteCardItem(
    note: RecentNoteItem,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(18.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.04f),
                spotColor = Color.Black.copy(alpha = 0.06f)
            )
            .clip(shape)
            .background(Color.White)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(InnerCircleBg)
                ) {
                    Canvas(modifier = Modifier.size(20.dp)) {
                        when (note.iconType) {
                            RecentNoteIconType.Database -> drawDatabaseIcon(PurplePrimary)
                            RecentNoteIconType.Math -> drawCalculatorIcon(PurplePrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = note.title,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextDarkPrimary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "${note.subject} · ${note.date} · ${note.duration}",
                        fontSize = 11.sp,
                        color = TextSubtitle
                    )
                }
            }

            Canvas(modifier = Modifier.size(16.dp)) {
                drawChevronRight(TextMuted)
            }
        }
    }
}

/**
 * Bottom Navigation Bar Composable
 */
@Composable
private fun HomeBottomNavigationBar(
    selectedTab: HomeBottomTab,
    onTabSelect: (HomeBottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 12.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            // Tab 1: Home
            BottomTabItem(
                label = "Home",
                isSelected = selectedTab == HomeBottomTab.Home,
                onClick = { onTabSelect(HomeBottomTab.Home) },
                icon = { drawHomeIcon(if (selectedTab == HomeBottomTab.Home) PurplePrimary else TextMuted) }
            )

            // Tab 2: My Notes
            BottomTabItem(
                label = "My Notes",
                isSelected = selectedTab == HomeBottomTab.Notes,
                onClick = { onTabSelect(HomeBottomTab.Notes) },
                icon = { drawNotesTabIcon(if (selectedTab == HomeBottomTab.Notes) PurplePrimary else TextMuted) }
            )

            // Tab 3: Profile
            BottomTabItem(
                label = "Profile",
                isSelected = selectedTab == HomeBottomTab.Profile,
                onClick = { onTabSelect(HomeBottomTab.Profile) },
                icon = { drawProfileTabIcon(if (selectedTab == HomeBottomTab.Profile) PurplePrimary else TextMuted) }
            )
        }
    }
}

@Composable
private fun BottomTabItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    icon: DrawScope.() -> Unit
) {
    val pillBg = if (isSelected) InnerCircleBg else Color.Transparent
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(pillBg)
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Canvas(modifier = Modifier.size(20.dp)) {
                icon()
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) PurplePrimary else TextMuted
            )
        }
    }
}

// --- Custom Canvas Graphics Helpers ---

private fun DrawScope.drawBellIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val path = Path().apply {
        moveTo(size.width * 0.3f, size.height * 0.7f)
        lineTo(size.width * 0.2f, size.height * 0.7f)
        quadraticTo(
            size.width * 0.2f, size.height * 0.45f,
            size.width * 0.35f, size.height * 0.3f
        )
        lineTo(size.width * 0.35f, size.height * 0.2f)
        quadraticTo(
            size.width * 0.35f, size.height * 0.1f,
            size.width * 0.5f, size.height * 0.1f
        )
        quadraticTo(
            size.width * 0.65f, size.height * 0.1f,
            size.width * 0.65f, size.height * 0.2f
        )
        lineTo(size.width * 0.65f, size.height * 0.3f)
        quadraticTo(
            size.width * 0.8f, size.height * 0.45f,
            size.width * 0.8f, size.height * 0.7f
        )
        lineTo(size.width * 0.7f, size.height * 0.7f)
    }
    drawPath(path = path, color = color, style = stroke)
    drawArc(
        color = color,
        startAngle = 0f,
        sweepAngle = 180f,
        useCenter = false,
        topLeft = Offset(size.width * 0.4f, size.height * 0.72f),
        size = Size(size.width * 0.2f, size.height * 0.18f),
        style = stroke
    )
}

private fun DrawScope.drawNoteIconSmall(color: Color) {
    val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
        size = Size(size.width * 0.6f, size.height * 0.7f),
        cornerRadius = CornerRadius(3.dp.toPx()),
        style = stroke
    )
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.4f), end = Offset(size.width * 0.65f, size.height * 0.4f), strokeWidth = 1.5.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.6f), end = Offset(size.width * 0.55f, size.height * 0.6f), strokeWidth = 1.5.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawSubjectsIconSmall(color: Color) {
    drawLine(color = color, start = Offset(size.width * 0.25f, size.height * 0.8f), end = Offset(size.width * 0.25f, size.height * 0.4f), strokeWidth = 1.5.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.5f, size.height * 0.8f), end = Offset(size.width * 0.5f, size.height * 0.2f), strokeWidth = 1.5.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.75f, size.height * 0.8f), end = Offset(size.width * 0.75f, size.height * 0.5f), strokeWidth = 1.5.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawQuizIconSmall(color: Color) {
    val stroke = Stroke(width = 1.5.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(color = color, radius = size.width * 0.35f, center = Offset(size.width / 2, size.height / 2), style = stroke)
}

private fun DrawScope.drawMicrophoneIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.35f, size.height * 0.15f),
        size = Size(size.width * 0.3f, size.height * 0.45f),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = stroke
    )
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
}

private fun DrawScope.drawUploadIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    // Tray base
    val path = Path().apply {
        moveTo(size.width * 0.2f, size.height * 0.55f)
        lineTo(size.width * 0.2f, size.height * 0.8f)
        lineTo(size.width * 0.8f, size.height * 0.8f)
        lineTo(size.width * 0.8f, size.height * 0.55f)
    }
    drawPath(path = path, color = color, style = stroke)
    // Upload arrow
    drawLine(color = color, start = Offset(size.width * 0.5f, size.height * 0.65f), end = Offset(size.width * 0.5f, size.height * 0.2f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val headPath = Path().apply {
        moveTo(size.width * 0.35f, size.height * 0.35f)
        lineTo(size.width * 0.5f, size.height * 0.2f)
        lineTo(size.width * 0.65f, size.height * 0.35f)
    }
    drawPath(path = headPath, color = color, style = stroke)
}

private fun DrawScope.drawNotePadIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
        size = Size(size.width * 0.6f, size.height * 0.7f),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = stroke
    )
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.38f), end = Offset(size.width * 0.65f, size.height * 0.38f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.58f), end = Offset(size.width * 0.55f, size.height * 0.58f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
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

private fun DrawScope.drawDatabaseIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawArc(color = color, startAngle = 0f, sweepAngle = 360f, useCenter = false, topLeft = Offset(size.width * 0.2f, size.height * 0.15f), size = Size(size.width * 0.6f, size.height * 0.22f), style = stroke)
    drawArc(color = color, startAngle = 0f, sweepAngle = 180f, useCenter = false, topLeft = Offset(size.width * 0.2f, size.height * 0.38f), size = Size(size.width * 0.6f, size.height * 0.22f), style = stroke)
    drawArc(color = color, startAngle = 0f, sweepAngle = 180f, useCenter = false, topLeft = Offset(size.width * 0.2f, size.height * 0.62f), size = Size(size.width * 0.6f, size.height * 0.22f), style = stroke)
}

private fun DrawScope.drawCalculatorIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
        size = Size(size.width * 0.6f, size.height * 0.7f),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = stroke
    )
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.3f, size.height * 0.25f),
        size = Size(size.width * 0.4f, size.height * 0.18f),
        cornerRadius = CornerRadius(2.dp.toPx()),
        style = stroke
    )
}

private fun DrawScope.drawChevronRight(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val path = Path().apply {
        moveTo(size.width * 0.35f, size.height * 0.2f)
        lineTo(size.width * 0.65f, size.height * 0.5f)
        lineTo(size.width * 0.35f, size.height * 0.8f)
    }
    drawPath(path = path, color = color, style = stroke)
}

private fun DrawScope.drawHomeIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val path = Path().apply {
        moveTo(size.width * 0.15f, size.height * 0.45f)
        lineTo(size.width * 0.5f, size.height * 0.15f)
        lineTo(size.width * 0.85f, size.height * 0.45f)
        lineTo(size.width * 0.85f, size.height * 0.85f)
        lineTo(size.width * 0.15f, size.height * 0.85f)
        close()
    }
    drawPath(path = path, color = color, style = stroke)
}

private fun DrawScope.drawNotesTabIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.15f),
        size = Size(size.width * 0.6f, size.height * 0.7f),
        cornerRadius = CornerRadius(3.dp.toPx()),
        style = stroke
    )
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.4f), end = Offset(size.width * 0.65f, size.height * 0.4f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.35f, size.height * 0.6f), end = Offset(size.width * 0.55f, size.height * 0.6f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawProfileTabIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(
        color = color,
        radius = size.width * 0.22f,
        center = Offset(size.width * 0.5f, size.height * 0.32f),
        style = stroke
    )
    val bodyPath = Path().apply {
        moveTo(size.width * 0.2f, size.height * 0.82f)
        quadraticTo(
            size.width * 0.2f, size.height * 0.62f,
            size.width * 0.5f, size.height * 0.62f
        )
        quadraticTo(
            size.width * 0.8f, size.height * 0.62f,
            size.width * 0.8f, size.height * 0.82f
        )
    }
    drawPath(path = bodyPath, color = color, style = stroke)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    AIBasedClassNotesAppTheme {
        DashboardScreenContent(uiState = DashboardUiState())
    }
}
