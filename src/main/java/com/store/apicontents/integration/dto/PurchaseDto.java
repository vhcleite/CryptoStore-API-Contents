package com.store.apicontents.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PurchaseDto {

    @JsonProperty("id_compra")
    private Long purchaseId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("content_id")
    private Long contentId;

    @JsonProperty("value")
    private Double value;

    @JsonProperty("data_hora_vencimento_pagamento")
    private LocalDateTime expirationDateTime;

    @JsonProperty("status_pagamento")
    private PaymentStatus paymentStatus;

    public PurchaseDto() {
    }

    public PurchaseDto(Long purchaseId, String userId, Long contentId, Double value, PaymentStatus paymentStatus) {
        this.purchaseId = purchaseId;
        this.userId = userId;
        this.contentId = contentId;
        this.value = value;
        this.paymentStatus = paymentStatus;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getExpirationDateTime() {
        return expirationDateTime;
    }

    public void setExpirationDateTime(LocalDateTime expirationDateTime) {
        this.expirationDateTime = expirationDateTime;
    }
}
