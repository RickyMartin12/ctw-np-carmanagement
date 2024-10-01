package com.ctw.car.entity;

import java.util.UUID;

public class Car {

    private UUID id;
    private String model;
    private String brand;
    private EngineType engineType;

    public Car() {

    }

    public Car(UUID id, String brand, String model, EngineType engineType) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.engineType = engineType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }
}

