package com.school.tsinjo.repository;

import com.school.tsinjo.model.Help;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HelpRepository extends JpaRepository<Help, Long> {
  List<Help> findAllByOrderByDateDesc();
}
