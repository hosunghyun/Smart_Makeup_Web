package com.smwhc.smart_makeup_web.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.smwhc.smart_makeup_web.Table.User;

// 회원정보를 위한 레포지토리
public interface UserRepository extends JpaRepository<User, String> {
    
}
