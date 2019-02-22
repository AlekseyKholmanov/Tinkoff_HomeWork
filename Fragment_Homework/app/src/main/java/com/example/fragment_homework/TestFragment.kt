package com.example.fragment_homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment.view.*

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
    private var mNumber: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment, container,false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            mNumber = arguments!!.getInt(ARG_DOCUMENT_NUMBER)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val view = view?.findViewById<TextView>(R.id.fragment_text)
        view!!.setText("Документ №" + mNumber.toString())

    }
}