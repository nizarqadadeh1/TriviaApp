package edu.gwu.android.trivia_project

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.widget.Button
import java.util.Random
import android.widget.TextView
import android.graphics.Color
import android.util.Log
import kotlinx.android.synthetic.main.activity_questions.*
import java.util.concurrent.ThreadLocalRandom
import com.appdynamics.eumagent.runtime.Instrumentation;

class QuestionsActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView

    private lateinit var choice1: Button

    private lateinit var choice2: Button

    private lateinit var choice3: Button

    private lateinit var choice4: Button

    lateinit var currentQuestion: Question

    object cashe{
        var questionsAnswered = 0
        var score = 0

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        currentQuestion = intent.getSerializableExtra("currentQuestion") as Question




        questionTextView = findViewById(R.id.question)
        choice1 = findViewById(R.id.choiceA)
        choice2 = findViewById(R.id.choiceB)
        choice3 = findViewById(R.id.choiceC)
        choice4 = findViewById(R.id.choiceD)



        val options = arrayListOf<String>()
        options.add(currentQuestion.choice1)
        options.add(currentQuestion.choice2)
        options.add(currentQuestion.correctAnswer)
        options.add(currentQuestion.choice3)

        Log.e("here $options", "d")

        options.shuffle()
        //options.shuffled()

        Log.e("here $options", "d")

        questionTextView.setText(currentQuestion.question)
        choice1.setText(options[0])
        choice2.setText(options[1])
        choice3.setText(options[2])
        choice4.setText(options[3])



        choice1.setOnClickListener{
            checkAnswer(choice1)
            val intent1 = Intent(this, TriviaActivity::class.java)
            intent1.putExtra("score", cashe.questionsAnswered)
            intent1.putExtra("score1", cashe.score)
            setResult(Activity.RESULT_OK, intent1)
            finish()

        }
        choice2.setOnClickListener{

            checkAnswer(choice2)
            val intent1 = Intent(this, TriviaActivity::class.java)
            intent1.putExtra("score", cashe.questionsAnswered)
            intent1.putExtra("score1", cashe.score)
            setResult(Activity.RESULT_OK, intent1)
            finish()
        }
        choice3.setOnClickListener{

            checkAnswer(choice3)
            val intent1 = Intent(this, TriviaActivity::class.java)
            intent1.putExtra("score", cashe.questionsAnswered)
            intent1.putExtra("score1", cashe.score)
            setResult(Activity.RESULT_OK, intent1)
            finish()
        }
        choice4.setOnClickListener{

            checkAnswer(choice4)
            val intent1 = Intent(this, TriviaActivity::class.java)
            intent1.putExtra("score", cashe.questionsAnswered)
            intent1.putExtra("score1", cashe.score)
            setResult(Activity.RESULT_OK, intent1)
            finish()
        }

    }
    fun checkAnswer(button: Button) {
        if(button.text == currentQuestion.correctAnswer) {
            button.setBackgroundColor(Color.GREEN)
            cashe.score ++
            cashe.questionsAnswered++
        }else{
            button.setBackgroundColor(Color.RED)
            cashe.questionsAnswered++
        }

    }


}
