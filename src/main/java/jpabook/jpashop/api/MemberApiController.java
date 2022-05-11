package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;


@RestController             // Controller + ResponseBody
@RequiredArgsConstructor    // 생성자 주입
public class MemberApiController {

    private final MemberService memberService;


    // 회원조회
    // 문제점
    // 1. 엔티티를 응답값으로 직접 외붕에 노출
    // 2. 엔티티가 한 api만 대응
    @GetMapping("/api/v1/members")
    public List<Member> memberV1(){
        return memberService.findMemebers();
    }

    @GetMapping("/api/v2/members")
    public Result memberV2(){
        List<MemberDTO> collect = memberService.findMemebers().stream()
                    .map(m -> new MemberDTO(m.getName()))
                    .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }
    // 회원가입
    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 등록
     * @param request
     * @return id
     */
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    /**
     * 회원 수정
     * @param id, request
     * @return id, name
     */
    @PutMapping("/api/v2/members/{id}")
    // PUT : 같은걸 여러번 해도 한번만 됨
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDTO{
        private String name;
    }

    @Data
    static class CreateMemberRequest{
        @NotEmpty
        private String name;

        private Long id;
    }


    @Data
    static class CreateMemberResponse{
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest{
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse{
        private Long id;
        private String name;
    }
}
