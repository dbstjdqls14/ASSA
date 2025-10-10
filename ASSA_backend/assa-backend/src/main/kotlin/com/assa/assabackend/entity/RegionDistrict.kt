package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "region_district")
data class RegionDistrict (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_district_id")
    val regionDistrictId: Long,

    @Column(name="region_metro_id", nullable = false)
    val regionMetroId: Long,

    @Column(name="name", length = 50, nullable = false)
    val name: String,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_metro_id", insertable = false, updatable = false)
    val regionMetro: RegionMetro? = null,

    @OneToMany(mappedBy = "regionDistrict", fetch = FetchType.LAZY)
    val appUsers: List<AppUser> = emptyList()


)