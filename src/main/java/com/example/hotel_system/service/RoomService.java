package com.example.hotel_system.service;

import com.example.hotel_system.model.Amenities;
import com.example.hotel_system.model.RoomImage;

import com.example.hotel_system.model.RoomModel;
import com.example.hotel_system.repository.AmenityRepository;
import com.example.hotel_system.repository.RoomRepository;
import com.example.hotel_system.request.RoomRequest;
import com.example.hotel_system.response.AmentiesResponse;
import com.example.hotel_system.response.RoomDetailsResponse;
import com.example.hotel_system.response.RoomResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final FileStorageService fileStorageService;
    private final AmenityRepository amenityRepository;

    public RoomService(RoomRepository roomRepository, FileStorageService fileStorageService,AmenityRepository amenityRepository) {
        this.roomRepository = roomRepository;
        this.fileStorageService = fileStorageService;
        this.amenityRepository = amenityRepository;
    }

    public List<RoomResponse> getAllRooms() {
        List<RoomModel> rooms = roomRepository.findAll();
        List<RoomResponse> roomResponses = new ArrayList<>();

        for (RoomModel room : rooms) {
            RoomResponse response = new RoomResponse();
            response.setId(room.getId());
            response.setTitle(room.getTitle());
            response.setDescription(room.getDescription());
            response.setAmenities(
                    room.getAmenities()
                            .stream()
                            .map(this::mapAmenity)
                            .collect(java.util.stream.Collectors.toSet())
            );
            response.setPricePerNight(room.getPricePerNight());
            response.setImages(room.getImages());
            // Add other fields as necessary

            roomResponses.add(response);
        }

        return roomResponses;
    }

    public RoomDetailsResponse getRoomById(Long id) {
        RoomModel room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        List<String> fullImages = room.getImages()
                .stream()
                .map(img -> baseUrl + img)
                .toList();

        return new RoomDetailsResponse(
                room.getId(),
                room.getTitle(),
                room.getDescription(),
                room.getAmenities(),
                room.getPricePerNight(),
                fullImages
        );
    }

    public RoomModel createRoom(RoomRequest request) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile image : request.getImages()) {
            String imagePath = fileStorageService.save(image);
            imageUrls.add(imagePath); // RELATIVE PATH ONLY
        }
        RoomModel room = new RoomModel();
        Set<Amenities> amenities = new HashSet<>();

        for (Long amenityId : request.getAmenityIds()) {
            Amenities amenity = amenityRepository
                    .findById(amenityId)
                    .orElseThrow(() -> new RuntimeException("Amenity not found"));
            amenities.add(amenity);
        }

        room.setTitle(request.getTitle());
        room.setDescription(request.getDescription());
        room.setRoomType(request.getRoomType());
        room.setAmenities(amenities);
        room.setPricePerNight(request.getPricePerNight());
        room.setBedSize(request.getBedSize());
        room.setBedType(request.getBedType());
        room.setRating(request.getRating());
        room.setMaxGuest(request.getMaxGuest());
        room.setImages(imageUrls);

        return roomRepository.save(room);
    }

    public Page<RoomResponse> getRooms(Pageable pageable) {
        return roomRepository.findAll(pageable)
                .map(this::mapToResponse);
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
    @Value("${app.base-url}")
    private String baseUrl;

    private RoomResponse mapToResponse(RoomModel room) {
        RoomResponse response = new RoomResponse();
        response.setId(room.getId());
        response.setTitle(room.getTitle());
        response.setDescription(room.getDescription());
        response.setPricePerNight(room.getPricePerNight());

        // ✅ amenities with FULL icon URL
        Set<AmentiesResponse> amenityResponses = room.getAmenities()
                .stream()
                .map(this::mapAmenity)
                .collect(java.util.stream.Collectors.toSet());

response.setAmenities(amenityResponses);
        List<String> fullImageUrls = room.getImages()
                .stream()
                .map(img -> baseUrl + img)
                .toList();

        response.setImages(fullImageUrls);
        return response;
    }
    private AmentiesResponse mapAmenity(Amenities amenity) {
        AmentiesResponse res = new AmentiesResponse();
        res.setId(amenity.getId());
        res.setName(amenity.getName());
        res.setIcon(baseUrl + amenity.getIcon());
        return res;
    }
}

