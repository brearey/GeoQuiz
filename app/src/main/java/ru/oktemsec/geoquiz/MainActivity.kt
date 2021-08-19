package ru.oktemsec.geoquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlin.math.round

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val KEY_SCORES = "scores"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    //QuizViewModel instance
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_main)

        //Variables from savedInstance
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        val currentScores = savedInstanceState?.getInt(KEY_SCORES, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        quizViewModel.scores = currentScores

        //Views links
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        //answer button listeners
        trueButton.setOnClickListener {
            checkAnswer(true)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }

        //cheat button
        cheatButton.setOnClickListener {
            val intent = Intent(this, CheatActivity::class.java)
            startActivity(intent)
        }

        //navigation buttons listeners
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        //text view click listener
        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "OnSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        savedInstanceState.putInt(KEY_SCORES, quizViewModel.scores)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun updateQuestion() {
        val percentScores:Double = round((quizViewModel.scores.toDouble() / quizViewModel.getSizeQuestionBank().toDouble()) * 100)
        val toastPercentScores = Toast.makeText(this, "Your scores: $percentScores %", Toast.LENGTH_SHORT)
        toastPercentScores.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toastPercentScores.show()

        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            quizViewModel.scores++
            Log.d(TAG, quizViewModel.getSizeQuestionBank().toString())
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        val toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }
}