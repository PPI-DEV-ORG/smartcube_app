package com.ppidev.smartcube.presentation.profile

import com.ppidev.smartcube.data.remote.dto.EdgeServerItemDto
import com.ppidev.smartcube.data.remote.dto.UserDto

data class ProfileState(
    val isLoading: Boolean = false,
    val isOpenDialogInviteUser: Boolean = false,
    val isOpenDialogSuccessGetInvitationCode: Boolean = false,
    val isLoadingGetInvitationCode: Boolean = false,
    val invitationCode: String = "",
    val errorSelectServer: String = "",

    val isOpenDialogJoinUserGroup: Boolean = false,
    val inputInvitationCode: String = "",
    val errorJoinUserGroup: String = "",
    val isLoadingJoinUserGroup: Boolean = false,

    val user: UserDto? = null,
    val listServer: List<EdgeServerItemDto> = emptyList(),
    val selectedServerId: UInt? = null,
    val selectedServerLabel: String = "",
)