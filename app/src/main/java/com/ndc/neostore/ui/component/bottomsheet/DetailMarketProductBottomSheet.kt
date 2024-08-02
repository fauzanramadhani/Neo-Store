package com.ndc.neostore.ui.component.bottomsheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ndc.neostore.R
import com.ndc.neostore.ui.component.button.OutlinedButton
import com.ndc.neostore.ui.theme.Inter
import com.ndc.neostore.utils.toCurrency

@Composable
fun DetailMarketProductBottomSheet(
    productImageUrl: String = "",
    productPrice: Long = 0L,
    productStock: Int = 0,
    productName: String = "",
    productDescription: String = "",
    sellerProfileUrl: String = "",
    sellerName: String = "",
    onCheckOut: () -> Unit = {}
) {
    val color = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color.background)
            .padding(bottom = 32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = productImageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.error_image),
            modifier = Modifier
                .fillMaxWidth()
                .height(228.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = productPrice.toCurrency(),
                style = typography.titleLarge,
                color = color.primary
            )
            Text(
                text = "Stock: $productStock",
                style = typography.bodyMedium,
                color = color.onSurfaceVariant
            )
        }
        Text(
            text = productName,
            style = typography.titleSmall,
            color = color.onBackground,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            AsyncImage(
                model = sellerProfileUrl.ifEmpty { "https://cdn-icons-png.flaticon.com/512/5951/5951752.png" },
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
            text = productDescription,
            style = typography.bodyLarge,
            color = color.onBackground,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            text = "Checkout",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            onClick = onCheckOut
        )
    }
}