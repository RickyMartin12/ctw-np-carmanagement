package com.ctw.dto;

public class ReservationWithCarDTO {
    public int id;
    public String name;
    public String location;
    public String contactNumber;
    public String licenseNumber;
    public java.sql.Timestamp dateHour;
    public java.sql.Timestamp dateHourFim;
    public String car_id;
    public String model;
    public String engineType;
    public String brand;
    public String image;
    
    // Constructor
    public ReservationWithCarDTO(int id, String name, String location, String contactNumber, String licenseNumber,
                                  java.sql.Timestamp dateHour, java.sql.Timestamp dateHourFim, String car_id, String model, String engineType, String brand, String image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.contactNumber = contactNumber;
        this.licenseNumber = licenseNumber;
        this.dateHour = dateHour;
        this.dateHourFim = dateHourFim;
        this.car_id = car_id;
        this.model = model;
        this.engineType = engineType;
        this.brand = brand;
        this.image = image;
    }
}

