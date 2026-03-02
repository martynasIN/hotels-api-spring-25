package com.hotel.is.services;

import com.hotel.is.entity.Hotel;
import org.hibernate.query.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface HotelService {
    List<Hotel> findAll();

    Hotel findById(int id);

    Hotel save(Hotel hotel);

    void deleteById(int id);
}
