package com.mergenc.bigmarket.data.mapper

import com.mergenc.bigmarket.data.remote.dto.IntradayInfoDto
import com.mergenc.bigmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val datePattern = "yyyy-MM-dd HH:mm:ss"
    val dateFormatter = DateTimeFormatter.ofPattern(datePattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, dateFormatter)

    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}