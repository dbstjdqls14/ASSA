package com.assa.assabackend.repository

import com.assa.assabackend.entity.PriceAggregate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PriceAggregateRepository : JpaRepository<PriceAggregate, Int>