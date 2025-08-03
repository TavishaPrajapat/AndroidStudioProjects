package com.tavisha.booktrackerapp
data class Book(
    var title: String,
    var author: String,
    var status: String= "Unread" ,
    var Selected: Boolean = false
)
