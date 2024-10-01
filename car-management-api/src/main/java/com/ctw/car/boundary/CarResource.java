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
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
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
    
    
}
