package com.ibrahim.onlinebookshop.serviceImpl;

import com.ibrahim.onlinebookshop.dto.BookDto;
import com.ibrahim.onlinebookshop.dto.ReviewDto;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.exceptions.ReviewNotFound;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Review;
import com.ibrahim.onlinebookshop.model.UserEntity;
import com.ibrahim.onlinebookshop.repository.BookRepo;
import com.ibrahim.onlinebookshop.repository.ReviewRepo;
import com.ibrahim.onlinebookshop.repository.UserRepo;
import com.ibrahim.onlinebookshop.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ReviewRepo reviewRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;

    @Override
    public ReviewDto createBookReview(int bookId, ReviewDto bookReviewDto) throws InvalidBookId, ReviewNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByEmail(authentication.getName());
        int userId = user.get().getUserId();

        UserEntity userEntity = userRepo.findByUserId(userId);
        Book bookEntity = bookRepo.findByBookId(bookId);
         if(bookEntity==null)throw new InvalidBookId("Book Id Not found");
         if(bookReviewDto==null)throw new ReviewNotFound("ReviewDto Not found");
        Review bookReviewEntity = new Review();

        bookReviewEntity.setBook(bookEntity);
        bookReviewEntity.setUserEntity(userEntity);
        bookReviewEntity.setComments(bookReviewDto.getComments());
        bookReviewEntity.setRatings(bookReviewDto.getRatings());
        reviewRepo.save(bookReviewEntity);

        return modelMapper.map(bookReviewEntity, ReviewDto.class);

    }

    @Override
    public List<ReviewDto> allBookReview(int bookId) throws InvalidBookId,Exception {
        Book bookEntity = bookRepo.findByBookId(bookId);
        if(bookEntity==null)throw new InvalidBookId("Book Id is Not Valid");
        List<Review> bookReviews = reviewRepo.findAllByBook(bookEntity);
       return bookReviews.stream()
                .map(reviewEntity -> modelMapper.map(reviewEntity, ReviewDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public ReviewDto updateReview(int bookId, int reviewId, ReviewDto bookReviewDto) throws UnAuthorizedPeople,InvalidBookId,ReviewNotFound,Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByEmail(authentication.getName());
        String currentUserRole = user.get().getRole();
        int currentUserId = user.get().getUserId();
        Book book=bookRepo.findByBookId(bookId);
        if(book==null)throw new InvalidBookId("Book Id is Invalid");
        Review bookReview =reviewRepo.findByReviewId(reviewId);
        if(bookReview==null)throw new ReviewNotFound("Review Id Not found");

        if(bookReviewDto==null)throw new ReviewNotFound("ReviewDto is Incomplete");
        int userId = bookReview.getUserEntity().getUserId();

        if (currentUserId!=userId && currentUserRole.equals("CUSTOMER")) {
            throw new UnAuthorizedPeople("Access Restricted");
        }

        if (bookReview.getBook().getBookId()!=bookId) {
            throw new InvalidBookId("Book id or Review id is Invalid!");
        }

        bookReview.setRatings(bookReviewDto.getRatings());
        bookReview.setComments(bookReviewDto.getComments());

        reviewRepo.save(bookReview);


        return modelMapper.map(bookReview, ReviewDto.class);
    }

    @Override
    public void deleteReview(int bookId, int reviewId) throws InvalidBookId,ReviewNotFound,UnAuthorizedPeople,Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepo.findByEmail(authentication.getName());
        String currentUserRole = user.get().getRole();
        int currentUserId = user.get().getUserId();
        Book book=bookRepo.findByBookId(bookId);
        if(book==null)throw new InvalidBookId("Book Id Not found");
        Review bookReview = reviewRepo.findByReviewId(reviewId);
        if(bookReview==null)throw new ReviewNotFound("Review Id Not Found");
        int userId = bookReview.getUserEntity().getUserId();

        if (currentUserId!=userId && currentUserRole.equals("CUSTOMER")) {
            throw new UnAuthorizedPeople("Access Denied");
        }

        if (bookReview.getBook().getBookId()!=bookId) {
            throw new InvalidBookId("Book id or Review id is wrong!");
        }

        reviewRepo.delete(bookReview);
    }
}
