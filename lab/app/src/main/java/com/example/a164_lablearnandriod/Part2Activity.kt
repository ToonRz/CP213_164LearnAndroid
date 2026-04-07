package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {
    var contactList = mutableStateListOf<String>()
        private set
    
    var isLoading by mutableStateOf(false)
        private set

    init {
        loadMore()
    }

    fun loadMore() {
        if (isLoading) return
        isLoading = true
        // Mock data A-Z
        val newItems = ('A'..'Z').map { char -> "Contact $char - ${contactList.size / 26 + 1}" }
        
        // Simulate delay
        viewModelScope.launch {
            delay(2000)
            contactList.addAll(newItems)
            isLoading = false
        }
    }
}

class Part2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _164_LabLearnAndriodTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactListScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(modifier: Modifier = Modifier, viewModel: ContactViewModel = viewModel()) {
    val listState = rememberLazyListState()
    val contacts = viewModel.contactList
    val isLoading = viewModel.isLoading

    // Detect when scrolling to the end
    val endOfListReached by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem?.index != 0 && lastVisibleItem?.index == listState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && !isLoading) {
            viewModel.loadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize()
    ) {
        // Group by first letter for Sticky Headers
        val grouped = contacts.groupBy { it.first() }

        grouped.forEach { (initial, contactsUnderInitial) ->
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(8.dp)
                ) {
                    Text(
                        text = initial.toString(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            items(contactsUnderInitial) { contact ->
                ListItem(
                    headlineContent = { Text(contact) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
