package com.coneill.climbit.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.climbit.R

class ProjectsAdapter(private val dataset: List<Project>):
    RecyclerView.Adapter<ProjectsAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nameTextView)
        val grade: TextView = view.findViewById(R.id.gradeTextView)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_project_card, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = dataset.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = dataset[position].name
        holder.grade.text = dataset[position].grade
    }


}