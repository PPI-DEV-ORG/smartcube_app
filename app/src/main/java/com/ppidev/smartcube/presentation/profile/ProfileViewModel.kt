package com.ppidev.smartcube.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
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
    private val userProfileUseCase: Lazy<IUserProfileUseCase>
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getProfile()
        }
    }

    fun onEvent(event: ProfileEvent) {
        viewModelScope.launch {
            when (event) {
                ProfileEvent.GetUserProfile -> getProfile()
                ProfileEvent.UpdateProfile -> {}
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
}