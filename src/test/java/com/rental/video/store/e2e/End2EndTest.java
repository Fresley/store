package com.rental.video.store.e2e;

import com.rental.video.store.entity.Video;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class End2EndTest {
    @LocalServerPort
    private int port;
    private String uri;

    @PostConstruct
    public void init() {
        uri = "http://localhost:" + port;
    }

    @Test
    void whenGetAllVideosCalled_thenReturnValues() {
        Object[] result = get(uri + "/rent/videos").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Object[].class);

        assertThat(Arrays.asList(result)).isNotEmpty();
    }

    @Test
    void whenCheckPriceForRentingCalled_thenReturnPrice() {
        Video video = new Video();
        video.setName("Matrix 11");
        Video video2 = new Video();
        video.setName("Spider Man");
        List<Video> videos = new ArrayList<>(Arrays.asList(video, video2));

        Object[] result = with().body(videos).contentType(ContentType.JSON).when()
                .put("rent/price").then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(Object[].class);

        assertThat(Arrays.asList(result)).isNotEmpty();
    }
}
