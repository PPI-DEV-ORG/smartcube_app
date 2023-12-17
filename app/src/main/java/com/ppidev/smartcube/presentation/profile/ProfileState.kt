package com.ppidev.smartcube.presentation.profile

import com.ppidev.smartcube.data.remote.dto.UserDto

data class ProfileState(
    val isLoading: Boolean = false,
    val user: UserDto? = null
)