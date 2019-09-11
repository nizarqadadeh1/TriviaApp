package edu.gwu.android.trivia_project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.analytics.FirebaseAnalytics
import com.appdynamics.eumagent.runtime.Instrumentation;

class PracticeActivity : AppCompatActivity() {

    private lateinit var practiceQuesion: TextView

    private lateinit var practiceAnswer: TextView

    private lateinit var getQuestion: Button

    private lateinit var playAgainButton: Button

    private lateinit var firebaseAnalytics2: FirebaseAnalytics



    lateinit var currentQuestion: practiceQuestion

    override fun onCreate(savedInstanceState: Bundle?) {
        Instrumentation.start(" ", getApplicationContext());
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice)

        //currentQuestion = intent.getSerializableExtra("currentQuestion1") as practiceQuestion


        practiceQuesion = findViewById(R.id.practiceQuestion)
        practiceAnswer = findViewById(R.id.practiceAnswer)
        getQuestion = findViewById(R.id.getQuestion)
        playAgainButton = findViewById(R.id.playAgain)

        firebaseAnalytics2 = FirebaseAnalytics.getInstance(this)


        playAgainButton.setOnClickListener {
            QuestionsActivity.cashe.score =0
            QuestionsActivity.cashe.questionsAnswered =0
            val intent3 = Intent(this, TriviaActivity::class.java)
            startActivity(intent3)
        }


        getQuestion.setOnClickListener {

            TriviaManager().retrievePracticeQuestions(

                successCallback = { qList ->
                    runOnUiThread {

                        firebaseAnalytics2.logEvent("API2_success", null)
                        currentQuestion = qList
                        Log.e("this $currentQuestion", "this $qList")
                        val thisQuestion = currentQuestion.question.toString()
                        val thisAnswer = currentQuestion.correctAnswer.toString()
                        practiceQuesion.setText(thisQuestion)
                        practiceAnswer.setText(thisAnswer)
                        //val intent1 = Intent(this, PracticeActivity::class.java)
                        //intent1.putExtra("currentQuestion1", qList)
                        //startActivity(intent1)

                    }
                },
                errorCallback = {
                    runOnUiThread {
                        firebaseAnalytics2.logEvent("API2_failed", null)
                        android.widget.Toast.makeText(this, "List error", android.widget.Toast.LENGTH_LONG)
                            .show()
                    }
                }
            )
        }


    }
}
