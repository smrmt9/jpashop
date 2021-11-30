package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import static org.junit.Assert.*;

// Junit 실행 할때 Spring이랑 역어서 실핼하기
@RunWith(SpringRunner.class)
// SpringBoot를 띄운 상태에서 Test하기 위해서 필요
@SpringBootTest
@Transactional
public class ItemUpdateTest {

    @Autowired
    EntityManager em;

    Logger logger = LoggerFactory.getLogger(Logger.class);

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class, 1L);
        // TX (트랜잭션)
        // 변경감지 == dirty checking
        // 트랜잭션 안에서는 아래처럼 데이터를 바꾼경우 트랜잭션 flush 할때 JPA에서 변경사항을 감지해서 Update문을 자동으로 날린다 -> 변경감지라고한다.
        book.updateBook("testBook", book.getPrice(), book.getStockQuantity(), book.getAuthor(), book.getIsbn());

        // 이 메서드가 끝날시 Spring Transcation 에서 해당 Entity 값의 변경을 감지해서 (duty checking)을 해서 Update를 자동으로 쳐준다.
    }


    @Test
    public void 변경감지() throws Exception{
        //given

        Book book = Book.createBook("t1", 100, 100, "저자는", "979");
        em.persist(book);
        logger.info("========================================= id: {} == name {}:", book.getId(), book.getName());
        String b1 = book.getName();
        //when
        updateTest();
        logger.info("========================================= id: {} == name {}:", book.getId(), book.getName());
        //then

        assertEquals("두 책 제목은 달라야 한다", b1, book.getName());
    }

}
