package com.assa.assabackend.dto

import java.time.OffsetDateTime

data class MarketPriceResponse(
    val phoneId: Int,
    val source: String,
    val status: String, // READY | COLLECTING
    val asOf: OffsetDateTime? = null,
    val sampleCount: Int? = null,
    val medianPrice: Int? = null,
    val p25Price: Int? = null,
    val p75Price: Int? = null,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val reliability: Short? = null
)
