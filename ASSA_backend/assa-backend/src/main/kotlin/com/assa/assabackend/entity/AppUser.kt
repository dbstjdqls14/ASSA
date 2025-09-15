package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime


// User.kt
@Entity
@Table(name = "app_user")
data class AppUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Long = 0,

    @Column(name = "region_metro_id")
    val regionMetroId: Int? = null,

    @Column(name = "region_district_id")
    val regionDistrictId: Int? = null,

    @Column(name = "name", length = 30, nullable = false)
    val name: String,

    @Column(name = "email", length = 60, nullable = false)
    val email: String,

    @Column(name = "phone_id")
    val phoneId: Int? = null,

    @Column(name = "profile_path", length = 300)
    val profilePath: String? = null,

    @Column(name = "is_deleted", nullable = false)
    val isDeleted: Boolean = false,

    @Column(name = "deleted_time")
    val deletedTime: LocalDateTime? = null,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

    // 연관관계 매핑 (필요한 경우)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_metro_id", insertable = false, updatable = false)
    val regionMetro: RegionMetro? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_district_id", insertable = false, updatable = false)
    val regionDistrict: RegionDistrict? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_id", insertable = false, updatable = false)
    val phone: Phone? = null
)
