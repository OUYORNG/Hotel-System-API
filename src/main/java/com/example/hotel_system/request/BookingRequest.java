package com.example.hotel_system.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class BookingRequest {
    private Long id;
    private String customerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer nights;
    private Double taxes;
    private Double discount;
    private Integer guests;
    private String address;
    private Long roomId;
    private String status;
}
