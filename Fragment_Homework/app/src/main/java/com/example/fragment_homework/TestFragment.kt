package com.example.fragment_homework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

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
        return inflater.inflate(R.layout.fragment, container,false)
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
        view!!.text = getString(R.string.fragment_start_text,mNumber)
    }
}