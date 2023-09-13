package com.ibrahim.onlinebookshop.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ReviewDto {

    private int reviewId;
    private String comments;
    private double ratings;
    private int user_id;
    private int book_id;
}
