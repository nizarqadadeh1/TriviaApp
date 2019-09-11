package edu.gwu.android.trivia_project

import android.util.Log
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.TimeUnit
import org.json.JSONObject

class TriviaManager{


    val okHttpClient: OkHttpClient = OkHttpClient.Builder().let { builder ->

        // For printing request / response to logs
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(logging)

        // Network timeouts
        builder.connectTimeout(20, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        builder.build()
    }

    fun retrieveQuestion( category: Int,

        successCallback: (Question) -> Unit,
        errorCallback: (Exception) -> Unit
    ) {

        val request = Request.Builder()
            .url("https://opentdb.com/api.php?amount=1&category=$category&type=multiple")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val questions = mutableListOf<Question>()
                    val json = JSONObject(responseString)
                    val question = json.getJSONArray("results")


                    val curr = question.getJSONObject(0)
                    val currentQuestion = curr.getString("question")
                    val correct = curr.getString("correct_answer")
                    val wrongAnsArray = curr.getJSONArray("incorrect_answers")
                    val multiple1 = wrongAnsArray.get(0).toString()
                    val multiple2 = wrongAnsArray.get(1).toString()
                    val multiple3 = wrongAnsArray.get(2).toString()





                    questions.add(
                        Question(
                            question = currentQuestion,
                            correctAnswer = correct,
                            choice1 = multiple1,
                            choice2 = multiple2,
                            choice3 = multiple3
                        )

                    )


                    successCallback(questions[0])

                }
                else {
                    errorCallback(java.lang.Exception("no Question available"))
                }
            }
        })


    }
    fun retrievePracticeQuestions(

                          successCallback: (practiceQuestion) -> Unit,
                          errorCallback: (Exception) -> Unit
    ) {
        val request = Request.Builder()
            .url("http://jservice.io/api/random")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                errorCallback(e)
            }
            override fun onResponse(call: Call, response: Response) {
                val responseString: String? = response.body()?.string()
                if (response.isSuccessful && responseString != null) {
                    val practiceQuestions = mutableListOf<practiceQuestion>()
                    val json1 = JSONArray(responseString)
                    val json = json1.getJSONObject(0)
                    val practiceQuestion = json.get("question")
                    val practiceAnswer = json.get("answer")


                    val practice1 = practiceQuestion.toString()
                    val answer1 = practiceAnswer.toString()

                    practiceQuestions.add(
                        practiceQuestion(
                            question = practice1,
                            correctAnswer = answer1

                        )

                    )
                    Log.e("here $practiceQuestions" ," " )

                    successCallback(practiceQuestions[0])

                }
                else {
                    errorCallback(java.lang.Exception("no Question available"))
                }
            }
        })

    }
}