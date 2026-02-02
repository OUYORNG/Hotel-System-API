package com.example.hotel_system.service;

import com.example.hotel_system.model.RoomImage;

import com.example.hotel_system.model.RoomModel;
import com.example.hotel_system.repository.RoomRepository;
import com.example.hotel_system.request.RoomRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<RoomModel> getAllRooms() {
        return roomRepository.findAll();
    }

    public RoomModel getRoomById(Long id) {
        return roomRepository.findById(id)
                .orElse(null);
    }

    // Create room (NO images here)
    public RoomModel createRoom(RoomRequest request) {
        RoomModel room = new RoomModel();

        room.setTitle(request.getTitle());
        room.setDescription(request.getDescription());
        room.setRoomType(request.getRoomType());
        room.setPricePerNight(request.getPricePerNight());
        room.setBedSize(request.getBedSize());
        room.setBedType(request.getBedType());
        room.setRating(request.getRating());
        room.setMaxGuest(request.getMaxGuest());

        return roomRepository.save(room);
    }

    public Page<RoomModel> getRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    public RoomModel updateRoom(Long id, RoomRequest request) {
        RoomModel existingRoom = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        existingRoom.setTitle(request.getTitle());
        existingRoom.setDescription(request.getDescription());
        existingRoom.setRoomType(request.getRoomType());
        existingRoom.setPricePerNight(request.getPricePerNight());
        existingRoom.setBedSize(request.getBedSize());
        existingRoom.setBedType(request.getBedType());
        existingRoom.setRating(request.getRating());
        existingRoom.setMaxGuest(request.getMaxGuest());

        return roomRepository.save(existingRoom);
    }

    public void deleteRoom(Long id) {
        RoomModel room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        roomRepository.delete(room);
    }
}
