package com.coneill.climbit.activities

import android.content.res.TypedArray
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coneill.climbit.fragments.AddClimbDialog
import com.coneill.climbit.model.ClimbsAdapter
import com.coneill.climbit.model.Singleton
import com.coneill.climbit.views.ActionBarView
import com.example.climbit.R
import android.R as AR


class LogbookActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logbook)

        val viewManager = LinearLayoutManager(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val viewAdapter = ClimbsAdapter(Singleton.climbs)


        val ATTRS = intArrayOf(AR.attr.listDivider)
        val a: TypedArray = obtainStyledAttributes(ATTRS)
        val divider = a.getDrawable(0)
        val inset = 32 // dp
        val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
        a.recycle()

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            viewManager.orientation
        )
        dividerItemDecoration.setDrawable(insetDivider)

        // Set the view manager and view adapter for the recyclerView
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(dividerItemDecoration)
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
            AddClimbDialog().show(fragmentManager, "add_climb_dialog")
        }
    }
}
