package com.coneill.climbit.view.activities

import android.os.AsyncTask
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
        updateDataset()
    }

    /**
     * Called by MyDeleteDialog when delete button is pressed
     */
    override fun onDelete(baseClimb: BaseClimb) {
        val project = baseClimb as Project
        Model.projects.remove(project)
        updateDataset()
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
        updateDataset()
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
        updateDataset()
    }

    private fun updateDataset() {
        UpdateDatasetTask(this).execute(gradeFilter, cragFilter)
    }

    fun replaceDatasetWith(newDataset: MutableList<Project>) {
        dataset.removeAll { true }
        for (project in newDataset) {
            dataset.add(project)
        }
        projectsAdapter.notifyDataSetChanged()
    }

    companion object {
        /**
         * Async function to retrieve the filtered set of projects from the model. Upon completion replaces dataset
         * In ProjectsActivity with the filtered dataset
         */
        private class UpdateDatasetTask internal constructor(val context: ProjectsActivity):
            AsyncTask<String?, String, MutableList<Project>>() {

            /**
             * @param params: gradeFilter: String, cragFilter: String
             */
            override fun doInBackground(vararg params: String?): MutableList<Project> {
                val dataset = Model.projects.toMutableList()
                val gradeFilter = params[0]
                val cragFilter = params[1]
                return filterDataset(dataset, cragFilter, gradeFilter)
            }

            private fun filterDataset(
                dataset: MutableList<Project>,
                cragFilter: String?,
                gradeFilter: String?
            ): MutableList<Project> {
                cragFilter?.let { filter ->
                    dataset.retainAll {
                        it.crag.toLowerCase(Locale.ROOT) == filter.toLowerCase(
                            Locale.ROOT
                        )
                    }
                }
                gradeFilter?.let { filter ->
                    dataset.retainAll { it.grade == filter.toInt() }
                }
                return dataset
            }

            override fun onPostExecute(result: MutableList<Project>?) {
                super.onPostExecute(result)
                result?.let { context.replaceDatasetWith(it) }
            }

        }
    }
}
