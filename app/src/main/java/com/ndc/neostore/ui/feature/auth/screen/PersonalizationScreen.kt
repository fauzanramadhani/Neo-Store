package com.ndc.neostore.ui.feature.auth.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import com.ndc.neostore.ui.component.dialog.DialogConfirmation
import com.ndc.neostore.ui.component.textfield.PrimaryTextField
import com.ndc.neostore.ui.component.textfield.TextFieldState
import com.ndc.neostore.ui.feature.auth.AuthAction
import com.ndc.neostore.ui.feature.auth.AuthState

@Composable
fun PersonalizationScreen(
    state: AuthState,
    action: (AuthAction) -> Unit,
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val focusManager = LocalFocusManager.current

    BackHandler {
        action(AuthAction.OnPersonalizationLogoutDialogVisibilityChange(true))
    }

    DialogConfirmation(
        show = state.personalizationLogoutDialogVisible,
        title = "Apakah anda yakin ingin keluar?",
        onDismiss = {
            action(AuthAction.OnPersonalizationLogoutDialogVisibilityChange(false))
        },
        onConfirmation = {
            action(AuthAction.OnPersonalizationLogout)
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color.background)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
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
                        action(AuthAction.OnPersonalizationLogoutDialogVisibilityChange(true))
                    }
            )
            Text(
                text = "Silahkan lengkapi informasi diri anda",
                style = typography.titleSmall.copy(fontWeight = FontWeight.Normal),
                color = color.onBackground,
                modifier = Modifier
                    .padding(start = 12.dp),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                Text(
                    text = "Nama Lengkap",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = state.personalizationNameState,
                    placeholder = "Masukan nama kamu",
                    value = state.personalizationNameValue,
                    onValueChange = {
                        if (it.isNotEmpty() && it.length < 4)
                            action(
                                AuthAction.OnPersonalizationNameStateChange(
                                    TextFieldState.Error(
                                        "Minimal 4 karakter"
                                    )
                                )
                            )
                        else
                            action(AuthAction.OnPersonalizationNameStateChange(TextFieldState.Empty))
                        action(AuthAction.OnPersonalizationNameValueChange(it))
                    },
                    onClearValue = {
                        action(AuthAction.OnPersonalizationNameValueChange(""))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
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
                text = "Simpan",
                enabled = !state.loadingState && state.personalizationNameValue.isNotEmpty()
                        && state.personalizationNameState !is TextFieldState.Error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                focusManager.clearFocus(true)
                action(AuthAction.OnPersonalizationSaved)
            }
        }
    }
}