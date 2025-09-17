package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "capacity")
data class Capacity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capacity_id", nullable = false)
    val capacityId: Long,

    @Column(name = "size",nullable = false, length = 50)
    val size: String,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

)
