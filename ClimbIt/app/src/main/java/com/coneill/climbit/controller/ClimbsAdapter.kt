package com.coneill.climbit.controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.model.Model
import com.coneill.climbit.view.views.ClimbCardView
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_grade_card.view.*

class ClimbsAdapter(private val gradesList: List<Int>, private val context: Context) :
    RecyclerView.Adapter<ClimbsAdapter.ClimbViewHolder>() {

    var expandedPosition = -1

    class ClimbViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gradeTextView: TextView = view.gradeTextView
        val numItemsTextView = view.numItemsTextView
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
        var isExpanded = position == expandedPosition
        var grade = gradesList[position]
        val climbsList = Model.climbs.getOrDefault(grade, mutableListOf())

        holder.numItemsTextView.text = context.getString(R.string.items, climbsList.size)

        // When grade card has been selected, expand to show all climbs of that grade
        if (isExpanded) {
            holder.subItems.removeAllViews()
            for (climb in climbsList) {
                val climbCard =
                    ClimbCardView(context)
                climbCard.climb = climb
                holder.subItems.addView(climbCard)
            }
            holder.subItems.visibility = View.VISIBLE
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


}
