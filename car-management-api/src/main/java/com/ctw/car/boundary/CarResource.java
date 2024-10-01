package com.ctw.car.boundary;


import com.ctw.car.control.CarRepository;
import com.ctw.car.control.CarService;
import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.car.entity.EngineType;
import com.ctw.car.entity.Routes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path(Routes.CAR)
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CarResource {

    private final CarService carService;
    
    @Inject
    CarRepository carRepository; // Assuming you have a CarRepository to interact with the database


    @Inject
    public CarResource(final CarService carService) {
        this.carService = carService;
    }

    @GET
    public Response getCars(
            @QueryParam("brand") String brand
    ) {
        List<Car> cars = this.carService.getCars();
        return Response.status(200).entity(cars).build();
    }
    
    @GET
    @Path("{id}")
    public Response getCarById(@PathParam("id") String id) {
    	try {
    		UUID uuid = UUID.fromString(id);
            Car car = carRepository.findByIdAndSelectModelAndBrand(uuid);
            return Response.ok(car).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    
    @POST
    @Path("/create") // Define the path for creating a new car
    public Response createCar(@QueryParam("model") String model,
            @QueryParam("brand") String brand,
            @QueryParam("engineType") EngineType engineType) {
    	
    	Car car = new Car();
        car.setModel(model);
        car.setBrand(brand);
        car.setEngineType(engineType);

        // Call the service to create the car
        Car createdCar = carService.createCar(car);
        
        // Return a response with the created car and a 201 status
        return Response.status(Response.Status.CREATED)
                .entity(createdCar)
                .build();
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Transactional // Ensure this method is transactional
    public Response deleteCar(@PathParam("id") UUID id) {
        boolean deleted = carService.deleteCar(id); // Call service method

        if (deleted) {
            return Response.noContent().build(); // Return 204 No Content on success
        } else {
            return Response.status(Response.Status.NOT_FOUND) // Return 404 Not Found if the car does not exist
                           .entity("Car not found with ID: " + id)
                           .build();
        }
    }
    
    
    @PUT
    @Path("{id}/edit")
    public Response editCarById(@PathParam("id") String id, CarEntity car) {
    	
    	UUID carId = UUID.fromString(id);  // Convert string ID to UUID

        // Retrieve the existing car by ID
        CarEntity existingCar = carRepository.findByUUID(carId);
        if (existingCar == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Car not found").build();
        }
        else {
        	// Update the existing car with the new data
            existingCar.setBrand(car.getBrand());
            existingCar.setModel(car.getModel());
            existingCar.setEngineType(car.getEngineType());
            //existingCar.setCreatedAt(LocalDateTime.now());

            // Call the repository to update the car
            carRepository.updateCar(existingCar);

            // Return a success response with the updated car entity
            return Response.ok(existingCar).build();
        }

        
    	
        

    }
    
    
}
