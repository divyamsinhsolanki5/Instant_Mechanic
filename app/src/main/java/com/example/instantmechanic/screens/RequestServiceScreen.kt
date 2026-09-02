package com.example.instantmechanic.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.instantmechanic.model.Mechanic
import com.example.instantmechanic.model.ServiceRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestServiceScreen(
    mechanic: Mechanic,
    isSubmitted: Boolean,
    onBackClick: () -> Unit,
    onSubmit: (ServiceRequest) -> Unit,
    onDone: () -> Unit
) {
    var customerName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var vehicleNumber by remember { mutableStateOf("") }
    var selectedService by remember { mutableStateOf(mechanic.services.firstOrNull() ?: "") }
    var problemDescription by remember { mutableStateOf("") }
    var expandedDropdown by remember { mutableStateOf(false) }

    // ૩. Form Validation Checks
    val isNameValid = customerName.trim().length >= 2
    val isPhoneValid = phoneNumber.matches(Regex("^[6-9]\\d{9}$"))
    val isVehicleValid = vehicleNumber.trim().isNotEmpty()
    val isFormValid = isNameValid && isPhoneValid && isVehicleValid

    if (isSubmitted) {
        AlertDialog(
            onDismissRequest = {},
            shape = RoundedCornerShape(20.dp),
            title = { Text("🎉 Request Sent!", fontWeight = FontWeight.Bold) },
            text = { Text("Your service request has been sent to ${mechanic.name}. They will call you shortly.") },
            confirmButton = {
                Button(
                    onClick = onDone,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00))
                ) { Text("Go to Home") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Service", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF7F8FA))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Text(
                        text = "Booking with: ${mechanic.name}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6D00)
                    )

                    // Full Name Field
                    OutlinedTextField(
                        value = customerName,
                        onValueChange = { customerName = it },
                        label = { Text("Full Name *") },
                        isError = customerName.isNotEmpty() && !isNameValid,
                        supportingText = {
                            if (customerName.isNotEmpty() && !isNameValid) {
                                Text("Name must be at least 2 characters", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Phone Number Field
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {
                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                phoneNumber = it
                            }
                        },
                        label = { Text("Phone Number (10 digits) *") },
                        isError = phoneNumber.isNotEmpty() && !isPhoneValid,
                        supportingText = {
                            if (phoneNumber.isNotEmpty() && !isPhoneValid) {
                                Text("Enter a valid 10-digit mobile number", color = MaterialTheme.colorScheme.error)
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Vehicle Number Field
                    OutlinedTextField(
                        value = vehicleNumber,
                        onValueChange = { vehicleNumber = it.uppercase() },
                        label = { Text("Vehicle Number (e.g. GJ01AB1234) *") },
                        leadingIcon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Service Dropdown
                    ExposedDropdownMenuBox(
                        expanded = expandedDropdown,
                        onExpandedChange = { expandedDropdown = !expandedDropdown }
                    ) {
                        OutlinedTextField(
                            value = selectedService,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Select Service") },
                            leadingIcon = { Icon(Icons.Default.Build, contentDescription = null) },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdown) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedDropdown,
                            onDismissRequest = { expandedDropdown = false }
                        ) {
                            mechanic.services.forEach { service ->
                                DropdownMenuItem(
                                    text = { Text(service) },
                                    onClick = {
                                        selectedService = service
                                        expandedDropdown = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = problemDescription,
                        onValueChange = { problemDescription = it },
                        label = { Text("Describe the issue (optional)") },
                        leadingIcon = { Icon(Icons.Default.Description, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {
                            onSubmit(
                                ServiceRequest(
                                    customerName = customerName,
                                    phoneNumber = phoneNumber,
                                    vehicleNumber = vehicleNumber,
                                    selectedService = selectedService,
                                    problemDescription = problemDescription,
                                    mechanicId = mechanic.id,
                                    mechanicName = mechanic.name
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00)),
                        enabled = isFormValid
                    ) {
                        Text("Confirm & Book Request", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}