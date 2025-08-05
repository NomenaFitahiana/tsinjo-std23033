package com.school.tsinjo.endpoint.rest.controller.health;

import com.school.tsinjo.model.Donation;
import com.school.tsinjo.repository.DonationRepository;
import com.school.tsinjo.repository.HelpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TsinjoController {

  @Autowired private DonationRepository donationRepository;

  @Autowired private HelpRepository helpRepository;

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("donations", donationRepository.findAllByOrderByDateDesc());
    model.addAttribute("helps", helpRepository.findAllByOrderByDateDesc());
    return "index";
  }

  @PostMapping("/don")
  public String submitDonation(Donation donation, Model model) {
    donation.setPaymentId("temp-" + System.currentTimeMillis());
    donation.setPaymentStatus("VERIFYING");
    donation.setDate(java.time.LocalDateTime.now());
    donationRepository.save(donation);

    return "redirect:/";
  }
}
