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
import com.example.Simtas.ui.forgotpassword.ForgotPasswordScreen
import com.example.Simtas.ui.login.LoginScreen
import com.example.Simtas.ui.onboarding.StudentOnboardingScreen
import com.example.Simtas.ui.signup.SignupScreen
import com.example.Simtas.ui.splash.SplashScreen
import com.example.Simtas.ui.subjects.SubjectSelectionScreen
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.welcome.WelcomeScreen
import kotlinx.coroutines.delay

enum class AppScreen {
    Splash,
    Welcome,
    Login,
    Signup,
    ForgotPassword,
    StudentOnboarding,
    SubjectSelection
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
                    // Future Dashboard Screen
                }
            )
        }
    }
}
