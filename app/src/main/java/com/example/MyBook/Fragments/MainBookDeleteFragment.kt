package com.example.MyBook.Fragments

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.MyBook.Clases.Book
import com.example.MyBook.R

class MainBookDeleteFragment : Fragment() {

    lateinit var v : View
    lateinit var selectedBook : Book
    lateinit var titulo : TextView
    lateinit var bt_continuar : Button
    lateinit var bt_cancel : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedBook = requireArguments().getParcelable(MainBookDeleteFragment.ARG_PARAM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main_book_delete, container, false)
        val img : ImageView = v.findViewById(R.id.iv_main_book_delete)
        Glide
            .with(img.context)
            .load(selectedBook.imageurl.toString())
            .into(img)
        img.scaleType = ImageView.ScaleType.FIT_CENTER
        val titulo : TextView = v.findViewById(R.id.iv_main_book_delete)
        titulo.textSize = 20F
        titulo.setGravity(Gravity.CENTER)
        bt_continuar.setOnClickListener(){

        }
        bt_cancel.setOnClickListener(){

        }
        return v
    }
    companion object {
        private val ARG_PARAM = "SelectedBook"
        fun newInstance(selectedBook: Book): MainBookDeleteFragment {
            val fragment = MainBookDeleteFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM, selectedBook)
            fragment.arguments = args
            return fragment
        }
    }
}