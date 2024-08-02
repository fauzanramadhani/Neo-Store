package com.ndc.neostore.ui.feature.dashboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.AccountButton
import com.ndc.neostore.ui.feature.dashboard.DashboardAction
import com.ndc.neostore.ui.feature.dashboard.DashboardState
import com.ndc.neostore.ui.navigation.NavRoute

@Composable
fun AccountScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    listState: LazyListState,
    state: DashboardState,
    action: (DashboardAction) -> Unit,
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = paddingValues.calculateTopPadding() + 16.dp,
            bottom = paddingValues.calculateBottomPadding() + 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AccountButton(
                text = "Pengaturan Akun",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user_cicrle),
                        contentDescription = "",
                        tint = color.onPrimaryContainer,
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
            ) {
                action(DashboardAction.OnBottomSheetVisibilityChange(true))
            }
        }
        item {
            AccountButton(
                text = "Transaksi",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_transaction),
                        contentDescription = "",
                        tint = color.onPrimaryContainer,
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
            ) {
                navHostController.navigate(NavRoute.TransactionScreen.route) {
                    launchSingleTop = true
                }
            }

        }
        item {
            AccountButton(
                text = "Keluar",
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "",
                        tint = color.onErrorContainer,
                        modifier = Modifier
                            .size(24.dp)
                    )
                },
                containerColor = color.errorContainer,
                contentColor = color.onErrorContainer
            ) {
                action(DashboardAction.OnLogout)
            }
        }
    }
}