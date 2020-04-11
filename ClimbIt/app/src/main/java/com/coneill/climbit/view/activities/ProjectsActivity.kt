package com.coneill.climbit.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.view.fragments.AddProjectDialog
import com.coneill.climbit.controller.ProjectsAdapter
import com.coneill.climbit.model.BaseClimb
import com.coneill.climbit.model.Model
import com.coneill.climbit.model.Project
import com.coneill.climbit.view.fragments.FilterProjectDialog
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.coneill.climbit.view.fragments.SearchDialog
import com.coneill.climbit.view.views.ActionBarView
import com.example.climbit.R
import kotlinx.android.synthetic.main.activity_logbook.*
import java.util.*

/**
 * Activity used for listing users goal climbs.
 */
class ProjectsActivity : AppCompatActivity(),
    AddProjectDialog.OnProjectAddedListener,
    MyDeleteDialog.OnDeleteListener,
    SearchDialog.OnSearchListener,
    FilterProjectDialog.OnFiltersEnabledListener{

    var gradeFilter: String? = null
    var cragFilter: String? = null
    private val dataset = Model.projects.toMutableList()

    private val projectsAdapter = ProjectsAdapter(dataset, this)
    private val viewManager = LinearLayoutManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projects)

        // Set the view manager and view adapter for the recyclerView
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = projectsAdapter
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
            AddProjectDialog().show(fragmentManager, "add project dialog")
        }

        actionBarView.searchButton.setOnClickListener {
            SearchDialog().show(fragmentManager, "search dialog")
        }

        actionBarView.filterButton.setOnClickListener {
            FilterProjectDialog.newInstance(this).show(fragmentManager, "filter projects dialog")
        }
    }

    /**
     * Called whenever the user successfully adds a project to the projects list.
     */
    override fun onProjectAdded() {
        Toast.makeText(this, "Project Added!", Toast.LENGTH_LONG).show()
        resetDataset()
        projectsAdapter.notifyDataSetChanged()
    }

    /**
     * Called by MyDeleteDialog when delete button is pressed
     */
    override fun onDelete(baseClimb: BaseClimb) {
        val project = baseClimb as Project
        Model.projects.remove(project)
        this.projectsAdapter.notifyDataSetChanged()
    }

    /**
     * Search for the first occurrence of a project that starts with the given query.
     * Case-insensitive
     */
    override fun onSearch(climbName: String) {
        val position = dataset.indexOfFirst {
            it.name.toLowerCase(Locale.ROOT).startsWith(climbName.toLowerCase(Locale.ROOT))
        }
        recyclerView.scrollToPosition(position)
    }

    /**
     * Called when a switch is turned on in the filter dialog box.
     */
    override fun onSwitchEnabled(filterId: Int, filterContent: String) {
        when (filterId) {
            FilterProjectDialog.CRAG -> {
                cragFilter = filterContent
            }
            FilterProjectDialog.GRADE -> {
                gradeFilter = filterContent
            }
        }
        resetDataset()
        applyFilters()
    }

    /**
     * Called when a switch is turned off in the filter dialog box
     */
    override fun onSwitchDisabled(filterId: Int) {
        when (filterId) {
            FilterProjectDialog.CRAG -> {
                cragFilter = null
            }
            FilterProjectDialog.GRADE -> {
                gradeFilter = null
            }
        }
        resetDataset()
        applyFilters()
    }

    /**
     * Re-synchronise dataset with Model.projects
     */
    fun resetDataset() {
        dataset.removeAll { true }
        for (project in Model.projects) {
            dataset.add(project)
        }
    }

    /**
     * Apply gradefilter and cragFilter to dataset in place.
     */
    fun applyFilters() {
        if (cragFilter != null) {
            dataset.retainAll { it.crag.toLowerCase(Locale.ROOT) == cragFilter?.toLowerCase(Locale.ROOT) }
        }
        if (gradeFilter != null) {
            dataset.retainAll {
                it.grade == gradeFilter?.toInt()
            }
        }
        projectsAdapter.notifyDataSetChanged()
    }
}
