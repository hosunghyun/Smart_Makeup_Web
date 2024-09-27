package com.smwhc.smart_makeup_web.Makeup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smwhc.smart_makeup_web.User.UserRepository;

@Service
public class MakeUpService {
    @Autowired
    private final MakeUpRepository makeUpRepository;
    
    @Autowired
    private final UserRepository userRepository;

    public MakeUpService(MakeUpRepository makeUpRepository, UserRepository userRepository) {
        this.makeUpRepository = makeUpRepository;
        this.userRepository = userRepository;
    }

    // 1. 화장 저장 기능
    public void save(MakeUpDTO makeUpDTO) {
        MakeUp makeUp = new MakeUp();
        makeUp.setUser(userRepository.findById(makeUpDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User not found")));
        
        makeUp.setColor_code(makeUpDTO.getColor_code());
        makeUp.setOpacity(makeUpDTO.getOpacity());
        makeUpRepository.save(makeUp);
    }
    // 2. 화장 삭제 기능

    // 3. 화장 수정 기능

    // 4. 화장 변경 기능
}
