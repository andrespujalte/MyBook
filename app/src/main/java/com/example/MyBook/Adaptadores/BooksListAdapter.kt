package com.example.MyBook.Adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.MyBook.Clases.Book
import com.example.MyBook.R

import com.bumptech.glide.Glide

class BooksListAdapter(private var bookList: MutableList<Book>, val onItemclick: (Int) -> Unit) : RecyclerView.Adapter<BooksListAdapter.BookHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book,parent,false)
        return (BooksListAdapter.BookHolder(view))
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookHolder, position: Int) {
        holder.setName(bookList[position].bookname)
        holder.setImg((bookList[position].imageurl))
        holder.getCardLayout()
            .setOnClickListener{
            onItemclick(position)
        }
    }


    class BookHolder (v : View ) : RecyclerView.ViewHolder(v){
        private var view : View
        init {
            this.view = v
        }
        fun setName(name: String){
            val txt : TextView = view.findViewById(R.id.textView_rv_sector)
            txt.text = name

        }
        fun setImg(url: String){
            val img : ImageView = view.findViewById(R.id.ImageView_rv_sector)
            Glide
                .with(img.context)
                .load(url)
                .into(img)
        }
        fun getImageView() : ImageView {
            return view.findViewById(R.id.ImageView_rv_sector)
        }

        fun getCardLayout(): CardView{
            return  view.findViewById(R.id.cv_book_item)
        }
    }
}