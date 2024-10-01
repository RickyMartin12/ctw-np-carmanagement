package com.ctw.car.control;

import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class CarRepository implements PanacheRepository<CarEntity> {
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
                "SELECT new com.ctw.car.entity.Car(c.id, c.model, c.brand, c.engineType) FROM CarEntity c WHERE c.id = :id", Car.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle case where no result is found
        }
    }
}
