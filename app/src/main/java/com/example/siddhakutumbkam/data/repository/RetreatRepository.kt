package com.example.siddhakutumbkam.data.repository

import com.example.siddhakutumbkam.data.model.Retreat
import com.example.siddhakutumbkam.data.model.RetreatBooking
import com.example.siddhakutumbkam.data.model.RetreatScheduleItem
import com.example.siddhakutumbkam.data.model.BookingStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

class RetreatRepository {
    
    // Mock data for demonstration - in real app, this would come from backend API
    private val mockRetreats = listOf(
        Retreat(
            id = "1",
            title = "Silent Meditation Retreat",
            description = "A 3-day silent meditation retreat focusing on inner peace and self-realization under the guidance of Swami Maitreyanandji.",
            startDate = "2025-02-15",
            endDate = "2025-02-17",
            location = "Rishikesh, Uttarakhand",
            maxParticipants = 30,
            currentParticipants = 12,
            price = 5000.0,
            imageUrl = "https://example.com/retreat1.jpg",
            requirements = listOf(
                "Comfortable meditation clothes",
                "Personal water bottle",
                "Yoga mat (optional - provided on site)"
            ),
            schedule = listOf(
                RetreatScheduleItem("05:30", "Morning Meditation", "Silent sitting meditation"),
                RetreatScheduleItem("07:00", "Yoga Practice", "Gentle hatha yoga"),
                RetreatScheduleItem("08:30", "Breakfast", "Sattvic vegetarian meal"),
                RetreatScheduleItem("10:00", "Discourse", "Spiritual teachings by Swamiji"),
                RetreatScheduleItem("12:00", "Lunch", "Silent meal"),
                RetreatScheduleItem("14:00", "Rest Period", "Personal reflection time"),
                RetreatScheduleItem("16:00", "Walking Meditation", "Mindful walking in nature"),
                RetreatScheduleItem("17:30", "Evening Meditation", "Group meditation session"),
                RetreatScheduleItem("19:00", "Dinner", "Light vegetarian meal"),
                RetreatScheduleItem("20:30", "Satsang", "Community sharing and chanting")
            )
        ),
        Retreat(
            id = "2",
            title = "Spiritual Awakening Workshop",
            description = "A transformative weekend workshop exploring the path of self-discovery and spiritual awakening.",
            startDate = "2025-03-08",
            endDate = "2025-03-09",
            location = "Haridwar, Uttarakhand",
            maxParticipants = 50,
            currentParticipants = 23,
            price = 3000.0,
            imageUrl = "https://example.com/retreat2.jpg",
            requirements = listOf(
                "Notebook and pen",
                "Comfortable clothing",
                "Open mind and heart"
            )
        ),
        Retreat(
            id = "3",
            title = "Advanced Meditation Intensive",
            description = "A 7-day intensive program for experienced practitioners seeking deeper meditation experiences.",
            startDate = "2025-04-20",
            endDate = "2025-04-26",
            location = "Dharamshala, Himachal Pradesh",
            maxParticipants = 20,
            currentParticipants = 8,
            price = 12000.0,
            imageUrl = "https://example.com/retreat3.jpg",
            requirements = listOf(
                "Minimum 1 year meditation experience",
                "Personal meditation cushion",
                "Commitment to silence"
            )
        )
    )
    
    private val bookings = mutableListOf<RetreatBooking>()
    
    suspend fun getUpcomingRetreats(): Result<List<Retreat>> = withContext(Dispatchers.IO) {
        try {
            // Simulate network delay
            delay(1000)
            Result.success(mockRetreats.filter { it.isActive })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getRetreatById(id: String): Result<Retreat?> = withContext(Dispatchers.IO) {
        try {
            delay(500)
            val retreat = mockRetreats.find { it.id == id }
            Result.success(retreat)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun bookRetreat(
        retreatId: String,
        participantName: String,
        participantEmail: String,
        participantPhone: String,
        emergencyContact: String,
        emergencyPhone: String,
        specialRequirements: String
    ): Result<RetreatBooking> = withContext(Dispatchers.IO) {
        try {
            delay(1500) // Simulate API call
            
            val booking = RetreatBooking(
                id = UUID.randomUUID().toString(),
                retreatId = retreatId,
                participantName = participantName,
                participantEmail = participantEmail,
                participantPhone = participantPhone,
                emergencyContact = emergencyContact,
                emergencyPhone = emergencyPhone,
                specialRequirements = specialRequirements,
                bookingDate = System.currentTimeMillis().toString(),
                status = BookingStatus.PENDING
            )
            
            bookings.add(booking)
            Result.success(booking)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun getUserBookings(userEmail: String): Result<List<RetreatBooking>> = withContext(Dispatchers.IO) {
        try {
            delay(800)
            val userBookings = bookings.filter { it.participantEmail == userEmail }
            Result.success(userBookings)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
