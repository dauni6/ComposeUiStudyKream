@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)

package com.example.composeuistudykream.ui.product

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composeuistudykream.R
import com.example.composeuistudykream.model.Product
import com.example.composeuistudykream.ui.component.PullToRefreshLazyColumn
import kotlinx.coroutines.launch

@Composable
fun ProductScreen(viewModel: ProductViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            ProductTopBar()
        }
    ) { paddingValues ->
        var isRefreshing by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        Box(modifier = Modifier.fillMaxSize()) {
            ScrollableContent(
                paddingValues = paddingValues,
                items = viewModel.productStateFlow.collectAsState().value,
                isRefreshing = isRefreshing,
                onRefresh = {
                    scope.launch {
                        isRefreshing = true
                        viewModel.fetchProducts()
                        isRefreshing = false
                    }
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
        items = items,
        content = { product ->

        },
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    )
    ProductPager()
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
                .fillMaxWidth()
                .height(400.dp),
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
                .padding(start = 12.dp, end = 12.dp, bottom = 140.dp)
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
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
