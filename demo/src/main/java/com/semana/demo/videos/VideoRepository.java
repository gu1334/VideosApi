package com.semana.demo.videos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository <Video, Long> {


}
