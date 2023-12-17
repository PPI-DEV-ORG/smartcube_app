package com.ppidev.smartcube.presentation.profile

sealed class ProfileEvent {
    object GetUserProfile: ProfileEvent()
    object UpdateProfile: ProfileEvent()
}