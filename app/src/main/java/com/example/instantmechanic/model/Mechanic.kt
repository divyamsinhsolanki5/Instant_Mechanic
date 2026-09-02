package com.example.instantmechanic.model

data class Mechanic(
    val id: String,
    val name: String,
    val rating: Double,
    val distance: String,
    val location: String,
    val address: String,
    val services: List<String>,
    val workingHours: String,
    val phoneNumber: String,
    val isOpen: Boolean
)

data class ServiceRequest(
    val customerName: String,
    val phoneNumber: String,
    val vehicleNumber: String,
    val selectedService: String,
    val problemDescription: String,
    val mechanicId: String,
    val mechanicName: String = ""
)
