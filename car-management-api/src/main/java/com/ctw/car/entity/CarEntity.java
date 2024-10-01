package com.ctw.car.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "T_CAR")
public class CarEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    public UUID id;

    @Column(name = "BRAND", nullable = false)
    public String brand;

    @Column(name = "MODEL", nullable = false)
    public String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "ENGINE_TYPE", nullable = false)
    public EngineType engineType;

    @Column(name = "CREATED_AT", updatable = false, nullable = false)
    public LocalDateTime createdAt;

    @Column(name = "CREATED_BY", updatable = false)
    public String createdBy;

    public static Car toCar(CarEntity carEntity) {
        if (carEntity != null) {
            return new Car(carEntity.id, carEntity.brand, carEntity.model, carEntity.engineType); // Adjust as per your Car class
        }
        return null;
    }

}

