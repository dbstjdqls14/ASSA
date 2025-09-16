package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "brand")
data class Brand(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id", nullable = false)
    val brandId: Int,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    val phone: Phone? = null

)
