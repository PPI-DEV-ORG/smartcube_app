package com.ppidev.smartcube.presentation.edge_server.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ppidev.smartcube.common.Resource
import com.ppidev.smartcube.contract.domain.use_case.edge_server.IListEdgeServerUseCase
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEdgeServerViewModel @Inject constructor(
    private val listEdgeServerUseCase: Lazy<IListEdgeServerUseCase>
): ViewModel() {
    var state by mutableStateOf(ListEdgeServerState())
        private set

    fun onEvent(event: ListEdgeServerEvent){
        viewModelScope.launch {
            when(event) {
                ListEdgeServerEvent.GetListEdgeServer -> {
                    getListEdgeServer()
                }

                ListEdgeServerEvent.OnRefresh -> {
                    getListEdgeServer()
                }
            }
        }
    }

    private fun getListEdgeServer() {
        listEdgeServerUseCase.get().invoke().onEach {
            when(it){
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
                        listEdgeServer = it.data?.data ?: emptyList(),
                        isLoading = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}