package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbar_history_activity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompleyedDates()
    }

    private fun getAllCompleyedDates(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allCompletedDateList = dbHandler.getAllCompletedDateList()

        if(allCompletedDateList.size > 0) {
            tv_history.visibility = View.VISIBLE
            rv_history.visibility = View.VISIBLE
            tv_no_data_available.visibility = View.GONE

            rv_history.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this, allCompletedDateList)
            rv_history.adapter = historyAdapter
        } else {
            tv_history.visibility = View.GONE
            rv_history.visibility = View.GONE
            tv_no_data_available.visibility = View.VISIBLE
        }
    }

}