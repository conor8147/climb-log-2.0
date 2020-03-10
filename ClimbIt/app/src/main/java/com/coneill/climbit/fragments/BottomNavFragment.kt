package com.coneill.climbit.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.coneill.climbit.activities.ProjectsActivity
import com.coneill.climbit.activities.TestActivity

import com.example.climbit.R

/**
 * Fragment class for the bottom navigation bar. Used to navigate between the Home,
 * Projects, and Logbook activities.
 */
class BottomNavFragment : Fragment() {

    var icon: Int = HOME
        set(newIcon) {
            setHighlightedIcon(newIcon)
            field = newIcon
        }

    private var homeIcon: ImageView? = null
    private var bookIcon: ImageView? = null
    private var heartIcon: ImageView? = null

    private var homeBackground: ImageView? = null
    private var bookBackground: ImageView? = null
    private var heartBackground: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =  inflater.inflate(R.layout.fragment_bottom_nav, container, false)

        homeIcon = view.findViewById(R.id.home_icon)
        bookIcon = view.findViewById(R.id.book_icon)
        heartIcon = view.findViewById(R.id.heart_icon)

        homeBackground = view.findViewById(R.id.home_icon_background)
        bookBackground = view.findViewById(R.id.book_icon_background)
        heartBackground = view.findViewById(R.id.heart_icon_background)

        homeBackground?.setOnClickListener {
            if (activity !is TestActivity) {
                startActivity(
                    Intent(context, TestActivity::class.java)
                )
            }
        }
        bookBackground?.setOnClickListener {
        }
        heartBackground?.setOnClickListener {
            if (activity !is ProjectsActivity) {
                startActivity(
                    Intent(context, ProjectsActivity::class.java)
                )
            }
        }

        // Need to intially set icon when class is initialised
        setHighlightedIcon(icon)

        return view
    }

    private fun clearHighlighting() {
        bookIcon?.setImageResource(R.drawable.ic_book)
        homeIcon?.setImageResource(R.drawable.ic_home)
        heartIcon?.setImageResource(R.drawable.ic_heart)

        bookBackground?.setImageResource(R.drawable.icon_background_transparent)
        homeBackground?.setImageResource(R.drawable.icon_background_transparent)
        heartBackground?.setImageResource(R.drawable.icon_background_transparent)

    }

    /**
     * Sets the background of the specified icon to orange, and the stroke color of the icon to white
     * @param whichIcon: Should be one: HOME, HEART, BOOK (these are companion fields to BottomNavFragment
     */
    private fun setHighlightedIcon(whichIcon: Int) {
        clearHighlighting()
        when (whichIcon) {
            HOME -> {
                homeIcon?.setImageResource(R.drawable.ic_home_light)
                homeBackground?.setImageResource(R.drawable.icon_background)
            }
            BOOK -> {
                bookIcon?.setImageResource(R.drawable.ic_book_light)
                bookBackground?.setImageResource(R.drawable.icon_background)
            }
            else -> {
                heartIcon?.setImageResource(R.drawable.ic_heart_light)
                heartBackground?.setImageResource(R.drawable.icon_background)
            }
        }
    }

    companion object {
        const val HOME = 0
        const val HEART = 1
        const val BOOK = 2
    }

}

