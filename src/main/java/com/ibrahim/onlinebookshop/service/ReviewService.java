package com.ibrahim.onlinebookshop.service;

import com.ibrahim.onlinebookshop.dto.ReviewDto;
import com.ibrahim.onlinebookshop.exceptions.InvalidBookId;
import com.ibrahim.onlinebookshop.exceptions.ReviewNotFound;
import com.ibrahim.onlinebookshop.exceptions.UnAuthorizedPeople;
import com.ibrahim.onlinebookshop.model.Review;

import java.util.List;

public interface ReviewService {

    public ReviewDto createBookReview(int bookId, ReviewDto bookReviewDto) throws InvalidBookId, ReviewNotFound;
    public List<ReviewDto> allBookReview(int bookId) throws InvalidBookId,Exception;

    public ReviewDto updateReview(int bookId, int reviewId, ReviewDto bookReviewDto) throws InvalidBookId,ReviewNotFound, UnAuthorizedPeople,Exception;

    public void deleteReview(int bookId, int reviewId) throws InvalidBookId,ReviewNotFound,UnAuthorizedPeople,Exception;
     public List<ReviewDto>getAllUserReview();
}
