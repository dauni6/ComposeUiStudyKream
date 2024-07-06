@file:OptIn(
    ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.composeuistudykream.ui.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeuistudykream.R
import com.example.composeuistudykream.model.Product
import com.example.composeuistudykream.model.ProductInfoTabItem
import com.example.composeuistudykream.ui.component.DisabledInteractionSource
import com.example.composeuistudykream.ui.component.PullToRefreshLazyColumn
import com.example.composeuistudykream.ui.component.RoundedIndicator
import com.example.composeuistudykream.ui.component.roundedTabIndicatorOffset
import com.example.composeuistudykream.ui.theme.darker
import timber.log.Timber

@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentWindowInsets = WindowInsets.navigationBars,
        topBar = {
            ProductTopBar()
        },
        bottomBar = {
            ProductPurchaseOrSellBar()
        }
    ) { innerPadding ->
        val products = viewModel.productStateFlow.collectAsState().value
        val isRefreshing = viewModel.isRefreshingFlow.collectAsState().value

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ) {
            Timber.d("items size = > ${products.size}")
            ScrollableContent(
                paddingValues = innerPadding,
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
        productInfoRowTabContent = { ProductInfoContent() },
        marketPriceVariationContent = { ProductMarketPriceVariationContent() },
        productSizeContent = { ProductSizeContent() },
        productStyleContent = {
            val dogImages = listOf(
                R.drawable.dog1,
                R.drawable.dog2,
                R.drawable.dog3,
                R.drawable.dog4,
                R.drawable.dog5,
                R.drawable.dog6,
                R.drawable.dog7,
                R.drawable.dog8,
                R.drawable.dog9,
            )
            ProductStyleContent(dogImages)
        },
        recommendationContent = { products ->
            ProductRow(products)
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

@Composable
fun ProductInfoContent() {
    val tabItems = listOf(
        ProductInfoTabItem(title = "시세"),
        ProductInfoTabItem(title = "사이즈"),
        ProductInfoTabItem(title = "스타일"),
        ProductInfoTabItem(title = "추천"),
    )
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.Black
                )
            },
            containerColor = Color.White,
        ) {
            tabItems.forEachIndexed { index, item ->
                Tab(
                    selected = index == selectedTabIndex,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = item.title,
                            fontSize = 10.sp,
                            color = Color.Black,
                        )
                    }
                )
            }
        }
    }

}

@Composable
fun ProductMarketPriceVariationContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "시세",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.size(6.dp))
        var currentTab by remember {
            mutableStateOf("체결 거래")
        }
        MarketPriceTabRow { tab ->
            currentTab = tab
        }

        when (currentTab) {
            "체결 거래" -> MarketPriceConcludingTabContent()
            "판매 입찰" -> SaleBiddingTabContent()
            else -> PurchaseBiddingTabContent()
        }

    }
}

@Composable
fun MarketPriceTabRow(
    onShowCurrentTabContent: @Composable (String) -> Unit
) {
    val titles = listOf("체결 거래", "판매 입찰", "구매 입찰")
    var tabIndex by remember { mutableIntStateOf(0) }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        RoundedIndicator(
            modifier = Modifier
                .roundedTabIndicatorOffset(tabPositions[tabIndex])
        )
    }
    Box {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = indicator,
            containerColor = Color.LightGray.copy(alpha = 0.3f),
            contentColor = Color.White,
            divider = {}, //we don't want any divider
            modifier = Modifier
                .padding(5.dp)
                .height(30.dp)
                .clip(
                    shape = RoundedCornerShape(14.dp)
                )
        ) {
            titles.forEachIndexed { index, title ->
                if (index == tabIndex) onShowCurrentTabContent(title)

                Tab(
                    text = {
                        Text(
                            text = title,
                            fontSize = 10.sp,
                            color = if (index == tabIndex) Color.Black else Color.DarkGray,
                            fontWeight = if (index == tabIndex) FontWeight.Bold else FontWeight.Normal,
                        )
                    },
                    selected = tabIndex == index,
                    interactionSource = DisabledInteractionSource(), //to disable ripple effect
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .padding(4.dp)
                        .zIndex(2f),
                    onClick = { tabIndex = index }
                )
            }
        }
    }
}

