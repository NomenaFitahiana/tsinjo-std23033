package com.school.tsinjo.repository;

import com.school.tsinjo.model.Donation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
  List<Donation> findAllByOrderByDateDesc();

  List<Donation> findByPaymentStatus(String status);
}
