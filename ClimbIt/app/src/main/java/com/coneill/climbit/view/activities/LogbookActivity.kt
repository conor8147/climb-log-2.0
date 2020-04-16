package com.coneill.climbit.view.activities

import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.view.fragments.AddClimbDialog
import com.coneill.climbit.view.fragments.FilterClimbDialog
import com.coneill.climbit.controller.ClimbsAdapter
import com.coneill.climbit.model.BaseClimb
import com.coneill.climbit.model.Climb
import com.coneill.climbit.model.Model
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.coneill.climbit.view.fragments.SearchDialog
import com.coneill.climbit.view.views.ActionBarView
import com.example.climbit.R
import java.util.*


class LogbookActivity : AppCompatActivity(), AddClimbDialog.OnClimbAddedListener,
    FilterClimbDialog.OnFiltersEnabledListener,
    MyDeleteDialog.OnDeleteListener,
    SearchDialog.OnSearchListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var climbsAdapter: ClimbsAdapter

    private val dataset  = getClimbsCopy()

    var cragFilter: String? = null
    var styleFilter: String? = null
    var starFilter: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)
        initViews()
        initActionBar()
    }

    private fun initViews() {
        viewManager = LinearLayoutManager(this)
        recyclerView = findViewById(R.id.recyclerView)

        climbsAdapter = ClimbsAdapter(dataset, this)

        // Set the view manager and view adapter for the recyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = climbsAdapter
            setDividers()
        }
    }

    /**
     * Returns a deep copy of Model.climbs
     */
    fun getClimbsCopy(): MutableMap<Int, MutableList<Climb>> {
        val newMap: MutableMap<Int, MutableList<Climb>> = mutableMapOf()
        for (entry in Model.climbs) {
            val grade = entry.toPair().first
            val climbList = entry.toPair().second
            newMap[grade] = climbList.toMutableList()
        }
        return newMap
    }

    /**
     * Set Decorations for recyclerView
     */
    private fun RecyclerView.setDividers() {
        val attrs = intArrayOf(android.R.attr.listDivider)
        val a: TypedArray = obtainStyledAttributes(attrs)
        val divider = a.getDrawable(0)
        val inset = 32 // dp
        val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
        a.recycle()

        val dividerItemDecoration = DividerItemDecoration(
            this.context,
            viewManager.orientation
        )
        dividerItemDecoration.setDrawable(insetDivider)
        this.addItemDecoration(dividerItemDecoration)

    }

    /**
     * Initialise the buttons (filter, search, and add) in the action bar and set their
     * onClickListeners
     */
    private fun initActionBar() {
        val fragmentManager = supportFragmentManager
        val actionBarView: ActionBarView = findViewById(R.id.actionBarView)

        actionBarView.addButton.setOnClickListener {
            AddClimbDialog().show(fragmentManager, null)
        }

        actionBarView.filterButton.setOnClickListener {
            FilterClimbDialog.newInstance(this).show(fragmentManager, null)
        }

        actionBarView.searchButton.setOnClickListener {
            SearchDialog().show(fragmentManager, "search dialog")
        }
    }

    /**
     * Called by AddClimbDialog when a new climb is successfully added to the dataset
     */
    override fun onClimbAdded() {
        updateDataset()
        climbsAdapter.notifyDataSetChanged()
    }

    /**
     * Called by FilterClimbsDialog when a new filter switch is enabled.
     * switch id will be one of FilterClimbsDialog.[CRAG, GRADE, STARS]
     */
    override fun onSwitchEnabled(filterId: Int, filterContent: String) {
        if (filterContent == "") return
        when (filterId) {
            FilterClimbDialog.CRAG -> {
                cragFilter = filterContent
            }
            FilterClimbDialog.STYLE -> {
                styleFilter = filterContent
            }
            FilterClimbDialog.STARS -> {
                starFilter = filterContent.toInt()
            }
        }
        updateDataset()
    }

    override fun onSwitchDisabled(filterId: Int) {
        when (filterId) {
            FilterClimbDialog.CRAG -> {
                cragFilter = null
            }
            FilterClimbDialog.STYLE -> {
                styleFilter = null
            }
            FilterClimbDialog.STARS -> {
                starFilter = null
            }
        }
        updateDataset()
    }

    override fun onDelete(baseClimb: BaseClimb) {
        val climb: Climb = baseClimb as Climb
        Model.climbs[climb.grade]?.remove(climb) != null
        updateDataset()
        Toast.makeText(this, "${climb.name} deleted.", Toast.LENGTH_LONG).show()
    }

    /**
     * Search for the first occurrence of a climb that starts with the given query.
     * Case-insensitive
     */
    override fun onSearch(climbName: String) {
        val climb: Climb = Model.climbs.values.flatten().find {
            climbName.toLowerCase(Locale.ROOT).startsWith(it.name.toLowerCase(Locale.ROOT))
        } ?: return

        val position = Model.climbs.keys.indexOf(climb.grade)
        recyclerView.scrollToPosition(position)

    }

    private fun updateDataset() {
        UpdateDatasetTask(this).execute(styleFilter, cragFilter, starFilter)
    }

    private fun replaceDatasetWith(newDataset: MutableMap<Int, MutableList<Climb>>) {
        dataset.clear()
        for (entry in newDataset) {
            val grade = entry.toPair().first
            val climbList = entry.toPair().second
            dataset[grade] = mutableListOf()
            for (climb in climbList) {
                dataset[grade]?.add(climb)
            }
        }
        climbsAdapter.notifyDataSetChanged()
    }


    companion object {
        /**
         * Async function to retrieve the filtered set of projects from the model. Upon completion replaces dataset
         * In LogbookActivity with the filtered dataset
         * Should be defined in a companion object to prevent memory leaks
         */
        private class UpdateDatasetTask internal constructor(val context: LogbookActivity):
            AsyncTask<Any?, String, MutableMap<Int, MutableList<Climb>>>() {

            /**
             * @param params in order: styleFilter: String, cragFilter: String, starsFilter: Int
             */
            override fun doInBackground(vararg params: Any?): MutableMap<Int, MutableList<Climb>>  {
                val dataset = context.getClimbsCopy()
                val styleFilter = params[0] as String?
                val cragFilter = params[1] as String?
                val starsFilter = params[2] as Int?
                return filterDataset(dataset, cragFilter, styleFilter, starsFilter)
            }

            private fun filterDataset(
                dataset: MutableMap<Int, MutableList<Climb>>,
                cragFilter: String?,
                styleFilter: String?,
                starsFilter: Int?
            ): MutableMap<Int, MutableList<Climb>> {

                for (entry in dataset) {
                    val grade = entry.toPair().first
                    val climbsList = entry.toPair().second

                    cragFilter?.let { filter ->
                        climbsList.retainAll { it.crag.toLowerCase(Locale.ROOT) == filter.toLowerCase(Locale.ROOT) }
                    }

                    styleFilter?.let { filter ->
                        climbsList.retainAll { it.style.toLowerCase(Locale.ROOT) == filter.toLowerCase(Locale.ROOT) }
                    }

                    starsFilter?.let { filter ->
                        climbsList.retainAll { it.stars == filter }
                    }

                }

                return dataset
            }

            override fun onPostExecute(result: MutableMap<Int, MutableList<Climb>>?) {
                super.onPostExecute(result)
                result?.let { context.replaceDatasetWith(it) }
            }

        }
    }
}
