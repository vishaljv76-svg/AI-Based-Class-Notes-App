package com.example.Simtas.ui.profile

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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import com.example.Simtas.ui.home.HomeBottomTab
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.theme.InnerCircleBg
import com.example.Simtas.ui.theme.PurplePrimary
import com.example.Simtas.ui.theme.SplashBackground
import com.example.Simtas.ui.theme.TextDarkPrimary
import com.example.Simtas.ui.theme.TextMuted
import com.example.Simtas.ui.theme.TextSubtitle

val LogoutBg = Color(0xFFFFF0F0)
val LogoutRedText = Color(0xFFD32F2F)

/**
 * Profile & Settings screen adhering to MVVM architecture.
 */
@Composable
fun ProfileSettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileSettingsViewModel = remember { ProfileSettingsViewModel() },
    onEditProfileClick: () -> Unit = {},
    onCancelEdit: () -> Unit = {},
    onSaveChanges: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onCourseChange: (String) -> Unit = {},
    onDepartmentChange: (String) -> Unit = {},
    onSemesterChange: (String) -> Unit = {},
    onAccountPasswordClick: () -> Unit = {},
    onPrivacyStorageClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onTabSelect: (HomeBottomTab) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    ProfileSettingsScreenContent(
        uiState = uiState,
        modifier = modifier,
        onEditProfileClick = {
            viewModel.onEditProfileClicked()
            onEditProfileClick()
        },
        onCancelEdit = viewModel::onCancelEditClicked,
        onSaveChanges = viewModel::onSaveChangesClicked,
        onNameChange = viewModel::onEditNameChange,
        onCourseChange = viewModel::onEditCourseChange,
        onDepartmentChange = viewModel::onEditDepartmentChange,
        onSemesterChange = viewModel::onEditSemesterChange,
        onToggleStudyReminders = viewModel::toggleStudyReminders,
        onToggleNotesReadyAlerts = viewModel::toggleNotesReadyAlerts,
        onAccountPasswordClick = onAccountPasswordClick,
        onPrivacyStorageClick = onPrivacyStorageClick,
        onLogoutClick = {
            viewModel.onLogoutClicked()
            onLogoutClick()
        },
        onTabSelect = { tab ->
            viewModel.selectTab(tab)
            onTabSelect(tab)
        }
    )
}

