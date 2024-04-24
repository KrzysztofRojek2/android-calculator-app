package com.example.calculator

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.SimpleCalculatorBinding
import org.mariuszgromada.math.mxparser.Expression


open class SimpleCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: SimpleCalculatorBinding
    private val specialCharacters = arrayOf('.', '+', '-', '*', '/')
    private val operators = arrayOf('+', '-', '*', '/')
    private val handler = Handler(Looper.getMainLooper())
    private var clearClickCount = 0
    private val clearRunnable = Runnable {
        clearClickCount = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        enableEdgeToEdge()
        binding = SimpleCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnAllClear.setOnClickListener {
            binding.tvFormula.setText("")
            binding.tvResult.setText("0.0")
        }
        binding.btnClear.setOnClickListener {
            clearClickCount++
            if (clearClickCount == 2) {
                binding.tvFormula.setText("")
                clearClickCount = 0
            } else {
                var sth = binding.tvFormula.text.toString()
                if (sth.isNotEmpty()) {
                    sth = sth.substring(0, sth.length - 1)
                    binding.tvFormula.setText(sth)
                }
                handler.removeCallbacks(clearRunnable)
                handler.postDelayed(clearRunnable, 300)
            }
        }
        binding.btn0.setOnClickListener {
            binding.tvFormula.setText(addToInputText("0"))
        }
        binding.btn1.setOnClickListener {
            binding.tvFormula.setText(addToInputText("1"))
        }
        binding.btn2.setOnClickListener {
            binding.tvFormula.setText(addToInputText("2"))
        }
        binding.btn3.setOnClickListener {
            binding.tvFormula.setText(addToInputText("3"))
        }
        binding.btn4.setOnClickListener {
            binding.tvFormula.setText(addToInputText("4"))
        }
        binding.btn5.setOnClickListener {
            binding.tvFormula.setText(addToInputText("5"))
        }
        binding.btn6.setOnClickListener {
            binding.tvFormula.setText(addToInputText("6"))
        }
        binding.btn7.setOnClickListener {
            binding.tvFormula.setText(addToInputText("7"))
        }
        binding.btn8.setOnClickListener {
            binding.tvFormula.setText(addToInputText("8"))
        }
        binding.btn9.setOnClickListener {
            binding.tvFormula.setText(addToInputText("9"))
        }
        binding.btnDot.setOnClickListener {
            binding.tvFormula.setText(addToInputText("."))
        }
        binding.btnDivide.setOnClickListener {
            binding.tvFormula.setText(addToInputText("/"))
        }
        binding.btnMultiply.setOnClickListener {
            binding.tvFormula.setText(addToInputText("*"))
        }
        binding.btnMinus.setOnClickListener {
            binding.tvFormula.setText(addToInputText("-"))
        }
        binding.btnPlus.setOnClickListener {
            binding.tvFormula.setText(addToInputText("+"))
        }

        binding.btnEquals.setOnClickListener {
            val formula = binding.tvFormula.text.toString()
            val result = calculateResult(formula)
            binding.tvResult.setText(result.toString())
        }
        binding.btnChangeSign.setOnClickListener {
            changeSign()
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected open fun addToInputText(buttonValue: String): String {
        if (!canAddCharacter(buttonValue)) {
            showToast("Nie można wpisać tego znaku.")
            return binding.tvFormula.text.toString()
        }
        return "${binding.tvFormula.text}$buttonValue"
    }

    protected fun canAddCharacter(buttonValue: String): Boolean {
        if (buttonValue == ".") {
            return canAddDot()
        }
        if (isLastCharacterSpecial() && specialCharacters.contains(buttonValue.single())) {
            return false
        }
        return true
    }


    protected fun calculateResult(formula: String): Double {
        val expression = Expression(formula)
        return try {
            val result = expression.calculate()
            if (result.isNaN()) {
                showToast("Nie można dzielić przez zero.")
                0.0
            } else {
                result
            }
        } catch (e: Exception) {
            showToast("Wystąpił błąd podczas obliczania wyniku.")
            0.0
        }
    }

    protected fun isLastCharacterSpecial(): Boolean {
        val lastCharacter = binding.tvFormula.text.toString().lastOrNull() ?: return false
        return specialCharacters.contains(lastCharacter)
    }

    protected fun changeSign() {
        val currentText = binding.tvResult.text.toString()
        if (currentText.isEmpty()) {
            binding.tvResult.setText("-")
        } else if (currentText.startsWith("-")) {
            binding.tvResult.setText(currentText.substring(1))
        } else {
            binding.tvResult.setText("-$currentText")
        }
    }

    protected fun canAddDot(): Boolean {
        val text = binding.tvFormula.text.toString()
        if (text.isEmpty()) {
            return false
        }
        for (i in text.length - 1 downTo 0) {
            val char = text[i]
            if (char == '.') {
                return false
            } else if (operators.contains(char)) {
                return true
            }
        }
        return true
    }


}