package com.ibrahim.onlinebookshop.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
public class ProfileDto {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String role;
}
