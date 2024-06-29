package com.example.composeuistudykream.model

import androidx.compose.runtime.Immutable

@Immutable
data class DeliveryInfo(
    val title: String,
    val mainInfo: String,
    val subInfo: String,
    val additionalInfo: String,
)
