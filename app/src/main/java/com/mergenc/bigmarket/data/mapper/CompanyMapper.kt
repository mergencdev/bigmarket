package com.mergenc.bigmarket.data.mapper

import com.mergenc.bigmarket.data.local.CompanyListEntity
import com.mergenc.bigmarket.data.remote.dto.CompanyInfoDto
import com.mergenc.bigmarket.domain.model.CompanyInfo
import com.mergenc.bigmarket.domain.model.CompanyList

fun CompanyListEntity.toCompanyList(): CompanyList {
    return CompanyList(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyList.toCompanyListEntity(): CompanyListEntity {
    return CompanyListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        name = name ?: "",
        symbol = symbol ?: "",
        description = description ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}