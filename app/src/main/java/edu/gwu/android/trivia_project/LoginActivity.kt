package edu.gwu.android.trivia_project

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.support.annotation.NonNull;
import android.text.Editable;
import com.google.firebase.auth.FirebaseAuth
import android.text.TextWatcher;
import android.util.Log
import android.view.View;
import android.widget.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val PREF_FILENAME = "TriviaApp"
    private val PREF_SAVED_USERNAME = "SAVED_USERNAME"
    private val PREF_SAVED_PASSWORD = "SAVED_PASSWORD"

    private lateinit var usernameEditText: EditText

    private lateinit var passwordEditText: EditText

    private lateinit var loginButton: Button

    private lateinit var signUpButton: Button

    private lateinit var progressBar: ProgressBar

    private lateinit var rememberMe: CheckBox

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var firebaseAnalytics: FirebaseAnalytics



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        signUpButton = findViewById(R.id.signup)
        rememberMe = findViewById(R.id.checkBox1)


        //editor.putString(PREF_SAVED_USERNAME, usernameEditText.toString())


        //val savedPassword: String = preferences.getString(PREF_SAVED_PASSWORD, passwordEditText.text.toString())

        //preferences.edit().putString(PREF_SAVED_USERNAME, savedUsername).apply()
        //preferences.edit().putString(PREF_SAVED_PASSWORD, savedPassword).apply()

        val preferences = getSharedPreferences(PREF_FILENAME, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        val savedUsername: String =  usernameEditText.text.toString()

        if(rememberMe.isChecked){

            editor.putString(PREF_SAVED_USERNAME, savedUsername).apply()
            editor.apply()
            editor.putBoolean(PREF_SAVED_USERNAME, true).apply()

        }
        else{
            editor.remove(PREF_SAVED_USERNAME).apply()
            editor.putBoolean(PREF_SAVED_USERNAME ,false).apply()
        }


        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)


        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity :: class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener{

            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    firebaseAnalytics.logEvent("login_success", null)

                    if (user != null) {
                        Toast.makeText(this, "Logged in as ${user.email}!", Toast.LENGTH_SHORT).show()
                    }

                    // Start the ChooseLocationActivity, sending it the inputted username
                    val intent1 = Intent(this, TriviaActivity::class.java)
                    intent.putExtra("User", username)
                    startActivity(intent1)
                } else {
                    val exception = task.exception
                    val bundle = Bundle()
                    val reason = if (exception is FirebaseAuthInvalidCredentialsException)
                        "invalid_credentials" else "failed_to_connect"

                    bundle.putString("error_type", reason)
                    firebaseAnalytics.logEvent("login_failed", bundle)
                    Toast.makeText(this, "Login failed: $exception", Toast.LENGTH_SHORT).show()
                }
            }
        }




    }



    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val usernameText: String = usernameEditText.text.toString()
            val passwordText: String = passwordEditText.text.toString()
            loginButton.isEnabled = usernameText.isNotEmpty() && passwordText.isNotEmpty()
            signUpButton.isEnabled = (usernameText.isNotEmpty() && passwordText.isNotEmpty()) ||usernameText.isEmpty() || passwordText.isEmpty()
        }
    }

}
