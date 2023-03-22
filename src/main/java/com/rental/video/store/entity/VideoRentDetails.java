package com.rental.video.store.entity;

import java.math.BigDecimal;

public interface VideoRentDetails {
    int getId();

    String getName();

    String getType();

    BigDecimal getPrice();

    String getRelease();

    int getPeriodStart();

    int getPeriodEnd();

    String getChargeType();
}
