package com.raywenderlich.android.drinkit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

    private val messageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            text_view_notification.text = intent?.extras?.getString("message")
        }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    button_retrieve_token.setOnClickListener {
        if (checkGooglePlayServices()){
            FirebaseMessaging.getInstance().token
                    .addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            Log.w(TAG, "getInstanceId failed", task.exception)
                            return@OnCompleteListener
                        }

                        val token = task.result

                        val msg = getString(R.string.token_prefix, token)

                        Log.d(TAG, msg)
                        Toast.makeText(baseContext, msg, Toast.LENGTH_LONG).show()
                    })
        } else {
            Log.w(TAG, "Device doesn't have Google Play Services")
            Toast.makeText(baseContext, "Please install or update Google Play Services",
                    Toast.LENGTH_LONG).show()
        }

    }

      val bundle = intent.extras
      val stringExtra: String? = bundle?.getString("text")
      if (!stringExtra.isNullOrEmpty()) {
          text_view_notification.text = stringExtra
      }
  }


  override fun onStart() {
    super.onStart()
    LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
    IntentFilter("MyData"))
  }

  override fun onStop() {
    super.onStop()
    LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver)
  }

  // TODO: Add a method for receiving notifications

private fun checkGooglePlayServices(): Boolean {
    val status = GoogleApiAvailabilityLight.getInstance()
            .isGooglePlayServicesAvailable(this)
    return if (status != ConnectionResult.SUCCESS) {
        Log.e(TAG, "Error")
//        Ask user to update Google play services and manage the error.
        Toast.makeText(baseContext, "Pleease Update Your Google Play Services",
                Toast.LENGTH_LONG).show()
        false
    } else {
        Log.i(TAG, "Google Play Services Updated")

        true
    }
}

  // TODO: Create a message receiver constant

  companion object {
    private const val TAG = "MainActivity"
  }
}