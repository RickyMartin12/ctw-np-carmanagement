package com.ctw.car.control;

import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.car.entity.EngineType;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Dependent
public class CarService {
    private final CarRepository carRepository;

    @Inject
    public CarService(final CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCars() {
        return carRepository.fetchAllCars();
    }
    
    @Transactional
    public Car createCar(Car car) {
    	CarEntity carEntity = new CarEntity();
        //carEntity.id = UUID.randomUUID(); // Generate a new UUID
        carEntity.brand = car.getBrand();
        carEntity.model = car.getModel();
        carEntity.engineType = car.getEngineType();
        carEntity.createdAt = LocalDateTime.now(); // Set the current time
        carEntity.createdBy = "system"; // Replace with actual user if applicable

        carRepository.persist(carEntity); // Persist the CarEntity
        return CarEntity.toCar(carEntity); // Convert back to Car and return
    }
    
    @Transactional
    public boolean deleteCar(UUID id) {
        CarEntity car = carRepository.findByUUID(id); // Find the car by ID

        if (car != null) {
            carRepository.delete(car); // Delete the car if found
            return true; // Return true if deletion was successful
        }

        return false; // Return false if the car was not found
    }
}
