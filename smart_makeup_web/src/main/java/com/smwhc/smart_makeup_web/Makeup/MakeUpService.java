package com.smwhc.smart_makeup_web.Makeup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smwhc.smart_makeup_web.Member.MemberRepository;

@Service
public class MakeUpService {
    @Autowired
    private final MakeUpRepository makeUpRepository;
    
    @Autowired
    private final MemberRepository memberRepository;

    public MakeUpService(MakeUpRepository makeUpRepository, MemberRepository memberRepository) {
        this.makeUpRepository = makeUpRepository;
        this.memberRepository = memberRepository;
    }

    // 1. 화장 저장 기능
    // public void save(MakeUpDTO makeUpDTO) {
    //     MakeUp makeUp = new MakeUp();
    //     Optional<Member> optionalMember = memberRepository.findById(makeUpDTO.getMember_id());

    //     Member member = optionalMember.orElseThrow(() -> new RuntimeException("Member not found"));

    //     makeUp.setMember(member);
    //     makeUp.setColor_code(makeUpDTO.getColor_code());
    //     makeUp.setOpacity(makeUpDTO.getOpacity());
    //     makeUpRepository.save(makeUp);
    // }
    // 2. 화장 삭제 기능

    // 3. 화장 수정 기능

    // 4. 화장 변경 기능
}
