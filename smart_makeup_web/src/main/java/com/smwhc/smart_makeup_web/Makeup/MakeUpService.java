package com.smwhc.smart_makeup_web.Makeup;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.smwhc.smart_makeup_web.Member.Member;

@Service
public class MakeUpService {
    @Autowired
    private final MakeUpRepository makeUpRepository;

    public MakeUpService(MakeUpRepository makeUpRepository) {
        this.makeUpRepository = makeUpRepository;
    }

    public void savemakeup(MakeUp makeUp) {
        List<MakeUp> isMakeUp = makeUpRepository.findByMember(makeUp.getMember());
        MakeUp savemakeup = new MakeUp();

        if(isMakeUp.isEmpty()) {    // 만약 비었으면 새로 저장
            savemakeup.setMember(makeUp.getMember());
            savemakeup.setOpacity(makeUp.getOpacity());
            savemakeup.setColor_code(makeUp.getColor_code());
            savemakeup.setNumber(makeUp.getNumber());
            savemakeup.setCategory(makeUp.getCategory());
        }
        else {  // 사용자 화장 정보가 저장된 것이 있으니 아래 코드 실행
            for(MakeUp makeup : isMakeUp) {     // 몇 인지 모르니 forEach 문으로 반복문 실행
                if(makeup.getCategory().equals(makeUp.getCategory()) && makeup.getNumber().equals(makeUp.getNumber())) {    // 만약 화장품 종류와 저장할 위치 버튼 이 같다면 데이터를 갱신
                    savemakeup.setId(makeup.getId());
                    savemakeup.setMember(makeUp.getMember());
                    savemakeup.setOpacity(makeUp.getOpacity());
                    savemakeup.setColor_code(makeUp.getColor_code());
                    savemakeup.setNumber(makeUp.getNumber());
                    savemakeup.setCategory(makeUp.getCategory());
                    break;
                }
                else {      // 같은 것이 없으니 새로 저장
                    savemakeup.setMember(makeUp.getMember());
                    savemakeup.setOpacity(makeUp.getOpacity());
                    savemakeup.setColor_code(makeUp.getColor_code());
                    savemakeup.setNumber(makeUp.getNumber());
                    savemakeup.setCategory(makeUp.getCategory());
                }
            }
        }

        makeUpRepository.save(savemakeup);
    }

    // 멤버에 저장된 화장을 전부 반환
    public List<MakeUp> findByMember(Member member) {
        List<MakeUp> makeUps = makeUpRepository.findByMember(member);

        return makeUps;
    }
}
