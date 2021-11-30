package jpabook.jpashop.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
public class Book extends Item {

    private String author;
    private String isbn;

    private Book(){};

    @Builder
    private Book( String name, int price, int stockQuantity, String author, String isbn){
        super(name, price, stockQuantity);
        this. author = author;
        this. isbn = isbn;

    };

    public static Book createBook(String name, int price, int stockQuantity, String author, String isbn){
        Book book = Book.builder().name(name).price(price).stockQuantity(stockQuantity).author(author).isbn(isbn)
                .build();
        return book;
    }

    public void updateBook(String name, int price, int stockQuantity, String author, String isbn){
        super.change(name,price,stockQuantity);
        this.author = author;
        this.isbn = isbn;
    }
}
