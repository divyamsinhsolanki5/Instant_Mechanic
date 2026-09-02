package com.example.instantmechanic.data

import com.example.instantmechanic.model.Mechanic
import com.example.instantmechanic.model.ServiceRequest
import kotlinx.coroutines.delay

class MechanicRepository {

    private val bookedRequests = mutableListOf<ServiceRequest>()

    private val mockMechanics = listOf(
        Mechanic(
            id = "1",
            name = "Speedy Auto Care",
            rating = 4.8,
            distance = "1.5 km",
            location = "Main Highway",
            address = "Shop 12, Express Highway Circle",
            services = listOf("General Service", "Oil Change", "Brake Repair", "Tyre Puncture"),
            workingHours = "09:00 AM - 08:00 PM",
            phoneNumber = "+91 9876543210",
            isOpen = true
        ),
        Mechanic(
            id = "2",
            name = "Patel Motors & Garage",
            rating = 4.5,
            distance = "3.2 km",
            location = "Station Road",
            address = "Plot 4, Near Railway Station",
            services = listOf("Engine Diagnostics", "Battery Jumpstart", "AC Repair"),
            workingHours = "10:00 AM - 07:00 PM",
            phoneNumber = "+91 9823456781",
            isOpen = true
        ),
        Mechanic(
            id = "3",
            name = "Reliable Bike & Car Works",
            rating = 4.2,
            distance = "5.0 km",
            location = "GIDC Area",
            address = "Phase 2, GIDC Industrial Estate",
            services = listOf("Towing Service", "Full Body Wash", "Wheel Alignment"),
            workingHours = "08:30 AM - 09:00 PM",
            phoneNumber = "+91 9712345678",
            isOpen = false
        )
    )

    suspend fun getMechanics(): Result<List<Mechanic>> {
        delay(1200) // Shimmer ઇફેક્ટ જોવા માટે
        return Result.success(mockMechanics)
    }

    suspend fun submitServiceRequest(request: ServiceRequest): Result<Boolean> {
        delay(600)
        bookedRequests.add(0, request)
        return Result.success(true)
    }

    fun cancelServiceRequest(request: ServiceRequest) {
        bookedRequests.remove(request)
    }

    fun getBookedRequests(): List<ServiceRequest> {
        return bookedRequests.toList()
    }
}
