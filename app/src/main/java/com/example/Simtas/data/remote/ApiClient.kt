package com.example.Simtas.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

data class ApiResponse(
    val isSuccess: Boolean,
    val statusCode: Int,
    val jsonObject: JSONObject?,
    val errorMessage: String? = null
)

/**
 * Lightweight, zero-dependency HTTP client for Android communicating with PHP REST APIs.
 */
object ApiClient {

    private const val CONNECT_TIMEOUT_MS = 10000
    private const val READ_TIMEOUT_MS = 15000

    suspend fun get(path: String, token: String? = SessionManager.getToken()): ApiResponse =
        withContext(Dispatchers.IO) {
            executeRequest(method = "GET", path = path, jsonPayload = null, token = token)
        }

    suspend fun post(path: String, jsonPayload: String, token: String? = SessionManager.getToken()): ApiResponse =
        withContext(Dispatchers.IO) {
            executeRequest(method = "POST", path = path, jsonPayload = jsonPayload, token = token)
        }

    suspend fun delete(path: String, token: String? = SessionManager.getToken()): ApiResponse =
        withContext(Dispatchers.IO) {
            executeRequest(method = "DELETE", path = path, jsonPayload = null, token = token)
        }

    private fun executeRequest(
        method: String,
        path: String,
        jsonPayload: String?,
        token: String?
    ): ApiResponse {
        val fullUrl = if (path.startsWith("http://") || path.startsWith("https://")) {
            path
        } else {
            val base = ApiConfig.BASE_URL.trimEnd('/')
            val endpoint = path.trimStart('/')
            "$base/$endpoint"
        }

        var connection: HttpURLConnection? = null
        try {
            val url = URL(fullUrl)
            connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = method
            connection.connectTimeout = CONNECT_TIMEOUT_MS
            connection.readTimeout = READ_TIMEOUT_MS
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("ngrok-skip-browser-warning", "true")

            if (!token.isNullOrEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer $token")
            }

            if (jsonPayload != null && (method == "POST" || method == "PUT" || method == "PATCH")) {
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
                OutputStreamWriter(connection.outputStream, "UTF-8").use { writer ->
                    writer.write(jsonPayload)
                    writer.flush()
                }
            }

            val statusCode = connection.responseCode
            val inputStream = if (statusCode in 200..299) {
                connection.inputStream
            } else {
                connection.errorStream ?: connection.inputStream
            }

            val responseString = BufferedReader(InputStreamReader(inputStream, "UTF-8")).use { reader ->
                reader.readText()
            }

            val json = try {
                if (responseString.trim().startsWith("{")) {
                    JSONObject(responseString)
                } else null
            } catch (e: Exception) {
                null
            }

            val isSuccess = statusCode in 200..299 && (json?.optBoolean("success", true) ?: true)
            val message = if (json != null && json.has("message")) json.optString("message") else if (!isSuccess) "Server returned code $statusCode" else null

            return ApiResponse(
                isSuccess = isSuccess,
                statusCode = statusCode,
                jsonObject = json,
                errorMessage = if (!isSuccess) message else null
            )

        } catch (e: Exception) {
            return ApiResponse(
                isSuccess = false,
                statusCode = 0,
                jsonObject = null,
                errorMessage = "Network error: ${e.localizedMessage ?: "Unable to connect to server"}"
            )
        } finally {
            connection?.disconnect()
        }
    }
}
