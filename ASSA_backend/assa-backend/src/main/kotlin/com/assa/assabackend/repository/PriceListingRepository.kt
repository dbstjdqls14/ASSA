package com.assa.assabackend.repository

import com.assa.assabackend.entity.PriceListing
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceListingRepository : JpaRepository<PriceListing, Int>
