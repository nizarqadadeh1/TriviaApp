package edu.gwu.android.trivia_project

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ScoresActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView

    private lateinit var yourScore: TextView

    private lateinit var totalQuestionsTextView: TextView

    private lateinit var yourQuestions: TextView

    private lateinit var playAgainButton: Button

    private lateinit var practiceButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        scoreTextView = findViewById(R.id.yourScoreTextView)
        yourScore = findViewById(R.id.scoreTextView)
        totalQuestionsTextView = findViewById(R.id.yourTotalQuestions)
        yourQuestions = findViewById(R.id.yourTotalTextView)
        practiceButton = findViewById(R.id.practice)




        val totalQuestions: Int  = intent.getIntExtra("totalQuestions", 0)
        val score: Int  = intent.getIntExtra("score", 0)
        //val score = intent.getStringExtra("score")
        Log.e("Here is my $score" , " score ")
        Log.e("Here is my $totalQuestions" , " score ")

        val scoreString = score.toString()
        val questionsString = totalQuestions.toString()


        yourScore.setText(scoreString)
        yourQuestions.setText(questionsString)



        playAgainButton = findViewById(R.id.playAgainButton)

        playAgainButton.setOnClickListener {

            QuestionsActivity.cashe.questionsAnswered =0
            QuestionsActivity.cashe.score = 0
            val intent1 = Intent(this, TriviaActivity::class.java)
            startActivity(intent1)
        }

        practiceButton.setOnClickListener {

            val intent2 = Intent(this, PracticeActivity::class.java)
            startActivity(intent2)

        }
    }
}
