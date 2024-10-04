package com.ctw.car.control;

import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.dto.CarDTO;
import com.ctw.dto.ReservationWithCarDTO;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CarRepository implements PanacheRepository<CarEntity> {
	
	@PersistenceContext
    EntityManager entityManager;
	
    public List<Car> fetchAllCars() {
        return listAll()
                .stream()
                .map(CarEntity::toCar)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public Car findByIdAndSelectModelAndBrand(UUID id) {
    	try {
            // Query to select only model and brand
            TypedQuery<Car> query = getEntityManager().createQuery(
                "SELECT new com.ctw.car.entity.Car(c.id, c.brand, c.model, c.engineType, c.color, c.image) FROM CarEntity c WHERE c.id = :id", Car.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no result is found
        }
    }
    
    public CarEntity findByUUID(UUID id) {
        return entityManager.find(CarEntity.class, id);
    }
    
    public Car findByIdAndSelectModelAndBrandBySring(String idString) {
        try {
            // Convert the String id to UUID
            UUID id = convertHexToUUID(idString);
            
            // Query to select only model and brand
            TypedQuery<Car> query = getEntityManager().createQuery(
                "SELECT new com.ctw.car.entity.Car(c.id, c.brand, c.model, c.engineType, c.color, c.image) FROM CarEntity c WHERE c.id = :id", Car.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no result is found
        } catch (IllegalArgumentException e) {
            // Handle invalid UUID string
            System.err.println("Invalid UUID string: " + idString);
            return null;
        }
    }
    
    public static UUID convertHexToUUID(String hexString) {
        // Remove the '0x' prefix if present
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        // Check if the hex string is 32 characters long
        if (hexString.length() != 32) {
            throw new IllegalArgumentException("Invalid hex string length for UUID: " + hexString);
        }

        // Create UUID from the hex string
        long mostSigBits = Long.parseUnsignedLong(hexString.substring(0, 16), 16);
        long leastSigBits = Long.parseUnsignedLong(hexString.substring(16), 16);

        return new UUID(mostSigBits, leastSigBits);
    }
    
    
    
    @Transactional
    public void updateCar(CarEntity car) {
        entityManager.merge(car); // Merges the state of the car entity
    }
    
    
    public List<CarDTO> fetchAllCarsReserv() {
        // Native SQL query
        String sql = "SELECT c.id, c.BRAND, c.MODEL FROM t_car c";

        // Create the native query
        Query query = entityManager.createNativeQuery(sql);

        @SuppressWarnings("unchecked")
        List<Object[]> results = query.getResultList();
        List<CarDTO> carLists = new ArrayList<>();

        for (Object[] row : results) {
        	byte[] carIdBytes = (byte[]) row[0];
            String carIdString = bytesToHex(carIdBytes); // Convert to hex string
            CarDTO carListEach = new CarDTO(
            	carIdString,
                (String) row[1],
                (String) row[2]
            );
            carLists.add(carListEach);
        }

        return carLists;
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
