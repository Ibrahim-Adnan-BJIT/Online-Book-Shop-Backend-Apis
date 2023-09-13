package com.ibrahim.onlinebookshop.repository;

import com.ibrahim.onlinebookshop.dto.ReviewDto;
import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo  extends JpaRepository<Review,Integer> {


    List<Review> findAllByBook(Book bookEntity) throws Exception;

    Review findByReviewId(int reviewId) throws Exception;
}
