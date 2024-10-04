package com.smwhc.smart_makeup_web.Image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    public List<Image> findByBoardId(Long boardId);
}
