package com.example.Simtas.ui.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Simtas.ui.theme.AIBasedClassNotesAppTheme
import com.example.Simtas.ui.theme.PurplePrimary
import com.example.Simtas.ui.theme.SplashBackground
import com.example.Simtas.ui.theme.TextDarkPrimary
import com.example.Simtas.ui.theme.TextMuted
import com.example.Simtas.ui.theme.TextSubtitle

/**
 * Login Screen component adhering to MVVM architecture.
 */
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = remember { LoginViewModel() },
    onBackClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    LoginScreenContent(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onTogglePasswordVisibility = viewModel::togglePasswordVisibility,
        onForgotPasswordClick = {
            viewModel.onForgotPasswordClicked()
            onForgotPasswordClick()
        },
        onLoginClick = {
            viewModel.onLoginClicked { onLoginSuccess() }

        },
        onSignUpClick = onSignUpClick
    )
}

@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onTogglePasswordVisibility: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
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
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Top Bar with Back Arrow and Title
                TopNavigationBar(onBackClick = onBackClick)

                Spacer(modifier = Modifier.height(28.dp))

                // Notebook Logo Icon
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(PurplePrimary)
                ) {
                    Canvas(modifier = Modifier.size(30.dp)) {
                        drawNotebookPencil(Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Title & Subtitle
                Text(
                    text = "Welcome back, learner.",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Your ideas are right where you left them.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextSubtitle
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Email Input Field
                Text(
                    text = "Email address",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.email,
                    onValueChange = onEmailChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
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
                        Canvas(modifier = Modifier.size(20.dp)) {
                            drawMailIcon(TextMuted)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Password Input Field
                Text(
                    text = "Password",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDarkPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
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
                        Canvas(modifier = Modifier.size(20.dp)) {
                            drawLockIcon(TextMuted)
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = onTogglePasswordVisibility) {
                            Canvas(modifier = Modifier.size(20.dp)) {
                                drawEyeIcon(TextMuted, isOff = !uiState.isPasswordVisible)
                            }
                        }
                    },
                    visualTransformation = if (uiState.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Forgot Password Link
                Text(
                    text = "Forgot password?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = PurplePrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .clickable { onForgotPasswordClick() }
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (!uiState.errorMessage.isNullOrEmpty()) {
                    Text(
                        text = uiState.errorMessage,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFD32F2F),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                }

                // Login Button
                Button(
                    onClick = onLoginClick,
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
                            drawRightArrow(Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Login",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Sign Up Redirect
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Text(
                        text = "Sign Up",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = PurplePrimary,
                        modifier = Modifier.clickable { onSignUpClick() }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }

            // Bottom Security Info Card
            SecurityInfoCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            )
        }
    }
}

/**
 * Top Navigation Bar with Back Arrow and Title
 */
@Composable
private fun TopNavigationBar(onBackClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
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
            text = "Login",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDarkPrimary
        )
    }
}

/**
 * Bottom Security / Privacy Card
 */
@Composable
private fun SecurityInfoCard(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFEEF3FF))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                drawShieldCheckIcon(PurplePrimary)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Your lectures stay yours.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PurplePrimary
                )
                Text(
                    text = "Private, secure and ready to learn.",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = TextSubtitle
                )
            }
        }
    }
}

// --- Custom Canvas Graphics Helpers ---

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

private fun DrawScope.drawNotebookPencil(color: Color) {
    val strokeWidth = 2.dp.toPx()
    val padLeft = size.width * 0.25f
    val padTop = size.height * 0.2f
    val padWidth = size.width * 0.5f
    val padHeight = size.height * 0.6f

    drawRoundRect(
        color = color,
        topLeft = Offset(padLeft, padTop),
        size = Size(padWidth, padHeight),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = Stroke(width = strokeWidth)
    )

    val lineStartX = padLeft + (padWidth * 0.25f)
    val lineEndX = padLeft + (padWidth * 0.75f)
    drawLine(
        color = color,
        start = Offset(lineStartX, padTop + (padHeight * 0.35f)),
        end = Offset(lineEndX, padTop + (padHeight * 0.35f)),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
    drawLine(
        color = color,
        start = Offset(lineStartX, padTop + (padHeight * 0.65f)),
        end = Offset(lineStartX + (padWidth * 0.35f), padTop + (padHeight * 0.65f)),
        strokeWidth = strokeWidth,
        cap = StrokeCap.Round
    )
}

private fun DrawScope.drawMailIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    drawRoundRect(
        color = color,
        topLeft = Offset(size.width * 0.1f, size.height * 0.25f),
        size = Size(size.width * 0.8f, size.height * 0.5f),
        cornerRadius = CornerRadius(4.dp.toPx()),
        style = stroke
    )
    val path = Path().apply {
        moveTo(size.width * 0.15f, size.height * 0.3f)
        lineTo(size.width * 0.5f, size.height * 0.55f)
        lineTo(size.width * 0.85f, size.height * 0.3f)
    }
    drawPath(path = path, color = color, style = stroke)
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
        quadraticTo(
            size.width * 0.32f, size.height * 0.15f,
            size.width * 0.5f, size.height * 0.15f
        )
        quadraticTo(
            size.width * 0.68f, size.height * 0.15f,
            size.width * 0.68f, size.height * 0.3f
        )
        lineTo(size.width * 0.68f, size.height * 0.45f)
    }
    drawPath(path = path, color = color, style = stroke)
}

private fun DrawScope.drawEyeIcon(color: Color, isOff: Boolean) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val path = Path().apply {
        moveTo(size.width * 0.1f, size.height * 0.5f)
        quadraticTo(
            size.width * 0.5f, size.height * 0.15f,
            size.width * 0.9f, size.height * 0.5f
        )
        quadraticTo(
            size.width * 0.5f, size.height * 0.85f,
            size.width * 0.1f, size.height * 0.5f
        )
    }
    drawPath(path = path, color = color, style = stroke)
    drawCircle(color = color, radius = size.width * 0.15f, center = Offset(size.width / 2, size.height / 2), style = stroke)

    if (isOff) {
        drawLine(
            color = color,
            start = Offset(size.width * 0.2f, size.height * 0.2f),
            end = Offset(size.width * 0.8f, size.height * 0.8f),
            strokeWidth = 1.8.dp.toPx(),
            cap = StrokeCap.Round
        )
    }
}

private fun DrawScope.drawShieldCheckIcon(color: Color) {
    val stroke = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
    val shieldPath = Path().apply {
        moveTo(size.width * 0.5f, size.height * 0.1f)
        lineTo(size.width * 0.85f, size.height * 0.25f)
        lineTo(size.width * 0.85f, size.height * 0.55f)
        quadraticTo(
            size.width * 0.85f, size.height * 0.85f,
            size.width * 0.5f, size.height * 0.95f
        )
        quadraticTo(
            size.width * 0.15f, size.height * 0.85f,
            size.width * 0.15f, size.height * 0.55f
        )
        lineTo(size.width * 0.15f, size.height * 0.25f)
        close()
    }
    drawPath(path = shieldPath, color = color, style = stroke)

    val checkPath = Path().apply {
        moveTo(size.width * 0.35f, size.height * 0.52f)
        lineTo(size.width * 0.47f, size.height * 0.64f)
        lineTo(size.width * 0.65f, size.height * 0.42f)
    }
    drawPath(path = checkPath, color = color, style = stroke)
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
fun LoginScreenPreview() {
    AIBasedClassNotesAppTheme {
        LoginScreenContent(uiState = LoginUiState())
    }
}
