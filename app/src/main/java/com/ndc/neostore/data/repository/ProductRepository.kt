package com.ndc.neostore.data.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import com.ndc.neostore.data.source.network.firebase.dto.MarketProductDto
import com.ndc.neostore.data.source.network.firebase.dto.ProductDto
import com.ndc.neostore.data.source.network.firebase.dto.UserDto
import javax.inject.Inject

class ProductRepository @Inject constructor() {

    private val auth = Firebase.auth
    private val databaseRef = Firebase.database.reference
    private val productImageRef: StorageReference = Firebase
        .storage
        .reference
        .child("images")
        .child("productImages")

    fun uploadProductImage(
        productId: String,
        productImage: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val ref = productImageRef
            .child(auth.currentUser?.uid ?: "")
            .child(productId)
        ref.putFile(productImage)
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                ref.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    onSuccess(downloadUri.toString())
                } else {
                    onFailure(task.exception?.message.toString())
                }
            }
    }

    fun addProduct(
        productId: String,
        productImageUrl: String,
        productName: String,
        productDescription: String,
        productPrice: Long,
        productStock: Int,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val sellerUid = auth.currentUser?.uid ?: ""
        val createdAt = System.currentTimeMillis()
        val productDto = ProductDto(
            productId = productId,
            sellerUid = sellerUid,
            productImageUrl = productImageUrl,
            productName = productName,
            productDescription = productDescription,
            productPrice = productPrice,
            productStock = productStock,
            createdAt = createdAt
        )
        val userProductIdRef = databaseRef
            .child("users")
            .child(sellerUid)
            .child("productId")

        databaseRef
            .child("product")
            .child(productId)
            .setValue(productDto)
            .addOnSuccessListener {
                userProductIdRef
                    .limitToLast(1)
                    .get()
                    .addOnSuccessListener { productIdSnapshot ->
                        if (productIdSnapshot.exists()) {
                            val lastProductIdKey = productIdSnapshot
                                .children
                                .mapNotNull {
                                    it.key
                                }
                                .last()
                                .toInt()
                            val newId = lastProductIdKey + 1
                            userProductIdRef
                                .child(newId.toString())
                                .setValue(productId)
                                .addOnSuccessListener {
                                    onSuccess()
                                }
                                .addOnFailureListener {
                                    onFailure(it.message.toString())
                                }
                        } else {
                            userProductIdRef
                                .child("0")
                                .setValue(productId)
                                .addOnSuccessListener {
                                    onSuccess()
                                }
                                .addOnFailureListener {
                                    onFailure(it.message.toString())
                                }
                        }
                    }.addOnFailureListener {
                        onFailure(it.message.toString())
                    }
            }
            .addOnFailureListener {
                onFailure(it.message.toString())
            }
    }

    fun getMyProduct(
        onSuccess: (List<ProductDto>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        databaseRef
            .child("product")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val productList = snapshot.children.map { product ->
                        product.getValue(ProductDto::class.java) ?: ProductDto()
                    }.filter {
                        it.sellerUid == uid
                    }.sortedByDescending { it.createdAt }
                    onSuccess(productList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }
            })
    }

    fun getMarketProduct(
        onSuccess: (List<MarketProductDto>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val uid = auth.currentUser?.uid ?: ""
        databaseRef
            .child("product")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val productList = mutableListOf<MarketProductDto>()
                    snapshot
                        .children
                        .forEachIndexed { index, product ->
                            val productDto =
                                product.getValue(ProductDto::class.java) ?: ProductDto()
                            databaseRef
                                .child("users")
                                .child(productDto.sellerUid)
                                .get()
                                .addOnCompleteListener { userSnapshot ->
                                    val userDto = userSnapshot.result.getValue(UserDto::class.java)
                                        ?: UserDto()
                                    productList.add(
                                        MarketProductDto(
                                            productId = productDto.productId,
                                            sellerUid = productDto.sellerUid,
                                            sellerName = userDto.name,
                                            sellerProfileUrl = userDto.profileUrl,
                                            productImageUrl = productDto.productImageUrl,
                                            productName = productDto.productName,
                                            productDescription = productDto.productDescription,
                                            productPrice = productDto.productPrice,
                                            productStock = productDto.productStock,
                                            createdAt = productDto.createdAt
                                        )
                                    )
                                    if (productList.size.toLong() == snapshot.childrenCount) {
                                        val sortedMarketProductList = productList
                                            .filter { it.sellerUid != uid }
                                            .sortedByDescending { it.createdAt }
                                        onSuccess(sortedMarketProductList)
                                    }
                                }.addOnFailureListener {
                                    onFailure(it.message.toString())
                                }
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

            })
    }

    fun getMarketProductById(
        productId: String,
        onSuccess: (MarketProductDto) -> Unit,
        onFailure: (String) -> Unit
    ) {
        databaseRef
            .child("product")
            .child(productId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val productDto = snapshot.getValue(ProductDto::class.java) ?: ProductDto()
                    databaseRef
                        .child("users")
                        .child(productDto.sellerUid)
                        .get()
                        .addOnCompleteListener { userSnapshot ->
                            val userDto = userSnapshot.result.getValue(UserDto::class.java) ?: UserDto()
                            val marketProductDto = MarketProductDto(
                                productId = productDto.productId,
                                sellerUid = productDto.sellerUid,
                                sellerName = userDto.name,
                                sellerProfileUrl = userDto.profileUrl,
                                productImageUrl = productDto.productImageUrl,
                                productName = productDto.productName,
                                productDescription = productDto.productDescription,
                                productPrice = productDto.productPrice,
                                productStock = productDto.productStock,
                                createdAt = productDto.createdAt
                            )
                            onSuccess(marketProductDto)
                        }
                        .addOnFailureListener {
                            onFailure(it.message.toString())
                        }
                }

                override fun onCancelled(error: DatabaseError) {
                    onFailure(error.message)
                }

            })
    }
}