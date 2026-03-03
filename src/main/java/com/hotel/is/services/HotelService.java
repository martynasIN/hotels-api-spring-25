package com.hotel.is.services;

import com.hotel.is.entity.Hotel;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();

    Hotel findById(long id);

    Hotel save(Hotel hotel);

    void deleteById(long id);
}
