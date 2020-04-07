package com.coneill.climbit.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.view.fragments.AddProjectDialog
import com.coneill.climbit.controller.ProjectsAdapter
import com.coneill.climbit.model.Model
import com.coneill.climbit.view.views.ActionBarView
import com.example.climbit.R

/**
 * Activity used for listing users goal climbs.
 */
class ProjectsActivity : AppCompatActivity(), AddProjectDialog.OnProjectAddedListener {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)

        viewManager = LinearLayoutManager(this)
        viewAdapter =
            ProjectsAdapter(Model.projects)

        // Set the view manager and view adapter for the recyclerView
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        initActionBar()
    }

    /**
     * Initialise the buttons (filter, search, and add) in the action bar and set their
     * onClickListeners
     */
    private fun initActionBar() {
        val fragmentManager = supportFragmentManager
        val actionBarView: ActionBarView = findViewById(R.id.actionBarView)

        actionBarView.addButton.setOnClickListener {
            AddProjectDialog().show(fragmentManager, "add_project_dialog")
        }
    }

    /**
     * Called whenever the user successfully adds a project to the projects list.
     */
    override fun onProjectAdded() {
        Toast.makeText(this, "Project Added!", Toast.LENGTH_LONG).show()
        viewAdapter.notifyDataSetChanged()
    }
}
