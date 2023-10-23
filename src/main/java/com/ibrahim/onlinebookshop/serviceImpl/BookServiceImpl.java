package com.ibrahim.onlinebookshop.serviceImpl;

import com.ibrahim.onlinebookshop.dto.BookDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.repository.BookRepo;
import com.ibrahim.onlinebookshop.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BookDto createBook(BookDto book) throws InvalidBookId{
           Book book1=new Book();
           if(book==null)throw new InvalidBookId("Book Can Not be Null");
           book1.setName(book.getName());
           book1.setAuthor(book.getAuthor());
           book1.setIsAvailable(book.getIsAvailable());
           bookRepo.save(book1);
           return book;
    }

    @Override
    public BookDto getById(int id) throws InvalidBookId {
        Book book=bookRepo.findById(id).orElseThrow(()->new InvalidBookId("Please Enter Valid Id"));
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        List<Book>books=bookRepo.findAll();
        return books.stream().map((todo) -> modelMapper.map(todo, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(BookDto book,int id) throws InvalidBookId{
        Book book1=bookRepo.findById(id).orElseThrow(()->new InvalidBookId("Please Enter Valid Id"));
        book1.setName(book.getName());
        book1.setAuthor(book.getAuthor());
        book1.setIsAvailable(book.getIsAvailable());
        bookRepo.save(book1);
        return book;
    }

    @Override
    public void deleteBook(int id) throws InvalidBookId, AlreadyOwned {

        Book book1=bookRepo.findById(id).orElseThrow(()->new InvalidBookId("Please Enter Valid Id"));

        if(book1.getIsAvailable().equals("OCCUPIED")) throw new AlreadyOwned("This book is Borrowed by another person");
        bookRepo.deleteById(book1.getBookId());

    }
}
