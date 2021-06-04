package com.example.vku_decuong_2.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class FontCustomTextView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    private lateinit var fontTypeface: Typeface

    init {
        setFont()
    }

    fun setFont() {
        fontTypeface = Typeface.createFromAsset(context.assets, "fonts/greatvibes_regular.otf")
        val typeface: Typeface = fontTypeface
        setTypeface(typeface)
    }

}