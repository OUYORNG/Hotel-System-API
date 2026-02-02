package com.example.hotel_system.service;

import com.example.hotel_system.model.RoomImage;
import com.example.hotel_system.model.RoomModel;
import com.example.hotel_system.repository.RoomImageRepository;
import com.example.hotel_system.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RoomImageService {

    private final RoomRepository roomRepository;
    private final RoomImageRepository roomImageRepository;

    public RoomImageService(RoomRepository roomRepository, RoomImageRepository roomImageRepository) {
        this.roomRepository = roomRepository;
        this.roomImageRepository = roomImageRepository;
    }

    // Upload multiple images for a room
    public List<RoomImage> addImagesToRoom(Long roomId, MultipartFile[] files) throws IOException {
        RoomModel room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        List<RoomImage> images = new ArrayList<>();
        for (MultipartFile file : files) {
            // Folder to store uploaded images
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, file.getBytes());

            // Save image info to DB
            RoomImage roomImage = new RoomImage();
            roomImage.setImage(path.toString()); // store file path
            roomImage.setRoom(room);
            images.add(roomImage);
        }

        roomImageRepository.saveAll(images);
        return images;
    }

    // Delete an image by ID
    public void deleteImage(Long imageId) {
        RoomImage image = roomImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));
        roomImageRepository.delete(image);
    }

    // Get all images of a room
    public List<RoomImage> getImagesByRoomId(Long roomId) {
        return roomImageRepository.findByRoomId(roomId);
    }
}
