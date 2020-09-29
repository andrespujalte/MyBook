package com.example.MyBook.Fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.MyBook.Clases.Book
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login_password.*


class MainBookDetailOther : Fragment() {

    lateinit var selectedBook : Book
    lateinit var textView_author: TextView
    lateinit var editText_author: EditText
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
    lateinit var editstate: String
    lateinit var textView_edit : TextView
    lateinit var fbotton_edit: FloatingActionButton
    lateinit var fbotton_back: FloatingActionButton
    lateinit var book: Book
    lateinit var v : View
    private var db : appDatabase? = null
    private var bookDao : bookDao? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedBook = requireArguments().getParcelable(MainBookDetailOther.ARG_PARAM)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main_book_detail_other, container, false)
        setHasOptionsMenu(true)
        textView_bookname = v.findViewById(R.id.tv_others_bookname)
        textView_bookname.text = getString(R.string.tv_others_bookname)
        textView_author = v.findViewById(R.id.tv_others_authorname)
        textView_bookname.text = getString(R.string.tv_others_bookname)
        textView_publisher = v.findViewById(R.id.tv_others_publisher)
        textView_bookname.text = getString(R.string.tv_others_publisher)
        textView_isbn = v.findViewById(R.id.tv_others_isbn)
        textView_bookname.text = getString(R.string.tv_others_isbn)
        textView_category = v.findViewById(R.id.tv_others_category)
        textView_bookname.text = getString(R.string.tv_others_category)
        textView_url= v.findViewById(R.id.tv_others_url)
        textView_url.text = getString(R.string.tv_others_url)


        editText_bookname = v.findViewById(R.id.et_others_bookname)
        editText_bookname.setText(selectedBook.bookname.toString())
        editText_bookname.setEnabled(false);
        editText_author = v.findViewById(R.id.et_others_authorname)
        editText_author.setText(selectedBook.authorname.toString())
        editText_author.setEnabled(false);
        editText_publisher = v.findViewById(R.id.et_others_publisher)
        editText_publisher.setText(selectedBook.publisher.toString())
        editText_publisher.setEnabled(false);
        editText_isbn = v.findViewById(R.id.et_others_isbn)
        editText_isbn.setText(selectedBook.isbn.toString())
        editText_isbn.setEnabled(false);
        editText_url = v.findViewById(R.id.et_others_url)
        editText_url.setText(selectedBook.imageurl.toString())
        editText_url.setEnabled(false);
        spinner_category = v.findViewById(R.id.spinner_book_other_category)
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
        spinner_category.setSelection((spinner_category.getAdapter() as ArrayAdapter<String?>).getPosition(selectedBook.category.toString())   )
        spinner_category.setEnabled(false)
        editstate = getString(R.string.tv_other_edit_no)
        db = appDatabase.getAppDataBase(v.context)
        bookDao = db?.bookDao()

        fbotton_edit = v.findViewById(R.id.btr_book_others_edit)
        fbotton_back = v.findViewById(R.id.btr_book_others_back)
        fbotton_edit.setOnClickListener{

            if(editstate == getString(R.string.tv_other_edit_no))
            {
                editText_bookname.setEnabled(true);
                editText_author.setEnabled(true);
                editText_publisher.setEnabled(true);
                editText_isbn.setEnabled(true);
                editText_url.setEnabled(true);
                spinner_category.setEnabled(true)
                editstate = getString(R.string.tv_other_edit)
            }
            else
            {
                editText_bookname.setEnabled(false)
                editText_author.setEnabled(false)
                editText_publisher.setEnabled(false)
                editText_isbn.setEnabled(false)
                editText_url.setEnabled(false)
                spinner_category.setEnabled(false)
                editstate = getString(R.string.tv_other_edit_no)
                selectedBook.authorname = editText_author.text.toString()
                selectedBook.bookname = editText_bookname.text.toString()
                selectedBook.category = spinner_category.selectedItem.toString()
                selectedBook.imageurl = editText_url.text.toString()
                selectedBook.publisher = editText_publisher.text.toString()
                bookDao?.updateBook(selectedBook)
            }

        }
        fbotton_back.setOnClickListener{
            val actionMainBookDetailContainerToMainBookFragment =   MainBookDetailContainerDirections.actionMainBookDetailContainerToMainBookFragment()
            v.findNavController().navigate(actionMainBookDetailContainerToMainBookFragment)

        }
        return v
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(isVisible()){
            inflater.inflate(R.menu.toolbar_book_details,menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = when(item.itemId) {
            R.id.action_Borrar -> {
                val builder = AlertDialog.Builder(v.context)
                builder.setTitle("BORRANDO TITULO")
                builder.setMessage("¿ESTA SEGURO DE QUERER BORRAR ESTE TITULO?")
                builder.setPositiveButton(android.R.string.yes) {
                        dialog, which -> Toast.makeText(v.context,"Borrado", Toast.LENGTH_SHORT).show()
                    bookDao?.deleteById(selectedBook.idbook.toInt())
                    val actionMainBookDetailContainerToMainBookFragment =   MainBookDetailContainerDirections.actionMainBookDetailContainerToMainBookFragment()
                    v.findNavController().navigate(actionMainBookDetailContainerToMainBookFragment)
                }
                builder.setNegativeButton(android.R.string.no) { dialog, which ->Toast.makeText(v.context,android.R.string.no, Toast.LENGTH_SHORT).show()
                }
                builder.show()

            }
            else -> ""
        }
        return super.onOptionsItemSelected(item)

    }
    companion object {
        private val ARG_PARAM = "SelectedBook"
        fun newInstance(selectedBook: Book): MainBookDetailOther {
            val fragment = MainBookDetailOther()
            val args = Bundle()
            args.putParcelable(ARG_PARAM, selectedBook)
            fragment.arguments = args
            return fragment
        }
    }

}