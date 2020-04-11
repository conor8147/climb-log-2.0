package com.coneill.climbit.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.coneill.climbit.model.BaseClimb
import com.coneill.climbit.model.Climb
import com.example.climbit.R

class MyDeleteDialog(val climb: BaseClimb): DialogFragment() {

    private var listener: OnDeleteListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.delete_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        layout.setOnClickListener {
            dismiss()
            listener?.onDelete(climb)
        }

        return layout
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDeleteListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnDeleteListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnDeleteListener {
        fun onDelete(baseClimb: BaseClimb)
    }
}