package com.assa.assabackend.service

import com.assa.assabackend.dto.UserRegisterPhoneRequest
import com.assa.assabackend.dto.UserRegisterPhoneRequest.Companion.toEntity
import com.assa.assabackend.repository.PhoneRepository
import org.springframework.stereotype.Service

@Service
class PhoneService(
    private val phoneRepository: PhoneRepository
) {
    fun postPhone(request : UserRegisterPhoneRequest){
        phoneRepository.save(request.toEntity())
    }
}