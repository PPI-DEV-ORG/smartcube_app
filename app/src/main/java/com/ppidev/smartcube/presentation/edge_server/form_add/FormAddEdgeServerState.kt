package com.ppidev.smartcube.presentation.edge_server.form_add

data class FormAddEdgeServerState(
    val test: String,

) {
    data class EdgeServerItem(
        val serverName: String,
        val description: String,
        val serverVendor: String
    )
}