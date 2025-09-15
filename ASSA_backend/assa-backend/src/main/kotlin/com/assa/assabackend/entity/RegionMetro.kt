package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "region_metro")
data class RegionMetro(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_metro_id")
    val regionMetroId: Long = 0,
    @Column(name="name", length = 50, nullable = false)
    val name: String? = null,
    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),
)