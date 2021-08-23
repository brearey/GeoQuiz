package ru.oktemsec.geoquiz

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex = 0
    var scores:Int = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        if (currentIndex > 0) {
            currentIndex -= 1
        }
        else {
            currentIndex = 0
        }
    }
    fun getSizeQuestionBank():Int {
        return questionBank.size
    }

    //Test post request to https://oktemsec.ru/water/order.php
    val client = HttpClient(Android) {
        // setting these properties is optional; you don't need to if you wish to use the defaults
        engine {
            connectTimeout = 100_000
            socketTimeout = 100_000
        }
    }


}