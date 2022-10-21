package com.mergenc.bigmarket.data.csv

import com.mergenc.bigmarket.data.mapper.toIntradayInfo
import com.mergenc.bigmarket.data.remote.dto.IntradayInfoDto
import com.mergenc.bigmarket.domain.model.CompanyList
import com.mergenc.bigmarket.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {

    override suspend fun parseCSVFile(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { row ->
                val timestamp = row.getOrNull(0) ?: return@mapNotNull null
                val close = row.getOrNull(4) ?: return@mapNotNull null
                val dto = IntradayInfoDto(timestamp, close.toDouble())
                dto.toIntradayInfo()
            }.filter {
                it.date.dayOfMonth == LocalDateTime.now().minusDays(1).dayOfMonth
            }.sortedBy {
                it.date.hour
            }.also {
                csvReader.close()
            }
        }
    }
}