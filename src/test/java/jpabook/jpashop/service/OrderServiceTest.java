package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;


    // 주문
    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember();

        Item book = createBook("시골 JPA", 10000, 10);

        int count = 2;

        //when
        Long orderId =orderService.order(member.getId(), book.getId(), count);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("상품 주문 시 상태는 ORDER", OrderStatus.ORDER , getOrder.getStatus());
        assertEquals("주문한 상품종류 수가 정확해야 한다. ", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.",10000 * count, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8, book.getStockQuantity());
    }




    // 주문 시 재고 수량을 초과하면 예외가 떨어져야한다.
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member member = createMember();
        Item book = createBook("시골 JPA", 10000, 10);
        int count = 11;
        //when
        orderService.order(member.getId(),book.getId(),count);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }
    // 주문 취소
    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = createMember();
        Item book = createBook("시골 JPA", 10000, 10);
        int count = 9;
        Long orderId = orderService.order(member.getId(),book.getId(),count);

        //when
        orderService.orderCancel(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals("주문 상태가 CANCEL이 되어야 한다", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("수량이 원복 되어야 한다.", 10, book.getStockQuantity());
    }
    // 수량 감소 (단위테스트)
    // 단위 테스트
    @Test
    public void 수량감소() throws Exception{
        //given
        Item book = new Book();
        book.setStockQuantity(10);

        //when
        book.removeStock(11);

        //then
        fail("수량 감소 시 예외가 발생 해야한다. ");
    }
    // 주문 검색



    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAdress(new Address("서울","경기","123-123"));
        em.persist(member);
        return member;
    }
    private Item createBook(String name, int price, int stockQuantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }
}