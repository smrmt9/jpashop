package jpabook.jpashop.domain;


import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 기본생성자
    protected Address(){}

    // 값 타입은 변경이 되면 안됨으로 Setter를 지우고 생성자만 만들어서
    // 처음 만들들때만 값을 넣어준다
    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
