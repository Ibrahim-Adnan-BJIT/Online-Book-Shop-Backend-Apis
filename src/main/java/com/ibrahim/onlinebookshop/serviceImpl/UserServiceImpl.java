package com.ibrahim.onlinebookshop.serviceImpl;

import com.ibrahim.onlinebookshop.constans.AppConstants;
import com.ibrahim.onlinebookshop.dto.UserDto;
import com.ibrahim.onlinebookshop.exceptions.EmailAlreadyExist;
import com.ibrahim.onlinebookshop.exceptions.ResourceNotFoundException;
import com.ibrahim.onlinebookshop.model.UserEntity;
import com.ibrahim.onlinebookshop.repository.UserRepo;
import com.ibrahim.onlinebookshop.service.UserService;
import com.ibrahim.onlinebookshop.utlis.JWTUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto user) throws ResourceNotFoundException {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new EmailAlreadyExist("Already Exists");
        if(user==null) throw new ResourceNotFoundException("User Cant be Null");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setRole(user.getRole());
        String publicUserId = JWTUtils.generateUserID(10);
        userEntity.setUsername(publicUserId);
        userEntity.setAddress(user.getAddress());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnedValue = modelMapper.map(storedUserDetails,UserDto.class);
        String accessToken = JWTUtils.generateToken(userEntity.getEmail());
        returnedValue.setAccessToken(AppConstants.TOKEN_PREFIX + accessToken);
        return returnedValue;
    }

    @Override
    public UserDto getUser(String email) throws EmailAlreadyExist {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        if(userEntity==null)throw new EmailAlreadyExist("Invalid Email");
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }


    @Override
    public UserDto getById(int id) throws ResourceNotFoundException{
       UserEntity user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Invalid Id"));
       return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        return new User(userEntity.getEmail(),userEntity.getPassword(),
                true,true,true,true,new ArrayList<>());
    }
}
