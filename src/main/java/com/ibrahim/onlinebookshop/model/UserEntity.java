package com.ibrahim.onlinebookshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int userId;
     private String username;
     private String firstName;
     private String lastName;
     private String email;
     private String password;
     private String address;
     private String role;


 /*   public User(String firstName, String lastName, String email, String password, String address, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.role = role;
    }*/


}
