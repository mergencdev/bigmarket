package com.mergenc.bigmarket.data.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun parseCSVFile(stream: InputStream): List<T>
}