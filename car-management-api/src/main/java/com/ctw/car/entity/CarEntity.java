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

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
    
    

}

