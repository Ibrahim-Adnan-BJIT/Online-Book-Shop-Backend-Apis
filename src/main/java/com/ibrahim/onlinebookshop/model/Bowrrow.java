package com.ibrahim.onlinebookshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "bowrrow")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Bowrrow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bowrrow_Id;
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

    private LocalDate bowrrowDate;
    private LocalDate returnDate;
    private LocalDate duedate;



}
