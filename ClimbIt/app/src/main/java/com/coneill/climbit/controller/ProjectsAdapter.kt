package com.coneill.climbit.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.model.Project
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_project_card.view.*

class ProjectsAdapter(private val dataset: List<Project>):
    RecyclerView.Adapter<ProjectsAdapter.ProjectCardViewHolder>() {

    class ProjectCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.nameTextView
        val grade: TextView = view.gradeTextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_project_card, parent, false)
        return ProjectCardViewHolder(
            view
        )
    }

    override fun getItemCount(): Int = dataset.size

    /**
     * Called when view is bound to the view holder.
     * Sets the name and grade fields of the project card.
     */
    override fun onBindViewHolder(holder: ProjectCardViewHolder, position: Int) {
        holder.name.text = dataset[position].name
        holder.grade.text = dataset[position].grade.toString()
    }


}