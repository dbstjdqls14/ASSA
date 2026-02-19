package com.assa.assabackend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "price_aggregate")
data class PriceAggregate(

    @Id
    @Column(name = "phone_id")
    val phoneId: Int,

    @Column(name = "source", nullable = false, length = 20)
    val source: String, // "BUNJANG"

    @Column(name = "as_of", nullable = false)
    val asOf: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "sample_count", nullable = false)
    val sampleCount: Int,

    @Column(name = "median_price", nullable = false)
    val medianPrice: Int,

    @Column(name = "p25_price", nullable = false)
    val p25Price: Int,

    @Column(name = "p75_price", nullable = false)
    val p75Price: Int,

    @Column(name = "min_price", nullable = false)
    val minPrice: Int,

    @Column(name = "max_price", nullable = false)
    val maxPrice: Int,

    @Column(name = "reliability", nullable = false)
    val reliability: Short = 50
)
