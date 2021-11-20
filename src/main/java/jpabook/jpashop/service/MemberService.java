package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// JPA에서 데이터 변환 및 작업들은 Transactional 안에서 이루어져야한다.
// JavaX보다 SpringFramwork의 Transactional이 사용하는게 좋다
// class에 Transactional(readOnly = true)를 설정하면 하위 모든 메서드에 적용되며
// 메서드에서 따로 Transactional을 설정하면 설정된 Transactional을 우선하여 반영한다.
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    public final MemberRepository memberRepository;




    // 회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 전체 조회
    public List<Member> findMemebers(){
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    public void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    
}
