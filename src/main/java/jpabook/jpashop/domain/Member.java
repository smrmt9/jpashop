package jpabook.jpashop.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {


    // 식별자를 ID로 Mapping을 해주고,
    // GeneratedValue : 테이블에서 자동 생성됨
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;

    // 내장 타입을 쓸경우 해당 클래스에 @Embedable을 해당 객체에 @Embedded로 매핑을해준다 (단 한군데만 해줘도 된다)
    @Embedded
    private Address address;

    //연관관계의 거울 읽기전용이 된다
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();



}
