package com.ndc.neostore.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ndc.neostore.R
import com.ndc.neostore.utils.toCurrency

@Composable
fun WalletCard(
    modifier: Modifier = Modifier,
    balance: Long = 0L,
    onTopUpClicked: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.onPrimary)
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wallet),
                contentDescription = "",
                tint = color.onBackground,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = balance.toCurrency(),
                style = typography.bodySmall,
                color = color.onBackground
            )
        }
        VerticalDivider(
            thickness = 0.5.dp,
            color = color.outline
        )
        Row(
            modifier = Modifier
                .weight(0.5f)
                .clickable(onClick = onTopUpClicked)
                .fillMaxHeight()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_credit_card),
                contentDescription = "",
                tint = color.onBackground,
                modifier = Modifier
                    .size(24.dp)
            )
            Text(
                text = "Isi Saldo",
                style = typography.bodySmall,
                color = color.onBackground
            )
        }
    }
}