package com.example.kotlincalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlincalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Botões CLEAR
    private var currentNumber = ""
    private var operator: Char? = null
    private var operand1: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Configura os listenner
        settingsButtons()

        //Remoção da barra de menus
        supportActionBar?.hide()

        //Animação do botão
        null.onBracketClick()

    }

    //Manipulação dos botões
    private fun settingsButtons() {
        with(binding) {
            button0.setOnClickListener { onDigitClick("0") }
            button1.setOnClickListener { onDigitClick("1") }
            button2.setOnClickListener { onDigitClick("2") }
            button3.setOnClickListener { onDigitClick("3") }
            button4.setOnClickListener { onDigitClick("4") }
            button5.setOnClickListener { onDigitClick("5") }
            button6.setOnClickListener { onDigitClick("6") }
            button7.setOnClickListener { onDigitClick("7") }
            button8.setOnClickListener { onDigitClick("8") }
            button9.setOnClickListener { onDigitClick("9") }

            buttonBracketRight.setOnClickListener { onDigitClick(")") }
            buttonBracketLeft.setOnClickListener { onDigitClick("(") }
            buttonDot.setOnClickListener { onDigitClick(".") }


            buttonAddition.setOnClickListener { onOperatorClick('+' )  }
            buttonSubtraction.setOnClickListener { onOperatorClick('-') }
            buttonMultiply.setOnClickListener { onOperatorClick('*') }
            buttonDivision.setOnClickListener { onOperatorClick('/') }


            buttonEquals.setOnClickListener { onEqualsClick() }
            buttonClear.setOnClickListener { onClearClick() }
        }
    }

    private fun Char?.onBracketClick() {
        when (this) {
            '(' -> {
                binding.calculation.text = "("
            }

            ')' -> {
                binding.calculation.text = ")"
            }
        }
    }

    private fun onDigitClick(digit: String) {
        currentNumber += digit
        binding.calculation.text = currentNumber
        if (operator == null) {
            operand1 = currentNumber.toDoubleOrNull()
        }
    }

    private fun onOperatorClick(op: Char) {
        operand1 = currentNumber.toDoubleOrNull()
        operator = op
        currentNumber = ""
    }

    //CALCULO
    private fun onEqualsClick() {
        val operand2 = currentNumber.toDoubleOrNull()
        if (operand1 != null && operand2 != null && operator != null) {
            val result = when (operator) {
                '+' -> operand1!! + operand2
                '-' -> operand1!! - operand2
                '*' -> operand1!! * operand2
                '/' -> if (operand2 != 0.0) operand1!! / operand2 else Double.NaN
                else -> Double.NaN
            }
            if (result.isNaN()) {
                "@string/error_message".also { binding.calculation.text = it }
                currentNumber = ""
                operator = null
                operand1 = null
            } else {
                binding.calculation.text = result.toString()
                currentNumber = result.toString()
                operator = null
                operand1 = null
            }
        }
    }

    //LIMPAR
    private fun onClearClick() {
        currentNumber = ""
        operator = null
        operand1 = null
        binding.calculation.text = ""
    }
}
