package com.coneill.climbit.view.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.climbit.R

/**
 * Custom view used for the Logbook card that displays grade and number of items.
 */
class GradeCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_grade_card, this, true)
        val gradeView: TextView = findViewById(R.id.gradeTextView)
        val numItemsView: TextView = findViewById(R.id.numItemsTextView)
    }


}