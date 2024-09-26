package com.smwhc.smart_makeup_web.Makeup;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MakeUp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long makeup_id; 
}
