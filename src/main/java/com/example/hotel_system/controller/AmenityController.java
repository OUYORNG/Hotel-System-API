package com.example.hotel_system.controller;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.request.AmenityRequest;
import com.example.hotel_system.response.AmentiesResponse;
import com.example.hotel_system.service.AmenityService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final AmenityService amenityService;

    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public List<AmentiesResponse> getAllAmenities() {
        return amenityService.getAllAmenities();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Amenities createAmenity(
            @RequestParam("name") String name,
            @RequestParam("icon") MultipartFile icon
    ) {
        return amenityService.createAmenity(name, icon);
    }


}
