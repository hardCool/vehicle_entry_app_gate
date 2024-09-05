package com.example.test_vehicle_entry

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
    private val db = FirebaseFirestore.getInstance()

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

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                        storeUserApprovalStatus(uid, name, email)
                        sendApprovalEmail(name, email)
                        Toast.makeText(this, "Registration successful. Approval pending.", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun storeUserApprovalStatus(uid: String, name: String, email: String) {
        val user = hashMapOf(
            "uid" to uid,
            "name" to name,
            "email" to email,
            "approval_status" to "pending"
        )

        db.collection("users").document(uid)
            .set(user)
            .addOnSuccessListener {
                // Successfully stored user approval status
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }

    private fun sendApprovalEmail(name: String, email: String) {
        val recipientEmail = "hardevcool@gmail.com"
        val subject = "New User Approval Request"
        val baseUrl = "https://yourbackenddomain.com/approve_user" // Replace with your backend URL
        val uniqueToken = UUID.randomUUID().toString() // Generate a unique token
        val approvalLink = "$baseUrl?token=$uniqueToken"
        val message = """
        Please approve the following user:

        Name: $name
        Email: $email

        Click the link below to approve the user:
        $approvalLink
    """.trimIndent()

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
                        return PasswordAuthentication("vehicleentrysys@gmail.com", "icek yzsz vpfa hmnm")
                    }
                })

                val mimeMessage = MimeMessage(session).apply {
                    setFrom(InternetAddress("vehicleentrysys@gmail.com"))
                    addRecipient(Message.RecipientType.TO, InternetAddress(recipientEmail))
                    this.subject = subject
                    setText(message)
                }

                Transport.send(mimeMessage)
                println("Email sent successfully")
            } catch (e: MessagingException) {
                e.printStackTrace()
                println("Failed to send email: ${e.message}")
            }
        }.start()
    }

}


