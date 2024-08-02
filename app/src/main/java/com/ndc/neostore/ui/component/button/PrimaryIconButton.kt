package com.ndc.neostore.ui.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "",
    icon: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        contentColor = color.onPrimary
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(if (enabled) color.primary else color.onPrimaryContainer)
                .padding(4.dp)
        ) {
            icon.invoke()
            Text(
                text = text,
                style = typography.labelSmall,
            )
        }
    }
}