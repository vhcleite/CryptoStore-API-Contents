package com.store.apicontents.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.apicontents.integration.dto.PaymentStatus;
import com.store.apicontents.model.ContentStatus;
import com.sun.istack.NotNull;

public class ContentGetDto {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("status")
    private ContentStatus status;

    @JsonProperty("status_pagamento")
    private PaymentStatus paymentStatus;

    public ContentGetDto() {
    }

    public ContentGetDto(Long id, String name, String description, Double price, ContentStatus status, PaymentStatus paymentStatus) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
