package com.coneill.climbit.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.DialogFragment
import com.coneill.climbit.view.activities.ProjectsActivity
import com.example.climbit.R

class FilterProjectDialog(val parent: ProjectsActivity): DialogFragment() {

    private var listener: OnFiltersEnabledListener? = null

    private lateinit var cragEditText: EditText
    private lateinit var gradeEditText: EditText

    private lateinit var cragSwitch: Switch
    private lateinit var gradeSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.filter_projects_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initViews(layout)

        return layout
    }

    private fun initViews(v: View) {
        cragEditText = v.findViewById(R.id.cragEditText)
        gradeEditText = v.findViewById(R.id.gradeEditText)
        cragSwitch = v.findViewById(R.id.cragSwitch)
        gradeSwitch = v.findViewById(R.id.gradeSwitch)

        v.findViewById<Button>(R.id.doneButton).setOnClickListener {
            dismiss()
        }

        if (parent.cragFilter != null) {
            cragSwitch.isChecked = true
            cragEditText.setText(parent.cragFilter)
        }
        if (parent.gradeFilter != null) {
            gradeSwitch.isChecked = true
            gradeEditText.setText(parent.gradeFilter)
        }

        cragSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && cragEditText.text.toString() != "") {
                listener?.onSwitchEnabled(CRAG, cragEditText.text.toString())
            } else if (!isChecked) {
                listener?.onSwitchDisabled(CRAG)
            }
        }

        gradeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && gradeEditText.text.toString() != "") {
                listener?.onSwitchEnabled(GRADE, gradeEditText.text.toString())
            } else if (!isChecked){
                listener?.onSwitchDisabled(GRADE)
            }
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
        const val CRAG = 0
        const val GRADE = 1

        fun newInstance(parent: ProjectsActivity): FilterProjectDialog {
            return FilterProjectDialog(parent)
        }
    }
}
