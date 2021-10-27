package com.example.headsupapproom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_heads.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeadsActivity : AppCompatActivity() {
    //step 2: Do your UI, declare and initialize them in your activity
    //my views
    lateinit var edName: EditText
    lateinit var edT1: EditText
    lateinit var edT2: EditText
    lateinit var edT3: EditText
    lateinit var btnSubmit: Button

    //step 7: Managing Data
    //step 7 - 1) create an instance of the database.
    lateinit var CelebDB:CelebDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heads)

        //step 7 - 1) create an instance of the database.
        CelebDB=CelebDatabase.getInstance(this)

        //step 2
        edName=findViewById(R.id.edName)
        edT1=findViewById(R.id.edT1)
        edT2=findViewById(R.id.edT2)
        edT3=findViewById(R.id.edT3)
        btnSubmit=findViewById(R.id.btnSubmit)

        //getData()

        btnSubmit.setOnClickListener(){
            if (edName.text.isNotEmpty() && edT1.text.isNotEmpty() &&
                edT2.text.isNotEmpty() && edT3.text.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch{
                    //step 7- 2) call Dao method that you need (here insertCeleb)
                    val celebTableObj=CelebTable(0, edName.text.toString(),edT1.text.toString(), edT2.text.toString(),edT3.text.toString())
                    CelebDB.getCelebDao().insertCeleb(celebTableObj)
                    getData()
                }//end CoroutineScope
                Toast.makeText(applicationContext, "data added successfully", Toast.LENGTH_SHORT).show()
            }
        }//end listener

    }//end onCreate()

    fun getData(){
        CoroutineScope(Dispatchers.IO).launch{
            withContext(Dispatchers.Main){
                //step 7- 2) call Dao method that you need (here getAll)
                rv_add.adapter=RecycelerAdapter(this@HeadsActivity, CelebDB.getCelebDao().getAll())
                rv_add.layoutManager= LinearLayoutManager(this@HeadsActivity)
            }//
        }//end CoroutineScope
    }//end getData

}//end class