package com.example.siddhakutumbkam.data.model

import java.util.Date

data class Retreat(
    val id: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val maxParticipants: Int,
    val currentParticipants: Int,
    val price: Double,
    val imageUrl: String,
    val isActive: Boolean = true,
    val requirements: List<String> = emptyList(),
    val schedule: List<RetreatScheduleItem> = emptyList()
)

data class RetreatScheduleItem(
    val time: String,
    val activity: String,
    val description: String
)

data class RetreatBooking(
    val id: String,
    val retreatId: String,
    val participantName: String,
    val participantEmail: String,
    val participantPhone: String,
    val emergencyContact: String,
    val emergencyPhone: String,
    val specialRequirements: String,
    val bookingDate: String,
    val status: BookingStatus = BookingStatus.PENDING
)

enum class BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}
