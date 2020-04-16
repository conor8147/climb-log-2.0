package com.coneill.climbit.view.activities

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.coneill.climbit.controller.doShortVibrate
import com.coneill.climbit.model.*
import com.example.climbit.R

import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class HomeActivity : AppCompatActivity(), OnChartValueSelectedListener {

    private lateinit var graph: PieChart
    var numOnsights = 0
    var numFlashes = 0
    var numRedpoints = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initPieChart()
        CalculateScoreTask(this).execute()
    }

    private fun initPieChart() {
        graph = findViewById(R.id.chart)
        updateGraphValues(0.5f, 0.3f, 0.2f)
        UpdateGraphTask(this).execute()
    }

    fun updateScore(newScore: Int) {
        findViewById<TextView>(R.id.totalScoreTextView).text = newScore.toString()
    }

    fun updateGraphValues(redpointValue: Float, flashValue: Float, onsightValue: Float) {
        val entries: MutableList<PieEntry> = mutableListOf (
            PieEntry(redpointValue, Climb.REDPOINT),
            PieEntry(flashValue, Climb.FLASH),
            PieEntry(onsightValue, Climb.ONSIGHT)
        )
        val set = PieDataSet(entries, "").apply {
            colors = listOf(
                ContextCompat.getColor(applicationContext, R.color.redpoint),
                ContextCompat.getColor(applicationContext, R.color.flash),
                ContextCompat.getColor(applicationContext, R.color.onsight)
            )
        }
        val data = PieData(set).apply { setValueTextSize(0f) }

        graph.apply {
            setDrawMarkers(false)
            setDrawEntryLabels(false)
            setDrawRoundedSlices(true)
            description.isEnabled = false
            legend.isEnabled = false
            setOnChartValueSelectedListener(this@HomeActivity)
            setCenterTextTypeface(resources.getFont(R.font.sf_pro_regular))
            setCenterTextColor(ContextCompat.getColor(this@HomeActivity, R.color.blueGreyText))
            this.data = data
        }

    }

    companion object {
        class CalculateScoreTask(val context: HomeActivity): AsyncTask<Any, Any, Int>() {

            override fun doInBackground(vararg params: Any?): Int =
                Model.climbs.values.flatten().sumBy { it.grade }

            override fun onPostExecute(score: Int?) {
                score?.let {
                    context.updateScore(it)
                }
            }
        }

        class UpdateGraphTask(val context: HomeActivity): AsyncTask<Any, Any, List<Int>>() {

            override fun doInBackground(vararg params: Any?): List<Int> {
                val allClimbs = Model.climbs.values.flatten()
                val total = allClimbs.size
                val numOnsighted = allClimbs.filter{ it.style == Climb.ONSIGHT }.size
                val numFlashed = allClimbs.filter{ it.style == Climb.FLASH }.size
                val numRedpointed = allClimbs.filter{ it.style == Climb.REDPOINT }.size

                return listOf(total, numOnsighted, numFlashed, numRedpointed)
            }

            override fun onPostExecute(result: List<Int>?) {
                super.onPostExecute(result)
                val total = result?.get(0)?.toFloat() ?: return
                val fractionOnsighted: Float = result[1] / total
                val fractionFlashed: Float = result[2] / total
                val fractionRedpointed: Float = 1.0f - (fractionOnsighted + fractionFlashed)

                context.numOnsights = result[1]
                context.numFlashes = result[2]
                context.numRedpoints = result[3]

                context.updateGraphValues(fractionRedpointed, fractionFlashed, fractionOnsighted)
            }
        }
    }

    /**
     * Called when Pie Chart is initialised and no slice has been selected
     */
    override fun onNothingSelected() {}

    /**
     * Called when pie chart is initialised and a slice is clicked on
     */
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        val entry = e as PieEntry?
        doShortVibrate(this)
        graph.centerText = "${getString(R.string.climbs)}\n" +
                when (entry?.label) {
                    Climb.REDPOINT -> getString(R.string.redpointed) + ":\n$numRedpoints"
                    Climb.FLASH -> getString(R.string.flashed) + ":\n$numFlashes"
                    else -> getString(R.string.onsighted) + ":\n$numOnsights"
                }
    }
}
