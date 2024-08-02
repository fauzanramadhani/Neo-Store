package com.ndc.neostore.ui.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.theme.Inter
import com.ndc.neostore.ui.theme.NeoStoreTheme
import com.ndc.neostore.utils.toCurrency

@Composable
fun MarketCard(
    modifier: Modifier = Modifier,
    productName: String,
    productImage: String,
    price: Long,
    sellerImage: String,
    sellerName: String,
    onClick: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .border(
                width = 1.dp,
                color = color.outline,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        AsyncImage(
            model = productImage,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.error_image),
            modifier = Modifier
                .height(158.dp)
        )
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(
                    top = 150.dp,
                ),
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .background(color.background)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = sellerImage.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.error_image),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(14.dp)
                    )
                    Text(
                        text = sellerName,
                        fontFamily = Inter,
                        fontWeight = FontWeight.Normal,
                        fontSize = 9.sp,
                        color = color.onBackground,
                        maxLines = 1,
                    )
                }
                Text(
                    text = productName,
                    style = typography.titleSmall,
                    color = color.onBackground,
                    maxLines = 3,
                    minLines = 3
                )
                Text(
                    text = price.toCurrency(),
                    style = typography.labelSmall,
                    color = color.primary,
                    maxLines = 1,
                )
            }
        }
    }
}

@Preview
@Composable
fun MarketCardPreview() {
    val configuration = LocalConfiguration.current

    NeoStoreTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(10) {
                MarketCard(
                    productName = if (it % 2 == 0) "100 Diamond Mobile Legends Cepat Murah Mantap" else "100hh",
                    productImage = "https://seagm-media.seagmcdn.com/material/1168.jpg?x-oss-process=image/resize,w_480",
                    price = 500000,
                    sellerImage = "https://ogletree.com/app/uploads/people/alexandre-abitbol.jpg",
                    sellerName = "Fauzan Ramadhani"
                )
            }
        }
    }
}