package com.coneill.climbit.view.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.DialogFragment
import com.example.climbit.R


class SearchDialog: DialogFragment() {

    private var listener: OnSearchListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.search_dialog, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val searchBox = layout.findViewById<SearchView>(R.id.searchView)
        searchBox.isIconified = false

        searchBox.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                listener?.onSearch(query)
                dismiss()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean = false
        })
        return layout
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnSearchListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnSearchListener {
        fun onSearch(climbName: String)
    }
}