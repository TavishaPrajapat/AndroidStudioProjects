package com.tavisha.booktrackerapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView
import com.tavisha.booktrackerapp.R

class BookListAdapter(private val context: Context, private val books: MutableList<Book>) : BaseAdapter() {

    override fun getCount(): Int = books.size

    override fun getItem(position: Int): Any = books[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val book = books[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.book_item, parent, false)

        val tvTitle: TextView = view.findViewById(R.id.tvTitle)
        val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val radioButton: RadioButton = view.findViewById(R.id.rbSelect)

        tvTitle.text = book.title
        tvAuthor.text = book.author
        tvStatus.text = if (book.status == "Read") "Read" else "Unread"
        radioButton.isChecked = book.Selected

        // When a RadioButton is clicked, toggle the selected status of the book
        radioButton.setOnClickListener {
            book.Selected = !book.Selected
            notifyDataSetChanged()
        }

        return view
    }
}
