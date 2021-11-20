package jpabook.jpashop.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //주인
    @ManyToOne(fetch = FetchType .LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();


    // 1:1 관계에서는 엑세스가 많이 일어나는 곳을 주인으로 한다 (주인인곳이 FK)
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    //enum 타입
    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문상태 [ORDER, CANCEL]


    // === 연관 관계 편입 메서드 === //
    // 연관관계편입메서드의 위치는 컨트롤 하는 곳에 만드는게 좋다
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //== 생성 메서드 (정적 팩토리 메서드) ==//
    // Order가 연관관계를 쫙 걸면서 상태와 시간을 세팅이 된다.
    // 이런 스타일로 작성 하는게 중요한 점은 앞으로 생성하는 것을 변경할 때 이 메서드만 변경하면 된다.
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems ){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }


    //==비즈니스 로직==//
    /**
     * 주문취소
     */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw  new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //== 조회 로직 ==//
    public  int getTotalPrice(){
        //int totalPrice = 0;
        //for (OrderItem orderItem : orderItems){
        //    totalPrice += orderItem.getTotalPrice();
        //}
        // Stream & Lambda 위 코드를 Stream과 Lambda 식으로 표현하면 아래와 같이 변한다.
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
