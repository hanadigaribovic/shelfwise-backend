package com.shelfwise.shelfwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookEntity {
        @Id
        @GeneratedValue
        @Column(name = "BookID")
        UUID bid;

        @Column(name = "title")
        String title;

        @Column(name = "author")
        String author;

        @Column(name = "genre")
        String genre;

        @Column(name = "price")
        Double price;

        @Column(name = "description")
        String description;
}
