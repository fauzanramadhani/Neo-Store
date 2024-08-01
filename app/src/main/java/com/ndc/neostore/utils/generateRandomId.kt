package com.ndc.neostore.utils

import java.util.Random

fun generateRandomId(key: String): String {
    val prefix = "$key-"
    val digitLength = 12

    val seed = System.currentTimeMillis()
    val random = Random(seed)

    val number = StringBuilder()
    repeat(digitLength) {
        number.append(random.nextInt(10))
    }

    return "$prefix$number"
}