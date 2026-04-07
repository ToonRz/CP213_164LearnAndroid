package com.example.a164_lablearnandriod

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a164_lablearnandriod.ui.theme._164_LabLearnAndriodTheme
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SideEffectViewModel : ViewModel() {
    private val _errorFlow = MutableSharedFlow<String>()
    val errorFlow = _errorFlow.asSharedFlow()

    fun triggerError() {
        viewModelScope.launch {
            _errorFlow.emit("Something went wrong! (Error from ViewModel)")
        }
    }
}

class SideEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _164_LabLearnAndriodTheme {
                SnackbarSideEffectScreen()
            }
        }
    }
}

@Composable
fun SnackbarSideEffectScreen(viewModel: SideEffectViewModel = viewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Mission 5: Use LaunchedEffect to observe the error stream from ViewModel
    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Side Effects Tutorial (Mission 5)", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = { viewModel.triggerError() }) {
                Text("Trigger Error")
            }
        }
    }
}
