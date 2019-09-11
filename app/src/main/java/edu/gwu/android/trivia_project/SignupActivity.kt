package edu.gwu.android.trivia_project

import android.support.v7.app.AppCompatActivity


import android.os.Bundle

import android.support.annotation.NonNull;
import android.util.Log
import android.text.Editable;
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

class SignupActivity : AppCompatActivity() {

    private lateinit var usernameEditText1: EditText

    private lateinit var passwordEditText1: EditText

    private lateinit var confirmpasswordEditText: EditText

    private lateinit var signupButton: Button

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        firebaseAuth = FirebaseAuth.getInstance()

        usernameEditText1 = findViewById(R.id.username1);
        passwordEditText1 = findViewById(R.id.password1);
        confirmpasswordEditText = findViewById(R.id.confirmpassword);
        signupButton = findViewById(R.id.signUp);

        usernameEditText1.addTextChangedListener(textWatcher)
        passwordEditText1.addTextChangedListener(textWatcher)
        confirmpasswordEditText.addTextChangedListener(textWatcher)



        signupButton.setOnClickListener {


            val username1 = usernameEditText1.text.toString()
            val password1 = passwordEditText1.text.toString()
            val confirmation = confirmpasswordEditText.text.toString()


            if(password1 == confirmation) {
                firebaseAuth.createUserWithEmailAndPassword(username1, password1).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = firebaseAuth.currentUser
                        if (user != null) {
                            Toast.makeText(this, "Account created for ${user.email}!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    } else {
                        val exception = task.exception
                        Toast.makeText(this, "Account creation failed! $exception", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "password does not match!",Toast.LENGTH_SHORT ).show()
            }
        }

    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameText: String = usernameEditText1.text.toString()
            val passwordText: String = passwordEditText1.text.toString()
            val confirmpasswordText: String = confirmpasswordEditText.text.toString()
            signupButton.isEnabled = usernameText.isNotEmpty() && passwordText.isNotEmpty()&& confirmpasswordText.isNotEmpty()
        }
    }

}