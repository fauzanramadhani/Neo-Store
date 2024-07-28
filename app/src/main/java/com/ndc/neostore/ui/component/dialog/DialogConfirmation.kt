package com.ndc.neostore.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogConfirmation(
    show: Boolean = false,
    title: String = "",
    onDismiss: () -> Unit = {},
    onConfirmation: () -> Unit = {},

) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    if (show) BasicAlertDialog(
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(color.background),
        onDismissRequest = onDismiss,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = title,
                style = typography.titleSmall,
                color = color.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Batal",
                    style = typography.bodySmall,
                    color = color.onBackground,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onDismiss)
                )
                Text(
                    text = "Konfirmasi",
                    style = typography.bodySmall,
                    color = color.onBackground,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(onClick = onConfirmation)
                )
            }
        }
    }
}