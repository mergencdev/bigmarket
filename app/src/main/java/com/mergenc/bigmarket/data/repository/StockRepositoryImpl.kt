package com.mergenc.bigmarket.data.repository

import com.mergenc.bigmarket.data.csv.CSVParser
import com.mergenc.bigmarket.data.csv.IntradayInfoParser
import com.mergenc.bigmarket.data.local.StockDatabase
import com.mergenc.bigmarket.data.mapper.toCompanyInfo
import com.mergenc.bigmarket.data.mapper.toCompanyList
import com.mergenc.bigmarket.data.mapper.toCompanyListEntity
import com.mergenc.bigmarket.data.remote.StockApi
import com.mergenc.bigmarket.domain.model.CompanyInfo
import com.mergenc.bigmarket.domain.model.CompanyList
import com.mergenc.bigmarket.domain.model.IntradayInfo
import com.mergenc.bigmarket.domain.repository.StockRepository
import com.mergenc.bigmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListParser: CSVParser<CompanyList>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {
    private val dao = db.dao

    override suspend fun getCompanyList(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyList>>> {
        return flow {
            emit(Resource.Loading(true))
            val localList = dao.searchCompanyList(query)
            emit(Resource.Success(
                data = localList.map { it.toCompanyList() }
            ))

            val isDbEmpty = localList.isEmpty() && query.isBlank()
            val loadFromCache = !isDbEmpty && !fetchFromRemote
            if (loadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteList = try {
                val response = api.getList()
                companyListParser.parseCSVFile(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Error"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: "Error"))
                null
            }

            remoteList?.let { list ->
                emit(Resource.Loading(isLoading = false))
                emit(Resource.Success(
                    data = dao.searchCompanyList("").map { it.toCompanyList() }
                ))
                dao.clearCompanyList()
                dao.insertCompanyList(
                    list.map { it.toCompanyListEntity() }
                )
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntraday(symbol)
            val results = intradayInfoParser.parseCSVFile(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error")
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Error")
        }
    }
}