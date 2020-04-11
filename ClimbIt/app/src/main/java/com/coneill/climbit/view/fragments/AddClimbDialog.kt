package com.coneill.climbit.view.fragments


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.coneill.climbit.model.Climb
import com.coneill.climbit.model.Model
import com.example.climbit.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Dialog used for adding climbs to the dataset.
 */
class AddClimbDialog: DialogFragment() {

    private var listener: OnClimbAddedListener? = null
    lateinit var nameEditText: EditText
    lateinit var cragEditText: EditText
    lateinit var dateEditText: EditText
    lateinit var gradeEditText: EditText
    lateinit var styleSpinner: Spinner
    lateinit var starsRatingBar: RatingBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_add_climb_dialog, container, false)
        initViews(layout)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        layout.findViewById<Button>(R.id.doneButton)
            .setOnClickListener { buttonClicked() }
        dateEditText.addTextChangedListener(dateEntryWatcher)
        return layout
    }

    private fun initViews(layout: View) {
        nameEditText = layout.findViewById(R.id.nameEditText)
        cragEditText = layout.findViewById(R.id.cragEditText)
        dateEditText = layout.findViewById(R.id.dateEditText)
        gradeEditText = layout.findViewById(R.id.gradeEditText)
        styleSpinner = layout.findViewById(R.id.styleSpinner)
        starsRatingBar = layout.findViewById(R.id.ratingBar)


        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            Climb.ascentTypes
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            styleSpinner.adapter = adapter
        }

    }

    private fun addClimb() {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy", Locale.ENGLISH)

        Model.addClimb(
            nameEditText.text.toString(),
            styleSpinner.selectedItem.toString(),
            gradeEditText.text.toString().toInt(),
            cragEditText.text.toString(),
            starsRatingBar.rating.toInt(),
            LocalDate.parse(dateEditText.text.toString(), formatter)
        )
    }

    private fun buttonClicked() {
        listener?.onClimbAdded()
        addClimb()
        dismiss()
    }

    /**
     * Called when fragment is attached to the window, checks that the context implements
     * the OnProjectAdded Listener, otherwise..
     * @throws RuntimeException
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClimbAddedListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnClimbAddedListener")
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
    interface OnClimbAddedListener {
        fun onClimbAdded()
    }

    /**
     * Watcher to be used on date input fields. Ensures that date is in format dd/mm/yyyy
     */
    private val dateEntryWatcher: TextWatcher = object : TextWatcher {

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            var currentInput = s.toString()
            var isValid = true

            if (currentInput.length == 2 && before == 0) {
                if (currentInput.toInt() < 1 || currentInput.toInt() > 31) {
                    isValid = false
                } else {
                    currentInput += "/"
                    dateEditText.setText(currentInput)
                    dateEditText.setSelection(currentInput.length)
                }
            } else if (currentInput.length == 5 && before == 0) {
                if (currentInput.slice(3..4).toInt() < 1 || currentInput.slice(3..4).toInt() > 12) {
                    isValid = false
                } else {
                    currentInput += "/"
                    dateEditText.setText(currentInput)
                    dateEditText.setSelection(currentInput.length)
                }
            } else if (currentInput.length != 10) {
                isValid = false
            }
            if (!isValid) {
                dateEditText.error = "Enter a valid date: dd/MM/YYYY"
            } else {
                dateEditText.error = null
            }
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }
    }


}
