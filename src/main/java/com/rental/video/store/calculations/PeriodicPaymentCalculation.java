package com.rental.video.store.calculations;

import com.rental.video.store.entity.VideoRentDetails;

import java.math.BigDecimal;

public class PeriodicPaymentCalculation implements PaymentCalculation {
    private VideoRentDetails video;

    public PeriodicPaymentCalculation(VideoRentDetails video) {
        this.video = video;
    }

    @Override
    public BigDecimal calculatePayment(VideoRentDetails video, int days) {
        return video.getPrice();
    }

    @Override
    public BigDecimal calculateExtraPayment(VideoRentDetails video, int paidDays, long totalDays) {
        return new BigDecimal(0);
    }
}
