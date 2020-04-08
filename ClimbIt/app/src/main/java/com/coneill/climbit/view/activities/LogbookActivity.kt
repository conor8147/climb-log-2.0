package com.coneill.climbit.view.activities

import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.view.fragments.AddClimbDialog
import com.coneill.climbit.view.fragments.FilterClimbDialog
import com.coneill.climbit.controller.ClimbsAdapter
import com.coneill.climbit.model.Climb
import com.coneill.climbit.model.Model
import com.coneill.climbit.view.fragments.MyDeleteDialog
import com.coneill.climbit.view.views.ActionBarView
import com.example.climbit.R


class LogbookActivity : AppCompatActivity(), AddClimbDialog.OnClimbAddedListener, FilterClimbDialog.OnFiltersEnabledListener, MyDeleteDialog.OnDeleteListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter: ClimbsAdapter

    private val gradesList = Model.grades

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

        viewAdapter = ClimbsAdapter(gradesList, this)

        // Set the view manager and view adapter for the recyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            setDividers()
        }
    }

    /**
     * Set Decorations for recyclerView
     */
    private fun RecyclerView.setDividers() {
        val ATTRS = intArrayOf(android.R.attr.listDivider)
        val a: TypedArray = obtainStyledAttributes(ATTRS)
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
    }

    /**
     * Called by AddClimbDialog when a new climb is successfully added to the dataset
     */
    override fun onClimbAdded() {
        viewAdapter.notifyDataSetChanged()
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
    }

    override fun onDelete(climb: Climb) {
        Model.climbs[climb.grade]?.remove(climb) != null
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(this, "${climb.name} deleted.", Toast.LENGTH_LONG).show()
    }
}