@Composable
fun MarketPriceConcludingTabContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "옵션",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Text(
                text = "거래가",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = "거래일",
                fontSize = 8.sp,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun SaleBiddingTabContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "옵션",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Text(
                text = "판매 희망가",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = "수량",
                fontSize = 8.sp,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun PurchaseBiddingTabContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "옵션",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Text(
                text = "구매 희망가",
                fontSize = 8.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = "수량",
                fontSize = 8.sp,
                color = Color.Black,
            )
        }
    }
}

@Composable
fun ProductSizeContent() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "사이즈 정보",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.size(8.dp))

        val tableData = listOf(
            listOf(
                "KR",
                "225",
                "230",
                "235",
                "240",
                "245",
                "250",
                "255",
                "260",
                "265",
                "270",
                "275",
                "280",
                "285",
                "290"
            ),
            listOf(
                "US (M)",
                "9.5",
                "10",
                "10.5",
                "11",
                "11.5",
                "12",
                "12.5",
                "13",
                "13.5",
                "14",
                "14.5",
                "15",
                "15.5",
                "16"
            ),
            listOf(
                "US (W)",
                "11",
                "11.5",
                "12",
                "12.5",
                "13",
                "13.5",
                "14",
                "14.5",
                "15",
                "15.5",
                "16",
                "16.5",
                "17",
                "17.5"
            ),
            listOf(
                "UK",
                "11",
                "11.5",
                "12",
                "12.5",
                "13",
                "13.5",
                "14",
                "14.5",
                "15",
                "15.5",
                "16",
                "16.5",
                "17",
                "17.5"
            ),
            listOf(
                "JP",
                "11",
                "11.5",
                "12",
                "12.5",
                "13",
                "13.5",
                "14",
                "14.5",
                "15",
                "15.5",
                "16",
                "16.5",
                "17",
                "17.5"
            ),
            listOf(
                "EU",
                "11",
                "11.5",
                "12",
                "12.5",
                "13",
                "13.5",
                "14",
                "14.5",
                "15",
                "15.5",
                "16",
                "16.5",
                "17",
                "17.5"
            ),
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            ScrollableTableWithFixedColumn(tableData)
        }
    }
}

@Composable
fun ScrollableTableWithFixedColumn(data: List<List<String>>) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)  // 내용에 맞춰 높이 조정
            .background(Color.White)
    ) {
        // 첫 번째 열 (고정)
        Column {
            data.forEach { row ->
                FirstColumnCell(text = row.first())
            }
        }

        // 나머지 열 (스크롤 가능)
        Row(
            modifier = Modifier.horizontalScroll(scrollState)
        ) {
            for (colIndex in 1 until data[0].size) {
                Column {
                    data.forEachIndexed { index, row ->
                        Cell(text = row[colIndex], index == 0)
                    }
                }
            }
        }
    }
}

@Composable
fun FirstColumnCell(
    text: String,
) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 40.dp)
            .background(Color.LightGray.copy(alpha = 0.1f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 8.sp,
            color = Color.Black,
        )
        HorizontalDivider(
            modifier = Modifier
                .height(0.5.dp)
                .align(Alignment.BottomEnd),
            color = Color.LightGray.copy(0.3f),
        )
    }
}

@Composable
fun Cell(
    text: String,
    isBackgroundColor: Boolean,
) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 40.dp)
            .background(if (isBackgroundColor) Color.LightGray.copy(alpha = 0.1f) else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 8.sp,
            color = Color.Black,
        )
        HorizontalDivider(
            modifier = Modifier
                .height(0.5.dp)
                .align(Alignment.BottomEnd),
            color = Color.LightGray.copy(0.3f),
        )
    }
}

