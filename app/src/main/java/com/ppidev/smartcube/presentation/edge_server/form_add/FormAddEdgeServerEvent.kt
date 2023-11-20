package com.ppidev.smartcube.presentation.edge_server.form_add

sealed class FormAddEdgeServerEvent {
    object HandleAddEdgeServer : FormAddEdgeServerEvent()

    data class OnNameEdgeServerChange(val name: String) : FormAddEdgeServerEvent()

    data class OnDescriptionEdgeServerChange(val desc: String) : FormAddEdgeServerEvent()

    data class OnVendorEdgeServerChange(val vendor: String) : FormAddEdgeServerEvent()

    data class HandleCloseDialog(val callback: () -> Unit) : FormAddEdgeServerEvent()
}