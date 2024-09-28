package com.smwhc.smart_makeup_web.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. 회원 등록 기능
    public void save(UserDTO userDTO) {
        User user = new User();
        user.setUser(userDTO);

        userRepository.save(user);
    }
    // 2. 회원 삭제 기능

    // 3. 회원 변경 기능

    // 4. 회원 찾기
    public User finduser(String user_id) {
        Optional<User> user = userRepository.findById(user_id);
        return user.orElse(null);
    }
}
