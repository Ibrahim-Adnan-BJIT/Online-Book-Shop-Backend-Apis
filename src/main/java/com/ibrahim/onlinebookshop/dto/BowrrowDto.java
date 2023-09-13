package com.ibrahim.onlinebookshop.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BowrrowDto {

    private int bowrrowiId;

    private int user_id;
    private int book_id;
    private LocalDate bowrrowDate;
    private LocalDate returnDate;
    private LocalDate duedate;
}
