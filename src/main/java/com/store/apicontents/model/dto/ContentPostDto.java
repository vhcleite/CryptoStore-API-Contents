package com.store.apicontents.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.store.apicontents.model.ContentStatus;
import com.sun.istack.NotNull;

public class ContentPostDto {


    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    @NotNull
    private String name;

    @JsonProperty("description")
    @NotNull
    private String description;

    @JsonProperty("price")
    @NotNull
    private Double price;

    @JsonProperty("status")
    @NotNull
    private ContentStatus status;

    @JsonProperty("path")
    @NotNull
    private String path;

    public ContentPostDto() {
    }

    public ContentPostDto(Long id, String name, String description, Double price, ContentStatus status, String path) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.path = path;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
