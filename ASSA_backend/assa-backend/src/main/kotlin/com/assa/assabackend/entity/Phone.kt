package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "phone")
data class Phone(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id", nullable = false)
    val phoneId: Int,

    @Column(name = "spec_id") // fk 1:1
    val specId: Int,

    @Column(name = "brand_id") // fk n:1
    val brandId: Int? = null,

    @Column(name = "capacity_id") // fk n:1
    val capacityId: Int? = null,

    @Column(name = "name", nullable = false)
    val name: String,

    @Column(name = "color")
    val color: String? = null,

    @Column(name = "phone_image") // 폰 사진 경로
    val phoneImage: String? = null,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

    // 종속자들
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spec_id", insertable = false, updatable = false)
    val spec: Spec? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", insertable = false, updatable = false)
    val brand: Brand? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "capacity_id", insertable = false, updatable = false)
    val capacity: Capacity? = null,


    // 피종속자들
    @OneToOne(mappedBy = "phone", fetch = FetchType.LAZY)
    val appUser: AppUser? = null
)

/**
 * spec_id
 * name v
 * brand_id
 * capacity_id
 * color
 * phone_image
 * created_time v
 */