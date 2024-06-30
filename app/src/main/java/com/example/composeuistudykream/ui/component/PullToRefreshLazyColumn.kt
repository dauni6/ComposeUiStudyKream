package com.example.composeuistudykream.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun <T> PullToRefreshLazyColumn(
    modifier: Modifier = Modifier,
    items: List<T>,
    pagerContent: @Composable () -> Unit,
    priceContent: @Composable () -> Unit,
    additionalBenefitContent: @Composable () -> Unit,
    deliveryInfoContent: @Composable () -> Unit,
    productInfoRowTabContent: @Composable () -> Unit,
    content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val pullToRefreshState = rememberPullToRefreshState()
    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
        ) {
            item { pagerContent() }
            item { priceContent() }
            item { additionalBenefitContent() }
            item { deliveryInfoContent() }
            stickyHeader { productInfoRowTabContent() }
            items(items) {
                content(it)
            }
            // 아래 5개의 아이템은 테스트를 위해 넣었음(삭제할 것!)
            item { deliveryInfoContent() }
            item { deliveryInfoContent() }
            item { deliveryInfoContent() }
            item { deliveryInfoContent() }
            item { deliveryInfoContent() }
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter),
            state = pullToRefreshState
        )
    }
}