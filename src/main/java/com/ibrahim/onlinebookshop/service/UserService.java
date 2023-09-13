package com.ibrahim.onlinebookshop.service;

import com.ibrahim.onlinebookshop.dto.UserDto;
import com.ibrahim.onlinebookshop.exceptions.EmailAlreadyExist;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.model.UserEntity;

public interface UserService {

    UserDto createUser(UserDto user) throws ResourceNotFoundException;
    UserDto getUser(String email) throws EmailAlreadyExist;

    UserDto getById(int id) throws ResourceNotFoundException;
}
