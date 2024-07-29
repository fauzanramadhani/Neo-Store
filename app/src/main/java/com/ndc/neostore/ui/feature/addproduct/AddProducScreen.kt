package com.ndc.neostore.ui.feature.addproduct

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.PrimaryButton
import com.ndc.neostore.ui.component.textfield.PrimaryTextField
import com.ndc.neostore.ui.component.textfield.TextFieldState
import com.ndc.neostore.ui.component.topbar.TopBarSecondary
import com.ndc.neostore.utils.Toast
import com.ndc.neostore.utils.isNumber

@Composable
fun AddProductScreen(
    navHostController: NavHostController,
    state: AddProductState,
    effect: AddProductEffect,
    action: (AddProductAction) -> Unit,
) {
    val ctx = LocalContext.current
    val focusManager = LocalFocusManager.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                action(AddProductAction.OnImageProductChange(it))
            }
        }
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            pickImageLauncher.launch("image/*")
        } else {
            Toast(ctx, "Anda perlu mengizinkan akses penyimpanan").short()
        }
    }

    Scaffold(
        topBar = {
            TopBarSecondary(
                title = "Tambah Produk"
            ) {
                navHostController.navigateUp()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.primary)
            .safeDrawingPadding()
            .background(color = color.onBackground)
    ) { paddingValues ->

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp,
                    bottom = 32.dp
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Foto Produk",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = color.outline
                        )
                        .size(148.dp)
                        .clickable {
                            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
                            if (ContextCompat.checkSelfPermission(
                                    ctx,
                                    permission
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                // Permission granted, proceed with image selection
                                pickImageLauncher.launch("image/*")
                            } else {
                                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                ) {
                    if (state.productImageUri != null) {
                        AsyncImage(
                            model = state.productImageUri,
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = Color.Black.copy(0.3f)
                                ),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .clip(RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(color.primary)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_upload),
                                contentDescription = "",
                                tint = color.onPrimary
                            )
                        }
                        Text(
                            text = if (state.productImageUri == null) "Upload" else "Ubah",
                            style = typography.labelSmall,
                            color = if (state.productImageUri == null) color.onBackground else color.onPrimary
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Nama Produk",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textFieldState = TextFieldState.Empty,
                    placeholder = "Silahkan masukan nama produk",
                    value = state.productNameValue,
                    onClearValue = {
                        action(AddProductAction.OnProductNameValueChange(""))
                    },
                    onValueChange = {
                        action(AddProductAction.OnProductNameValueChange(it))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Deskripsi Produk",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(),
                    textFieldState = TextFieldState.Empty,
                    placeholder = "Silahkan masukan deskripsi produk",
                    value = state.productDescriptionValue,
                    onValueChange = {
                        action(AddProductAction.OnProductDescriptionValueChange(it))
                    },
                    onClearValue = {
                        action(AddProductAction.OnProductDescriptionValueChange(""))
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Harga",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(),
                    textFieldState = TextFieldState.Empty,
                    placeholder = "Silahkan masukan jumlah harga",
                    value = state.productPriceValue,
                    onValueChange = {
                        if (it.isNumber() || it.isEmpty())
                            action(AddProductAction.OnProductPriceValueChange(it))
                    },
                    onClearValue = {
                        action(AddProductAction.OnProductPriceValueChange(""))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Next
                    ),
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Stok",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                PrimaryTextField(
                    modifier = Modifier
                        .height(54.dp)
                        .fillMaxWidth(),
                    textFieldState = TextFieldState.Empty,
                    placeholder = "Silahkan masukan jumlah stok",
                    value = state.productStockValue,
                    onValueChange = {
                        if (it.isNumber() || it.isEmpty())
                            action(AddProductAction.OnProductStockValueChange(it))
                    },
                    onClearValue = {
                        action(AddProductAction.OnProductStockValueChange(""))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    )
                )
            }
            Spacer(modifier = Modifier.padding(bottom = 16.dp))
            with(state) {
                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Tambah",
                    enabled = productImageUri != null && productNameValue.isNotEmpty()
                            && productDescriptionValue.isNotEmpty() && productPriceValue.isNotEmpty()
                            && productStockValue.isNotEmpty()
                ) {

                }
            }
        }
    }
}