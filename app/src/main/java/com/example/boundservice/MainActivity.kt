package com.example.boundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.boundservice.service.MyBoundService
import com.example.boundservice.ui.theme.BoundServiceTheme

class MainActivity : ComponentActivity() {
    private var mBoundService: MyBoundService? = null
    private var mIsBound = false
    var count = 1


    //B2 connect:
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.LocalBinder
            mBoundService = binder.getService()
            mIsBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mIsBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoundServiceTheme {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        if (mIsBound) {
                            count++
                            val num: Int? = mBoundService?.getRandomNumber(count)
                            Toast.makeText(this, "number: $num", Toast.LENGTH_LONG).show()
                        }
                    }){
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        //B1: khoi tao service
        Intent(this, MyBoundService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mIsBound) {
            unbindService(serviceConnection)
            mIsBound = false
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BoundServiceTheme {
        Greeting("Android")
    }
}