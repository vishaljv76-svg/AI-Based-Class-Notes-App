package com.example.Simtas.data.repository

import com.example.Simtas.data.remote.ApiClient
import com.example.Simtas.data.remote.SessionManager
import org.json.JSONObject

data class ProfileData(
    val userName: String,
    val userEmail: String,
    val userInitials: String,
    val courseText: String,
    val departmentText: String,
    val semesterBadgeText: String,
    val yearBadgeText: String,
    val hasProfile: Boolean
)

object ProfileRepository {

    suspend fun fetchProfile(): ProfileData? {
        val userId = SessionManager.getUserId()
        val response = ApiClient.get("profile/profile_fetch.php?user_id=$userId")

        if (response.isSuccess && response.jsonObject != null) {
            val json = response.jsonObject
            val user = json.optJSONObject("user")
            val profile = json.optJSONObject("profile")

            val name = user?.optString("name") ?: SessionManager.getUserName()
            val email = user?.optString("email") ?: SessionManager.getUserEmail()
            val initials = user?.optString("initials") ?: "AM"

            val course = profile?.optString("course") ?: "B.Tech · Computer Science"
            val dept = profile?.optString("department") ?: "Computer Science & Engineering"
            val semBadge = profile?.optString("semester_badge") ?: "Semester 7"
            val yearBadge = profile?.optString("year_badge") ?: "Final year"

            return ProfileData(
                userName = name,
                userEmail = email,
                userInitials = initials,
                courseText = course,
                departmentText = dept,
                semesterBadgeText = semBadge,
                yearBadgeText = yearBadge,
                hasProfile = json.optBoolean("has_profile", false)
            )
        }
        return null
    }

    suspend fun saveOrUpdateProfile(
        name: String,
        course: String,
        department: String,
        semester: String
    ): Boolean {
        val payload = JSONObject().apply {
            put("userId", SessionManager.getUserId())
            put("studentName", name)
            put("selectedCourse", course)
            put("selectedDepartment", department)
            put("selectedSemester", semester)
        }.toString()

        val response = ApiClient.post("profile/profile_edit.php", payload)
        if (response.isSuccess) {
            SessionManager.updateUserName(null, name)
            return true
        }
        return false
    }

    suspend fun addProfileOnboarding(
        course: String,
        department: String,
        semester: String,
        name: String? = null
    ): Boolean {
        val payload = JSONObject().apply {
            put("userId", SessionManager.getUserId())
            put("selectedCourse", course)
            put("selectedDepartment", department)
            put("selectedSemester", semester)
            if (!name.isNullOrEmpty()) {
                put("studentName", name)
            }
        }.toString()

        val response = ApiClient.post("profile/profile_add.php", payload)
        return response.isSuccess
    }
}

