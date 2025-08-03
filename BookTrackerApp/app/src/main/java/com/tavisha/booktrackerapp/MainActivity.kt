package com.tavisha.booktrackerapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var bookListView: ListView
    private lateinit var bookListAdapter: BookListAdapter
    private val books = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the ListView
        bookListView = findViewById(R.id.listViewBooks)


        // Initialize the Adapter
        bookListAdapter = BookListAdapter(this, books)

        // Set the Adapter to the ListView
        bookListView.adapter = bookListAdapter

        // Set up actions (Add, Delete, Mark as Read)
        setupActions()
    }

    private fun setupActions() {
        // Example: Delete selected book
        findViewById<Button>(R.id.btnDeleteBook).setOnClickListener {
            val selectedBooks = books.filter { it.Selected }
            if (selectedBooks.isNotEmpty()) {
                selectedBooks.forEach { book ->
                    books.remove(book)
                    Toast.makeText(this, "${book.title} deleted", Toast.LENGTH_SHORT).show()
                }
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No books selected for deletion", Toast.LENGTH_SHORT).show()
            }
        }

        // Example: Mark selected book as Read
        findViewById<Button>(R.id.btnMarkRead).setOnClickListener {
            val selectedBooks = books.filter { it.Selected }
            if (selectedBooks.isNotEmpty()) {
                selectedBooks.forEach { book ->
                    book.Selected = false
                    book.status = "Read" // Mark the book as Read
                    Toast.makeText(this, "${book.title} marked as Read", Toast.LENGTH_SHORT).show()
                }
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "No books selected", Toast.LENGTH_SHORT).show()
            }
        }

        // Example: Add a new book
        findViewById<Button>(R.id.btnAddBook).setOnClickListener {
            val title = findViewById<EditText>(R.id.etBookTitle).text.toString()
            val author = findViewById<EditText>(R.id.etBookAuthor).text.toString()
            if (title.isNotEmpty() && author.isNotEmpty()) {
                val newBook = Book(title, author)
                books.add(newBook)
                bookListAdapter.notifyDataSetChanged()
                Toast.makeText(this, "New book added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter book title and author", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
