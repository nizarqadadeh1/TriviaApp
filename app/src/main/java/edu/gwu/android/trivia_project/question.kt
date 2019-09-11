package edu.gwu.android.trivia_project

import java.io.Serializable

data class Question(
    val question: String,
    val correctAnswer: String,
    val choice1: String,
    val choice2: String,
    val choice3: String

):Serializable