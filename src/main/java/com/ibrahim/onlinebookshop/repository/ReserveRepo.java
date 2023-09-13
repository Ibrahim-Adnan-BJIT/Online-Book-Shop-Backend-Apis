package com.ibrahim.onlinebookshop.repository;

import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReserveRepo extends JpaRepository<Reserve,Integer> {
    Reserve findByBook(Book book);
}
