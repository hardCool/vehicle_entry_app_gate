package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var signupButton: Button
    private lateinit var signupEmail: EditText
    private lateinit var signupPassword: EditText
    private lateinit var signupName: EditText
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        signupButton = findViewById(R.id.signupButton)
        signupEmail = findViewById(R.id.signupEmail)
        signupPassword = findViewById(R.id.signupPassword)
        signupName = findViewById(R.id.signupName)
        loginLink = findViewById(R.id.loginLink)

        signupButton.setOnClickListener {
            val email = signupEmail.text.toString().trim()
            val password = signupPassword.text.toString().trim()
            val name = signupName.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendApprovalEmail(name, email)
                    // Show message that approval is pending
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    // Show error message
                }
            }
        }

        loginLink.setOnClickListener {
            // Launch LoginActivity when "Have an account? Please Login!" is clicked
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun sendApprovalEmail(name: String, email: String) {
        val recipientEmail = "hardev.28094439@delhipolice.gov.in"
        var subject = "New User Approval Request"
        val message = "Please approve the following user:\n\nName: $name\nEmail: $email"

        Thread {
            try {
                val props = Properties().apply {
                    put("mail.smtp.host", "smtp.gmail.com")
                    put("mail.smtp.port", "465")
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                }

                val session = Session.getDefaultInstance(props, object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication("your_email@gmail.com", "your_password")
                    }
                })

                val mimeMessage = MimeMessage(session).apply {
                    setFrom(InternetAddress("your_email@gmail.com"))
                    addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
                    subject = subject
                    setText(message)
                }

                Transport.send(mimeMessage)
            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }.start()
    }
}
