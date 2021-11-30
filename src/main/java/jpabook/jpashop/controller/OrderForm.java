package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class OrderForm {


  private Long memberId;

  private Long itemId;

  private int count;


}
