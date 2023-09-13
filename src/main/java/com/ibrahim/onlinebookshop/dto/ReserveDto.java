package com.ibrahim.onlinebookshop.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ReserveDto {
    private int reverseId;
    private String reservationStatus;
    private LocalDate reserveDate;
    private int user_id;
    private int book_id;

}
