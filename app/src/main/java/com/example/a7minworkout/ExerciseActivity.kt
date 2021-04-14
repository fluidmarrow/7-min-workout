package com.example.a7minworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var restTimer: CountDownTimer? = null
    private var restProgress: Long = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var player : MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogFunction()
        }
        tts = TextToSpeech(this,this)

        exerciseList = Constants.defaultExerciseList()
        speakOut("Get Ready for " + exerciseList!![0].getName())
        setUpRestView()
        SetUpExerciseStatusRecyclerView()
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player != null){
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun setRestProgressBar(){
        progress_bar.progress = restProgress.toInt()
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progress_bar.progress = 10-restProgress.toInt()
                tv_timer.text = (10-restProgress).toString()
            }

            override fun onFinish() {
                ll_RestView.visibility = View.GONE
                ll_ExerciseView.visibility = View.VISIBLE
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setUpExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        progress_bar_e.progress = restProgress.toInt()
        restTimer = object : CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progress_bar_e.progress = 30-restProgress.toInt()
                tv_timer_e.text = (30-restProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList!!.size - 1) {
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpRestView(){
        if(currentExercisePosition >= 0){
            tv_rest_screen_exercise_name.text = "Get Ready For " + exerciseList!![currentExercisePosition+1].getName()
            speakOut("Get ready for " + exerciseList!![currentExercisePosition+1].getName())
        }



        ll_ExerciseView.visibility = View.GONE
        ll_RestView.visibility = View.VISIBLE
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0

        }

        setRestProgressBar()
    }

    private fun setUpExerciseView(){
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0

        }
        try{
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()
        } catch (e:Exception){
            e.printStackTrace()
        }
        setExerciseProgressBar()
        iv_image.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tv_exercise_name.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(Status: Int) {
        if(Status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language not supported")
            }
            }else {
            Log.e("TTS","initialization Failed")
        }
    }

    private fun speakOut(text: String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun SetUpExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    @Override
    override fun onBackPressed(){

        customDialogFunction()

    }

    private fun customDialogFunction(){
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.btn_yes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.btn_no.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }


}