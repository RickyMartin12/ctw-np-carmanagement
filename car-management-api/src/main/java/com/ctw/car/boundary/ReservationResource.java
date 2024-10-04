package com.ctw.car.boundary;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import com.ctw.booking.control.ReservationRepository;
import com.ctw.booking.control.ReservationService;
import com.ctw.booking.entity.Reservation;
import com.ctw.car.entity.Car;
import com.ctw.dto.ReservationDTO;
import com.ctw.dto.ReservationWithCarDTO;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/reservation") // Make sure this matches your URL
public class ReservationResource {

private final ReservationService reservationService;
    
    @Inject
   ReservationRepository reservationRespository; // Assuming you have a CarRepository to interact with the database
    
    @Context
    UriInfo uriInfo;


    @Inject
    public ReservationResource(final ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCars() {
        List<ReservationWithCarDTO> reservations = this.reservationService.getReservation();
        return Response.status(200).entity(reservations).build();
    }
    
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationById(@PathParam("id") int id) {
    	try {
    		ReservationWithCarDTO res = reservationRespository.findByIdAndSelectCarAndReservation(id);
            return Response.ok(res).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional // Ensure transaction is active
    public Response createReservation(ReservationDTO reservationDTO) {
    	Reservation reservation = new Reservation();
        reservation.setName(reservationDTO.getName());
        reservation.setLocation(reservationDTO.getLocation());
        reservation.setContactNumber(reservationDTO.getContactNumber());
        reservation.setLicenseNumber(reservationDTO.getLicenseNumber());
        
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(reservationDTO.getDataInicio(), inputFormatter);
        LocalDateTime dateTimeFim = LocalDateTime.parse(reservationDTO.getDataFim(), inputFormatter);

        // Format the LocalDateTime to 'yyyy-MM-dd HH:mm:ss' format
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateHour = dateTime.format(outputFormatter);
        String formattedDateHourFim = dateTimeFim.format(outputFormatter);

        reservation.setDateHour(Timestamp.valueOf(formattedDateHour)); // Handle proper format conversion
        
        
        String hexString = reservationDTO.getCarId();
        
        if (hexString.startsWith("0x")) {
            hexString = hexString.substring(2);
        }

        // Format it as UUID (8-4-4-4-12)
        String uuidString = hexString.replaceFirst(
            "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w+)", 
            "$1-$2-$3-$4-$5"
        );
        reservation.setCarId(UUID.fromString(uuidString));
        reservation.setDataHourFim(Timestamp.valueOf(formattedDateHourFim));

        // Add logic to save the reservation (e.g., using Panache repository)
        reservationRespository.persist(reservation);

        // Logic to save the reservation
        return Response.status(Response.Status.CREATED).entity(reservationDTO).build();
    }
    
    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional // Ensure this method is transactional
    public Response deleteReservation(@PathParam("id") Integer id) {
        boolean deleted = reservationService.deleteReservation(id); // Call service method

        if (deleted) {
            return Response.noContent().build(); // Return 204 No Content on success
        } else {
            return Response.status(Response.Status.NOT_FOUND) // Return 404 Not Found if the car does not exist
                           .entity("Car not found with ID: " + id)
                           .build();
        }
    }
}
