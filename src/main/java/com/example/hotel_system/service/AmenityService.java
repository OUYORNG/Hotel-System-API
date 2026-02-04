package com.example.hotel_system.service;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.repository.AmenityRepository;
import com.example.hotel_system.request.AmenityRequest;
import com.example.hotel_system.response.AmentiesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AmenityService {

    private final AmenityRepository amenityRepository;
    private final FileStorageService fileStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    public AmenityService(
            AmenityRepository amenityRepository,
            FileStorageService fileStorageService
    ) {
        this.amenityRepository = amenityRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<AmentiesResponse> getAllAmenities() {
        return amenityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Amenities createAmenity(String name, MultipartFile icon) {
        String iconPath = fileStorageService.save(icon);

        Amenities amenity = new Amenities();
        amenity.setName(name);
        amenity.setIcon(iconPath); // RELATIVE PATH ONLY

        return amenityRepository.save(amenity);
    }

    private AmentiesResponse mapToResponse(Amenities amenity) {
        AmentiesResponse response = new AmentiesResponse();
        response.setId(amenity.getId());
        response.setName(amenity.getName());
        response.setIcon(baseUrl + amenity.getIcon()); // FULL URL
        return response;
    }
}

