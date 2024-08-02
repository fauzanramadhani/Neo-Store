package com.ndc.neostore.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
fun MyProductCard(
    modifier: Modifier = Modifier,
    productName: String,
    productImage: String,
    stock: Int,
    price: Long,
    onClick: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(color.primaryContainer)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = productImage,
            contentDescription = "",
            error = painterResource(id = R.drawable.error_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(48.dp),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = productName,
                    style = typography.titleSmall,
                    color = color.onPrimaryContainer,
                    maxLines = 2,
                    minLines = 2
                )
                Text(
                    text = "Stok: ${if (stock >= 1) stock else "Habis"} ",
                    style = typography.bodySmall,
                    color = color.onPrimaryContainer,
                    maxLines = 1
                )
                Text(
                    text = "Harga ${price.toCurrency()}",
                    style = typography.bodySmall,
                    color = color.onPrimaryContainer,
                    maxLines = 1
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.ic_edit_fill),
                contentDescription = "",
                tint = color.onPrimaryContainer,
                modifier = Modifier
                    .weight(0.2f)
            )
        }
    }
}

@Preview
@Composable
fun MyProductCardPreview() {
    NeoStoreTheme {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(5) {
                MyProductCard(
                    productImage = "https://seagm-media.seagmcdn.com/material/1168.jpg?x-oss-process=image/resize,w_480",
                    productName = if (it % 2 == 0) "100 Diamond Mobile Legends Cepat Murah Mantap" else "100hh",
                    stock = 20,
                    price = 500000
                )
            }
        }
    }
}