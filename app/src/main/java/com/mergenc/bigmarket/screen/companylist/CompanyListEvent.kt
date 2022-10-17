package com.mergenc.bigmarket.screen.companylist

sealed class CompanyListEvent {
    object Refresh : CompanyListEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListEvent()
}