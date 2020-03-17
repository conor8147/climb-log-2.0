package com.coneill.climbit.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_climb_card.view.*


class ClimbsAdapter(private val dataset: List<Climb>) :
    RecyclerView.Adapter<ClimbsAdapter.ClimbViewHolder>() {

    var expandedPosition = -1

    class ClimbViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.climbNameTextView
        val star1: ImageView = view.star1
        val star2: ImageView = view.star2
        val star3: ImageView = view.star3

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClimbsAdapter.ClimbViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_climb_card, parent, false)

        return ClimbViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: ClimbViewHolder, position: Int) {
        val climb = dataset[position]
        holder.nameTextView.text = climb.name

        if (climb.stars > 0) {
            holder.star1.isVisible = true
        }
        if (climb.stars > 1) {
            holder.star2.isVisible = true
        }
        if (climb.stars > 2) {
            holder.star3.isVisible = true
        }

    }

}
