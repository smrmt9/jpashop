package jpabook.jpashop.domain;


import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    private int orderPrice; //주문가격
    private int count;  //주문 수량

    // 다양한 방식으로 객체생성을 막기위한 방법
    // lombook으로 @NoArgsConstructor(access = AccessLevel.PROTECTED) 사용해서 막을 수도 있다
    //protected OrderItem(){};


    //== 생성 메서드 (정적 팩토리 메서드) ==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // setter 없이 Builder를 사용해 객체 생성
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .orderPrice(orderPrice)
                .count(count)
                .build();

        item.removeStock(count);

        return orderItem;
    }

    @Builder
    private OrderItem(Item item, int orderPrice, int count){
        this.item = item;
        this.orderPrice = orderPrice;
        this.count = count;
    }


    //==비즈니스 로직==//
    public void cancel(){
        // 재고 수량을 원복해준다.
        item.addStock(count);
    }

    //== 조회 로직==//
    public int getTotalPrice() {
        return getOrderPrice() * count;
    }
}
