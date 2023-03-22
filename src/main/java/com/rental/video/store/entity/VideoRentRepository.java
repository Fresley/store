package com.rental.video.store.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VideoRentRepository extends JpaRepository<VideoRent, Integer> {

    List<VideoRent> findByCustomerAndRentEndDateIsNull(String customer);

    @Query("SELECT v paidDays FROM VideoRent v WHERE v.videoName IN (:videosToReturn) AND v.customer = :customer AND v.rentEndDate IS NULL")
    Collection<VideoRent> findVideosToReturn(@Param("videosToReturn") List<String> videosToReturn, @Param("customer") String customer);
}
