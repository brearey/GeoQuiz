package ru.oktemsec.geoquiz

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

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
    fun sendPostRequest(
        makeOrder:String = "true",
        phone:String = "89246628934",
        address:String = "android studio",
        countBottle:String = "3",
        paymentMethod:String = "Мобильный банк",
        note:String = "test kotlin"
    )
    {

        var reqParam = URLEncoder.encode("makeOrder", "UTF-8") + "=" + URLEncoder.encode(makeOrder, "UTF-8")
        reqParam += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")
        reqParam += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8")
        reqParam += "&" + URLEncoder.encode("countBottle", "UTF-8") + "=" + URLEncoder.encode(countBottle, "UTF-8")
        reqParam += "&" + URLEncoder.encode("paymentMethod", "UTF-8") + "=" + URLEncoder.encode(paymentMethod, "UTF-8")
        reqParam += "&" + URLEncoder.encode("note", "UTF-8") + "=" + URLEncoder.encode(note, "UTF-8")
        reqParam += "&" + URLEncoder.encode("note", "UTF-8") + "=" + URLEncoder.encode(note, "UTF-8")
        val mURL = URL("https://oktemsec.ru/water/order.php")

        with(mURL.openConnection() as HttpURLConnection) {
            // optional default is GET
            requestMethod = "POST"

            val wr = OutputStreamWriter(getOutputStream());
            wr.write(reqParam);
            wr.flush();

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                println("Response : $response")
            }
        }
    }

    //sendPostRequest(makeOrder = "1")

}