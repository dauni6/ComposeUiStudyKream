package com.example.composeuistudykream.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeuistudykream.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {

    val productStateFlow: MutableStateFlow<List<Product>> = MutableStateFlow(emptyList())

    fun fetchProducts() = viewModelScope.launch {
        delay(2000L)  // 서버로부터 새롭게 데이터 가져온다고 가정
        productStateFlow.update {
            listOf(Product(1, " ," ,"", ""))
        }
    }

}
