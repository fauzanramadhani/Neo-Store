package com.ndc.neostore.ui.component.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.theme.NeoStoreTheme
import com.ndc.neostore.utils.toCurrency

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    userProfileImageUrl: String = "",
    username: String = "",
    productImageUrl: String = "",
    productName: String = "",
    amount: Int = 0,
    totalPrice: Long = 0,
    status: String = "",
    onClick : () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(8.dp),
                color = color.outline
            )
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 12.dp),
        ) {
            AsyncImage(
                model = userProfileImageUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.error_image),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(18.dp)
            )
            Text(
                text = username,
                style = typography.bodySmall,
                color = color.onBackground,
                maxLines = 1,
            )
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = color.outline
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .padding(horizontal = 12.dp),
        ) {
            AsyncImage(
                model = productImageUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
                contentDescription = "",
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.error_image),
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .size(54.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = productName,
                    style = typography.labelSmall,
                    color = color.onBackground,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "x$amount",
                    style = typography.bodySmall,
                    color = color.onBackground
                )
            }
        }
        HorizontalDivider(
            thickness = 0.5.dp,
            color = color.outline
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Total Keseluruhan",
                style = typography.bodySmall,
                color = color.onBackground
            )
            Text(
                text = totalPrice.toCurrency(),
                style = typography.bodySmall,
                color = color.onBackground
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = "Status",
                style = typography.bodySmall,
                color = color.onBackground
            )
            Text(
                text = status,
                style = typography.bodySmall,
                color = color.onBackground
            )
        }
    }
}

@Preview
@Composable
fun TransactionCardPreview() {
    NeoStoreTheme {
        TransactionCard(
            modifier = Modifier.padding(12.dp),
            userProfileImageUrl = "",
            username = "Fauzan Ramadhani",
            productImageUrl = "https://kaleoz-media.seagmcdn.com/kaleoz-store/202404/oss-40337729e0550d499fe9781c2e8be72c.jpeg",
            productName = "100 Diamond Mobile Legends",
            amount = 3,
            totalPrice = 32000,
            status = "Dibayar"
        )
    }
}