@Composable
fun ProfileSettingsScreenContent(
    uiState: ProfileSettingsUiState,
    modifier: Modifier = Modifier,
    onEditProfileClick: () -> Unit = {},
    onCancelEdit: () -> Unit = {},
    onSaveChanges: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onCourseChange: (String) -> Unit = {},
    onDepartmentChange: (String) -> Unit = {},
    onSemesterChange: (String) -> Unit = {},
    onToggleStudyReminders: (Boolean) -> Unit = {},
    onToggleNotesReadyAlerts: (Boolean) -> Unit = {},
    onAccountPasswordClick: () -> Unit = {},
    onPrivacyStorageClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
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

                // Top Bar Title
                Text(
                    text = "Profile & settings",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // User Profile Card
                UserProfileCard(
                    uiState = uiState,
                    onEditClick = onEditProfileClick,
                    onCancelEdit = onCancelEdit,
                    onSaveChanges = onSaveChanges,
                    onNameChange = onNameChange,
                    onCourseChange = onCourseChange,
                    onDepartmentChange = onDepartmentChange,
                    onSemesterChange = onSemesterChange
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Study Preferences Section
                Text(
                    text = "STUDY PREFERENCES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 1.1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                StudyPreferencesGroupCard(
                    uiState = uiState,
                    onToggleStudyReminders = onToggleStudyReminders,
                    onToggleNotesReadyAlerts = onToggleNotesReadyAlerts
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Your Account Section
                Text(
                    text = "YOUR ACCOUNT",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextMuted,
                    letterSpacing = 1.1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                YourAccountGroupCard(
                    onAccountPasswordClick = onAccountPasswordClick,
                    onPrivacyStorageClick = onPrivacyStorageClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Logout Button
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(LogoutBg)
                        .clickable { onLogoutClick() }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Canvas(modifier = Modifier.size(18.dp)) {
                            drawLogoutIcon(LogoutRedText)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Logout",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = LogoutRedText
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // App Version Subtitle
                Text(
                    text = uiState.appVersionText,
                    fontSize = 10.sp,
                    color = TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

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
 * User Profile Card Component
 */
@Composable
private fun UserProfileCard(
    uiState: ProfileSettingsUiState,
    onEditClick: () -> Unit,
    onCancelEdit: () -> Unit = {},
    onSaveChanges: () -> Unit = {},
    onNameChange: (String) -> Unit = {},
    onCourseChange: (String) -> Unit = {},
    onDepartmentChange: (String) -> Unit = {},
    onSemesterChange: (String) -> Unit = {}
) {
    val shape = RoundedCornerShape(24.dp)
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
        if (!uiState.isEditing) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Initials Avatar
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(CircleShape)
                                .background(InnerCircleBg)
                        ) {
                            Text(
                                text = uiState.userInitials,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = uiState.userName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkPrimary
                            )

                            Spacer(modifier = Modifier.height(2.dp))

                            Text(
                                text = uiState.userEmail,
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }

                    IconButton(
                        onClick = onEditClick,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Canvas(modifier = Modifier.size(18.dp)) {
                            drawPencilEditIcon(PurplePrimary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Academic Details
                Text(
                    text = uiState.courseText,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = uiState.departmentText,
                    fontSize = 12.sp,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Badges Row
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(InnerCircleBg)
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = uiState.semesterBadgeText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PurplePrimary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color(0xFFEEF3FF))
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = uiState.yearBadgeText,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = PurplePrimary
                        )
                    }
                }
            }
        } else {
            // In-place Edit Form
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(InnerCircleBg)
                        ) {
                            Text(
                                text = uiState.userInitials,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = PurplePrimary
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Edit Profile",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextDarkPrimary
                            )
                            Text(
                                text = uiState.userEmail,
                                fontSize = 12.sp,
                                color = TextMuted
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Field 1: Student Name
                Text(
                    text = "Student Name",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.editName,
                    onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF9F9FB),
                        unfocusedContainerColor = Color(0xFFF9F9FB),
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedTextColor = TextDarkPrimary,
                        unfocusedTextColor = TextDarkPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Field 2: Course
                Text(
                    text = "Course",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.editCourse,
                    onValueChange = onCourseChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF9F9FB),
                        unfocusedContainerColor = Color(0xFFF9F9FB),
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedTextColor = TextDarkPrimary,
                        unfocusedTextColor = TextDarkPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Field 3: Department
                Text(
                    text = "Department",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.editDepartment,
                    onValueChange = onDepartmentChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF9F9FB),
                        unfocusedContainerColor = Color(0xFFF9F9FB),
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedTextColor = TextDarkPrimary,
                        unfocusedTextColor = TextDarkPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Field 4: Semester
                Text(
                    text = "Semester",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = uiState.editSemester,
                    onValueChange = onSemesterChange,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF9F9FB),
                        unfocusedContainerColor = Color(0xFFF9F9FB),
                        focusedBorderColor = PurplePrimary,
                        unfocusedBorderColor = Color(0xFFE2E8F0),
                        focusedTextColor = TextDarkPrimary,
                        unfocusedTextColor = TextDarkPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Action Buttons: Cancel and Save Changes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancelEdit,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(23.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextDarkPrimary
                        )
                    ) {
                        Text(
                            text = "Cancel",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = onSaveChanges,
                        modifier = Modifier
                            .weight(1f)
                            .height(46.dp),
                        shape = RoundedCornerShape(23.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PurplePrimary
                        )
                    ) {
                        Text(
                            text = "Save Changes",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

/**
 * Study Preferences Group Card
 */
@Composable
private fun StudyPreferencesGroupCard(
    uiState: ProfileSettingsUiState,
    onToggleStudyReminders: (Boolean) -> Unit,
    onToggleNotesReadyAlerts: (Boolean) -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.03f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(shape)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Option 1: Study Reminders
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        drawBellIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Study reminders",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Switch(
                    checked = uiState.isStudyRemindersEnabled,
                    onCheckedChange = onToggleStudyReminders,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PurplePrimary,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = InnerCircleBg
                    )
                )
            }

            // Option 2: Notes Ready Alerts
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        drawSparkleAlertIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Notes ready alerts",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Switch(
                    checked = uiState.isNotesReadyAlertsEnabled,
                    onCheckedChange = onToggleNotesReadyAlerts,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PurplePrimary,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = InnerCircleBg
                    )
                )
            }

            // Option 3: Theme
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        drawSunThemeIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Theme",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = uiState.themeModeText,
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Canvas(modifier = Modifier.size(12.dp)) {
                        drawChevronRight(TextMuted)
                    }
                }
            }
        }
    }
}

/**
 * Your Account Group Card
 */
@Composable
private fun YourAccountGroupCard(
    onAccountPasswordClick: () -> Unit,
    onPrivacyStorageClick: () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = shape,
                ambientColor = Color.Black.copy(alpha = 0.03f),
                spotColor = Color.Black.copy(alpha = 0.05f)
            )
            .clip(shape)
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Item 1: Account & password
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAccountPasswordClick() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        drawLockIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Account & password",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Canvas(modifier = Modifier.size(12.dp)) {
                    drawChevronRight(TextMuted)
                }
            }

            // Item 2: Privacy & audio storage
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPrivacyStorageClick() }
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(18.dp)) {
                        drawShieldIcon(PurplePrimary)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Privacy & audio storage",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextDarkPrimary
                    )
                }

                Canvas(modifier = Modifier.size(12.dp)) {
                    drawChevronRight(TextMuted)
                }
            }
        }
    }
}

