package com.ctw.dto;

public class CarDTO {
	private String id;
    private String brand;
    private String model;

    public CarDTO(String id, String brand, String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
