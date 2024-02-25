package com.osmancandincer.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var inputTextView: TextView
    private lateinit var outputTextView: TextView

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputTextView = findViewById(R.id.input)
        outputTextView = findViewById(R.id.output)
    }

    //Bu fonksiyon, sayı düğmelerine (0'dan 9'a) tıklandığında çağrılır.
    //xml'de onClick'te tanımlandı.
    fun onDigit(view: View) {
        val button = view as Button
        inputTextView.append(button.text)
        lastNumeric = true
    }

    //Bu fonksiyon, ondalık nokta düğmesine tıklandığında çağrılır.
    //xml'de onClick'te tanımlandı.
    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            inputTextView.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    //Bu fonksiyon, işlem operatörü düğmelerine (+, -, *, /) tıklandığında çağrılır.
    //xml'de onClick'te tanımlandı.
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(inputTextView.text.toString())) {
            inputTextView.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    //Bu fonksiyon, "C" düğmesine tıklandığında çağrılır.
    //xml'de onClick'te tanımlandı.
    fun onClear(view: View) {
        inputTextView.text = ""
        outputTextView.text = ""
        lastNumeric = false
        lastDot = false
    }

    //Bu fonksiyon, eşitlik düğmesine (=) tıklandığında çağrılır.
    //xml'de onClick'te tanımlandı.
    fun onEqual(view: View) {
        if (lastNumeric) {
            var value = inputTextView.text.toString()
            val expression = value.replace("×", "*")
            val result = evaluateExpression(expression)
            outputTextView.text = result.toString()
        }
    }

    //Bu fonksiyon, bir matematiksel ifadeyi değerlendirir ve sonucunu döndürür.
    //xml'de onClick'te tanımlandı.
    private fun evaluateExpression(expression: String): Double? {
        return try {
            val result = ExpressionBuilder(expression).build().evaluate()
            if (result.isNaN()) {
                Double.NaN
            } else {
                result
            }
        } catch (ex: ArithmeticException) {
            Double.NaN
        }
    }

    //Bu fonksiyon, verilen bir metinde işlem operatörlerinin eklenip eklenmediğini kontrol eder.
    //xml'de onClick'te tanımlandı.
    private fun isOperatorAdded(value: String): Boolean {
        return value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
    }
}
