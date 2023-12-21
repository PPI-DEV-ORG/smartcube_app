package com.ppidev.smartcube.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.data.remote.service.IMqttService
import com.ppidev.smartcube.contract.data.repository.ITokenAppRepository
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IInviteUserUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IJoinUserUseCase
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import com.ppidev.smartcube.contract.domain.use_case.user.IUserProfileUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileUseCase: Lazy<IUserProfileUseCase>,
    private val listEdgeServerUseCase: Lazy<IListEdgeServerUseCase>,
    private val inviteUserUseCase: Lazy<IInviteUserUseCase>,
    private val joinUserUseCase: Lazy<IJoinUserUseCase>,
    private val tokenRepository: Lazy<ITokenAppRepository>,
    private val mqttService: Lazy<IMqttService>
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set


    fun onEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                ProfileEvent.GetUserProfile -> getProfile()
                ProfileEvent.UpdateProfile -> {}
                is ProfileEvent.SetStatusOpenDialogInviteUser -> {
                    state = state.copy(
                        isOpenDialogInviteUser = event.status
                    )
                }

                is ProfileEvent.GetTokenInviteUser -> {
                    getInvitationCode(event.edgeServerId)
                }

                is ProfileEvent.OnChangeServerId -> {
                    state = state.copy(
                        selectedServerId = event.server.id,
                        selectedServerLabel = event.server.name
                    )
                }

                is ProfileEvent.SetDialogSuccessGetInvitationCode -> {
                    state = state.copy(
                        isOpenDialogSuccessGetInvitationCode = event.status
                    )
                }

                ProfileEvent.JoinUserGroup -> {
                    if (state.inputInvitationCode.isEmpty()) {
                        state = state.copy(
                            errorJoinUserGroup = "Please input invitation code!"
                        )
                        return@launch
                    }
                    joinUserGroup(state.inputInvitationCode)
                }

                is ProfileEvent.SetInputInvitationCode -> {
                    state = state.copy(
                        inputInvitationCode = event.str
                    )
                }

                is ProfileEvent.SetStatusOpenDialogJoinUser -> {
                    state = state.copy(
                        isOpenDialogJoinUserGroup = event.status
                    )
                }

                is ProfileEvent.Logout -> {
                    try {
                        val res = tokenRepository.get().removeAccessToken()
                        mqttService.get().disconnect()
                        event.callback(res)
                    } catch (e: Exception) {
                        event.callback(false)
                    }
                }

                is ProfileEvent.SetAlertLogoutStatus -> {
                    state = state.copy(
                        isShowAlertLogout = event.status
                    )
                }

                ProfileEvent.GetListServer -> {
                    getListEdgeServer()
                }
            }
        }
    }

    private suspend fun getProfile() {
        userProfileUseCase.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoading = false,
                        user = it.data?.data
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    state = state.copy(
                        listServer = it.data?.data ?: emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getInvitationCode(edgeServerId: UInt) {
        if (state.selectedServerId == null && state.selectedServerLabel.isEmpty()) {
            state = state.copy(
                errorSelectServer = "Please select the cluster"
            )
            return
        }

        inviteUserUseCase.get().invoke(edgeServerId).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        errorSelectServer = it.message.toString(),
                        isLoadingGetInvitationCode = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        errorSelectServer = "",
                        isLoadingGetInvitationCode = true
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        errorSelectServer = "",
                        isLoadingGetInvitationCode = false,
                        invitationCode = it.data?.data?.invitationCode.toString(),
                        isOpenDialogSuccessGetInvitationCode = true,
                        isOpenDialogInviteUser = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun joinUserGroup(invitationCode: String) {
        joinUserUseCase.get().invoke(invitationCode).onEach {
            when (it) {
                is Resource.Error -> {
                    state = state.copy(
                        errorJoinUserGroup = it.message.toString(),
                        isLoadingJoinUserGroup = false
                    )
                }

                is Resource.Loading -> {
                    state = state.copy(
                        errorJoinUserGroup = "",
                        isLoadingJoinUserGroup = true
                    )
                }

                is Resource.Success -> {
                    state = state.copy(
                        isLoadingJoinUserGroup = false,
                        isOpenDialogJoinUserGroup = false,
                        inputInvitationCode = "",
                        errorJoinUserGroup = ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}