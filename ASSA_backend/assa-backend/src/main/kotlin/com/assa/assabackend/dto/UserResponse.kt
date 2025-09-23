package com.assa.assabackend.dto

import com.assa.assabackend.entity.AppUser
import java.sql.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime


data class UserResponse ( // ?를 붙이는건 Entity에서 null이 들어갈 수 있기 때문?
    val email: String,
    val region_metro_id: Long?,
    val region_district_id: Long?,
    val name: String,
    val phone_id: Long?,
    val profile_path: String?,
    val is_deleted: Boolean,
    val deleted_time: LocalDateTime?,
    val created_time: LocalDateTime,
) {
    companion object {
        fun from(user: AppUser): UserResponse {
            return UserResponse(
                email = user.email,
                region_metro_id = user.regionMetroId,
                region_district_id = user.regionDistrictId,
                name = user.name,
                phone_id = user.phoneId,
                is_deleted = user.isDeleted,
                profile_path = user.profilePath,
                deleted_time = user.deletedTime,
                created_time = user.createdTime
            )
        }
    }
}
/**
 * region_metro_id
 * region_district_id
 * name
 * email
 * phone_id
 * profile_path
 * is_deleted
 * deleted_time
 * created_time
 */