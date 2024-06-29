package com.example.composeuistudykream.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeuistudykream.model.AdditionalBenefit
import com.example.composeuistudykream.model.DeliveryInfo
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
            getProducts()
        }
    }

    companion object {

        fun getProducts(): List<Product> {

            return listOf(
                Product(
                    immediatePurchasePrice = 117000,
                    englishName = "Nike Air Force 1 '07 Low white",
                    koreanName = "나이키 에어포스 1 '07 로우 화이트",
                    additionalBenefits = AdditionalBenefit(
                        point = "계좌 간편결제 시 1% 적립",
                        payment = "우리카드 KREAM카드 최대 5% 청구할인 외 4건" // 이 부분을 span으로 바로 처리 될 수 있도록 할 순 없을까?
                    ),
                    deliveryInfo = listOf(
                        DeliveryInfo(
                            title = "",
                            mainInfo = "",
                            subInfo = "",
                            additionalInfo = "",
                        ),
                        DeliveryInfo(
                            title = "",
                            mainInfo = "",
                            subInfo = "",
                            additionalInfo = "",
                        ),
                        DeliveryInfo(
                            title = "",
                            mainInfo = "",
                            subInfo = "",
                            additionalInfo = "",
                        ),
                        DeliveryInfo(
                            title = "",
                            mainInfo = "",
                            subInfo = "",
                            additionalInfo = "",
                        ),
                    )
                )
            )
        }

    }

}
