package com.mergenc.bigmarket.screen.companyinfo

import com.mergenc.bigmarket.domain.model.CompanyInfo
import com.mergenc.bigmarket.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
