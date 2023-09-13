package com.ibrahim.onlinebookshop.repository;

import com.ibrahim.onlinebookshop.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book,Integer> {

    Book findByBookId(int b_id);
}
