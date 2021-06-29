package com.example.healthapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() , SensorEventListener{
    var running = false
    var sensorManager:SensorManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor =  sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) //SAVES YOUR APP FROM CRASHING
        if (stepsSensor ==null){
            Toast.makeText(this,"No step counter sensor",Toast.LENGTH_SHORT).show()
        }else{
            sensorManager?.registerListener(this,stepsSensor,SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {   //app is running on the background
        super.onPause()
        running= false
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {  //update the ui ,r.id is the resource id
        if(running){
            val  steps : TextView= findViewById(R.id.stepsValue)
            if (event !=null) {

                steps.text = "" + event.values[0]
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("Not yet implemented")
    }
}