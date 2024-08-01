package com.ndc.neostore.ui.feature.detailcheckout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.OutlinedButton
import com.ndc.neostore.ui.component.dialog.DialogLoading
import com.ndc.neostore.ui.component.topbar.TopBarSecondary
import com.ndc.neostore.ui.theme.Inter
import com.ndc.neostore.utils.Toast
import com.ndc.neostore.utils.toCurrency

@Composable
fun DetailCheckoutScreen(
    navHostController: NavHostController,
    state: DetailCheckoutState,
    effect: DetailCheckoutEffect,
    action: (DetailCheckoutAction) -> Unit,
    productId: String = ""
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LaunchedEffect(productId) {
        action(DetailCheckoutAction.OnGetMarketProductById(productId))
    }

    DialogLoading(visible = state.loadingState)

    LaunchedEffect(effect) {
        when (effect) {
            DetailCheckoutEffect.Empty -> {}
            is DetailCheckoutEffect.OnShowToast -> Toast(ctx, effect.message).short()
        }
    }

    Scaffold(
        topBar = {
            TopBarSecondary(
                title = "Rincian Pembelian"
            ) {
                navHostController.navigateUp()
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.primary)
            .statusBarsPadding()
            .background(color = color.background)
    ) { paddingValues ->
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(
                    top = 16.dp,
                    bottom = 32.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Toko",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = state.marketProductDto.sellerProfileUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.error_image),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(24.dp)
                    )
                    Text(
                        text = state.marketProductDto.sellerName,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 9.sp,
                        color = color.onBackground,
                        maxLines = 1,
                    )
                }
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Produk",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Text(
                    text = state.marketProductDto.productName,
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Jumlah Pembelian",
                        style = typography.labelLarge,
                        color = color.onBackground
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_remove_circle),
                            contentDescription = "",
                            tint = if (state.buyAmount != 0) color.error else color.secondary,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable(
                                    enabled = state.buyAmount != 0,
                                    onClick = {
                                        action(DetailCheckoutAction.OnAmountChange(state.buyAmount - 1))
                                    }
                                )
                        )
                        Text(
                            text = state.buyAmount.toString(),
                            style = typography.bodySmall,
                            color = color.onBackground
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_circle),
                            contentDescription = "",
                            tint = if (state.buyAmount < state.marketProductDto.productStock) color.primary else color.secondary,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .clickable(
                                    enabled = state.buyAmount < state.marketProductDto.productStock,
                                    onClick = {
                                        action(DetailCheckoutAction.OnAmountChange(state.buyAmount + 1))
                                    }
                                )
                        )
                    }
                }
                if (state.buyAmountErrorState || state.marketProductDto.productStock == 0) {
                    Text(
                        text = "Stok tersisa ${state.marketProductDto.productStock}. Silahkan hubungi penjual untuk memperbarui stok",
                        style = typography.bodySmall,
                        color = color.error
                    )
                }
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Harga Satuan",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Text(
                    text = state.marketProductDto.productPrice.toCurrency(),
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Harga",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Text(
                    text = (state.marketProductDto.productPrice * state.buyAmount).toCurrency(),
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Biaya Admin",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Text(
                    text = state.adminFee.toCurrency(),
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total Keseluruhan",
                    style = typography.labelLarge,
                    color = color.onBackground
                )
                Text(
                    text = ((state.marketProductDto.productPrice * state.buyAmount) + state.adminFee).toCurrency(),
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Metode Pembayaran",
                        style = typography.labelLarge,
                        color = color.onBackground
                    )
                    Text(
                        text = "Ubah Metode Pembayaran",
                        style = typography.labelSmall,
                        color = color.primary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable {
                                // TODO
                            }
                    )
                }
                Text(
                    text = state.paymentMethod,
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                text = "Konfirmasi Checkout",
                enabled = state.buyAmount != 0 && !state.loadingState,
                onClick = {
                    action(DetailCheckoutAction.OnConfirmCheckout)
                }
            )
        }
    }
}