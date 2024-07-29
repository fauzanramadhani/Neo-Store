package com.ndc.neostore.ui.feature.dashboard.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ndc.neostore.ui.feature.dashboard.DashboardAction
import com.ndc.neostore.ui.feature.dashboard.DashboardState

@Composable
fun MyStoreScreen(
    paddingValues: PaddingValues,
    listState: LazyListState,
    state: DashboardState,
    action: (DashboardAction) -> Unit,
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
}