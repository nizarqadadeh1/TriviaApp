package edu.gwu.android.trivia_project

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.flags.Flag
import com.google.firebase.analytics.FirebaseAnalytics
import com.appdynamics.eumagent.runtime.Instrumentation;
import kotlinx.android.synthetic.main.activity_login.*

class TriviaActivity : AppCompatActivity() {

   // private lateinit var recyclerView: RecyclerView


    private lateinit var questionTextView: TextView

    private lateinit var geographyButton: Button

    private lateinit var sportsButton: Button

    private lateinit var scienceButton: Button

    private lateinit var historyButton: Button

    private lateinit var firebaseAnalytics1: FirebaseAnalytics

    //private lateinit var practiceButton: Button

    val code: Int = 1

    var totalQuestions: Int =0
    var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trivia)
        firebaseAnalytics1 = FirebaseAnalytics.getInstance(this)




        questionTextView = findViewById(R.id.category)

        geographyButton = findViewById(R.id.geography)

        sportsButton = findViewById(R.id.sports)

        scienceButton = findViewById(R.id.science)

        historyButton = findViewById(R.id.history)

        //practiceButton = findViewById(R.id.practice)






        if(totalQuestions < 2) {

            geographyButton.setOnClickListener {

                if (totalQuestions < 2) {
                    TriviaManager().retrieveQuestion(22,

                        successCallback = { qList ->
                            runOnUiThread {
                                firebaseAnalytics1.logEvent("API1_success", null)
                                val intent = Intent(this, QuestionsActivity::class.java)
                                intent.putExtra("currentQuestion", qList)
                                startActivityForResult(intent, code)
                            }
                        },
                        errorCallback = {
                            runOnUiThread {
                                android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    )
                } else {
                    finishIntent()
                }
            }

            sportsButton.setOnClickListener {

                if (totalQuestions < 2) {
                    TriviaManager().retrieveQuestion(21,

                        successCallback = { qList ->
                            runOnUiThread {
                                firebaseAnalytics1.logEvent("API1_success", null)
                                val intent = Intent(this, QuestionsActivity::class.java)
                                intent.putExtra("currentQuestion", qList)
                                startActivityForResult(intent, code)

                            }
                        },
                        errorCallback = {
                            runOnUiThread {
                                android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    )

                } else {
                    finishIntent()
                }
            }

            scienceButton.setOnClickListener {

                if (totalQuestions < 2) {
                    TriviaManager().retrieveQuestion(17,

                        successCallback = { qList ->
                            runOnUiThread {
                                firebaseAnalytics1.logEvent("API1_success", null)
                                val intent = Intent(this, QuestionsActivity::class.java)
                                intent.putExtra("currentQuestion", qList)
                                startActivityForResult(intent, code)

                            }
                        },
                        errorCallback = {
                            runOnUiThread {
                                android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    )
                } else {
                    finishIntent()
                }
            }

            historyButton.setOnClickListener {

                if (totalQuestions < 2) {
                    TriviaManager().retrieveQuestion(23,

                        successCallback = { qList ->
                            runOnUiThread {
                                firebaseAnalytics1.logEvent("API1_success", null)
                                val intent = Intent(this, QuestionsActivity::class.java)
                                intent.putExtra("currentQuestion", qList)
                                startActivityForResult(intent, code)

                            }
                        },
                        errorCallback = {
                            runOnUiThread {
                                android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    )
                } else {
                    finishIntent()
                }
            }
        }else{
            finishIntent()
        }



        }

    fun finishIntent(){
        val intent2 = Intent(this, ScoresActivity::class.java)
        intent2.putExtra("totalQuestions", totalQuestions)
        intent2.putExtra("score", score)
        startActivity(intent2)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == code && resultCode == Activity.RESULT_OK ){

                val answeredQuestions = data!!.getIntExtra("score", QuestionsActivity.cashe.questionsAnswered)
                val yourScore = data.getIntExtra("score1", QuestionsActivity.cashe.score)
                totalQuestions = answeredQuestions
                score = yourScore

        }
        else if( totalQuestions>=2){
            finishIntent()
        }

    }
     override fun onResume() {
        super.onResume()
         if(totalQuestions >= 2){
             finishIntent()
         }
    }



}
