package com.school.tsinjo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Help {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String beneficiaryEmail;
  private String beneficiaryName;
  private String paymentId;
  private BigDecimal amount;
  private LocalDateTime date;
  private String accidentDescription;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBeneficiaryEmail() {
    return beneficiaryEmail;
  }

  public void setBeneficiaryEmail(String beneficiaryEmail) {
    this.beneficiaryEmail = beneficiaryEmail;
  }

  public String getBeneficiaryName() {
    return beneficiaryName;
  }

  public void setBeneficiaryName(String beneficiaryName) {
    this.beneficiaryName = beneficiaryName;
  }

  public String getPaymentId() {
    return paymentId;
  }

  public void setPaymentId(String paymentId) {
    this.paymentId = paymentId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public String getAccidentDescription() {
    return accidentDescription;
  }

  public void setAccidentDescription(String accidentDescription) {
    this.accidentDescription = accidentDescription;
  }
}
