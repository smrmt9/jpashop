package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;


@Getter @Setter
public class OrderSearch {

    private String memberName;          // 회원명
    private OrderStatus orderStatus;    // 주문상태 ORDER, CANCEL

}
