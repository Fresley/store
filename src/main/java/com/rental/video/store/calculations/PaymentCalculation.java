package com.rental.video.store.calculations;

import com.rental.video.store.entity.VideoRentDetails;

import java.math.BigDecimal;

public interface PaymentCalculation {
    BigDecimal calculatePayment(VideoRentDetails video, int days);

    BigDecimal calculateExtraPayment(VideoRentDetails video, int paidDays, long totalDays);
}
