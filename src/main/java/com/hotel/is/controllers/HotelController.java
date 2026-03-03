package com.hotel.is.controllers;

import com.hotel.is.dao.HotelRepository;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private final HotelService hotelService;
    private final HotelRepository hotelRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public HotelController(
            HotelService hotelService,
            HotelRepository hotelRepository,
            ObjectMapper objectMapper
    ){
        this.hotelService = hotelService;
        this.hotelRepository = hotelRepository;
        this.objectMapper = objectMapper;
    }

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

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> getHotel(@PathVariable int hotelId){
        Hotel hotel = hotelService.findById(hotelId);
        if(hotel == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(HotelMapper.toResponse(hotel));
    }

    @PostMapping
    public ResponseEntity<HotelResponseDTO> addHotel(@Valid @RequestBody HotelCreateDTO hotelDTO){
        Hotel saved = hotelService.save(HotelMapper.toEntity(hotelDTO));
        return ResponseEntity.ok(HotelMapper.toResponse(saved));
    }

    @PatchMapping("/{hotelId}")
    public ResponseEntity<HotelResponseDTO> patchHotel(@PathVariable int hotelId,
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

    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(@PathVariable int hotelId){
        Hotel tempHotel = hotelService.findById(hotelId);

        if(tempHotel == null){
            return ResponseEntity.notFound().build();
        }

        hotelService.deleteById(hotelId);

        return ResponseEntity.ok("Deleted hotel id - " + hotelId);
    }
}
