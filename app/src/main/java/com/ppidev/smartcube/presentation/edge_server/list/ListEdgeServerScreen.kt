package com.ppidev.smartcube.presentation.edge_server.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ppidev.smartcube.ui.Screen

@Composable
fun ListEdgeServerScreen(
    state: ListEdgeServerState,
    event: (event: ListEdgeServerEvent) -> Unit,
    navHostController: NavHostController
) {

    LaunchedEffect(Unit) {
        event(ListEdgeServerEvent.GetListEdgeServer)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF6F6F6)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Cube Server")

            IconButton(onClick = {
                navHostController.navigate(Screen.FormAddEdgeServer.screenRoute)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add new edge server")
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(
                items = state.listEdgeServer,
                key = { _, d -> "${d.id}_${d.name}" }
            ) { _, item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Color(0xFFF6F6F6)
                        )
                        .padding(16.dp)
                ) {
                    Text(text = "${item.name}", fontSize = 20.sp)
                    Text(text = "${item.vendor}", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }
    }
}