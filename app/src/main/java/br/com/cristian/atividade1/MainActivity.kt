package br.com.hussan.atividade1

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import br.com.hussan.atividade1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var actualFontSize = 0
    private var actualText = ""
    private var actualColor = R.color.black

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        setViewsListener()
    }

    private fun setViewsListener() = binding.run {
        seekbarLetterSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, size: Int, b: Boolean) {
                actualFontSize = size
                txtResult.textSize = actualFontSize.toFloat()
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar) = Unit
        })

        edtText.doOnTextChanged { text, _, _, _ ->
            actualText = if (checkboxUppercase.isChecked)
                text.toString().uppercase()
            else text.toString().lowercase()

            txtResult.text = actualText
        }

        checkboxBold.setOnCheckedChangeListener { _, checked ->
            setStyle(checked, checkboxItalic.isChecked)
        }

        checkboxItalic.setOnCheckedChangeListener { _, checked ->
            setStyle(checkboxBold.isChecked, checked)
        }

        checkboxUppercase.setOnCheckedChangeListener { _, checked ->
            val actualText = if (checked)
                actualText.uppercase()
            else actualText.lowercase()

            txtResult.text = actualText
        }

        radioRed.setOnCheckedChangeListener { _, checked -> changeColor(checked, R.color.red) }
        radioBlue.setOnCheckedChangeListener { _, checked -> changeColor(checked, R.color.blue) }
        radioGreen.setOnCheckedChangeListener { _, checked -> changeColor(checked, R.color.green) }
    }

    private fun changeColor(checked: Boolean, @ColorRes selectedColor: Int) {
        if (checked) {
            actualColor = selectedColor
            binding.txtResult.setTextColor(ContextCompat.getColor(this@MainActivity, actualColor))
        }
    }

    private fun setStyle(bold: Boolean, italic: Boolean) {
        val style = when {
            bold && italic -> { Typeface.BOLD_ITALIC }
            !bold && !italic -> { Typeface.NORMAL }
            bold && !italic -> { Typeface.BOLD }
            else -> { Typeface.ITALIC }

        }
        binding.txtResult.setTypeface(null, style)
    }
}
