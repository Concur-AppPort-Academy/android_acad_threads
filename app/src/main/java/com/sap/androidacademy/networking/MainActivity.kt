package com.sap.androidacademy.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG = "VolleyActivity"
        val BASE_URL = "https://simple-server.cfapps.eu10.hana.ondemand.com/"
        val RAW_ENDPOINT = "raw"
        val JSON_ENDPOINT = "json"
        val ATTACHMENT_ENDPOINT = "file"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.retrieve_data_btn).setOnClickListener {
            retrieveData()
        }


    }

    private fun retrieveData() {
        onNetworkActivityStart()
        findViewById<TextView>(R.id.dataView).postDelayed({
            findViewById<TextView>(R.id.dataView).text = "DUMMY_DATA"
            onNetworkActivityEnd(false)
        }, 1500)

    }

    private fun onNetworkActivityStart() {
        findViewById<View>(R.id.error_view).visibility = View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
        findViewById<Button>(R.id.retrieve_data_btn).isClickable = false

    }

    private fun onNetworkActivityEnd(isError: Boolean) {
        findViewById<View>(R.id.error_view).visibility = if(isError) View.VISIBLE else View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.GONE
        findViewById<Button>(R.id.retrieve_data_btn).isClickable = true
    }
}