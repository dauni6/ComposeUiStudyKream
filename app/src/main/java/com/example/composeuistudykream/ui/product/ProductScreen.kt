@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.composeuistudykream.ui.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeuistudykream.R
import com.example.composeuistudykream.model.Product
import com.example.composeuistudykream.ui.component.PullToRefreshLazyColumn
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = {
            ProductTopBar()
        }
    ) { paddingValues ->
        val products = viewModel.productStateFlow.collectAsState().value
        val isRefreshing = viewModel.isRefreshingFlow.collectAsState().value

        Box(modifier = Modifier.fillMaxSize()) {
            ScrollableContent(
                paddingValues = paddingValues,
                items = products,
                isRefreshing = isRefreshing,
                onRefresh = {
                    viewModel.fetchProducts()
                }
            )
        }
    }
}

@Composable
fun ProductTopBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        ),
        title = {},
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Icon"
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Adding Item"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    contentDescription = "Alarm for Item"
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Share,
                    contentDescription = "Share Item"
                )
            }
        }
    )
}


@Composable
fun ScrollableContent(
    paddingValues: PaddingValues,
    items: List<Product>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
) {
    PullToRefreshLazyColumn(
        modifier = Modifier.padding(paddingValues),
        items = items,
        pagerContent = { ProductPager() },
        priceContent = { ProductPriceContent() },
        additionalBenefitContent = { ProductAdditionalBenefitContent() },
        deliveryInfoContent = { ProductDeliveryInfoContent() },
        content = { product ->

        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
}

@Composable
fun ProductPager() {
    Box(modifier = Modifier.fillMaxWidth()) {
        val pagerState = rememberPagerState(
            pageCount = { 4 }
        )

        // 더미 데이터
        val productItems = listOf(
            R.drawable.nike_air_force1_07_low_white_1,
            R.drawable.nike_air_force1_07_low_white_2,
            R.drawable.nike_air_force1_07_low_white_3,
            R.drawable.nike_air_force1_07_low_white_4,
        )

        HorizontalPager(
            modifier = Modifier
                .fillMaxSize()
                .height(300.dp)
                .align(Alignment.Center),
            state = pagerState
        ) {
            val resourceId = productItems[pagerState.currentPage]
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = resourceId),
                contentScale = ContentScale.Fit,
                contentDescription = "product item order : ${pagerState.pageCount}"
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 12.dp, end = 12.dp, bottom = 40.dp)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(2.dp)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun ProductPriceContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Text(
            text = "즉시 구매가",
            fontSize = 12.sp,
        )
        Text(
            text = "117,000원",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "Nike Air Force 1 '07 Low White",
            fontSize = 12.sp,
            color = Color.Black
        )
        Text(
            text = "나이키 에어포스 1 '07 로우 화이트",
            fontSize = 10.sp,
            color = Color.DarkGray,
        )
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun ProductAdditionalBenefitContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "추가 혜택",
                fontSize = 12.sp,
                color = Color.Black,
            )
            Text(
                text = "더보기",
                fontSize = 11.sp,
                color = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "포인트",
                fontSize = 10.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(
                text = "계좌 간편결제 시 1% 적립",
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.padding(top = 4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "결제",
                fontSize = 10.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.padding(start = 20.dp))
            Text(
                text = "우리카드 KREAM카드 최대 5% 청구할인",
                fontSize = 10.sp,
                color = Color.Black,
            )
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun ProductDeliveryInfoContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
    ) {
        Text(
            text = "배송 정보",
            fontSize = 12.sp,
            color = Color.Black,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.Green)
                    .alpha(0.5f)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.lightening_30),
                contentDescription = "lightening bolt"
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column {
                Row {
                    Text(
                        text = "빠른배송",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.padding(start = 2.dp))
                    Text(
                        text = "5,000원",
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.padding(start = 2.dp))
                    Image(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.questionmark_100),
                        contentDescription = "정보"
                    )
                }

                Text(
                    text = "지금 결제 시 모레 7/1(월) 도착 예정",
                    fontSize = 11.sp,
                    color = Color.Gray,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .alpha(0.5f)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.package_100),
                contentDescription = "package"
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column {
                Row {
                    Text(
                        text = "일반배송",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.padding(start = 2.dp))
                    Text(
                        text = "3,000원",
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                }

                Text(
                    text = "검수 후 배송 | 5-7일 내 도착 예정",
                    fontSize = 11.sp,
                    color = Color.Gray,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(Color.Magenta)
                    .alpha(0.5f)
                    .align(Alignment.CenterVertically),
                painter = painterResource(id = R.drawable.storehouse_100),
                contentDescription = "package"
            )
            Spacer(modifier = Modifier.padding(start = 10.dp))
            Column {
                Row {
                    Text(
                        text = "창고보관",
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.padding(start = 2.dp))
                    Text(
                        text = "첫 30일 무료",
                        fontSize = 12.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.padding(start = 2.dp))
                    Image(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(id = R.drawable.questionmark_100),
                        contentDescription = "정보"
                    )
                }

                Text(
                    text = "배송 없이 창고에 보관 | 빠르게 판매 가능",
                    fontSize = 11.sp,
                    color = Color.Gray,
                )

            }
        }

    }
}
