package com.mergenc.bigmarket.domain.repository

import com.mergenc.bigmarket.domain.model.CompanyList
import com.mergenc.bigmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>>
}