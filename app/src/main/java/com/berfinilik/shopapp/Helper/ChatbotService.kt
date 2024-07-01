package com.berfinilik.shopapp.Helper

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.berfinilik.shopapp.R
import com.google.auth.oauth2.GoogleCredentials
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream

class ChatbotService(private val context: Context) {

    private val client = OkHttpClient()
    private val projectId = "shopapp-428018"
    private val sessionId = "YOUR_SESSION_ID"

    private inner class GetAccessTokenTask(private val callback: (String?) -> Unit) : AsyncTask<Void, Void, String?>() {
        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val inputStream: InputStream = context.resources.openRawResource(R.raw.service_account)
                val credentials = GoogleCredentials.fromStream(inputStream)
                    .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
                credentials.refreshIfExpired()
                credentials.accessToken.tokenValue
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("GetAccessTokenTask", "Failed to get access token: ${e.message}")
                null
            }
        }

        override fun onPostExecute(result: String?) {
            callback(result)
        }
    }

    fun sendMessageToDialogflow(message: String, callback: (String) -> Unit) {
        GetAccessTokenTask { accessToken ->
            if (accessToken == null) {
                callback("Failed to get access token.")
                return@GetAccessTokenTask
            }

            val url = "https://dialogflow.googleapis.com/v2/projects/$projectId/agent/sessions/$sessionId:detectIntent"
            val jsonBody = JSONObject()
            val queryInput = JSONObject()
            val text = JSONObject()
            text.put("text", message)
            text.put("languageCode", "en")
            queryInput.put("text", text)
            jsonBody.put("queryInput", queryInput)

            val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), jsonBody.toString())
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .addHeader("Authorization", "Bearer $accessToken")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    callback("Failed to connect to Dialogflow.")
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.let {
                        val jsonResponse = JSONObject(it.string())
                        val botResponse = if (jsonResponse.has("queryResult")) {
                            jsonResponse.getJSONObject("queryResult").getString("fulfillmentText")
                        } else {
                            "Failed to get response from Dialogflow"
                        }
                        callback(botResponse)
                    }
                }
            })
        }.execute()
    }
}