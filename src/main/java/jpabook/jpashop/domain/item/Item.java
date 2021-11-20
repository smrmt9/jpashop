package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// 상속관계 전략
// InheritanceType (인헤리턴스)
// SINGLE_TABLE : 한 테이블에 전부
// TABLE_PER_CLASS : 북 무비 앨번 테이블 생성
// JOINED : 가장 정교하게
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 싱글 테이블로 DB에 저장할때 구분할 구분자가 필요하다
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();



    // == 비즈니스 로직 == //
    /**
     * stock(재고) 증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /**
     *  stock(재고) 감소
     */
    public void removeStock(int quantity) throws NotEnoughStockException {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }

        this.stockQuantity = restStock;

    }




}
