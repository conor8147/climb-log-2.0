package com.coneill.climbit.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.coneill.climbit.view.activities.HomeActivity
import com.coneill.climbit.view.activities.LogbookActivity
import com.coneill.climbit.view.activities.ProjectsActivity

import com.example.climbit.R

/**
 * Fragment class for the bottom navigation bar. Used to navigate between the Home,
 * Projects, and Logbook activities.
 */
class BottomNavFragment : Fragment() {

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
            startActivity(Intent(context, HomeActivity::class.java))
        }
        bookBackground?.setOnClickListener {
            startActivity(
                Intent(context, LogbookActivity::class.java)
            )
        }
        heartBackground?.setOnClickListener {
            startActivity(
                Intent(context, ProjectsActivity::class.java)
            )
        }

        // Need to intially set icon when class is initialised
        setIcons()

        return view
    }

    private fun resetIcons() {
        bookIcon?.setImageResource(R.drawable.ic_book)
        homeIcon?.setImageResource(R.drawable.ic_home)
        heartIcon?.setImageResource(R.drawable.ic_heart)

        bookBackground?.setImageResource(R.drawable.icon_background_transparent)
        homeBackground?.setImageResource(R.drawable.icon_background_transparent)
        heartBackground?.setImageResource(R.drawable.icon_background_transparent)

        bookBackground?.isClickable = true
        homeBackground?.isClickable = true
        heartBackground?.isClickable = true

    }

    /**
     * Sets the background of the specified icon to orange, and the stroke color of the icon to white
     * Also sets the icon for the current activity to be non-clickable
     */
    private fun setIcons() {
        resetIcons()
        when (activity) {
            is HomeActivity -> {
                homeIcon?.setImageResource(R.drawable.ic_home_light)
                homeBackground?.setImageResource(R.drawable.icon_background)
                homeBackground?.isClickable = false
            }
            is LogbookActivity -> {
                bookIcon?.setImageResource(R.drawable.ic_book_light)
                bookBackground?.setImageResource(R.drawable.icon_background)
                bookBackground?.isClickable = false
            }
            else -> {
                heartIcon?.setImageResource(R.drawable.ic_heart_light)
                heartBackground?.setImageResource(R.drawable.icon_background)
                heartBackground?.isClickable = false
            }
        }
    }
}

