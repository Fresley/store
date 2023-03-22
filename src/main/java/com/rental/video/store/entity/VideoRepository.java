package com.rental.video.store.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    @Query("SELECT name FROM Video")
    List<String> findUniqueVideos();

    @Query(value = "SELECT v.id as id, v.name as name, p.type as type, p.price as price, s.release_name as release, s.period_start as periodStart, s.period_end as periodEnd, s.charge as chargeType FROM video v \n" +
            "JOIN video_pricing_strategy s ON v.release_name = s.release_name\n" +
            "JOIN video_pricing p ON s.type = p.type\n" +
            "WHERE v.name IN (:names)\n" +
            "ORDER BY v.name", nativeQuery = true)
    Optional<List<VideoRentDetails>> findVideosPrices(@Param("names") List<String> names);
}
