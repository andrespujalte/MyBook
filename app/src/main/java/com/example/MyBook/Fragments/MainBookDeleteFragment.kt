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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.MyBook.Clases.Book
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R

class MainBookDeleteFragment : Fragment() {

    lateinit var v : View
    lateinit var selectedBook : Book
    lateinit var titulo : TextView
    lateinit var bt_continuar : Button
    lateinit var bt_cancel : Button
    private var db : appDatabase? = null
    private var bookDao : bookDao? = null
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
        val titulo : TextView = v.findViewById(R.id.tv_main_book_delete_titulo)
        titulo.textSize = 20F
        titulo.setGravity(Gravity.CENTER)
        bt_continuar = v.findViewById(R.id.bt_main_book_delete_continue)
        bt_cancel = v.findViewById(R.id.bt_main_book_delete_cancel)
        val actionback =  MainBookDeleteFragmentDirections.actionMainBookDeleteFragmentToMainBookFragment()

        bt_continuar.setOnClickListener(){
            val builder = AlertDialog.Builder(v.context)
            db = appDatabase.getAppDataBase(v.context)
            bookDao = db?.bookDao()
            builder.setTitle("BORRANDO TITULO")
            builder.setMessage("¿ESTA SEGURO DE QUERER BORRAR ESTE TITULO?")
            builder.setPositiveButton(android.R.string.yes) {
                    dialog, which -> Toast.makeText(v.context,"Borrado", Toast.LENGTH_SHORT).show()
                bookDao?.deleteById(selectedBook.idbook.toInt())
            }
            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(v.context,android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
            (v.findNavController().navigate(actionback))
        }
        bt_cancel.setOnClickListener(){

            (v.findNavController().navigate(actionback))
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