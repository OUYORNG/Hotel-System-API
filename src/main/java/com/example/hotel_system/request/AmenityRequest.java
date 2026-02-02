package com.example.hotel_system.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AmenityRequest {
    private Long id;
    private String name;
    private String icon;
}
