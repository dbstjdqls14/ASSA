package com.assa.assabackend.entity

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(
    name = "price_listing",
    indexes = [
        Index(name = "ix_price_listing_phone_collected", columnList = "phone_id, collected"),
        Index(name = "ix_price_listing_phone_price", columnList = "phone_id, price"),

    ],
    uniqueConstraints = [
        UniqueConstraint(name = "uq_price_listing_source_url", columnNames = ["source","url"])
    ]
    )
data class PriceListing(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "phone_id", nullable = false)
    val phoneId: Int,

    @Column(name = "source", nullable = false, length = 20)
    val source: String, // "BUNJANG"

    @Column(name = "collected_at", nullable = false)
    val collectedAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "posted_at")
    val postedAt: OffsetDateTime? = null,

    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    val title: String,

    @Column(name = "price", nullable = false)
    val price: Int,

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    val url: String,

    @Column(name = "condition_tag", length = 50)
    val conditionTag: String? = null,

    @Column(name = "is_sold")
    val isSold: Boolean? = null,

    @Column(name = "extra_json", columnDefinition = "jsonb")
    val extraJson: String? = null
)
