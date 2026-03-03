package com.hotel.is.services;

import com.hotel.is.dao.HotelRepository;
import com.hotel.is.entity.Hotel;
import com.hotel.is.exception.HotelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelsServiceImpl implements HotelService{
    private HotelRepository hotelRepository;

    @Autowired
    public HotelsServiceImpl(HotelRepository hotelRepository){
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<Hotel> findAll(){
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(long id){
        Optional<Hotel> result = hotelRepository.findById(id);

        Hotel hotel = null;

        if(result.isPresent()){
            hotel = result.get();
        }else{
            throw new HotelNotFoundException("Did not find hotel id - " + id);
        }

        return hotel;
    }

    @Override
    public Hotel save(Hotel hotel){
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteById(long id){
        hotelRepository.deleteById(id);
    }

}
