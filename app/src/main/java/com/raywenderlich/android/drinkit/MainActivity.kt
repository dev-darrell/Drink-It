package com.raywenderlich.android.drinkit

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailabilityLight
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingRegistrar
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.android.synthetic.main.activity_main.*


// TODO: import libraries

/**
 * Main Screen
 */
class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // TODO: create OnClickListener for the button_retrieve_token
    button_retrieve_token.setOnClickListener {
        if (checkGooglePlayServices()){
            
        } else {
            Log.w(TAG, "Device doesn't have Google Play Services")
        }
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
    }

    // TODO: check in bundle extras for notification data
  }


  override fun onStart() {
    super.onStart()
    //TODO: Register the receiver for notifications
  }

  override fun onStop() {
    super.onStop()
    // TODO: Unregister the receiver for notifications
  }

  // TODO: Add a method for receiving notifications

private fun checkGooglePlayServices(): Boolean {
    val status = GoogleApiAvailabilityLight.getInstance()
            .isGooglePlayServicesAvailable(this)
    return if (status != ConnectionResult.SUCCESS) {
        Log.e(TAG, "Error")
//        Ask user to update Google play services and manage the error.
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