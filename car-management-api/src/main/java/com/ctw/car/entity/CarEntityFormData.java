package com.ctw.car.entity;



import org.jboss.resteasy.annotations.providers.multipart.PartType;

import jakarta.ws.rs.FormParam;

public class CarEntityFormData {

	@FormParam("brand")
    public String brand;

    @FormParam("model")
    public String model;

    @FormParam("engineType")
    public EngineType engineType;
    
    @FormParam("color")
    public String color;

    @FormParam("image")
    @PartType("application/octet-stream") // Adjust based on file type, like "image/jpeg" or "image/png"
    public byte[] image;
    
    @FormParam("fileName")
    @PartType("text/plain")
    public String fileName;
    
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	
}

