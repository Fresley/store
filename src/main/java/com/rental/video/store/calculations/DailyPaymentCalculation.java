package com.rental.video.store.calculations;

import com.rental.video.store.entity.VideoRentDetails;

import java.math.BigDecimal;

public class DailyPaymentCalculation implements PaymentCalculation {
    private VideoRentDetails video;

    public DailyPaymentCalculation(VideoRentDetails video) {
        this.video = video;
    }

    @Override
    public BigDecimal calculatePayment(VideoRentDetails video, int days) {
        if (video.getPeriodStart() > days) return new BigDecimal(0);
        int daysToPay = days - video.getPeriodStart();
        BigDecimal decimalDays = new BigDecimal(daysToPay);

        return video.getPrice().multiply(decimalDays);
    }

    @Override
    public BigDecimal calculateExtraPayment(VideoRentDetails video, int paidDays, long totalDays) {
        if (video.getPeriodStart() > totalDays) return new BigDecimal(0);
        int daysToPay = (int) (totalDays - paidDays);
        BigDecimal decimalDays = new BigDecimal(daysToPay);

        return video.getPrice().multiply(decimalDays);
    }
}
