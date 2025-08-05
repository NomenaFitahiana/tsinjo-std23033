package com.school.tsinjo.service;

import com.school.tsinjo.model.Donation;
import com.school.tsinjo.repository.DonationRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {
  private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

  @Autowired private RestTemplate restTemplate;

  @Autowired private DonationRepository donationRepository;

  private String apiKey;
  private String volaUrl;

  public PaymentService() {
    this.apiKey = System.getenv("VOLA_API_KEY");
    if (apiKey == null || apiKey.isEmpty()) {
      this.apiKey = "default-api-key";
      logger.warn("VOLA_API_KEY not found in environment. Using default: {}", this.apiKey);
    } else {
      logger.info("VOLA_API_KEY loaded successfully: {}", this.apiKey);
    }

    this.volaUrl = System.getenv("VOLA_URL");
    if (volaUrl == null || volaUrl.isEmpty()) {
      this.volaUrl = "https://default-api-url.com/v3/payments/";
      logger.warn("VOLA_URL not found in environment. Using default: {}", this.volaUrl);
    } else {
      logger.info("VOLA_URL loaded successfully: {}", this.volaUrl);
    }
  }

  public String submitPayment(Donation donation) {
    logger.info("Initiating payment submission for donor: {}", donation.getDonorEmail());
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + apiKey);
    HttpEntity<Donation> request = new HttpEntity<>(donation, headers);

    try {
      String paymentId = restTemplate.postForObject(volaUrl, request, String.class);
      logger.info(
          "Payment submitted successfully for donor: {}. Payment ID: {}",
          donation.getDonorEmail(),
          paymentId);
      return paymentId;
    } catch (Exception e) {
      logger.error(
          "Failed to submit payment for donor: {}. Error: {}",
          donation.getDonorEmail(),
          e.getMessage(),
          e);
      throw e;
    }
  }

  @Scheduled(fixedRate = 5000)
  public void checkPaymentStatus() {
    logger.info("Starting scheduled check for pending payment statuses");
    List<Donation> verifyingDonations = donationRepository.findByPaymentStatus("VERIFYING");

    if (verifyingDonations.isEmpty()) {
      logger.warn("No pending payments (VERIFYING) found to check");
      return;
    }

    for (Donation donation : verifyingDonations) {
      logger.info("Checking payment status for payment ID: {}", donation.getPaymentId());
      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", "Bearer " + apiKey);
      HttpEntity<Void> request = new HttpEntity<>(headers);

      try {
        ResponseEntity<String> response =
            restTemplate.exchange(
                volaUrl + "/" + donation.getPaymentId(), HttpMethod.GET, request, String.class);
        String status = response.getBody();

        if ("SUCCEEDED".equals(status) || "FAILED".equals(status)) {
          donation.setPaymentStatus(status);
          donationRepository.save(donation);
          logger.info("Payment {} updated to status: {}", donation.getPaymentId(), status);
        } else {
          logger.warn(
              "Unexpected status returned for payment {}: {}", donation.getPaymentId(), status);
        }
      } catch (Exception e) {
        logger.error(
            "Error checking payment status for payment ID: {}. Error: {}",
            donation.getPaymentId(),
            e.getMessage(),
            e);
      }
    }
    logger.info("Completed scheduled check for pending payment statuses");
  }
}
