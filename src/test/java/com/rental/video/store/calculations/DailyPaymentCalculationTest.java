package com.rental.video.store.calculations;

import com.rental.video.store.entity.VideoRentDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DailyPaymentCalculationTest {

    @Mock
    private VideoRentDetails videoRentDetails;
    private DailyPaymentCalculation calc;

    @BeforeEach
    void setup() {
        calc = new DailyPaymentCalculation(videoRentDetails);
    }

    @Test
    void whenCalculatePaymentCalled_thenReturnResult() {
        when(videoRentDetails.getPeriodStart()).thenReturn(4);
        when(videoRentDetails.getPrice()).thenReturn(new BigDecimal(40));
        BigDecimal result = calc.calculatePayment(videoRentDetails, 6);

        assertThat(result.intValue()).isEqualTo(80);
    }
}
