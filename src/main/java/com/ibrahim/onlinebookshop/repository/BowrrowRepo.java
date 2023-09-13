package com.ibrahim.onlinebookshop.repository;

import com.ibrahim.onlinebookshop.model.Book;
import com.ibrahim.onlinebookshop.model.Bowrrow;
import com.ibrahim.onlinebookshop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BowrrowRepo extends JpaRepository<Bowrrow,Integer> {

    Bowrrow findByUserEntityAndBookAndReturnDateIsNull(UserEntity userEntity, Book bookEntity);


    List<Bowrrow> findAllByUserEntity(UserEntity userEntity);

    List<Bowrrow> findAllByUserEntityAndReturnDateIsNull(UserEntity userEntity);
}
