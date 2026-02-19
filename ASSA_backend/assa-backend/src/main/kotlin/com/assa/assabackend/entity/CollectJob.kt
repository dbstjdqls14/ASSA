package com.assa.assabackend.entity

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.OffsetDateTime

@Entity
@Table(name = "collect_job",
    indexes = [Index(name = "ix_collect_job_status_requested", columnList = "status, requested_at")])
data class CollectJob(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "phone_id", nullable = false)
    val phoneId: Int,

    @Column(name = "source", nullable = false, length = 20)
    val source: String, // "BUNJANG"

    @Column(name = "status", nullable = false, length = 20)
    val status: String = "PENDING", // default

    @Column(name = "requested_at", nullable = false)
    val requestedAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "started_at")
    val startedAt: OffsetDateTime? = null,

    @Column(name = "finished_at")
    val finishedAt: OffsetDateTime? = null,

    @Column(name = "meesage", columnDefinition = "TEXT")
    val meesage: String? = null

)
