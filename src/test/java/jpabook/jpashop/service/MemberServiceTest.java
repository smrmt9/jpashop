package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;

import static org.junit.Assert.*;



// Junit 실행 할때 Spring이랑 역어서 실핼하기
@RunWith(SpringRunner.class)
// SpringBoot를 띄운 상태에서 Test하기 위해서 필요
@SpringBootTest
// 테스트에서 사용할 경우 테스트 후 Rollback 한다. 
// 데이터가 DB로 들어가는 것을 보고 싶을 경우 EntityManager를 주입해 강제로 flush() 해주거나 해당 테스트 위에 @Rollback(false) 어노테이션을 붙쳐 rollback을 막는다
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;



    @Test
    public void 회원가입() throws Exception{
        // 이런게 주어졌을때 이걸 이렇게 하면 이렇게 된다 검증해라

        //given 값이 주어졌을때
        Member member = new Member();
        member.setName("kim");

        //when 이렇게 하면
        Long saveId = memberService.join(member);

        //then 이렇게된다 검증 비교
        assertEquals(member, memberRepository.findOne(saveId));

    }

    // expected 코드를 사용할 경우 IllegalStateException가 발생할 경우 잡아 줄수 있다
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.

        //then
        // Junit이 제공해주는 Assert에 fail()이 있다.
        fail("예외가 발생햐야 한다.");
    }



}