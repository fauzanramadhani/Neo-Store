package com.ndc.neostore.ui.feature.auth.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.PrimaryButton
import com.ndc.neostore.ui.component.dialog.DialogLoading
import com.ndc.neostore.ui.component.textfield.PasswordTextField
import com.ndc.neostore.ui.component.textfield.PrimaryTextField
import com.ndc.neostore.ui.component.textfield.TextFieldState
import com.ndc.neostore.ui.feature.auth.AuthAction
import com.ndc.neostore.ui.feature.auth.AuthEffect
import com.ndc.neostore.ui.feature.auth.AuthState

@Composable
fun RegisterScreen(
    state: AuthState,
    effect: AuthEffect,
    action: (AuthAction) -> Unit,
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current

    DialogLoading(visible = state.loadingState)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        if (!isSystemInDarkTheme()) {
            Image(
                painter = painterResource(id = R.drawable.register_background),
                contentDescription = "",
                modifier = Modifier
                    .height(203.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left),
                contentDescription = "",
                tint = color.primary,
                modifier = Modifier
                    .padding(start = 12.dp)
                    .clip(CircleShape)
                    .clickable {
                        action(AuthAction.OnScreenChange(0))
                    }
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 12.dp,
                    ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Daftar ke ",
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
                    textFieldState = state.registerEmailState,
                    placeholder = "Masukan email kamu",
                    value = state.registerEmailValue,
                    onValueChange = {
                        action(AuthAction.OnRegisterEmailStateChange(TextFieldState.Empty))
                        action(AuthAction.OnRegisterEmailValueChange(it))
                    },
                    onClearValue = {
                        action(AuthAction.OnRegisterEmailValueChange(""))
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
                    textFieldState = state.registerPasswordState,
                    placeholder = "Masukan password kamu",
                    value = state.registerPasswordValue,
                    onValueChange = {
                        when {
                            it.isNotEmpty() && state.registerPasswordConfirmationValue.isNotEmpty()
                                    && it != state.registerPasswordConfirmationValue -> {
                                action(AuthAction.OnRegisterPasswordStateChange(TextFieldState.Empty))
                                action(
                                    AuthAction.OnRegisterPasswordConfirmationStateChange(
                                        TextFieldState.Error("Konfirmasi password tidak sesuai")
                                    )
                                )
                            }

                            else -> {
                                action(AuthAction.OnRegisterPasswordStateChange(TextFieldState.Empty))
                                action(
                                    AuthAction.OnRegisterPasswordConfirmationStateChange(
                                        TextFieldState.Empty
                                    )
                                )
                            }
                        }
                        action(AuthAction.OnRegisterPasswordValueChange(it))
                    },
                    visible = state.registerPasswordVisible,
                    onVisibilityChange = {
                        action(AuthAction.OnRegisterPasswordVisibilityChange(it))
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
                    text = "Konfirmasi Password",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PasswordTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = state.registerPasswordConfirmationState,
                    placeholder = "Masukan password kamu lagi",
                    value = state.registerPasswordConfirmationValue,
                    onValueChange = {
                        when {
                            it.isNotEmpty() && state.registerPasswordValue != it -> {
                                action(
                                    AuthAction.OnRegisterPasswordConfirmationStateChange(
                                        TextFieldState.Error("Konfirmasi password tidak sesuai")
                                    )
                                )
                            }

                            else -> {
                                action(
                                    AuthAction.OnRegisterPasswordConfirmationStateChange(
                                        TextFieldState.Empty
                                    )
                                )
                            }
                        }
                        action(AuthAction.OnRegisterPasswordConfirmationValueChange(it))
                    },
                    visible = state.registerPasswordConfirmationVisible,
                    onVisibilityChange = {
                        action(AuthAction.OnRegisterPasswordConfirmationVisibilityChange(it))
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
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center,
        ) {
            PrimaryButton(
                text = "Daftar",
                enabled = !state.loadingState && state.registerEmailValue.isNotEmpty() && state.registerPasswordValue.isNotEmpty()
                        && state.registerPasswordConfirmationValue.isNotEmpty() && state.registerPasswordValue == state.registerPasswordConfirmationValue
                        && state.registerEmailState !is TextFieldState.Error && state.registerPasswordState !is TextFieldState.Error
                        && state.registerPasswordConfirmationState !is TextFieldState.Error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                // TODO
            }
        }
    }
}