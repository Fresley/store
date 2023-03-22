package com.rental.video.store.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "video_rent")
public class VideoRent {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "customer")
    private String customer;
    @Column(name = "video_name")
    private String videoName;
    @Column(name = "rent_start_date")
    private LocalDateTime rentStartDate;
    @Column(name = "rent_end_date")
    private LocalDateTime rentEndDate;
    @Column(name = "paid_days")
    private int paidDays;

    public VideoRent() {
    }

    public VideoRent(String customer, String videoName, LocalDateTime rentStartDate, LocalDateTime rentEndDate, int paidDays) {
        this.customer = customer;
        this.videoName = videoName;
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.paidDays = paidDays;
    }

    public VideoRent(int id, String customer, String videoName, LocalDateTime rentStartDate, LocalDateTime rentEndDate, int paidDays) {
        this.id = id;
        this.customer = customer;
        this.videoName = videoName;
        this.rentStartDate = rentStartDate;
        this.rentEndDate = rentEndDate;
        this.paidDays = paidDays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public LocalDateTime getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(LocalDateTime rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public LocalDateTime getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(LocalDateTime rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public int getPaidDays() {
        return paidDays;
    }

    public void setPaidDays(int paidDays) {
        this.paidDays = paidDays;
    }
}
