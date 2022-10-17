package com.mergenc.bigmarket.data.csv

import com.mergenc.bigmarket.domain.model.CompanyList
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListParser @Inject constructor() : CSVParser<CompanyList> {

    override suspend fun parseCSVFile(stream: InputStream): List<CompanyList> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader.readAll().drop(1).mapNotNull { row ->
                val symbol = row.getOrNull(0)
                val name = row.getOrNull(1)
                val exchange = row.getOrNull(2)
                CompanyList(
                    name = name ?: return@mapNotNull null,
                    symbol = symbol ?: return@mapNotNull null,
                    exchange = exchange ?: return@mapNotNull null
                )
            }.also {
                csvReader.close()
            }
        }
    }
}