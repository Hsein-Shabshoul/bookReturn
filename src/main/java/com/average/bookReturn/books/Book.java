package com.average.bookReturn.books;
import com.average.bookReturn.authors.Author;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "books")
public class Book implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull(message = "Name can not be null")
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private int quantity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
    public Book(){} //to work with get requests
    public Book(String title, String description){
        super();
        this.title = title;
        this.description = description;
    }
}
