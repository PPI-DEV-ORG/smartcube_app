package com.ppidev.smartcube.presentation.edge_server.list


sealed class ListEdgeServerEvent {
    object GetListEdgeServer: ListEdgeServerEvent()
    object OnRefresh: ListEdgeServerEvent()
}