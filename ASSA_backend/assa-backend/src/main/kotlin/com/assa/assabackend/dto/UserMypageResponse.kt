package com.assa.assabackend.dto

import com.assa.assabackend.entity.AppUser
import com.assa.assabackend.entity.Phone
import com.assa.assabackend.entity.RegionDistrict
import com.assa.assabackend.entity.RegionMetro

data class UserMypageResponse(
    val userId: Long,
    val name: String,
    val email: String,
    val region: UserRegion,
    val phone: Phone?,
    val profilePath: String?
) {
    companion object {
        fun from(user: AppUser): UserMypageResponse {
            return UserMypageResponse(
                userId = user.userId,
                name = user.name,
                email = user.email,
                region = UserRegion(
                    regionMetroId = user.regionMetro?.regionMetroId,
                    regionDistrictId = user.regionDistrict?.regionDistrictId
                ),
                phone = user.phone,
                profilePath = user.profilePath
            )
        }
    }
}
data class UserRegion(
    val regionMetroId: Long?,
    val regionDistrictId: Long?
)