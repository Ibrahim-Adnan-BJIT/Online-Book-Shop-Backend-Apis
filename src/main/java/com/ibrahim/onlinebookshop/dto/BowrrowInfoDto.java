package com.ibrahim.onlinebookshop.dto;

import com.ibrahim.onlinebookshop.model.Book;
import lombok.*;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BowrrowInfoDto {
    private int borrowId;
    private int bookEntity;
    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
