package com.smwhc.smart_makeup_web.Makeup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeUpRepository extends JpaRepository<MakeUp, Long> {
    
}