package com.smwhc.smart_makeup_web.Makeup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smwhc.smart_makeup_web.Member.Member;

@Repository
public interface MakeUpRepository extends JpaRepository<MakeUp, Long> {
    public List<MakeUp> findByMember(Member member);
}