package com.dataentrykotlinspringbootstack.frontend.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dataentrykotlinspringbootstack.frontend.controllers.EntryController

@Composable
fun EntryScreen(
    controller: EntryController,
    onLogout: () -> Unit
) {
    val state by controller.state.collectAsStateWithLifecycle()
    val logoutInteraction = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Entries", color = Color.Black, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(
                "Logout",
                color = Color.Black,
                fontSize = 16.sp,
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .clickable(
                        interactionSource = logoutInteraction,
                        indication = null,
                        onClick = onLogout
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                BwTextField(
                    value = state.input,
                    onValueChange = controller::onInputChange,
                    placeholder = "New entry",
                    enabled = !state.submitting
                )
            }
            Box(modifier = Modifier.width(90.dp)) {
                BwButton(
                    label = if (state.submitting) "..." else "Add",
                    enabled = !state.submitting && state.input.isNotBlank(),
                    onClick = controller::submit
                )
            }
        }

        if (state.error != null) {
            Text(state.error!!, color = Color.Black, fontSize = 14.sp)
        }

        if (state.loading) {
            Text("Loading...", color = Color.Black, fontSize = 14.sp)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.entries) { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(entry.content, color = Color.Black, fontSize = 16.sp)
                    Text("id=${entry.id}", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}