@Composable
fun ProductStyleContent(
    images: List<Int>
) {
    CustomImageGrid(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 첫 번째 큰 이미지
        Image(
            painter = painterResource(id = images.first()),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 나머지 작은 이미지들
        images.forEachIndexed { index, image ->
            if (index != 0) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun CustomImageGrid(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        // 첫 번째 아이템은 전체 너비의 절반을 차지
        val firstItemWidth = constraints.maxWidth / 2
        val firstItemHeight = firstItemWidth * 1 // 2:1 비율

        // 나머지 아이템들은 전체 너비의 1/4을 차지
        val itemSize = constraints.maxWidth / 4

        val placeables = measurables.mapIndexed { index, measurable ->
            val itemConstraints = if (index == 0) {
                Constraints.fixed(firstItemWidth, firstItemHeight)
            } else {
                Constraints.fixed(itemSize, itemSize)
            }
            measurable.measure(itemConstraints)
        }

        // 레이아웃 높이 계산
        val height = firstItemHeight + (placeables.size - 1 + 3) / 4 * itemSize

        layout(constraints.maxWidth, height) {
            var yPosition = 0
            var xPosition = firstItemWidth

            placeables.forEachIndexed { index, placeable ->
                if (index == 0) {
                    placeable.place(0, 0)
                    yPosition = firstItemHeight
                } else {
                    placeable.place(xPosition, yPosition)
                    xPosition += itemSize
                    if (xPosition >= constraints.maxWidth) {
                        xPosition = 0
                        yPosition += itemSize
                    }
                }
            }
        }
    }
}

@Composable
fun ProductRecommendationContent() {

}

@Composable
fun ProductPurchaseOrSellBar() {

    Box(
        modifier = Modifier.padding(
            bottom = BottomAppBarDefaults.windowInsets.asPaddingValues().calculateBottomPadding()
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(color = Color.White)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_bookmark_border_24),
                    contentDescription = "북마크"
                )
                Text(
                    text = "21.6만",
                    fontSize = 12.sp,
                    color = Color.Black,
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = Color.Red
                            .darker()
                            .copy(0.6f)
                    )
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "구매",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.size(10.dp))
                VerticalDivider(
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.size(6.dp))
                // 아래의 방식으로 사용중인데 lineHeight는 특정 값을 줘야하기 때문에 해상도 맞추기 까다로우니 그냥
                // Column 만들고 가격과 문자를 Text로 만들어서 세로정렬시키는게 낫겠다. 근데 지금은 연습이니 여러가지로 해본다.
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            ParagraphStyle(
                                lineHeight = 10.sp,  // 이 값을 조정하여 원하는 줄 간격을 설정
                                lineBreak = LineBreak.Paragraph.copy(strictness = LineBreak.Strictness.Default),
                            )
                        ) {
                            withStyle(
                                SpanStyle(
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                append("119,000\n")
                            }
                            withStyle(
                                SpanStyle(
                                    fontSize = 8.sp,
                                    color = Color.LightGray
                                )
                            ) {
                                append("즉시 구매가")
                            }
                        }
                    })
            }

            Spacer(modifier = Modifier.size(6.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        color = Color.Green
                            .darker()
                            .copy(0.6f)
                    )
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    text = "판매",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.size(10.dp))
                VerticalDivider(
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.size(6.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        ) {
                            append("150,000\n")
                        }
                        withStyle(
                            SpanStyle(
                                fontSize = 8.sp,
                                color = Color.LightGray
                            )
                        ) {
                            append("즉시 판매가")
                        }
                    },
                    lineHeight = 10.sp
                )
            }
        }

    }

}

@Composable
fun ProductRow(products: List<Product>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        ProductItem(
            modifier = Modifier.weight(1f),
            product = products.first()
        )
        Spacer(modifier = Modifier.size(4.dp))
        ProductItem(
            modifier = Modifier.weight(1f),
            product = products.last()
        )
    }
}

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // 신발 영역
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.nike_air_force1_07_low_white_1), contentDescription = "신발"
            )
            Text(
                modifier = Modifier.align(alignment = Alignment.TopEnd),
                text = "거래 287",
                fontSize = 7.sp,
                color = Color.Black,
            )
        }

        Text(
            text = "Nike",
            fontSize = 8.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = product.englishName,
            fontSize = 8.sp,
            color = Color.Black,
            maxLines = 2,
        )
        Text(
            text = product.koreanName,
            fontSize = 8.sp,
            color = Color.LightGray,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = product.immediatePurchasePrice.toString(),
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
    }

}
