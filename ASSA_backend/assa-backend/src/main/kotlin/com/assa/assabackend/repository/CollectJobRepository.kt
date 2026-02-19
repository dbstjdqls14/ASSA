package com.assa.assabackend.repository

import com.assa.assabackend.entity.CollectJob
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CollectJobRepository : JpaRepository<CollectJob, Long> {

    @Query("""
        select j from CollectJob j
        where j.phoneId = :phoneId and j.source = :source and j.status in ('PENDING','RUNNING')
        order by j.requestedAt desc
    """)
    fun findLatestActiveJob(
        @Param("phoneId") phoneId: Int,
        @Param("source") source: String,
    ): List<CollectJob>
}