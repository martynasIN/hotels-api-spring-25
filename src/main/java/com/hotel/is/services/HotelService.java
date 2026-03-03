package com.hotel.is.services;

import com.hotel.is.entity.Hotel;

import java.util.List;

public interface HotelService {

    /**
     * Returns all hotels stored in the database.
     *
     * @return list of all hotels, empty list if none exist
     */
    List<Hotel> findAll();

    /**
     * Finds a hotel by its primary key.
     *
     * @param id the hotel ID
     * @return the matching hotel entity
     * @throws com.hotel.is.exception.HotelNotFoundException if no hotel exists with the given ID
     */
    Hotel findById(long id);

    /**
     * Persists a new hotel or updates an existing one.
     *
     * @param hotel the hotel entity to save
     * @return the saved hotel with any generated fields (e.g. ID) populated
     */
    Hotel save(Hotel hotel);

    /**
     * Deletes the hotel with the given ID. No-op if the hotel does not exist.
     *
     * @param id the hotel ID to delete
     */
    void deleteById(long id);
}
