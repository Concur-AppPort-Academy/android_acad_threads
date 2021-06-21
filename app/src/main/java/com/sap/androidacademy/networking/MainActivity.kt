package com.sap.androidacademy.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var volleyQueue: RequestQueue

    companion object {
        val BASE_URL = "https://simple-server.cfapps.eu10.hana.ondemand.com/"
        val RAW_ENDPOINT = "raw"
        val JSON_ENDPOINT = "json"
        val ATTACHMENT_ENDPOINT = "file"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volleyQueue = Volley.newRequestQueue(this)

        findViewById<Button>(R.id.retrieve_data_btn).setOnClickListener {
            retrieveData()
        }


    }

    fun retrieveData() {
    }
}