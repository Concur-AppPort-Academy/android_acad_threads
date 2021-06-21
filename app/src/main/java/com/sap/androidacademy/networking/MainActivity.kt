package com.sap.androidacademy.networking

import android.net.ConnectivityManager
import android.net.Network
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {

    lateinit var volleyQueue: RequestQueue

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
        volleyQueue = Volley.newRequestQueue(this)

        findViewById<Button>(R.id.retrieve_data_btn).setOnClickListener {
            retrieveData()
        }

        getSystemService(ConnectivityManager::class.java).let {
            it.registerDefaultNetworkCallback(object: ConnectivityManager.NetworkCallback(){

            override fun onAvailable(network: Network) {
                findViewById<View>(R.id.no_network).post {
                    findViewById<View>(R.id.no_network).visibility = View.GONE
                    toggleNetworkButton(true)
                }
            }

            override fun onLost(network: Network) {
                findViewById<View>(R.id.no_network).post {
                    findViewById<View>(R.id.no_network).visibility = View.VISIBLE
                    toggleNetworkButton(false)
                }
            }

        })
        }


    }

    private fun retrieveData() {
        onNetworkActivityStart()
        volleyQueue.add(
        JsonObjectRequest(
            Request.Method.GET, "$BASE_URL$JSON_ENDPOINT", null, Response.Listener {
                findViewById<TextView>(R.id.name_tv).text = it.optString("name", "")
                findViewById<TextView>(R.id.surname_tv).text = it.optString("surname", "")
                findViewById<TextView>(R.id.favorite_day).text = it.optString("favouriteDay", "")
                onNetworkActivityEnd(false)
            }, Response.ErrorListener {
                onNetworkActivityEnd(true)
            }
        ))
    }

    private fun onNetworkActivityStart() {
        findViewById<TextView>(R.id.dataView).text = ""
        findViewById<View>(R.id.error_view).visibility = View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.VISIBLE
        toggleNetworkButton(false)

    }

    private fun onNetworkActivityEnd(isError: Boolean) {
        findViewById<View>(R.id.error_view).visibility = if(isError) View.VISIBLE else View.GONE
        findViewById<View>(R.id.progress_bar).visibility = View.GONE
        toggleNetworkButton(true)
    }

    private fun toggleNetworkButton(enabled: Boolean) {
        findViewById<Button>(R.id.retrieve_data_btn).isClickable = enabled
    }
}