package com.ppidev.smartcube.presentation.edge_server.form_add

data class FormAddEdgeServerState(
    val serverName: String = "",
    val description: String = "",
    val serverVendor: String ="",
    val isSuccess: Boolean? = null,
    val message: String = "",
    val isLoading: Boolean = false,
    val error: ErrorFormAddEdgeServer = ErrorFormAddEdgeServer()
) {
    data class ErrorFormAddEdgeServer(
        val serverName: String = "",
        val description: String = "",
        val serverVendor: String = ""
    ) {
        fun hasErrors(): Boolean {
            return serverName.isNotEmpty() || description.isNotEmpty() || serverVendor.isNotEmpty()
        }
    }
}