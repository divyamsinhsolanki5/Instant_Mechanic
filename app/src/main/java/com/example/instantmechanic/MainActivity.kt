package com.example.instantmechanic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instantmechanic.model.Mechanic
import com.example.instantmechanic.screens.HomeScreen
import com.example.instantmechanic.screens.MechanicDetailsScreen
import com.example.instantmechanic.screens.RequestServiceScreen
import com.example.instantmechanic.ui.theme.InstantMechanicTheme
import com.example.instantmechanic.viewmodel.MechanicViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MechanicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InstantMechanicTheme {
                val navController = rememberNavController()
                val uiState by viewModel.uiState.collectAsState()
                val isSubmitted by viewModel.requestSubmitted.collectAsState()
                val bookings by viewModel.bookings.collectAsState()
                var selectedMechanic by remember { mutableStateOf<Mechanic?>(null) }

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(
                            uiState = uiState,
                            bookings = bookings,
                            onMechanicClick = { mechanic ->
                                selectedMechanic = mechanic
                                navController.navigate("details")
                            },
                            onCancelBooking = { booking ->
                                viewModel.cancelBooking(booking)
                            },
                            onRetry = { viewModel.fetchMechanics() }
                        )
                    }

                    composable("details") {
                        selectedMechanic?.let { mechanic ->
                            MechanicDetailsScreen(
                                mechanic = mechanic,
                                onBackClick = { navController.popBackStack() },
                                onRequestServiceClick = { navController.navigate("request") }
                            )
                        }
                    }

                    composable("request") {
                        selectedMechanic?.let { mechanic ->
                            RequestServiceScreen(
                                mechanic = mechanic,
                                isSubmitted = isSubmitted,
                                onBackClick = { navController.popBackStack() },
                                onSubmit = { request -> viewModel.submitRequest(request) },
                                onDone = {
                                    viewModel.resetSubmissionState()
                                    navController.popBackStack("home", inclusive = false)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
