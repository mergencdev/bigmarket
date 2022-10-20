package com.mergenc.bigmarket.screen.companylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mergenc.bigmarket.domain.model.CompanyList
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Preview
@Composable
fun ComposableListScreenPreview() {
    // make string list
    CompanyListScreen()
}

@Composable
@Destination(start = true)
fun CompanyListScreen(
    //navigator: DestinationsNavigator,
    viewModel: CompanyListViewModel = hiltViewModel()
) {
    val refreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)
    val state = viewModel.state

    // Search column;
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(
                    CompanyListEvent.OnSearchQueryChange(it)
                )
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search")
            },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                viewModel.onEvent(CompanyListEvent.Refresh)
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.companies.size) { index ->
                    if (index < state.companies.size) {
                        val company = state.companies[index]
                        CompanyItem(
                            company = company,
                            modifier = Modifier.padding(8.dp)
                        )
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Navigate to company detail screen;
                                }
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}