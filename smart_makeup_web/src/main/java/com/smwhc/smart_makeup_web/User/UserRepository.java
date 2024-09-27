package com.smwhc.smart_makeup_web.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 회원정보를 위한 레포지토리
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    
}
