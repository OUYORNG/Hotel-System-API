package com.example.hotel_system.repository;

import com.example.hotel_system.model.Amenities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenities, Long> {
}
