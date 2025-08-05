package com.school.tsinjo.service;

import com.school.tsinjo.model.Donation;
import com.school.tsinjo.repository.DonationRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {
  private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

  @Autowired private RestTemplate restTemplate;

  @Autowired private DonationRepository donationRepository;

  @Value("${VOLA_API_KEY}")
  private String apiKey;

  @Value("${VOLA_URL}")
  private String volaUrl;

  public String submitPayment(Donation donation) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + apiKey);
    HttpEntity<Donation> request = new HttpEntity<>(donation, headers);
    String paymentId = restTemplate.postForObject(volaUrl, request, String.class);
    logger.info("Submitted payment for donation: {}", donation.getDonorEmail());
    return paymentId;
  }

  @Scheduled(fixedRate = 5000)
  public void checkPaymentStatus() {
    List<Donation> verifyingDonations = donationRepository.findByPaymentStatus("VERIFYING");
    for (Donation donation : verifyingDonations) {
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + apiKey);
      HttpEntity<Void> request = new HttpEntity<>(headers);
      try {
        String status =
            restTemplate
                .exchange(volaUrl + donation.getPaymentId(), HttpMethod.GET, request, String.class)
                .getBody();
        if ("SUCCEEDED".equals(status) || "FAILED".equals(status)) {
          donation.setPaymentStatus(status);
          donationRepository.save(donation);
          logger.info("Payment {} updated to {}", donation.getPaymentId(), status);
        }
      } catch (Exception e) {
        logger.error("Error checking payment {}", donation.getPaymentId(), e);
      }
    }
  }
}
