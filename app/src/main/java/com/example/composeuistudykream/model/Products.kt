package com.example.composeuistudykream.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Immutable
data class Products(
    val items: List<Product> = listOf()
)

@Immutable
data class Product(
    val immediatePurchasePrice: Int,
    val englishName: String,
    val koreanName: String,
    val additionalBenefits: String,
)

@Immutable
data class AdditionalBenefit(
    val point: String,
    val payment: String,
)
