package com.example.MyBook.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import com.example.MyBook.Clases.Book
import com.example.MyBook.Clases.User
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.roomexplorer.RoomExplorer
import kotlinx.android.synthetic.main.fragment_main_book_new.*


class MainBookNewFragment : Fragment() {

    lateinit var textView_author: TextView
    lateinit var editText_author: EditText
    lateinit var textView_sinopsis: TextView
    lateinit var editText_sinopsis: EditText
    lateinit var textView_publisher: TextView
    lateinit var editText_publisher: EditText
    lateinit var textView_bookname: TextView
    lateinit var editText_bookname: EditText
    lateinit var textView_isbn: TextView
    lateinit var editText_isbn: EditText
    lateinit var textView_url: TextView
    lateinit var editText_url: EditText
    lateinit var textView_category: TextView
    lateinit var spinner_category: Spinner
    lateinit var v : View
    private var db : appDatabase? = null
    private var bookDao : bookDao? = null
    lateinit var fbotton_add: FloatingActionButton
    lateinit var fbotton_back: FloatingActionButton
    lateinit var book : Book
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_main_book_new, container, false)
        textView_bookname = v.findViewById(R.id.tv_main_book_new_titulo)
        textView_author = v.findViewById(R.id.tv_main_book_new_autor)
        textView_publisher = v.findViewById(R.id.tv_main_book_new_editorial)
        textView_isbn = v.findViewById(R.id.tv_main_book_new_isbn)
        textView_category = v.findViewById(R.id.tv_main_book_categoria)
        textView_url= v.findViewById(R.id.tv_main_book_new_url)
        textView_sinopsis= v.findViewById(R.id.tv_main_book_new_sinopsis)
        editText_bookname = v.findViewById(R.id.et_main_book_new_titulo)
        editText_author = v.findViewById(R.id.et_main_book_new_autor)
        editText_publisher = v.findViewById(R.id.et_main_book_new_editorial)
        editText_isbn = v.findViewById(R.id.et_main_book_new_isbn)
        editText_url = v.findViewById(R.id.et_main_book_new_url)
        editText_sinopsis = v.findViewById(R.id.et_main_book_new_sinopsis)
        spinner_category = v.findViewById(R.id.spinner_main_book_new)
        val items = arrayOf(
            getString(R.string.Categoria_Divulgacion_Cientifica),
            getString(R.string.Categoria_Literatura_Adolencete),
            getString(R.string.Categoria_Novelas_Terror),
            getString(R.string.Categoria_Ensayos),
            getString(R.string.Categoria_Literatura_Niños),
            getString(R.string.Categoria_Novelas_Policiales),
            getString(R.string.Categoria_Novelas_Romanticas),
            getString(R.string.Categoria_Novelas_Romanticas),
            getString(R.string.Categoria_Universitarios)
        )
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(v.context,android.R.layout.simple_spinner_item,items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_category.setAdapter(adapter)
        fbotton_add = v.findViewById(R.id.fbt_main_book_new_add)
        fbotton_back = v.findViewById(R.id.fbt_main_book_new_cancel)

        fbotton_add.setOnClickListener {

            if(textView_author.text.toString() != "" &&textView_bookname.text.toString() != "" && textView_url.text.toString() != "") {
                val builder = AlertDialog.Builder(v.context)
                db = appDatabase.getAppDataBase(v.context)
                bookDao = db?.bookDao()
                builder.setTitle("AGREGANDO TITULO")
                builder.setMessage("¿ESTA SEGURO DE QUERER AGREGAR ESTE TITULO?")
                builder.setPositiveButton(android.R.string.yes) {
                        dialog, which -> Toast.makeText(v.context,"Nuevo Titulo Agregado", Toast.LENGTH_SHORT).show()
                    val maxid : Int? = bookDao?.getMaxIndex()
                    if (maxid != null) {
                        bookDao?.insertBook( Book(maxid.plus(1),editText_bookname.text.toString(), editText_author.text.toString(),editText_publisher.text.toString(),editText_isbn.text.toString(),spinner_category.selectedItem.toString(),editText_url.text.toString(),editText_sinopsis.text.toString()))
                        Snackbar.make(v,"Se inserto?",Snackbar.LENGTH_SHORT)
                    }
                    else{Snackbar.make(v,"ID nulo",Snackbar.LENGTH_SHORT)}

                    RoomExplorer.show(context, appDatabase::class.java, "BooksDB")
                    val actionMainBookNewFragmentToMainBookFragment =   MainBookNewFragmentDirections.actionMainBookNewFragmentToMainBookFragment()
                    v.findNavController().navigate(actionMainBookNewFragmentToMainBookFragment)
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(v.context,"hol", Toast.LENGTH_SHORT).show()
                }
                builder.show()

            }else{
                Snackbar.make(v,"Error de ingreso de datos",Snackbar.LENGTH_SHORT)
            }

        }
        fbotton_back.setOnClickListener {
            val actionMainBookNewFragmentToMainBookFragment =   MainBookNewFragmentDirections.actionMainBookNewFragmentToMainBookFragment()
            v.findNavController().navigate(actionMainBookNewFragmentToMainBookFragment)
        }
        return v
    }


}