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
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.coneill.climbit.model.Model

import com.example.climbit.R

/**
 * Class for the dialog used to add a climb to the projects board
 */
class AddProjectDialog : DialogFragment() {

    private var listener: OnProjectAddedListener? = null
    private lateinit var nameEditText: EditText
    private lateinit var gradeEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val layout = inflater.inflate(R.layout.fragment_add_project_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        layout.findViewById<Button>(R.id.addButton).setOnClickListener {
            if (addProject()) {
                dismiss()
            }
        }
        nameEditText = layout.findViewById(R.id.nameEditText)
        gradeEditText = layout.findViewById(R.id.styleEditText)

        return layout
    }

    /**
     * Attempts to add project to the Singleton.
     * @return true if the project is successfully added
     */
    private fun addProject(): Boolean {
        val name = nameEditText.text.toString()
        val grade = gradeEditText.text.toString().toIntOrNull()

        return if (!isGradeCorrectFormat(grade)) {
            Toast.makeText(context, "Invalid grade format.", Toast.LENGTH_LONG).show()
            false
        } else if (!isNameCorrectFormat(name)) {
            Toast.makeText(context, "Name is too long.", Toast.LENGTH_LONG).show()
            false
        } else {
            Model.addProject(
                name,
                grade!! // Grade is confirmed not null in first if block
            )
            listener?.onProjectAdded()
            true
        }
    }

    /**
     * Checks if the climb grade is in an acceptable format, ie integer in [0, 40]
     */
    private fun isGradeCorrectFormat(grade: Int?): Boolean {
        return grade != null &&
                grade >= 0 &&
                grade < 100
    }

    /**
     * Checks if the climb name is in an acceptable format.
     */
    private fun isNameCorrectFormat(name: String): Boolean {
        return name.length < 50
    }

    /**
     * Called when fragment is attached to the window, checks that the context implements
     * the OnProjectAdded Listener, otherwise..
     * @throws RuntimeException
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProjectAddedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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
    interface OnProjectAddedListener {
        fun onProjectAdded()
    }
}
