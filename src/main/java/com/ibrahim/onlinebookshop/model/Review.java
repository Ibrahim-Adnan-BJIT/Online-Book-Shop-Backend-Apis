package com.ibrahim.onlinebookshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "review")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;
    private String comments;
    private double ratings;
    @ManyToOne(fetch=FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})

    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch=FetchType.LAZY,
            cascade= {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})

    @JoinColumn(name="book_id")
    private Book book;

}
