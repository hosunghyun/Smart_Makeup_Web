package com.smwhc.smart_makeup_web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smwhc.smart_makeup_web.Repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String test() {
        System.out.println(userRepository.findAll());
        System.out.println("정상작동중");
        return "/";
    }
}
