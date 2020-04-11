package com.coneill.climbit.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.model.Climb
import com.coneill.climbit.model.Model
import com.coneill.climbit.view.activities.LogbookActivity
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.coneill.climbit.view.views.ClimbCardView
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_grade_card.view.*

/**
 * Adapter for populating recyclerview in Logbook with grade cards. Each grade card can be selected to expand into
 * A list of all the climbs of that grade.
 */
class ClimbsAdapter(private val gradesList: List<Int>, private val logbookActivity: LogbookActivity) :
    RecyclerView.Adapter<ClimbsAdapter.ClimbViewHolder>() {

    var expandedPosition = -1
    val fragmentManager: FragmentManager = logbookActivity.supportFragmentManager


    class ClimbViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val gradeTextView: TextView = view.gradeTextView
        val numItemsTextView: TextView = view.numItemsTextView
        val subItems: LinearLayout = view.subItemContainer
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClimbViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_grade_card, parent, false)
        return ClimbViewHolder(view)
    }

    override fun getItemCount(): Int = gradesList.size

    /**
     * Called when a new object is scrolled into view.
     * The linear layout should only be populated with climbs when it is revealed
     */
    override fun onBindViewHolder(holder: ClimbViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        val grade = gradesList[position]
        val climbsList = Model.climbs.getOrDefault(grade, mutableListOf())
        holder.numItemsTextView.text = logbookActivity.getString(R.string.items, climbsList.size)

        // When grade card has been selected, expand to show all climbs of that grade
        if (isExpanded) {
            expandHolder(holder, position, climbsList)
        } else {
            holder.subItems.visibility = View.GONE
            holder.subItems.removeAllViews()
        }

        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            notifyItemChanged(position)
        }
        holder.gradeTextView.text = gradesList[position].toString()
    }

    /**
     * Display all the climbs belonging to the given grade card (holder)
     */
    fun expandHolder(holder: ClimbViewHolder, position: Int, provisionalClimbsList: List<Climb>? = null) {
        val climbsList = if (provisionalClimbsList == null) {
            val grade = gradesList[position]
            Model.climbs.getOrDefault(grade, mutableListOf())
        } else {
            provisionalClimbsList
        }

        holder.subItems.removeAllViews()
        for (climb in climbsList) {
            val climbCard = ClimbCardView(logbookActivity, climb)
            climbCard.setOnLongClickListener {
                MyDeleteDialog(climb).show(fragmentManager, null)
                true
            }
            holder.subItems.addView(climbCard)
        }
        holder.subItems.visibility = View.VISIBLE
    }
}
