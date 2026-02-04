package com.example.hotel_system.response;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.model.RoomImage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailsResponse {
    private Long id;
    private String title;
    private String description;
    private Set<Amenities> amenities = new HashSet<>();
    private double pricePerNight;
    List<String> images;
}
