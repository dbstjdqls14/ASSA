package com.assa.assabackend.dto

import com.assa.assabackend.entity.Phone
import java.util.Date

data class UserRegisterPhoneRequest(
    val specId : Int,
    val name : String,
    val brandId : Int,
    val capacityId : Int,
    val color : String,
    val phoneImage : String, // url
){
    companion object {
        fun UserRegisterPhoneRequest.toEntity(): Phone =
            Phone(
                specId = this.specId,
                brandId = this.brandId,
                capacityId = this.capacityId,
                name = this.name,
                color = this.color,
                phoneImage = this.phoneImage
        )
    }
}
