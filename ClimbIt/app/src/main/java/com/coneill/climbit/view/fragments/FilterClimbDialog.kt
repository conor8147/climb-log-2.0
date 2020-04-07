package com.coneill.climbit.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import com.coneill.climbit.view.activities.LogbookActivity
import com.example.climbit.R

class FilterClimbDialog: DialogFragment() {

    private var parent: LogbookActivity? = null
    private var listener: OnFiltersEnabledListener? = null

    private lateinit var cragEditText: EditText
    private lateinit var styleEditText: EditText
    private lateinit var starsEditText: EditText

    private lateinit var cragSwitch: Switch
    private lateinit var styleSwitch: Switch
    private lateinit var starsSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.filter_climbs_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initViews(layout)

        return layout
    }

    private fun initViews(v: View) {
        cragEditText = v.findViewById(R.id.cragEditText)
        styleEditText = v.findViewById(R.id.styleEditText)
        starsEditText = v.findViewById(R.id.starsEditText)
        cragSwitch = v.findViewById(R.id.cragSwitch)
        styleSwitch = v.findViewById(R.id.styleSwitch)
        starsSwitch = v.findViewById(R.id.starsSwitch)

        cragSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listener?.onSwitchEnabled(CRAG, cragEditText.text.toString())
            } else {
                listener?.onSwitchDisabled(CRAG)
            }
        }

        styleSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listener?.onSwitchEnabled(STYLE, styleEditText.text.toString())
            } else {
                listener?.onSwitchDisabled(STYLE)
            }
        }

        starsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                listener?.onSwitchEnabled(STARS, starsEditText.text.toString())
            } else {
                listener?.onSwitchDisabled(STARS)
            }
        }

        if (parent?.starFilter != null) {
            starsSwitch.isChecked = true
            starsEditText.setText(parent?.starFilter!!.toString())
        }
        if (parent?.cragFilter != null) {
            cragSwitch.isChecked = true
            cragEditText.setText(parent?.cragFilter)
        }
        if (parent?.styleFilter != null) {
            styleSwitch.isChecked = true
            styleEditText.setText(parent?.styleFilter)
        }
    }

    /**
     * Called when fragment is attached to the window, checks that the context implements
     * the OnProjectAdded Listener, otherwise..
     * @throws RuntimeException
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFiltersEnabledListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFiltersEnabledListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    interface OnFiltersEnabledListener {
        fun onSwitchEnabled(filterId: Int, filterContent: String)
        fun onSwitchDisabled(filterId: Int)
    }

    companion object {
        fun newInstance(parent: LogbookActivity): FilterClimbDialog {
            val fragment = FilterClimbDialog()
            fragment.parent = parent
            return fragment

        }
        const val CRAG = 0
        const val STYLE = 1
        const val STARS = 2
    }
}
