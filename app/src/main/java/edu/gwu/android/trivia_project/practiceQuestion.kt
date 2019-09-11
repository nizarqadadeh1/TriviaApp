package edu.gwu.android.trivia_project

import java.io.Serializable


data class practiceQuestion(
    val question: String,
    val correctAnswer: String

): Serializable