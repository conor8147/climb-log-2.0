package com.coneill.climbit.controller

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.model.Project
import com.coneill.climbit.view.activities.ProjectsActivity
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.example.climbit.R
import kotlinx.android.synthetic.main.view_project_card.view.*


class ProjectsAdapter(private val dataset: MutableList<Project>, private val projectsActivity: ProjectsActivity):
    RecyclerView.Adapter<ProjectsAdapter.ProjectCardViewHolder>() {

    class ProjectCardViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.nameTextView
        val grade: TextView = view.gradeTextView
        val crag: TextView = view.cragTextView
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProjectCardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_project_card, parent, false)
        return ProjectCardViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    /**
     * Called when view is bound to the view holder.
     * Sets the name and grade fields of the project card.
     */
    override fun onBindViewHolder(holder: ProjectCardViewHolder, position: Int) {
        val project = dataset[position]
        holder.name.text = project.name
        holder.grade.text = project.grade.toString()
        holder.crag.text = project.crag
        val fragmentManager = projectsActivity.supportFragmentManager
        holder.view.setOnLongClickListener {            holder.view.startAnimation(AnimationUtils.loadAnimation(projectsActivity, R.anim.shake))
            doShortVibrate(projectsActivity)
            MyDeleteDialog(project).show(fragmentManager, null)
            true

        }
    }


}