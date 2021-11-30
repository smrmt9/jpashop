package jpabook.jpashop.domain.item;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("A")
@Getter
public class Album extends Item {

    private String artist;
    private String etc;

    private Album(){};

    @Builder
    private Album(String name, int price, int stockQuantity, String artist, String etc) {
        super(name, price, stockQuantity);
        this.artist = artist;
        this.etc = etc;
    }

    public Album createAlbum(String name, int price, int stockQuantity, String artist, String etc) {
        Album album = Album.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .artist(artist)
                .etc(etc)
                .build();
        return album;
    }
}
