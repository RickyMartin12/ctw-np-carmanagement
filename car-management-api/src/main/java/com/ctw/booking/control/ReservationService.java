package com.ctw.booking.control;

import java.util.List;


import com.ctw.dto.ReservationWithCarDTO;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

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
}
