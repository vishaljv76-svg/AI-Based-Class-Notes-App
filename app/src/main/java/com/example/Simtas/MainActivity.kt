package com.example.Simtas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.Simtas.ui.createnotes.CreateSmartNotesScreen
import com.example.Simtas.ui.forgotpassword.ForgotPasswordScreen
import com.example.Simtas.ui.home.DashboardScreen
import com.example.Simtas.ui.login.LoginScreen
import com.example.Simtas.ui.notes.GeneratedNotesScreen
import com.example.Simtas.ui.onboarding.StudentOnboardingScreen
import com.example.Simtas.ui.preview.LecturePreviewScreen
import com.example.Simtas.ui.record.RecordLectureScreen
import com.example.Simtas.ui.signup.SignupScreen
import com.example.Simtas.ui.splash.SplashScreen
import com.example.Simtas.ui.subjects.SubjectSelectionScreen
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.transcript.TranscriptScreen
import com.example.Simtas.ui.upload.UploadLectureScreen
import com.example.Simtas.ui.welcome.WelcomeScreen
import kotlinx.coroutines.delay

enum class AppScreen {
    Splash,
    Welcome,
    Login,
    Signup,
    ForgotPassword,
    StudentOnboarding,
    SubjectSelection,
    HomeDashboard,
    RecordLecture,
    UploadLecture,
    LecturePreview,
    Transcript,
    CreateSmartNotes,
    GeneratedNotes
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIBasedClassNotesAppTheme {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    var currentScreen by remember { mutableStateOf(AppScreen.Splash) }

    // Automatic navigation from Splash to Welcome screen after 2.5 seconds
    LaunchedEffect(currentScreen) {
        if (currentScreen == AppScreen.Splash) {
            delay(2500)
            currentScreen = AppScreen.Welcome
        }
    }

    when (currentScreen) {
        AppScreen.Splash -> {
            SplashScreen()
        }
        AppScreen.Welcome -> {
            WelcomeScreen(
                onGetStartedClick = {
                    currentScreen = AppScreen.Signup
                },
                onLoginClick = {
                    currentScreen = AppScreen.Login
                },
                onSignUpClick = {
                    currentScreen = AppScreen.Signup
                }
            )
        }
        AppScreen.Login -> {
            LoginScreen(
                onBackClick = {
                    currentScreen = AppScreen.Welcome
                },
                onForgotPasswordClick = {
                    currentScreen = AppScreen.ForgotPassword
                },
                onLoginSuccess = {
                    currentScreen = AppScreen.StudentOnboarding
                },
                onSignUpClick = {
                    currentScreen = AppScreen.Signup
                }
            )
        }
        AppScreen.Signup -> {
            SignupScreen(
                onBackClick = {
                    currentScreen = AppScreen.Welcome
                },
                onLoginClick = {
                    currentScreen = AppScreen.Login
                },
                onSignupSuccess = {
                    currentScreen = AppScreen.StudentOnboarding
                }
            )
        }
        AppScreen.ForgotPassword -> {
            ForgotPasswordScreen(
                onBackClick = {
                    currentScreen = AppScreen.Login
                },
                onBackToLoginClick = {
                    currentScreen = AppScreen.Login
                }
            )
        }
        AppScreen.StudentOnboarding -> {
            StudentOnboardingScreen(
                onBackClick = {
                    currentScreen = AppScreen.Welcome
                },
                onChooseSubjectsClick = {
                    currentScreen = AppScreen.SubjectSelection
                },
                onContinueClick = {
                    currentScreen = AppScreen.SubjectSelection
                }
            )
        }
        AppScreen.SubjectSelection -> {
            SubjectSelectionScreen(
                onBackClick = {
                    currentScreen = AppScreen.StudentOnboarding
                },
                onStartLearningClick = {
                    currentScreen = AppScreen.HomeDashboard
                }
            )
        }
        AppScreen.HomeDashboard -> {
            DashboardScreen(
                onNotificationClick = {
                    // Open notifications
                },
                onRecordLectureClick = {
                    currentScreen = AppScreen.RecordLecture
                },
                onUploadAudioClick = {
                    currentScreen = AppScreen.UploadLecture
                },
                onMyNotesClick = {
                    currentScreen = AppScreen.GeneratedNotes
                },
                onAiQuizClick = {
                    // Open quiz
                },
                onViewAllNotesClick = {
                    currentScreen = AppScreen.GeneratedNotes
                },
                onNoteClick = { noteId ->
                    currentScreen = AppScreen.GeneratedNotes
                }
            )
        }
        AppScreen.RecordLecture -> {
            RecordLectureScreen(
                onBackClick = {
                    currentScreen = AppScreen.HomeDashboard
                },
                onStopRecordingClick = {
                    currentScreen = AppScreen.LecturePreview
                }
            )
        }
        AppScreen.UploadLecture -> {
            UploadLectureScreen(
                onBackClick = {
                    currentScreen = AppScreen.HomeDashboard
                },
                onContinueClick = {
                    currentScreen = AppScreen.LecturePreview
                }
            )
        }
        AppScreen.LecturePreview -> {
            LecturePreviewScreen(
                onBackClick = {
                    currentScreen = AppScreen.HomeDashboard
                },
                onGenerateTranscriptClick = {
                    currentScreen = AppScreen.Transcript
                }
            )
        }
        AppScreen.Transcript -> {
            TranscriptScreen(
                onBackClick = {
                    currentScreen = AppScreen.LecturePreview
                },
                onGenerateNotesClick = {
                    currentScreen = AppScreen.CreateSmartNotes
                }
            )
        }
        AppScreen.CreateSmartNotes -> {
            CreateSmartNotesScreen(
                onBackClick = {
                    currentScreen = AppScreen.Transcript
                }
            )
        }
        AppScreen.GeneratedNotes -> {
            GeneratedNotesScreen(
                onBackClick = {
                    currentScreen = AppScreen.HomeDashboard
                },
                onViewFullNotesClick = {
                    // View full notes detail
                },
                onTakeQuizClick = {
                    // Open Quiz
                }
            )
        }
    }
}
