package com.example.loginregister

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregister.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //Declare viewBinding
    private lateinit var binding : ActivityLoginBinding

    //Declare firebaseAuth
    private lateinit var mAuth : FirebaseAuth

    //Initialise progressDialog
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init mAuth
        mAuth = FirebaseAuth.getInstance()

        //Initialise progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait!!")
        progressDialog.setCanceledOnTouchOutside(false)

        //Handle click login redirect
        binding.registerRedirectText.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Handle click register
        binding.loginButton.setOnClickListener {

            validateData()
        }

    }

    private var email = ""
    private var password = ""

    private fun validateData() {
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        if (email.isEmpty()){
            Toast.makeText(this,"Please enter email", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            Toast.makeText(this,"Please enter password", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter a valid email address",Toast.LENGTH_SHORT).show()

        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this,"Login Successful...",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                        finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Login Failed",Toast.LENGTH_SHORT).show()
                }
            }
    }
}