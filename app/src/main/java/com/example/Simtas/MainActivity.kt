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
import com.example.Simtas.ui.splash.SplashScreen
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.welcome.WelcomeScreen
import kotlinx.coroutines.delay

enum class AppScreen {
    Splash,
    Welcome
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
                    // Action for Get Started button
                },
                onLoginClick = {
                    // Action for Login link
                },
                onSignUpClick = {
                    // Action for Sign Up link
                }
            )
        }
    }
}
