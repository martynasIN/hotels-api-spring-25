package com.hotel.is.controllers;

import com.hotel.is.dto.HotelCreateDTO;
import com.hotel.is.dto.HotelResponseDTO;
import com.hotel.is.entity.Hotel;
import com.hotel.is.mapper.HotelMapper;
import com.hotel.is.services.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private final HotelService hotelService;
    private ObjectMapper objectMapper;

    @Autowired
    public HotelController(
            HotelService hotelService,
            ObjectMapper objectMapper
    ){
        this.hotelService = hotelService;
        this.objectMapper = objectMapper;
    }

    /**
     * Retrieves all hotels. Requires {@code ROLE_USER}.
     *
     * @return 200 with a JSON envelope containing status, result count, and hotel list
     */
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Map<String, Object>> findAll(){
        List<HotelResponseDTO> hotels = hotelService.findAll().stream()
                .map(HotelMapper::toResponse)
                .collect(Collectors.toList());
        Map<String, Object> response = Map.of(
                "status", "success",
                "result", hotels.size(),
                "data", hotels
        );

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a single hotel by ID.
     *
     * @param hotelId the hotel ID from the path
     * @return 200 with the hotel, or 404 if not found
     */
    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> getHotel(@PathVariable long hotelId){
        Hotel hotel = hotelService.findById(hotelId);
        if(hotel == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(HotelMapper.toResponse(hotel));
    }

    /**
     * Creates a new hotel. Requires {@code ROLE_ADMIN}.
     *
     * @param hotelDTO validated request body with hotel details
     * @return 200 with the created hotel
     */
    @PostMapping
    public ResponseEntity<HotelResponseDTO> addHotel(@Valid @RequestBody HotelCreateDTO hotelDTO){
        Hotel saved = hotelService.save(HotelMapper.toEntity(hotelDTO));
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    /**
     * Partially updates a hotel. Requires {@code ROLE_ADMIN}.
     * The {@code id} field is protected and cannot be patched.
     *
     * @param hotelId   the hotel ID from the path
     * @param patchData map of fields to update
     * @return 200 with the updated hotel, 400 if {@code id} is in the payload, or 404 if not found
     */
    @PatchMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> patchHotel(@PathVariable long hotelId,
                                                       @RequestBody Map<String, Object> patchData){
        Hotel tempHotel = hotelService.findById(hotelId);

        if(tempHotel == null){
            return ResponseEntity.notFound().build();
        }

        if(patchData.containsKey("id")){
            return ResponseEntity.badRequest().body(null);
        }

        ObjectNode  hotelNode = objectMapper.convertValue(tempHotel,ObjectNode.class);
        ObjectNode patchNode = objectMapper.convertValue(patchData, ObjectNode.class);

        hotelNode.setAll(patchNode);

        Hotel patchedHotel = objectMapper.convertValue(hotelNode, Hotel.class);
        patchedHotel.setUpdatedAt(LocalDateTime.now());

        Hotel saved = hotelService.save(patchedHotel);

        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    /**
     * Deletes a hotel by ID. Requires {@code ROLE_ADMIN}.
     *
     * @param hotelId the hotel ID from the path
     * @return 200 with a confirmation message, or 404 if not found
     */
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable long hotelId){
        Hotel tempHotel = hotelService.findById(hotelId);

        if(tempHotel == null){
            return ResponseEntity.notFound().build();
        }

        hotelService.deleteById(hotelId);

        return ResponseEntity.ok("Deleted hotel id - " + hotelId);
    }
}
