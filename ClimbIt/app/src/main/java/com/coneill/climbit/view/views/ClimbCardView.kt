package com.coneill.climbit.view.views

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.constraintlayout.widget.ConstraintLayout
import com.coneill.climbit.model.Climb
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_climb_card.view.*

/**
 * Custom view used for the Climb card that displays basic climb details
 */
class ClimbCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
): ConstraintLayout(context, attrs, defStyle) {

    val layout: View = LayoutInflater.from(context).inflate(R.layout.view_climb_card, this, true)
    private val nameTextView: TextView = layout.climbNameTextView


    constructor(context: Context, climb: Climb) : this(context) {
        nameTextView.text = climb.name
        climb.let {
            setStars(it.stars)
            setStyleAccent(it.style)
            nameTextView.text = it.name
        }
    }

    private fun setStars(numStars: Int) {
        if (numStars > 0) {
            layout.star1.visibility = View.VISIBLE
        }
        if (numStars > 1) {
            layout.star2.visibility = View.VISIBLE
        }
        if (numStars > 2) {
            layout.star3.visibility = View.VISIBLE
        }
    }

    /**
     * Set the colour of the small circle left of the climb name based on the style.
     * style should be one of: Climb.ONSIGHT, Climb.FLASH, Climb.REDPOINT
     */
    private fun setStyleAccent(style: String) {
        val colour: Int = when (style) {
            Climb.REDPOINT -> R.drawable.style_background_redpoint
            Climb.FLASH -> R.drawable.style_background_flash
            else -> R.drawable.style_background_onsight
        }

        layout.findViewById<View>(R.id.styleAccent).background = getDrawable(context, colour)
    }

}
