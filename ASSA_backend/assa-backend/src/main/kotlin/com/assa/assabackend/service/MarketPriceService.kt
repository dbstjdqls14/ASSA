package com.assa.assabackend.service

import com.assa.assabackend.dto.MarketPriceResponse
import com.assa.assabackend.entity.CollectJob
import com.assa.assabackend.repository.CollectJobRepository
import com.assa.assabackend.repository.PriceAggregateRepository
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.OffsetDateTime

@Service
class MarketPriceService(
    private val aggregateRepository: PriceAggregateRepository,
    private val jobRepository: CollectJobRepository
) {
    private val SOURCE = "BUNJANG"
    private val STALE_HOURS = 12L

    fun getMarketPrice(phoneId: Int): MarketPriceResponse{
        val agg = aggregateRepository.findById(phoneId).orElse(null)

        val now = OffsetDateTime.now()
        val isFresh = agg != null && Duration.between(agg.asOf, now).toHours() < STALE_HOURS

        if(isFresh){
            return MarketPriceResponse(
                phoneId = phoneId,
                source = agg!!.source,
                status = "READY",
                asOf = agg.asOf,
                sampleCount = agg.sampleCount,
                medianPrice = agg.medianPrice,
                p25Price = agg.p25Price,
                p75Price = agg.p75Price,
                minPrice = agg.minPrice,
                maxPrice = agg.maxPrice,
                reliability = agg.reliability
            )
        }

        val active = jobRepository.findLatestActiveJob(phoneId, SOURCE)
        if(active.isEmpty()){
            jobRepository.save(CollectJob(phoneId = phoneId, source = SOURCE))
        }

        return if (agg != null) {
            MarketPriceResponse(
                phoneId = phoneId,
                source = agg.source,
                status = "COLLECTING",
                asOf = agg.asOf,
                sampleCount = agg.sampleCount,
                medianPrice = agg.medianPrice,
                p25Price = agg.p25Price,
                p75Price = agg.p75Price,
                minPrice = agg.minPrice,
                maxPrice = agg.maxPrice,
                reliability = agg.reliability
            )
        } else {
            MarketPriceResponse(
                phoneId = phoneId,
                source = SOURCE,
                status = "COLLECTING"
            )
        }
    }
}