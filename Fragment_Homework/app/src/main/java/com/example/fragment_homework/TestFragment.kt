package com.example.fragment_homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*

class TestFragment: Fragment(){

    companion object {
        @JvmStatic
        fun newInstance(documentNumber:Int):TestFragment {
            val args = Bundle()
            args.putInt(ARG_DOCUMENT_NUMBER, documentNumber)
            val fragment = TestFragment()
            fragment.arguments = args
            return  fragment
        }

        private const val ARG_DOCUMENT_NUMBER = "page_count"
    }

    private var number:Int=0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment, container,false)
        val text = view.findViewById<TextView>(R.id.fragment_text)
        if(savedInstanceState!=null){
            number = savedInstanceState.getInt(ARG_DOCUMENT_NUMBER)
        }
        text.text = number.toString()
        return view
    }
}