package com.ndc.neostore.ui.feature.detailpurchase

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.R
import com.ndc.neostore.data.source.network.firebase.dto.OrderStatus
import com.ndc.neostore.ui.component.bottomsheet.NotReadyBottomSheet
import com.ndc.neostore.ui.component.button.PrimaryIconButton
import com.ndc.neostore.ui.component.dialog.DialogLoading
import com.ndc.neostore.ui.component.support.SupportText
import com.ndc.neostore.ui.component.topbar.TopBarSecondary
import com.ndc.neostore.utils.Toast
import com.ndc.neostore.utils.toCurrency
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPurchaseScreen(
    navHostController: NavHostController,
    state: DetailPurchaseState,
    effect: DetailPurchaseEffect,
    action: (DetailPurchaseAction) -> Unit,
    orderId: String = ""
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    LaunchedEffect(orderId) {
        action(DetailPurchaseAction.OnGetMyPurchaseOrderById(orderId))
    }

    LaunchedEffect(effect) {
        when (effect) {
            DetailPurchaseEffect.Empty -> {}
            is DetailPurchaseEffect.OnShowToast -> Toast(ctx, effect.message).short()
        }
    }

    DialogLoading(visible = state.loadingState)

    if (state.bottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        action(DetailPurchaseAction.OnBottomSheetVisibilityChange(false))
                    }
                }
            },
            sheetState = bottomSheetState,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp
            ),
            containerColor = color.background,
            dragHandle = null,
        ) {
            NotReadyBottomSheet()
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
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Toko",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    text = state.mySalesOrderDto.sellerName,
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
                PrimaryIconButton(
                    text = "Hubungi Penjual",
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chat),
                            contentDescription = "",
                            modifier = Modifier
                                .size(18.dp)
                        )
                    }
                ) {
                    action(DetailPurchaseAction.OnBottomSheetVisibilityChange(true))
                }
                Spacer(modifier = Modifier.padding(bottom = 8.dp))
                PrimaryIconButton(
                    text = "Kunjungi Toko",
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_store_light),
                            contentDescription = "",
                            modifier = Modifier
                                .size(18.dp)
                        )
                    }
                ) {
                    action(DetailPurchaseAction.OnBottomSheetVisibilityChange(true))
                }
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Produk:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = state.mySalesOrderDto.productName,
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Jumlah Pembelian:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = state.mySalesOrderDto.orderAmount.toString(),
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Harga Satuan:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = state.mySalesOrderDto.productPrice.toCurrency(),
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Total Harga:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = (state.mySalesOrderDto.productPrice * state.mySalesOrderDto.orderAmount).toCurrency(),
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Biaya Admin:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = state.mySalesOrderDto.adminFee.toCurrency(),
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Total Harga:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = ((state.mySalesOrderDto.productPrice * state.mySalesOrderDto.orderAmount) + state.mySalesOrderDto.adminFee).toCurrency(),
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Metode Pembayaran:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Text(
                    text = "Neo Pay",
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Nomor Pesanan: ",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    text = state.mySalesOrderDto.orderId,
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                PrimaryIconButton(
                    text = "Salin",
                    icon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = "",
                            modifier = Modifier
                                .size(18.dp)
                        )
                    }
                ) {
                    clipboard.setText(AnnotatedString(text = state.mySalesOrderDto.orderId))
                    Toast(ctx, "Berhasil menyalin nomor pesanan").short()
                }
            }
            HorizontalDivider(
                thickness = 0.5.dp,
                color = color.outline
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Status:",
                    style = typography.bodySmall,
                    color = color.secondary
                )
                Spacer(modifier = Modifier.padding(bottom = 4.dp))
                Text(
                    text = state.mySalesOrderDto.orderStatus,
                    style = typography.bodyMedium,
                    color = color.onBackground
                )
            }

            when (state.mySalesOrderDto.orderStatus) {
                OrderStatus.Dibayar.name -> Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                ) {
                    PrimaryIconButton(
                        text = "Batalkan",
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_close_circle),
                                contentDescription = "",
                                modifier = Modifier
                                    .size(18.dp)
                            )
                        }
                    ) {
                        action(DetailPurchaseAction.OnCancelOrder)
                    }
                    SupportText(
                        text = "Dengan melakukan pembatalan, " +
                                "maka seluruh dana akan dikembalikan ke saldo Neo Pay Anda."
                    )
                }

                OrderStatus.Diproses.name -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    ) {
                        PrimaryIconButton(
                            text = "Konfirmasi",
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_done_circle),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                            }
                        ) {
                            action(DetailPurchaseAction.OnConfirmOrder)
                        }
                        SupportText(
                            text = "Dengan melakukan konfirmasi, " +
                                    "uang akan di cairkan kepada penjual. " +
                                    "Konfirmasi akan dilakukan secara otomatis " +
                                    "jika anda tidak melakukannya dalam 3 hari."
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                    ) {
                        PrimaryIconButton(
                            text = "Ajukan Pengembalian",
                            icon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_done_circle),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                            }
                        ) {
                            action(DetailPurchaseAction.OnBottomSheetVisibilityChange(true))
                        }
                        SupportText(
                            text = "Dengan mengajukan pengembalian," +
                                    " anda akan dibuatkan ruang diskusi bersama " +
                                    "dengan penjual dan admin Neo Store. Kemudian admin " +
                                    "akan memutuskan penyetujuan pengembalian."
                        )
                    }
                }
            }
        }
    }
}