/**
 * Bottom Navigation Bar
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
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
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

private fun DrawScope.drawPencilEditIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.2f, size.height * 0.8f), end = Offset(size.width * 0.8f, size.height * 0.2f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawBellIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val path = Path().apply {
        moveTo(size.width * 0.3f, size.height * 0.7f)
        lineTo(size.width * 0.2f, size.height * 0.7f)
        quadraticTo(size.width * 0.2f, size.height * 0.45f, size.width * 0.35f, size.height * 0.3f)
        lineTo(size.width * 0.35f, size.height * 0.2f)
        quadraticTo(size.width * 0.35f, size.height * 0.1f, size.width * 0.5f, size.height * 0.1f)
        quadraticTo(size.width * 0.65f, size.height * 0.1f, size.width * 0.65f, size.height * 0.2f)
        lineTo(size.width * 0.65f, size.height * 0.3f)
        quadraticTo(size.width * 0.8f, size.height * 0.45f, size.width * 0.8f, size.height * 0.7f)
        lineTo(size.width * 0.7f, size.height * 0.7f)
    }
    drawPath(path = path, color = color, style = stroke)
}

private fun DrawScope.drawSparkleAlertIcon(color: Color) {
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

private fun DrawScope.drawSunThemeIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawCircle(color = color, radius = size.width * 0.25f, center = Offset(size.width / 2, size.height / 2), style = stroke)
    drawLine(color = color, start = Offset(size.width * 0.5f, size.height * 0.1f), end = Offset(size.width * 0.5f, size.height * 0.22f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.5f, size.height * 0.78f), end = Offset(size.width * 0.5f, size.height * 0.9f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.1f, size.height * 0.5f), end = Offset(size.width * 0.22f, size.height * 0.5f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawLine(color = color, start = Offset(size.width * 0.78f, size.height * 0.5f), end = Offset(size.width * 0.9f, size.height * 0.5f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
}

private fun DrawScope.drawLockIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.2f, size.height * 0.45f),
        size = Size(size.width * 0.6f, size.height * 0.45f),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = stroke
    )
    val path = Path().apply {
        moveTo(size.width * 0.32f, size.height * 0.45f)
        lineTo(size.width * 0.32f, size.height * 0.3f)
        quadraticTo(size.width * 0.32f, size.height * 0.15f, size.width * 0.5f, size.height * 0.15f)
        quadraticTo(size.width * 0.68f, size.height * 0.15f, size.width * 0.68f, size.height * 0.3f)
        lineTo(size.width * 0.68f, size.height * 0.45f)
    }
    drawPath(path = path, color = color, style = stroke)
}

private fun DrawScope.drawShieldIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val shieldPath = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.1f)
        lineTo(size.width * 0.85f, size.height * 0.25f)
        lineTo(size.width * 0.85f, size.height * 0.55f)
        quadraticTo(size.width * 0.85f, size.height * 0.85f, size.width * 0.5f, size.height * 0.95f)
        quadraticTo(size.width * 0.15f, size.height * 0.85f, size.width * 0.15f, size.height * 0.55f)
        lineTo(size.width * 0.15f, size.height * 0.25f)
        close()
    }
    drawPath(path = shieldPath, color = color, style = stroke)
}

private fun DrawScope.drawLogoutIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    // Door outline
    val path = Path().apply {
        moveTo(size.width * 0.55f, size.height * 0.2f)
        lineTo(size.width * 0.25f, size.height * 0.2f)
        lineTo(size.width * 0.25f, size.height * 0.8f)
        lineTo(size.width * 0.55f, size.height * 0.8f)
    }
    drawPath(path = path, color = color, style = stroke)

    // Arrow
    drawLine(color = color, start = Offset(size.width * 0.45f, size.height * 0.5f), end = Offset(size.width * 0.85f, size.height * 0.5f), strokeWidth = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val head = Path().apply {
        moveTo(size.width * 0.7f, size.height * 0.35f)
        lineTo(size.width * 0.85f, size.height * 0.5f)
        lineTo(size.width * 0.7f, size.height * 0.65f)
    }
    drawPath(path = head, color = color, style = stroke)
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
fun ProfileSettingsScreenPreview() {
    AIBasedClassNotesAppTheme {
        ProfileSettingsScreenContent(uiState = ProfileSettingsUiState())
    }
}
