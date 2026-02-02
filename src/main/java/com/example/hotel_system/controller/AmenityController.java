package com.example.hotel_system.controller;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.service.AmenityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public List<Amenities> getAllAmenities() {
        return amenityService.getAllAmenities();
    }
}
