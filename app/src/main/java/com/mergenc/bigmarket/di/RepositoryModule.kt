package com.mergenc.bigmarket.di

import com.mergenc.bigmarket.data.csv.CSVParser
import com.mergenc.bigmarket.data.csv.CompanyListParser
import com.mergenc.bigmarket.data.csv.IntradayInfoParser
import com.mergenc.bigmarket.data.repository.StockRepositoryImpl
import com.mergenc.bigmarket.domain.model.CompanyList
import com.mergenc.bigmarket.domain.model.IntradayInfo
import com.mergenc.bigmarket.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCompanyListParser(
        companyListParser: CompanyListParser
    ): CSVParser<CompanyList>

    @Binds
    @Singleton
    abstract fun intradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}