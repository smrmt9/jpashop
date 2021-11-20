package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

// 어노테이션 등록 >  자동으로 스프링 bean으로 관리됨
@Repository
public class MemberRepository {


    // PersistenceContext 어노테이션 사용 시 Spring이 EntityManger을 만들어 주입해준다
    @PersistenceContext
    private EntityManager em;


    public void save(Member member){
        em.persist(member);
    }
    // 단건 조회
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }

    // 다건 조회
    public List<Member> findAll(){
        // JPQL은 SQL과 차이가 있다 SQL은 테이블을 대상으로 쿼리를 하는반면 JPQL은 Entity(객체)를 대상으로 쿼리를 한다
        // 전부 찾아 올때는 JPQL을 작성해야한다. (쿼리, 반환타입)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 조건 조회 파라미터 바인딩해서 특정 회원 조회
    public List<Member> findByName(String name){
        // :name 파라미터를 바인딩한다 .setParameter()로 파마리터를 바인딩 하면 된다.
        return em.createQuery("select m From Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }




}
