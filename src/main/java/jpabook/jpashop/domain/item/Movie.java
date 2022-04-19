package jpabook.jpashop.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M")
@Getter
public class Movie extends Item{

    private String director;
    private String actor;

    public Movie(){};
    @Builder
    private Movie(String name, int price, int stockQuantity, String director, String actor) {
        super(name, price, stockQuantity);
        this.director = director;
        this.actor = actor;
    }

    public Movie createMovie(String name, int price, int stockQuantity, String director, String actor) {
        Movie movie = Movie.builder()
                .name(name).price(price).stockQuantity(stockQuantity)
                .director(director).actor(actor)
                .build();
        return movie;
    }
}
