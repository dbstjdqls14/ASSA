package com.assa.assabackend.repository

import com.assa.assabackend.entity.Phone
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhoneRepository : JpaRepository<Phone, Long>
{
}