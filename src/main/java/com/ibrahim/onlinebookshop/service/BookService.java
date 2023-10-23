package com.ibrahim.onlinebookshop.service;

import com.ibrahim.onlinebookshop.dto.BookDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.model.Book;


import java.util.List;

public interface BookService {

     BookDto createBook(BookDto book) throws InvalidBookId;
     BookDto getById(int bookId) throws InvalidBookId;
     List<BookDto> getAllBooks();

     BookDto updateBook(BookDto book,int id) throws InvalidBookId;

     void deleteBook(int id) throws InvalidBookId, AlreadyOwned;
}
