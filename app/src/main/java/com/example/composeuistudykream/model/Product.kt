package com.example.composeuistudykream.model

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val immediatePurchasePrice: Int,
    val englishName: String,
    val koreanName: String,
    val additionalBenefits: AdditionalBenefit,
    val deliveryInfo: List<DeliveryInfo>,
)
