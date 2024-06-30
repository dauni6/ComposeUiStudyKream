package com.example.composeuistudykream.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ProductInfoTabItem(
    val title: String,
    val unselectedIcon: ImageVector? = null,
    val selectedIcon: ImageVector? = null,
)
