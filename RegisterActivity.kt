package com.example.loginregister

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregister.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    //Declare viewBinding
    private lateinit var binding : ActivityRegisterBinding

    //Declare firebaseAuth
    private lateinit var mAuth : FirebaseAuth

    //Initialise progressDialog
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Init mAuth
        mAuth = FirebaseAuth.getInstance()

        //Initialise progressDialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait!!")
        progressDialog.setCanceledOnTouchOutside(false)

        //Handle click login redirect
        binding.loginRedirectText.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Handle click register
        binding.registerButton.setOnClickListener {

            validateData()
        }

    }
    private var email = ""
    private var password = ""
    private var cPassword = ""

    private fun validateData() {

        email = binding.registerEmailEt.text.toString().trim()
        password = binding.registerPasswordEt.text.toString().trim()
        cPassword = binding.confirmPasswordEt.text.toString().trim()

        if (email.isEmpty() && password.isEmpty() && cPassword.isEmpty()){
            Toast.makeText(this,"Please enter the required fields",Toast.LENGTH_SHORT).show()
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Please enter a valid email address",Toast.LENGTH_SHORT).show()
        }
        else if (password != cPassword){
            Toast.makeText(this,"Passwords do not match",Toast.LENGTH_SHORT).show()
        }
        else{
            registerUser()
        }

    }

    private fun registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Registration Successful",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Failed to register user ",Toast.LENGTH_SHORT).show()
                }
            }
    }
}