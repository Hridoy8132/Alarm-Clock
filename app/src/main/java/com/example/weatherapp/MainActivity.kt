package com.example.weatherapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import android.widget.ToggleButton
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    var alarmTimePicker :TimePicker? = null
    var pendingIntent : PendingIntent? = null
    var alarmManager : AlarmManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        alarmTimePicker = findViewById(R.id.timepicker)
        alarmManager = getSystemService (ALARM_SERVICE) as AlarmManager?
    }

    fun onToggleClicked(view: View) {

        var time: Long

        if ((view as ToggleButton).isChecked){

            Toast.makeText(this,"Alarm On",Toast.LENGTH_SHORT).show()

            var calender = Calendar.getInstance()

            calender[Calendar.HOUR_OF_DAY]= alarmTimePicker!!.currentHour
            calender[Calendar.MINUTE]= alarmTimePicker!!.currentHour

            val intent = Intent(this,alarm::class.java)

            pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

            time = calender.timeInMillis - calender.timeInMillis % 60000

            if (System.currentTimeMillis() > time){

                time = if (Calendar.AM_PM == 0) {

                    time +1000*60*60*12

                }

                else {

                    time + 1000 * 60 * 60*24
                }
                }

                alarmManager!!.setRepeating(AlarmManager.RTC_WAKEUP,time,1000,pendingIntent)


            }
             else
               {
                   alarmManager!!.cancel(pendingIntent)
                   Toast.makeText(this,"Alarm OFF",Toast.LENGTH_SHORT).show()

               }

        }
    }
