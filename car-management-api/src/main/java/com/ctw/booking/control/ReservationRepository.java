package com.ctw.booking.control;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.ctw.booking.entity.Reservation;
import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.dto.ReservationWithCarDTO;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ReservationRepository implements PanacheRepository<Reservation> {
	@Inject
    EntityManager entityManager; // Inject EntityManager

    public List<ReservationWithCarDTO> fetchAllReservation() {
        // Native SQL query
        String sql = "SELECT t.id, t.name, t.location, t.contact_number, t.license_number, t.date_hour, t.data_hour_fim, " +
                     "t.car_id, c.MODEL, c.ENGINE_TYPE, c.BRAND, c.IMAGE " +
                     "FROM t_reservation t " +
                     "INNER JOIN t_car c ON c.ID = t.car_id";

        // Create the native query
        Query query = entityManager.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        List<ReservationWithCarDTO> reservations = new ArrayList<>();

        for (Object[] row : results) {
        	byte[] carIdBytes = (byte[]) row[7];
            String carIdString = bytesToHex(carIdBytes); // Convert to hex string
            ReservationWithCarDTO reservation = new ReservationWithCarDTO(
                (Integer) row[0],
                (String) row[1],
                (String) row[2],
                (String) row[3],
                (String) row[4],
                (java.sql.Timestamp) row[5],
                (java.sql.Timestamp) row[6],
                carIdString,
                (String) row[8],
                (String) row[9],
                (String) row[10],
                (String) row[11]
            );
            reservations.add(reservation);
        }

        return reservations;
    }
    
    public ReservationWithCarDTO findByIdAndSelectCarAndReservation(int id) {
        try {
            // Native SQL query
            String sql = "SELECT t.id, t.name, t.location, t.contact_number, t.license_number, t.date_hour, t.data_hour_fim, " +
                         "t.car_id, c.MODEL, c.ENGINE_TYPE, c.BRAND, c.IMAGE " +
                         "FROM t_reservation t " +
                         "INNER JOIN t_car c ON c.ID = t.car_id " +
                         "WHERE t.id = :id";

            // Create native query using EntityManager
            Query query = entityManager.createNativeQuery(sql);

            // Set parameter for :id
            query.setParameter("id", id);

            // Execute the query and retrieve the result
            Object[] result = (Object[]) query.getSingleResult();
            
            byte[] carIdBytes = (byte[]) result[7];
            String carIdString = bytesToHex(carIdBytes); // Convert to hex string
            
            // Map the result to the DTO
            ReservationWithCarDTO reservation = new ReservationWithCarDTO(
                (Integer) result[0], // t.id
                (String) result[1],  // t.name
                (String) result[2],  // t.location
                (String) result[3],  // t.contact_number
                (String) result[4],  // t.license_number
                (java.sql.Timestamp) result[5],  // t.date_hour
                (java.sql.Timestamp) result[6], 
                carIdString,  // t.car_id (UUID or binary)
                (String) result[8],  // c.MODEL
                (String) result[9],  // c.ENGINE_TYPE
                (String) result[10],  // c.BRAND
                (String) result[11]  // c.IMAGE
            );

            return reservation;
        } catch (NoResultException e) {
            return null; // Handle case where no result is found
        }
    }
    
    
    public Reservation findById(Integer id) {
        return entityManager.find(Reservation.class, id);
    }
    
    @Transactional
    public void updateReservation(Reservation resv) {
        entityManager.merge(resv); // Merges the state of the car entity
    }
    

 // Helper method to convert byte[] to hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder("0x");
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
