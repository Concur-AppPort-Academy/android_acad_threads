package com.sap.androidacademy.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class MainActivity : AppCompatActivity() {

    // The volley queue, responsible for scheduling our network calls
    lateinit var volleyQueue: RequestQueue

    // Used to schedule calls manually, it is better to use a thread pool as creating a thread is expensive
    lateinit var executorService: ExecutorService

    companion object {
        val LOG_TAG = "VolleyActivity"

        // The server is included in the simple-server folder.
        val BASE_URL = "https://simple-server.cfapps.eu10.hana.ondemand.com/"
        val RAW_ENDPOINT = "raw"
        val JSON_ENDPOINT = "json"
        val ATTACHMENT_ENDPOINT = "file"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        volleyQueue = Volley.newRequestQueue(this)

        // Thread pool of one
        executorService = Executors.newFixedThreadPool(1)

        findViewById<Button>(R.id.retrieve_data_btn).setOnClickListener {
            retrieveData()
        }
    }

    override fun onStop() {
        super.onStop()

        // To prevent leaks, the executor is shutdown when this activity ends
        executorService.shutdown()
    }

    // Volley, calls the network on background threads, then calls the callbacks on the Main thread
    private fun volleyCall() {
        onNetworkActivityStart()
        val request = JsonObjectRequest(
            "$BASE_URL$JSON_ENDPOINT", null, Response.Listener {
                findViewById<TextView>(R.id.dataView).text = it.optString("name", "")
                onNetworkActivityEnd(false)
            }, Response.ErrorListener {
                onNetworkActivityEnd(true)

            }
        )
        volleyQueue.add(request)
    }


    // Using the connection with little framework forces us to handle threading ourselves
    private fun httpUrlConnectionCall() {
        executorService.submit {
            val connection = URL("$BASE_URL$RAW_ENDPOINT").openConnection()
            connection.getInputStream().use {
                // This is executed on the background thread, touching the UI here will cause an exception
                Log.d(LOG_TAG, String(it.readBytes()))
            }
        }
    }

    private fun retrieveData() {
        httpUrlConnectionCall()
        volleyCall()
    }

    // Shows the in progress spinner, resets the UI state
    private fun onNetworkActivityStart() {
        findViewById<TextView>(R.id.dataView).text = ""
        findViewById<View>(R.id.error_view).visibility = View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
        toggleNetworkButton(false)

    }

    // Stops showing network call spinner, shows error if the call has failed
    private fun onNetworkActivityEnd(isError: Boolean) {
        findViewById<View>(R.id.error_view).visibility = if(isError) View.VISIBLE else View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.GONE
        toggleNetworkButton(true)
    }

    // Disables the network button in to prevent multiple calls
    private fun toggleNetworkButton(enabled: Boolean) {
        findViewById<Button>(R.id.retrieve_data_btn).isClickable = enabled
    }
}