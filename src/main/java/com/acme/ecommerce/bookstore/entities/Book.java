package com.acme.ecommerce.bookstore.entities;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Ivan on 7/11/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = {"isbn"})
@ToString(of = {"id", "title", "author", "isbn"})
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String publicationDate;
    private String language;
    private String category;
    private Integer numberOfPages;
    private String format;
    private Integer isbn;
    private Double shippingWeight;
    private Double listPrice;
    private Double ourPrice;
    private Boolean active = Boolean.TRUE;
    private String description;
    private Integer inStockNumber;

}
