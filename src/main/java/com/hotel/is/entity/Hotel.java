package com.hotel.is.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="hotels")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="address")
    private String address;

    @Column(name="rankingAverage")
    private Double rankingAverage;

    @Column(name="room_price")
    private Double roomPrice;

    @Column(name="price_discount")
    private Double priceDoscount;

    @Column(name="comfort")
    private Integer comfort;

    @Column(name="summary")
    private String summary;

    @Column(name="description")
    private String description;

    @Column(name="image_cover")
    private String imageCover;

    @Column(name="createdAt")
    private LocalDateTime createdAt;

    @Column(name="updatedAt")
    private LocalDateTime updatedAt;

    public Hotel(){}

    public Hotel(Long id, String name, String address, Double rankingAverage, Double roomPrice, Double priceDoscount, Integer comfort, String description, String summary, String imageCover, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.rankingAverage = rankingAverage;
        this.roomPrice = roomPrice;
        this.priceDoscount = priceDoscount;
        this.comfort = comfort;
        this.description = description;
        this.summary = summary;
        this.imageCover = imageCover;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public Double getRankingAverage() {
        return rankingAverage;
    }

    public void setRankingAverage(Double rankingAverage) {
        this.rankingAverage = rankingAverage;
    }

    public Double getPriceDoscount() {
        return priceDoscount;
    }

    public void setPriceDoscount(Double priceDoscount) {
        this.priceDoscount = priceDoscount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getComfort() {
        return comfort;
    }

    public void setComfort(Integer comfort) {
        this.comfort = comfort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", rankingAverage=" + rankingAverage +
                ", roomPrice=" + roomPrice +
                ", priceDoscount=" + priceDoscount +
                ", comfort=" + comfort +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", imageCover='" + imageCover + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}
