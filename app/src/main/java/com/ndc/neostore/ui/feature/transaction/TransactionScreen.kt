package com.ndc.neostore.ui.feature.transaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.ui.component.topbar.TopBarSecondary
import com.ndc.neostore.ui.feature.transaction.screen.PurchaseScreen
import com.ndc.neostore.ui.feature.transaction.screen.SalesScreen
import com.ndc.neostore.utils.Toast
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(
    navHostController: NavHostController,
    state: TransactionState,
    effect: TransactionEffect,
    action: (TransactionAction) -> Unit,
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val pagerState = rememberPagerState(0) {
        2
    }
    val scope = rememberCoroutineScope()
    val purchaseListState = rememberLazyListState()
    val salesListState = rememberLazyListState()

    LaunchedEffect(effect) {
        when (effect) {
            TransactionEffect.Empty -> {}
            is TransactionEffect.OnShowToast -> Toast(ctx, effect.message).short()
        }
    }

    Scaffold(
        topBar = {
            TopBarSecondary(
                title = "Transaksi"
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
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions: List<TabPosition> ->
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                            .height(4.dp)
                            .background(color.tertiaryContainer)
                    )
                },
                divider = {}
            ) {
                for (i in 0..<pagerState.pageCount) {
                    Tab(
                        modifier = Modifier
                            .background(color.primary)
                            .height(48.dp),
                        selected = pagerState.currentPage == i,
                        selectedContentColor = color.onPrimary,
                        unselectedContentColor = color.onPrimary,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(i)
                            }
                        },
                    ) {
                        Text(
                            text = when (i) {
                                0 -> "Pembelian"
                                else -> "Penjualan"
                            },
                            style = if (pagerState.currentPage == i) typography.labelLarge else typography.bodyMedium,
                        )
                    }
                }

            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Top,
            ) { index ->
                when (index) {
                    0 -> PurchaseScreen(
                        listState = purchaseListState,
                        state = state,
                        action = action
                    )

                    1 -> SalesScreen(
                        listState = salesListState,
                        state = state,
                        action = action
                    )
                }
            }
        }
    }
}