package com.example.hotel_system.controller;

import com.example.hotel_system.model.RoomModel;
import com.example.hotel_system.request.RoomRequest;
import com.example.hotel_system.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public Page<RoomModel> getRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return roomService.getRooms(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomModel> getRoomById(@PathVariable Long id) {
        RoomModel room = roomService.getRoomById(id);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }

    @PostMapping
    public ResponseEntity<RoomModel> createRoom(@RequestBody RoomRequest request) {
        RoomModel savedRoom = roomService.createRoom(request);
        return ResponseEntity.ok(savedRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomModel> updateRoom(
            @PathVariable Long id,
            @RequestBody RoomRequest request
    ) {
        RoomModel room = roomService.updateRoom(id, request);
        return ResponseEntity.ok(room);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoomModel> deleteRoom(@PathVariable Long id){
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
