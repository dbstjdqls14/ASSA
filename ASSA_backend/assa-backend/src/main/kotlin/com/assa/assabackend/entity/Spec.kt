package com.assa.assabackend.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime

@Entity
@Table(name = "spec")
data class Spec(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spec_id", nullable = false)
    val specId: Int,

    @Column(name = "cpu", length =50)
    val cpu: String? = null,

    @Column(name = "ram", length =50)
    val ram: String? = null,

    @Column(name = "camera", length =50)
    val camera: String? = null,

    @Column(name = "battery", length =50)
    val battery: String? = null,

    @Column(name = "created_time", nullable = false)
    @CreationTimestamp
    val createdTime: LocalDateTime = LocalDateTime.now(),

    @OneToOne(mappedBy = "spec", fetch = FetchType.LAZY)
    val phone: Phone? = null
)

/**
 * spec_id v
 * cpu
 * ram
 * camera
 * battery
 * created_time v
 */
