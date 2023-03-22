package com.rental.video.store.controller;

import com.rental.video.store.model.DataSupplier;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RentTest {

    @LocalServerPort
    private int port;
    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @MockBean
    private DataSupplier service;

    @Test
    void whenGetAllVideosCalled_thenReturnValues() {
        List<String> testResult = new ArrayList<>(Arrays.asList("Matrix", "Matrix 2"));

        when(service.getAllVideos()).thenReturn(testResult);

        Object[] result = get(uri + "/rent/videos").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Object[].class);

        assertThat(Arrays.asList(result)).isEqualTo(testResult);
    }
}
