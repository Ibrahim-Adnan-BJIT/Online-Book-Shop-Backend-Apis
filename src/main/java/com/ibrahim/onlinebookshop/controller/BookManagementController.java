package com.ibrahim.onlinebookshop.controller;

import com.ibrahim.onlinebookshop.dto.*;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.service.BookService;
import com.ibrahim.onlinebookshop.service.BowrrowService;
import com.ibrahim.onlinebookshop.service.ReserveService;
import com.ibrahim.onlinebookshop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class BookManagementController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private BowrrowService bowrrowService;
    @Autowired
    private ReserveService reserveService;


    @PostMapping("/books/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        try {
            BookDto newBook = bookService.createBook(bookDto);
            return new  ResponseEntity<>(newBook, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/books/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> updateBook(@RequestBody BookDto bookDto,@PathVariable int id) {
        try {
            BookDto updatedBook = bookService.updateBook(bookDto,id);
            return new  ResponseEntity<>(updatedBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/books/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteBook(@PathVariable int id) {
        try {
            bookService.deleteBook(id);
            return new  ResponseEntity<>("Book Deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<?> allBooks() {
        try {
            List<BookDto> allBook = bookService.getAllBooks();
            return new  ResponseEntity<>(allBook, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/books/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getBookById(@PathVariable int id) {
        try {
            BookDto bookDto = bookService.getById(id);
            return new  ResponseEntity<>(bookDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/books/{bookId}/borrow")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> borrowBook(@PathVariable int bookId) {
        try {
            BowrrowDto book = bowrrowService.bookBorrowing(bookId);
            return new  ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/books/{bookId}/return")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> returnBook(@PathVariable int bookId) {
        try {
            BowrrowDto book = bowrrowService.bookReturning(bookId);
            return new  ResponseEntity<>(book, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users/{userId}/books")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<?> retriveBooks(@PathVariable int userId) {
        try {
            List<BookDto> allBookByUser = bowrrowService.getAllBookByUser(userId);
            return new ResponseEntity<>(allBookByUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users/{userId}/borrowed-books")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<?> retriveBorrowedBooks(@PathVariable int userId) {
        try {
            List<BookDto> allBookByUser = bowrrowService.getAllBorrowedBookByUser(userId);
            return new ResponseEntity<>(allBookByUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/users/{userId}/history")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> userHistory(@PathVariable int userId) {
        try {
            List<BowrrowInfoDto> userAllHistory = bowrrowService.getUserAllHistory(userId);
            return new ResponseEntity<>(userAllHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/books/{bookId}/reviews/create")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> createReview(@PathVariable int bookId, @RequestBody ReviewDto bookReviewDto) {
        try {
            ReviewDto newReview =  reviewService.createBookReview(bookId, bookReviewDto);
            return new ResponseEntity<>(newReview, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/{bookId}/reviews")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> seeAllReview(@PathVariable int bookId) {
        try {
            List <ReviewDto> newReview =  reviewService.allBookReview(bookId);
            return new ResponseEntity<>(newReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/books/reviews")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> seeUserReview() {
        try {
            List <ReviewDto> newReview =  reviewService.getAllUserReview();
            return new ResponseEntity<>(newReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/books/{bookId}/reviews/{reviewId}/delete")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> deleteReview (@PathVariable int bookId, @PathVariable int reviewId) {
        try {
            reviewService.deleteReview(bookId, reviewId);
            return new  ResponseEntity<>("Review Deleted SuccessFully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/books/{bookId}/reviews/{reviewId}/update")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> updateReview (@PathVariable int bookId, @PathVariable int reviewId, @RequestBody ReviewDto bookReviewDto) {
        try {
            ReviewDto updatedReview =  reviewService.updateReview(bookId, reviewId, bookReviewDto);
            return new  ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/books/{bookId}/reserve")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> reserveBook (@PathVariable int bookId) {
        try {
            ReserveDto updatedReview =  reserveService.createReserve(bookId);
            return new  ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/books/{bookId}/cancel-reservation")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<?> cancelReserveBook (@PathVariable int bookId) {
        try {
            ReserveDto cancelReview =  reserveService.cancelReserve(bookId);
            return new  ResponseEntity<>(cancelReview, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




}
