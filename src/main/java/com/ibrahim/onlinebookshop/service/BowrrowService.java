package com.ibrahim.onlinebookshop.service;

import com.ibrahim.onlinebookshop.dto.BookDto;
import com.ibrahim.onlinebookshop.dto.BowrrowDto;
import com.ibrahim.onlinebookshop.dto.BowrrowInfoDto;
import com.ibrahim.onlinebookshop.exceptions.AlreadyOwned;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Bowrrow;

import java.util.List;

public interface BowrrowService {

    public BowrrowDto bookBorrowing(int bookId) throws InvalidBookId, AlreadyOwned;
    public BowrrowDto bookReturning(int bookId) throws InvalidBookId;

    public List<BookDto> getAllBookByUser(int userId) throws ResourceNotFoundException,UnAuthorizedPeople;

    public List<BowrrowInfoDto> getUserAllHistory(int userId) throws ResourceNotFoundException, UnAuthorizedPeople;
    List<BookDto> getAllBorrowedBookByUser(int userId) throws ResourceNotFoundException,UnAuthorizedPeople;

}
