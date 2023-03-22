package com.rental.video.store.dto;

public class VideoRentRequest {
    private String videoName;
    private int daysToRent;

    public VideoRentRequest(String videoName, int daysToRent) {
        this.videoName = videoName;
        this.daysToRent = daysToRent;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public int getDaysToRent() {
        return daysToRent;
    }

    public void setDaysToRent(int daysToRent) {
        this.daysToRent = daysToRent;
    }
}
