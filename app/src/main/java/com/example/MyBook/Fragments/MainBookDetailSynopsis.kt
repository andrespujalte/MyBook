package com.example.MyBook.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.contains
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.navigation.findNavController
import com.example.MyBook.Adaptadores.BooksListAdapter
import com.example.MyBook.Clases.Book
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.wajahatkarim3.roomexplorer.RoomExplorer
import java.io.Serializable
import java.lang.UnsupportedOperationException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class MainBookDetailSynopsis : Fragment() {


    lateinit var selectedBook : Book
    lateinit var textView: TextView
    lateinit var v : View
    lateinit var fbotton_edit: FloatingActionButton
    lateinit var fbotton_back: FloatingActionButton
    lateinit var editText: EditText
    lateinit var book: Book
    private var db : appDatabase? = null
    private var bookDao : bookDao? = null
    lateinit var editstate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            selectedBook = requireArguments().getParcelable(ARG_PARAM)!!
        }


    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_main_book_detail_synopsis, container, false)
        setHasOptionsMenu(true)
        editstate = getString(R.string.tv_other_edit_no)
        editText = v.findViewById(R.id.et_sinopsys)
        fbotton_edit = v.findViewById(R.id.btr_book_synopsys_edit)
        fbotton_back = v.findViewById(R.id.btr_book_synopsys_back)
        editText.setText(selectedBook.synopsys.toString())
        editText.setShowSoftInputOnFocus(false);
        editText.setLongClickable(false);
        db = appDatabase.getAppDataBase(v.context)
        bookDao = db?.bookDao()
        fbotton_edit.setOnClickListener{

            if(editstate == getString(R.string.tv_other_edit_no))
            {
                editText.setShowSoftInputOnFocus(true);
                editText.setLongClickable(true);
                editstate = getString(R.string.tv_other_edit)
            }
            else
            {

                editText.setShowSoftInputOnFocus(false);
                editText.setLongClickable(false);
                editstate = getString(R.string.tv_other_edit_no)
                selectedBook.synopsys = editText.text.toString()
                bookDao?.updateBook(selectedBook)
                RoomExplorer.show(context, appDatabase::class.java, "BooksDB")
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
        fun newInstance(selectedBook: Book): MainBookDetailSynopsis {
            val fragment = MainBookDetailSynopsis()
            val args = Bundle()
            args.putParcelable(ARG_PARAM, selectedBook)
            fragment.arguments = args
            return fragment
        }
    }
}
