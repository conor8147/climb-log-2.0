package com.coneill.climbit.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.climbit.R

class ActionBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    val searchButton: ImageButton
    val addButton: ImageButton
    val filterButton: ImageButton

    init {
        LayoutInflater.from(context).inflate(R.layout.view_action_bar, this, true)
        searchButton = findViewById(R.id.searchButton)
        addButton = findViewById(R.id.addButton)
        filterButton = findViewById(R.id.filterButton)
    }

}