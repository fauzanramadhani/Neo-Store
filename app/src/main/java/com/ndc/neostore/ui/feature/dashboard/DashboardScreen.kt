package com.ndc.neostore.ui.feature.dashboard

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.bottomsheet.DetailMarketProductBottomSheet
import com.ndc.neostore.ui.component.bottomsheet.NotReadyBottomSheet
import com.ndc.neostore.ui.component.card.WalletCard
import com.ndc.neostore.ui.component.textfield.PrimaryTextField
import com.ndc.neostore.ui.feature.dashboard.screen.AccountScreen
import com.ndc.neostore.ui.feature.dashboard.screen.MarketScreen
import com.ndc.neostore.ui.feature.dashboard.screen.MyStoreScreen
import com.ndc.neostore.ui.navigation.NavRoute
import com.ndc.neostore.utils.Toast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navHostController: NavHostController,
    state: DashboardState,
    effect: DashboardEffect,
    action: (DashboardAction) -> Unit,
) {
    val ctx = LocalContext.current
    val color = MaterialTheme.colorScheme
    val view = LocalView.current
    val darkTheme: Boolean = isSystemInDarkTheme()
    val window = (view.context as Activity).window
    val typography = MaterialTheme.typography
    val homeBottomNavigationItems = listOf(
        HomeBottomNavigationItem(
            label = "Belanja",
            unselectedIcon = R.drawable.ic_store,
            selectedIcon = R.drawable.ic_store_fill,
        ),
        HomeBottomNavigationItem(
            label = "Toko Saya",
            unselectedIcon = R.drawable.ic_bag,
            selectedIcon = R.drawable.ic_bag_fill
        ),
        HomeBottomNavigationItem(
            label = "Akun",
            unselectedIcon = R.drawable.ic_account,
            selectedIcon = R.drawable.ic_account_fill
        ),
    )
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()
    val marketListState = rememberLazyGridState()
    val myStoreListState = rememberLazyListState()
    val accountListState = rememberLazyListState()

    BackHandler {
        (ctx as Activity).finish()
    }

    LaunchedEffect(effect) {
        when (effect) {
            DashboardEffect.Empty -> {}
            DashboardEffect.OnLogout -> navHostController.navigate(NavRoute.AuthScreen.route) {
                launchSingleTop = true
            }

            is DashboardEffect.OnError -> Toast(ctx, effect.message).short()
        }
    }

    if (state.bottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    bottomSheetState.hide()
                }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        action(
                            DashboardAction.OnBottomSheetVisibilityChange(
                                visible = false,
                                type = HomeBottomSheetType.NotReady
                            )
                        )
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
            when (val data = state.homeBottomSheetType) {
                HomeBottomSheetType.NotReady -> NotReadyBottomSheet()
                is HomeBottomSheetType.MarketProductDetail -> DetailMarketProductBottomSheet(
                    productImageUrl = data.marketProductDto.productImageUrl,
                    productPrice = data.marketProductDto.productPrice,
                    productStock = data.marketProductDto.productStock,
                    productName = data.marketProductDto.productName,
                    productDescription = data.marketProductDto.productDescription,
                    sellerProfileUrl = data.marketProductDto.sellerProfileUrl,
                    sellerName = data.marketProductDto.sellerName,
                ) {
                    action(
                        DashboardAction.OnBottomSheetVisibilityChange(visible = false)
                    )
                    navHostController.navigate(NavRoute.DetailCheckOutScreen.navigateWithId(data.marketProductDto.productId))
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color.primary)
            .statusBarsPadding()
            .background(color = color.background)
            .safeDrawingPadding(),
        topBar = {
            when (state.currentScreen) {
                2 -> Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(color.primary)
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 24.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = state.userDto.profileUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            error = painterResource(id = R.drawable.error_image),
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(24.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = state.userDto.name,
                                style = typography.titleMedium,
                                color = color.onPrimary,
                                maxLines = 1
                            )
                            Text(
                                text = state.userDto.email,
                                style = typography.bodySmall,
                                color = color.onPrimary,
                                maxLines = 1
                            )
                        }
                    }
                    WalletCard(
                        balance = state.userDto.balance,
                        onTopUpClicked = {
                            action(DashboardAction.OnBottomSheetVisibilityChange(visible = true))
                        }
                    )
                }

                else -> Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                        .background(color.primary)
                        .fillMaxWidth()
                        .padding(
                            start = 16.dp,
                            end = 16.dp,
                            top = 16.dp,
                            bottom = 24.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PrimaryTextField(
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "",
                                tint = color.onSurfaceVariant,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        },
                        placeholder = if (state.currentScreen == 0) "Cari toko atau produk" else "Cari produk saya",
                        enabled = false,
                        modifier = Modifier
                            .weight(0.8f)
                            .clickable {
                                action(
                                    DashboardAction.OnBottomSheetVisibilityChange(
                                        visible = true,
                                        type = HomeBottomSheetType.NotReady
                                    )
                                )
                            }
                    )
                    Icon(
                        painter = painterResource(
                            id = if (state.currentScreen == 0) R.drawable.ic_chat_fill else R.drawable.ic_notification
                        ),
                        contentDescription = "",
                        tint = color.onPrimary,
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .size(36.dp)
                            .clickable {
                                action(
                                    DashboardAction.OnBottomSheetVisibilityChange(
                                        visible = true,
                                        type = HomeBottomSheetType.NotReady
                                    )
                                )
                            }
                    )
                }
            }
        },
        floatingActionButton = {
            when (state.currentScreen) {
                1 -> FloatingActionButton(
                    modifier = Modifier.padding(12.dp),
                    contentColor = color.onPrimaryContainer,
                    containerColor = color.primaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        navHostController.navigate(NavRoute.AddProductScreen.route) {
                            launchSingleTop = true
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_square),
                        contentDescription = ""
                    )
                }
            }

        },
        bottomBar = {
            Surface(
                shadowElevation = 12.dp,
            ) {
                BottomNavigationBar(
                    homeBottomNavigationItems = homeBottomNavigationItems,
                    selectedIndex = state.currentScreen,
                    onSelectedIndexChange = {
                        action(DashboardAction.OnScreenChange(it))
                    }
                )
            }
        }
    ) { paddingValues ->
        when (state.currentScreen) {
            0 -> MarketScreen(
                listState = marketListState,
                paddingValues = paddingValues,
                state = state,
                action = action
            )

            1 -> MyStoreScreen(
                listState = myStoreListState,
                paddingValues = paddingValues,
                state = state,
                action = action
            )

            2 -> AccountScreen(
                navHostController = navHostController,
                paddingValues = paddingValues,
                listState = accountListState,
                state = state,
                action = action,
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    homeBottomNavigationItems: List<HomeBottomNavigationItem>,
    selectedIndex: Int,
    onSelectedIndexChange: (index: Int) -> Unit
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    BottomAppBar(
        containerColor = Color.Transparent,
    ) {
        homeBottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
            with(bottomNavigationItem) {
                val isSelected = selectedIndex == index
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onSelectedIndexChange(index)
                    },
                    label = {
                        Text(
                            text = label,
                            style = typography.labelSmall
                        )
                    },
                    icon = {
                        Icon(
                            painterResource(
                                id = if (isSelected) selectedIcon
                                else unselectedIcon
                            ),
                            contentDescription = ""
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = color.primary,
                        unselectedIconColor = color.secondary,
                        selectedTextColor = color.primary,
                        unselectedTextColor = color.secondary,
                        indicatorColor = color.primaryContainer
                    )
                )
            }
        }
    }
}