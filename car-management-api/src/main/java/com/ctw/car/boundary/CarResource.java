package com.ctw.car.boundary;


import com.ctw.car.control.CarRepository;
import com.ctw.car.control.CarService;
import com.ctw.car.entity.Car;
import com.ctw.car.entity.CarEntity;
import com.ctw.car.entity.CarEntityFormData;
import com.ctw.car.entity.EngineType;
import com.ctw.car.entity.Routes;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;


@Path(Routes.CAR)
@ApplicationScoped

public class CarResource {

    private final CarService carService;
    
    @Inject
    CarRepository carRepository; // Assuming you have a CarRepository to interact with the database
    
    @Context
    UriInfo uriInfo;


    @Inject
    public CarResource(final CarService carService) {
        this.carService = carService;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars(
            @QueryParam("brand") String brand
    ) {
        List<Car> cars = this.carService.getCars();
        return Response.status(200).entity(cars).build();
    }
    
    
    
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarById(@PathParam("id") String id) {
    	try {
    		UUID uuid = UUID.fromString(id);
            Car car = carRepository.findByIdAndSelectModelAndBrand(uuid);
            return Response.ok(car).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    
    /*@POST
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
    }*/
    
    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional // Ensure the method is transactional
    public Response createCar(@MultipartForm CarEntityFormData carFormData) {
    	
        try {
        	// Save car metadata to the database
            CarEntity car = new CarEntity();
            car.brand = carFormData.brand;
            car.model = carFormData.model;
            car.engineType = carFormData.engineType;
            car.color = carFormData.color;
            
            
            if (carFormData.image != null && carFormData.fileName != null) {
                // Specify the directory where the image will be saved
                String uploadDir = "uploads"; // Change this to your desired upload directory
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs(); // Create the directory if it does not exist
                }

                // Create a file with the desired name
                File imageFile = new File(dir, carFormData.fileName);

                try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                    fos.write(carFormData.image); // Write the image bytes to the file
                    fos.flush();
                    System.out.println("Image saved to: " + imageFile.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Image saving failed: " + e.getMessage())
                        .build();
                }
                
                String serverName = uriInfo.getBaseUri().toString();
                
                car.setImage(serverName  + "uploads/" + carFormData.fileName);
             
            } 
            
            
            carRepository.persist(car); // Save the car entity to the database

            // Optionally, return the file path in the response
            return Response.ok(car).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error creating car: " + e.getMessage())
                           .build(); // Return a 500 status if an error occurs
        }
        
        
        
        
        
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    
    
    /*
    @PUT
    @Path("{id}/edit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
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
    */
    
    @PUT
    @Path("{id}/edit")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Transactional // Ensure transaction is active
    public Response updateCar(@PathParam("id") String id, @MultipartForm CarEntityFormData carFormData) {
        // Logic to find the existing car by ID, update its fields
        // Save changes to the database
    	
    	try {
            // Example:
        	UUID carId = UUID.fromString(id);  // Convert string ID to UUID

            // Retrieve the existing car by ID
            CarEntity carEntity = carRepository.findByUUID(carId);
            
            if (carEntity == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Car not found").build();
            }
            else {
                carEntity.setBrand(carFormData.getBrand());
                carEntity.setModel(carFormData.getModel());
                carEntity.setEngineType(carFormData.getEngineType());
                carEntity.setColor(carFormData.getColor());
                
                if (carFormData.image != null && carFormData.fileName != null) {
                    // Specify the directory where the image will be saved
                    String uploadDir = "uploads"; // Change this to your desired upload directory
                    File dir = new File(uploadDir);
                    if (!dir.exists()) {
                        dir.mkdirs(); // Create the directory if it does not exist
                    }

                    // Create a file with the desired name
                    File imageFile = new File(dir, carFormData.fileName);

                    try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.write(carFormData.image); // Write the image bytes to the file
                        fos.flush();
                        System.out.println("Image saved to: " + imageFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Image saving failed: " + e.getMessage())
                            .build();
                    }
                    
                    String serverName = uriInfo.getBaseUri().toString();
                    
                    carEntity.setImage(serverName  + "uploads/" + carFormData.fileName);
                 
                }

                carRepository.updateCar(carEntity); // Save the changes
                return Response.ok(carEntity).build(); // Return updated car entity
            }
    		
    	} catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error creating car: " + e.getMessage())
                           .build(); // Return a 500 status if an error occurs
        }

    }
    
    
    
    
    
}
