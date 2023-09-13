package com.ibrahim.onlinebookshop.dto;

import com.ibrahim.onlinebookshop.model.Book;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class UserDto {
    private int userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String role;


    public void setAccessToken(String s) {
    }
}
