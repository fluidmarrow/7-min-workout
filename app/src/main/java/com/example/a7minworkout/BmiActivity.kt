package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val metricUnitView = "METRIC_UNIT_VIEW"
    val UsUnitView = "Us_Unit_View"
    var currentVisibleView: String = metricUnitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "Calculate BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        btn_calculate.setOnClickListener {
            if(currentVisibleView == metricUnitView){
                if(validateMetricUnits()){
                    val heightValue : Float = et_MetricUnitHeight.text.toString().toFloat()/100
                    val weightValue : Float = et_MetricUnitWeight.text.toString().toFloat()
                    val bmi = weightValue/(heightValue*heightValue)
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter a valid value",Toast.LENGTH_SHORT).show()
                }
            } else {
                if(validateUsUnits()){
                    val usHeightValueFeet : Float = etUsUnitHeightFeet.text.toString().toFloat()
                    val usHeightValueInch : Float = etUsUnitHeightInch.text.toString().toFloat() + (usHeightValueFeet*12)
                    val usWeightValue : Float = etUsUnitWeight.text.toString().toFloat()
                    val bmi = (usWeightValue / (usHeightValueInch*usHeightValueInch) ) * 703
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this@BMIActivity,"Please enter a valid value",Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitViewVisible()
        rgUnits.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitViewVisible()
            } else {
                makeVisibleUsUnitsView()
            }
        }
    }

    private fun makeVisibleMetricUnitViewVisible() {
        currentVisibleView = metricUnitView

        llUsUnitsView.visibility = View.GONE

        et_MetricUnitHeight.text!!.clear()
        et_MetricUnitWeight.text!!.clear()

        ll_Metric_Units_view.visibility = View.VISIBLE

        llDiplayBMIResult.visibility = View.INVISIBLE
    }



    private fun makeVisibleUsUnitsView(){
        currentVisibleView = UsUnitView

        ll_Metric_Units_view.visibility = View.GONE

        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()
        etUsUnitWeight.text!!.clear()

        llUsUnitsView.visibility = View.VISIBLE

        llDiplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDiplayBMIResult.visibility = View.VISIBLE
        tv_YourBMI.visibility = View.VISIBLE
        tv_BMIValue.visibility = View.VISIBLE
        tv_BMIType.visibility = View.VISIBLE
        tv_BMIDescription.visibility = View.VISIBLE

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tv_BMIValue.text = bmiValue
        tv_BMIType.text = bmiLabel
        tv_BMIDescription.text = bmiDescription

    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(et_MetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        } else if(et_MetricUnitWeight.text.toString().isEmpty()){
            isValid=false
        }

        return isValid
    }

    private fun validateUsUnits(): Boolean{
        var isValid = true
        if(etUsUnitHeightFeet.text.toString().isEmpty()){
            isValid = false
        }else if(etUsUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }else if(etUsUnitWeight.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }
}