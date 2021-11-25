package com.example.carworkshopbd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carworkshopbd.auth.AuthFragment
import com.example.carworkshopbd.main.MainFragment
import com.example.carworkshopbd.utils.SharedManager
import com.example.carworkshopbd.utils.addTo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openNextFragment()
    }

    private fun openNextFragment() {
        val sharedManager = SharedManager(this)
        if (sharedManager.isAuth) {
            supportFragmentManager.addTo(MainFragment())
        } else {
            supportFragmentManager.addTo(AuthFragment())
        }
    }
}