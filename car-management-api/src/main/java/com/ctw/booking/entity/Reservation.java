package com.ctw.booking.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "t_reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 2048)
    private String name;

    @Column(name = "location", length = 2048)
    private String location;

    @Column(name = "contact_number", length = 20)
    private String contactNumber;

    @Column(name = "license_number", length = 20)
    private String licenseNumber;

    @Column(name = "date_hour", nullable = true, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp dateHour;

    @Column(name = "car_id", nullable = false)
    private UUID carId;
    
    @Column(name = "data_hour_fim", nullable = true)
    private Timestamp dataHourFim;

    
	// Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public Timestamp getDateHour() {
        return dateHour;
    }

    public void setDateHour(Timestamp dateHour) {
        this.dateHour = dateHour;
    }

    public UUID getCarId() {
        return carId;
    }

    public void setCarId(UUID carId) {
        this.carId = carId;
    }
    
    public Timestamp getDataHourFim() {
		return dataHourFim;
	}

	public void setDataHourFim(Timestamp dataHourFim) {
		this.dataHourFim = dataHourFim;
	}

}

