package com.example.hotel_system.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
public class RoomRequest {
    private String title;
    private String description;
    private double pricePerNight;
    private String roomType;
    private String bedType;
    private Integer bedSize;
    private Float rating;
    private Integer maxGuest;
    private List<MultipartFile> images;
}
