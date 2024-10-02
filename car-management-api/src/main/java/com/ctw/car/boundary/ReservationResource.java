package com.ctw.car.boundary;

import java.util.List;
import java.util.UUID;

import com.ctw.booking.control.ReservationRepository;
import com.ctw.booking.control.ReservationService;
import com.ctw.car.entity.Car;
import com.ctw.dto.ReservationWithCarDTO;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
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
}
