package com.ctw.booking.control;

import java.util.List;
import java.util.UUID;

import com.ctw.booking.entity.Reservation;
import com.ctw.car.entity.CarEntity;
import com.ctw.dto.ReservationWithCarDTO;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class ReservationService {
	private final ReservationRepository reservationRepository;

    @Inject
    public ReservationService(final ReservationRepository carRepository) {
        this.reservationRepository = carRepository;
    }

    public List<ReservationWithCarDTO> getReservation() {
        return reservationRepository.fetchAllReservation();
    }
    
    @Transactional
    public boolean deleteReservation(Integer id) {
        Reservation reserv = reservationRepository.findById(id); // Find the car by ID

        if (reserv != null) {
        	reservationRepository.delete(reserv); // Delete the car if found
            return true; // Return true if deletion was successful
        }

        return false; // Return false if the car was not found
    }
}
