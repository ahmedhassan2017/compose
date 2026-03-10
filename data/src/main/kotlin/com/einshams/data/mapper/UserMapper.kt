package com.einshams.data.mapper

import com.einshams.data.remote.dto.UserDto
import com.einshams.data.remote.dto.DummyLoginResponseDto
import com.einshams.domain.model.User

fun DummyLoginResponseDto.toUserDto(): UserDto {
    return UserDto(
        remoteId = id.toString(),
        displayName = "$firstName $lastName",
        emailAddress = email
    )
}

fun UserDto.toDomain(): User {
    return User(
        id = remoteId,
        name = displayName,
        email = emailAddress
    )
}
