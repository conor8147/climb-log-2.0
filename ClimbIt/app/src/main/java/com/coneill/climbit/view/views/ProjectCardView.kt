package com.coneill.climbit.view.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.climbit.R

/**
 * Custom view for Project Cards (used to display the climbs on the users projects board).
 */
class ProjectCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    val gradeTextView: TextView
    val nameTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_project_card, this, true)
        gradeTextView = findViewById(R.id.gradeTextView)
        nameTextView = findViewById(R.id.nameTextView)
    }

}