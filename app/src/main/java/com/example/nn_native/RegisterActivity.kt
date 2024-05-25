package com.example.nn_native

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var regemail: EditText
    private lateinit var regmobile: EditText
    private lateinit var regpassword: EditText
    private lateinit var regsubmit: Button
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        regemail = findViewById(R.id.reg_email)
        regmobile = findViewById(R.id.reg_mobile)
        regpassword = findViewById(R.id.reg_password)
        tv = findViewById(R.id.signup_report_tv)
        regsubmit = findViewById(R.id.reg_submit)

        regsubmit.setOnClickListener {
            userRegister(
                regemail.text.toString(),
                regmobile.text.toString(),
                regpassword.text.toString()
            )
        }
    }

    private fun userRegister(email: String, mobile: String, password: String) {
        val name = "not applicable"
        val address = "not applicable"

        val call = ApiController.getInstance()
            .api
            .register(name, email, password, mobile, address)

        call.enqueue(object : Callback<SignupResponseModel> {
            override fun onResponse(
                call: Call<SignupResponseModel>,
                response: Response<SignupResponseModel>
            ) {
                val obj = response.body()
                val result = obj?.message?.trim()
                if (result.equals("inserted", ignoreCase = true)) {
                    tv.text = "Successfully Registered"
                    tv.setTextColor(Color.GREEN)
                    regemail.text.clear()
                    regmobile.text.clear()
                    regpassword.text.clear()
                }
                if (result.equals("exist", ignoreCase = true)) {
                    tv.text = "Sorry...! You are already registered"
                    tv.setTextColor(Color.RED)
                    regemail.text.clear()
                    regmobile.text.clear()
                    regpassword.text.clear()
                }
            }

            override fun onFailure(call: Call<SignupResponseModel>, t: Throwable) {
                tv.text = "Something went wrong"
                tv.setTextColor(Color.RED)
                regemail.text.clear()
                regmobile.text.clear()
                regpassword.text.clear()
            }
        })
    }
}