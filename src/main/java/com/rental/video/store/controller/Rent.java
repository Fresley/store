package com.rental.video.store.controller;

import com.rental.video.store.dto.VideoRentRequest;
import com.rental.video.store.model.DataSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/store")
public class Rent {

    @Autowired
    private DataSupplier service;

    @GetMapping("/videos")
    public ResponseEntity<List<String>> getUniqueAdvertisers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAllVideos());
    }

    @PutMapping("/price")
    public ResponseEntity<BigDecimal> checkPriceForRenting(@RequestBody List<VideoRentRequest> videos) {
        Map<String, BigDecimal> result;
        try {
            result = service.rentVideos(videos, "customer");
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "No videos found to rent"
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    /**
     * Will rent requested videos for customer specified in header 'customer'. If videos are available for
     * rent, total fee is returned.
     * @param customer header that will represent for who videos are rented
     * @param videos List of @VideoRentRequest objects representing videos to rent.
     * @return @BigDecimal as total sum of fees.
     */
    @PutMapping("/rent")
    public ResponseEntity<BigDecimal> rentVideos(
            @RequestHeader(name = "customer") String customer,
            @RequestBody List<VideoRentRequest> videos) {
        Map<String, BigDecimal> result;
        try {
            result = service.rentVideos(videos, customer);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "No videos found to rent"
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }

    @PutMapping("/return")
    public ResponseEntity<BigDecimal> returnVideos(
            @RequestHeader(name = "customer") String customer,
            @RequestBody List<VideoRentRequest> videos) {
        Map<String, BigDecimal> result;
        try {
            result = service.returnVideos(videos, customer);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT,
                    "No videos found to return"
            );
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    }
}
