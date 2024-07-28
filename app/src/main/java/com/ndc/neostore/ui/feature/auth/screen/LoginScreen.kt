package com.ndc.neostore.ui.feature.auth.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.OutlinedButton
import com.ndc.neostore.ui.component.button.OutlinedIconButton
import com.ndc.neostore.ui.component.button.PrimaryButton
import com.ndc.neostore.ui.component.textfield.PasswordTextField
import com.ndc.neostore.ui.component.textfield.PrimaryTextField
import com.ndc.neostore.ui.component.textfield.TextFieldState
import com.ndc.neostore.ui.feature.auth.AuthAction
import com.ndc.neostore.ui.feature.auth.AuthState

@Composable
fun LoginScreen(
    state: AuthState,
    action: (AuthAction) -> Unit,
) {
    val ctx = LocalContext.current
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(), onResult = { result ->
            result.data?.let {
                action(AuthAction.OnHandleLoginWithGoogle(it))
            }
        }
    )

    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

    BackHandler {
        (ctx as Activity).finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        if (!isSystemInDarkTheme()) {
            Image(
                painter = painterResource(id = R.drawable.login_background),
                contentDescription = "",
                modifier = Modifier
                    .height(203.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Masuk ke ",
                    style = typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                    color = color.onBackground
                )
                Text(
                    text = "Neo Store",
                    style = typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = color.primary
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Email",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = state.loginEmailState,
                    placeholder = "Masukan email kamu",
                    value = state.loginEmailValue,
                    onValueChange = {
                        action(AuthAction.OnLoginPasswordStateChange(TextFieldState.Empty))
                        action(AuthAction.OnLoginEmailValueChange(it))
                    },
                    onClearValue = {
                        action(AuthAction.OnLoginEmailValueChange(""))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Password",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = state.loginPasswordState,
                    placeholder = "Masukan password kamu",
                    value = state.loginPasswordValue,
                    onValueChange = {
                        action(AuthAction.OnLoginPasswordValueChange(it))
                    },
                    visible = state.loginPasswordVisible,
                    onVisibilityChange = {
                        action(AuthAction.OnLoginPasswordStateChange(TextFieldState.Empty))
                        action(AuthAction.OnLoginPasswordVisibilityChange(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    ),
                )
            }
            Text(
                text = "Lupa password?",
                style = typography.labelLarge,
                color = color.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .padding(horizontal = 12.dp)
            ) {
                OutlinedButton(
                    text = "Daftar",
                    modifier = Modifier
                        .weight(1f)
                ) {
                    action(AuthAction.OnScreenChange(1))
                }
                PrimaryButton(
                    text = "Masuk",
                    enabled = state.loginEmailValue.isNotEmpty() && state.loginEmailState !is TextFieldState.Error
                            && state.loginPasswordValue.isNotEmpty() && state.loginPasswordState !is TextFieldState.Error,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    action(AuthAction.OnLoginBasic)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Divider(
                    color = color.outline,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "ATAU",
                    style = typography.bodySmall,
                    color = color.onSurfaceVariant,
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )
                Divider(
                    color = color.outline,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            OutlinedIconButton(
                enabled = state.loginGoogleButtonEnabled,
                text = "Masuk dengan google",
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_google),
                        contentDescription = ""
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth()
            ) {
                state.gso?.let {
                    googleSignInLauncher.launch(it)
                }

            }
        }
    }
}