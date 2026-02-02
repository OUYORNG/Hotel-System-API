package com.example.hotel_system.service;

import com.example.hotel_system.model.Booking;
import com.example.hotel_system.model.RoomModel;
import com.example.hotel_system.repository.BookingRepository;
import com.example.hotel_system.repository.RoomRepository;
import com.example.hotel_system.request.BookingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository, RoomRepository roomRepository){
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }
    public List<Booking> getBookingsByStartDate(LocalDate startDate) {
        return bookingRepository.findByStartDate(startDate);
    }

    public List<Booking> getBookingsByEndDate(LocalDate endDate) {
        return bookingRepository.findByEndDate(endDate);
    }

    public List<Booking> getBookingsByDateRange(LocalDate start, LocalDate end) {
        return bookingRepository.findByStartDateBetween(start, end);
    }

    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public Booking createBooking(BookingRequest request) {
        RoomModel room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + request.getRoomId()));

        Booking booking = new Booking();
        booking.setCustomerName(request.getCustomerName());
        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setNights(request.getNights());
        booking.setGuests(request.getGuests());
        booking.setTaxes(request.getTaxes());
        booking.setDiscount(request.getDiscount());
        booking.setAddress(request.getAddress());
        booking.setRoom(room);
        booking.setStatus(request.getStatus() != null ? request.getStatus() : "PENDING");

        return bookingRepository.save(booking);
    }
}
