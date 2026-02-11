package com.assa.assabackend.dto

import com.assa.assabackend.entity.AppUser

data class UserProfileResponse(
    val profilePath: String?,
) {
companion object{
    fun from(user: AppUser): UserProfileResponse{
        return UserProfileResponse(
            profilePath = user.profilePath
        )
    }
}
}