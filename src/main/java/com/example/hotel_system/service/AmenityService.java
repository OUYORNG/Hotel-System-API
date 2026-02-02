package com.example.hotel_system.service;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.repository.AmenityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public List<Amenities> getAllAmenities() {
        return amenityRepository.findAll();
    }
}
