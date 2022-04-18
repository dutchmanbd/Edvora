package com.careers.edvora.data.mapper

import com.careers.edvora.data.remote.dto.UserDto
import com.careers.edvora.domain.model.User

fun UserDto.toUser() = User(
    name = name,
    stationCode = stationCode,
    url = url ?: ""
)

fun User.toUserDto() = UserDto(
    name = name,
    stationCode = stationCode,
    url = url
)