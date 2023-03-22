package com.rental.video.store.model;

import com.rental.video.store.calculations.DailyPaymentCalculation;
import com.rental.video.store.calculations.PaymentCalculation;
import com.rental.video.store.calculations.PeriodicPaymentCalculation;
import com.rental.video.store.dto.VideoRentRequest;
import com.rental.video.store.entity.VideoRent;
import com.rental.video.store.entity.VideoRentDetails;
import com.rental.video.store.entity.VideoRentRepository;
import com.rental.video.store.entity.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataSupplier {

    @Autowired
    private VideoRepository repository;
    @Autowired
    private VideoRentRepository rentRepository;

    public List<String> getAllVideos() {
        return repository.findUniqueVideos();
    }

    public Map<String, BigDecimal> rentVideos(List<VideoRentRequest> videos, String customer) {
        List<VideoRent> videosToStore = createRentEntities(videos, customer);

        rentRepository.saveAllAndFlush(videosToStore);

        return getPricesForVideos(videos);
    }

    public Map<String, BigDecimal> returnVideos(List<VideoRentRequest> videos, String customer) {
        List<String> videosToSearch = getVideosToSearch(videos);

        Collection<VideoRent> rentedVideos = rentRepository.findVideosToReturn(videosToSearch, customer);
        Optional<List<VideoRentDetails>> videosPrices = repository.findVideosPrices(videosToSearch);

        if (rentedVideos.isEmpty() || videosPrices.isEmpty()) {
            throw new IllegalArgumentException();
        }
        LocalDateTime currentDateTime = LocalDateTime.now();

        Map<String, List<VideoRentDetails>> videosByName = videosPrices.get().stream()
                .collect(Collectors.groupingBy(VideoRentDetails::getName));

        Map<String, BigDecimal> result = new HashMap<>();

        rentedVideos.forEach(video -> {
            long days = ChronoUnit.DAYS.between(video.getRentStartDate(), currentDateTime);
            if (days > video.getPaidDays()) {
                result.put(video.getVideoName(), getSumFromPaymentStrategies(videosByName.get(video.getVideoName()), video.getPaidDays(), days));
            }
            video.setRentEndDate(currentDateTime);
        });
        rentRepository.saveAllAndFlush(rentedVideos);

        return result;
    }

    public Map<String, BigDecimal> getPricesForVideos(List<VideoRentRequest> videos) {
        List<String> videosToSearch = getVideosToSearch(videos);

        Optional<List<VideoRentDetails>> videosPrices = repository.findVideosPrices(videosToSearch);

        if (videosPrices.isEmpty()) {
            throw new IllegalArgumentException();
        }

        Map<String, List<VideoRentDetails>> videosByName = videosPrices.get().stream()
                .collect(Collectors.groupingBy(VideoRentDetails::getName));

        Map<String, BigDecimal> result = new HashMap<>();

        videosByName.forEach((k, v) -> {
            int daysToRent = getDaysFromVideos(videos, k);
            result.put(k, getSumFromPaymentStrategies(v, daysToRent, 0));
        });
        return result;
    }

    private List<String> getVideosToSearch(List<VideoRentRequest> videos) {
        List<String> videosToSearch = videos.stream()
                .map(VideoRentRequest::getVideoName)
                .collect(Collectors.toList());
        return videosToSearch;
    }

    private Integer getDaysFromVideos(List<VideoRentRequest> videos, String videoName) {
        Optional<VideoRentRequest> foundVideo = videos.stream()
                .filter(element -> videoName.equals(element.getVideoName()))
                .findFirst();

        return foundVideo.get().getDaysToRent();
    }

    private BigDecimal getSumFromPaymentStrategies(List<VideoRentDetails> videos, int days, long totalDays) {
        BigDecimal result = new BigDecimal(0);
        List<VideoRentDetails> sortedVideos = videos.stream()
                .sorted(Comparator.comparingInt(VideoRentDetails::getPeriodStart))
                .collect(Collectors.toList());

        for (VideoRentDetails video : sortedVideos) {
            PaymentCalculation calculationStrategy = getPaymentCalculationStrategy(video);
            if (totalDays > 0) {
                result = result.add(calculationStrategy.calculateExtraPayment(video, days, totalDays));
            } else {
                result = result.add(calculationStrategy.calculatePayment(video, days));
            }
        }

        return result;
    }

    private PaymentCalculation getPaymentCalculationStrategy(VideoRentDetails video) {
        PaymentCalculation result;
        switch (video.getChargeType()) {
            case "DAILY":
                result = new DailyPaymentCalculation(video);
                break;
            case "PERIODIC":
                result = new PeriodicPaymentCalculation(video);
                break;
            default:
                throw new IllegalArgumentException("Invalid calculation type");
        }
        return result;
    }

    private List<VideoRent> createRentEntities(List<VideoRentRequest> videos, String customer) {
        List<VideoRent> entitiesToStore = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();

        videos.forEach(video -> {
            VideoRent videoToStore = new VideoRent(customer, video.getVideoName(), dateTime, null, video.getDaysToRent());
            entitiesToStore.add(videoToStore);
        });

        return entitiesToStore;
    }
}
