package com.ibrahim.onlinebookshop.controller;

import com.ibrahim.onlinebookshop.constans.AppConstants;
import com.ibrahim.onlinebookshop.dto.ProfileDto;
import com.ibrahim.onlinebookshop.dto.UserDto;
import com.ibrahim.onlinebookshop.dto.UserLoginReqModel;
import com.ibrahim.onlinebookshop.service.UserService;
import com.ibrahim.onlinebookshop.utlis.JWTUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserManagementController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;


    @GetMapping("/users/profile")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<?> userProfile() {
        try {
            ProfileDto profileDto=userService.getProfile();
            return new ResponseEntity<>(profileDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> userDetailsByUserId(@PathVariable int userId) {
        try {
            UserDto user = userService.getById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register (@RequestBody UserDto userDto) {
        try {
            UserDto createdUser = userService.createUser(userDto);
            String accessToken = JWTUtils.generateToken(createdUser.getEmail());
            Map<String, Object> registerResponse = new HashMap<>();
            registerResponse.put("user", createdUser);
            registerResponse.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);
            return ResponseEntity.status(HttpStatus.CREATED).body(registerResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody UserLoginReqModel userLoginReqModel, HttpServletResponse response) throws IOException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginReqModel.getEmail(), userLoginReqModel.getPassword()));
            if (authentication.isAuthenticated()) {
                UserDto userDto = userService.getUser(userLoginReqModel.getEmail());
                String accessToken = JWTUtils.generateToken(userDto.getEmail());

                Map<String, Object> loginResponse = new HashMap<>();
                loginResponse.put("role", userDto.getRole());
                loginResponse.put("email", userDto.getEmail());
                loginResponse.put("id",userDto.getUserId());
                loginResponse.put(AppConstants.HEADER_STRING, AppConstants.TOKEN_PREFIX + accessToken);
                return ResponseEntity.status(HttpStatus.OK).body(loginResponse);

            } else {
                return new ResponseEntity<>("Invalid email or password!", HttpStatus.UNAUTHORIZED);
            }
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>("Username not found!", HttpStatus.UNAUTHORIZED);

        }
    }

}
