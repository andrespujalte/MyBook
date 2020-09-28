package com.example.MyBook.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.MyBook.Clases.Book
import com.example.MyBook.Database.appDatabase
import com.example.MyBook.Database.bookDao
import com.example.MyBook.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
        editstate = getString(R.string.tv_other_edit_no)
        editText = v.findViewById(R.id.et_sinopsys)
        fbotton_edit = v.findViewById(R.id.btr_book_synopsys_edit)
        fbotton_back = v.findViewById(R.id.btr_book_synopsys_back)
        editText.setText(selectedBook.synopsys.toString())
        editText.setShowSoftInputOnFocus(false);
        editText.setLongClickable(false);

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
            }

        }
        fbotton_back.setOnClickListener{
            val actionMainBookDetailContainerToMainBookFragment =   MainBookDetailContainerDirections.actionMainBookDetailContainerToMainBookFragment()
            v.findNavController().navigate(actionMainBookDetailContainerToMainBookFragment)

        }
        return v
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
