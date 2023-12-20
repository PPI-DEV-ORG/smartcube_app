package com.ppidev.smartcube.presentation.profile

import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto

sealed class ProfileEvent {
    object GetUserProfile: ProfileEvent()
    object UpdateProfile: ProfileEvent()
    object JoinUserGroup: ProfileEvent()
    data class SetAlertLogoutStatus(val status: Boolean): ProfileEvent()
    data class Logout(val callback: (status: Boolean) -> Unit): ProfileEvent()
    data class SetInputInvitationCode(val str: String): ProfileEvent()
    data class SetStatusOpenDialogJoinUser(val status: Boolean): ProfileEvent()
    data class GetTokenInviteUser(val edgeServerId: UInt): ProfileEvent()
    data class SetDialogSuccessGetInvitationCode(val status: Boolean): ProfileEvent()
    data class OnChangeServerId(val server: EdgeServerItemDto): ProfileEvent()
    data class SetStatusOpenDialogInviteUser(val status: Boolean): ProfileEvent()
}