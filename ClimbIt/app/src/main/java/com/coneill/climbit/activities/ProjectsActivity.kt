package com.coneill.climbit.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.fragments.AddProjectDialog
import com.coneill.climbit.fragments.BottomNavFragment
import com.coneill.climbit.model.Project
import com.coneill.climbit.model.ProjectsAdapter
import com.coneill.climbit.model.Singleton
import com.coneill.climbit.views.ActionBarView
import com.example.climbit.R


class ProjectsActivity : AppCompatActivity(), AddProjectDialog.OnProjectAddedListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var projectList: List<Project>

    private lateinit var actionBarView: ActionBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)

        projectList = Singleton.projects

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProjectsAdapter(projectList)

        val fragmentManager = supportFragmentManager

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        actionBarView = findViewById(R.id.actionBarView)

        actionBarView.addButton.setOnClickListener {
            AddProjectDialog().show(fragmentManager, "add_project_dialog")
        }

    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        if (fragment is BottomNavFragment) {
            fragment.icon = BottomNavFragment.HEART
        }
    }

    override fun onProjectAdded() {
        Toast.makeText(this, "Project Added!", Toast.LENGTH_LONG).show()
        viewAdapter.notifyDataSetChanged()
    }
}
