package com.sap.androidacademy.networking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        val BASE_URL = "https://simple-server.cfapps.eu10.hana.ondemand.com/"
        val RAW_ENDPOINT = "raw"
        val JSON_ENDPOINT = "json"
        val ATTACHMENT_ENDPOINT = "file"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun retrieveData() {

    }
}