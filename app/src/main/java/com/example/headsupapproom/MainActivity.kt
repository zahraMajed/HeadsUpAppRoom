package com.example.headsupapproom

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    //step 2: Do your UI, declare and initialize them in your activity
    //my views
    lateinit var tvTimer: TextView

    lateinit var LL1: LinearLayout
    lateinit var btnStrat: Button
    lateinit var btnAdd: Button

    lateinit var LL2: LinearLayout
    lateinit var tvRotate: TextView
    lateinit var LL3: LinearLayout
    lateinit var tvName: TextView
    lateinit var tvT1: TextView
    lateinit var tvT2: TextView
    lateinit var tvT3: TextView

    //my variables
    var celeb=1

    //step 7: Managing Data
    //step 7 - 1) create an instance of the database.
    lateinit var CelebDB:CelebDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //step 7 - 1) create an instance of the database.
        CelebDB=CelebDatabase.getInstance(this)

        //step 2:
        tvTimer=findViewById(R.id.tvTimer)

        LL1=findViewById(R.id.LL1)
        btnStrat=findViewById(R.id.btnStart)
        btnAdd=findViewById(R.id.btnAdd)

        LL2=findViewById(R.id.LL2)
        tvRotate=findViewById(R.id.tvRotate)

        LL3=findViewById(R.id.LL3)
        tvName=findViewById(R.id.tvName)
        tvT1=findViewById(R.id.tvT1)
        tvT2=findViewById(R.id.tvT2)
        tvT3=findViewById(R.id.tvT3)

        btnStrat.setOnClickListener(){
            LL1.visibility= View.GONE
            LL2.visibility= View.VISIBLE
            countDownTimer1()
            getDBdata()
        }//end btnStart Listener

        btnAdd.setOnClickListener(){
            intent= Intent(this,HeadsActivity::class.java)
            startActivity(intent)
        }

    }//end onCreate()

    fun countDownTimer1(): CountDownTimer {
        return object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvTimer.setText("Time: " + millisUntilFinished / 1000)
            }
            override fun onFinish() {
                celeb=1
                this.cancel()
                tvTimer.setText("Time: -")
                LL2.visibility=View.GONE
                LL3.visibility=View.GONE
                LL1.visibility=View.VISIBLE
            }
        }.start()

    }//end countDownTimer()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            if (LL2.isVisible){
                LL2.visibility=View.GONE
                LL3.visibility=View.VISIBLE
                if (!isShouldStop()) {
                    getDBdata()
                }else
                    countDownTimer1().onFinish()
            }
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()

        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            if (LL3.isVisible){
                LL2.visibility=View.VISIBLE
                LL3.visibility=View.GONE
                if (!isShouldStop()){
                    celeb++
                }else
                    countDownTimer1().onFinish()
            }
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }//end onConfigurationChanged()

    fun isShouldStop(): Boolean {
        var isShouldStop=false
        val DBsize= CelebDB.getCelebDao().getAll().size
        if (DBsize < celeb){
            isShouldStop=true
        }
        return isShouldStop
    }

   fun getDBdata(){
       CoroutineScope(Dispatchers.IO).launch{
           withContext(Dispatchers.Main){
               //step 7- 2) call Dao method that you need (here getByID)
               var celebObj= CelebDB.getCelebDao().getByID(celeb)
               if(celebObj != null){
                   tvName.text=celebObj.name
                   tvT1.text=celebObj.t1
                   tvT2.text=celebObj.t2
                   tvT3.text=celebObj.t3
               }
               }
       }//end CoroutineScope
    }//end getDBdata

} //end class