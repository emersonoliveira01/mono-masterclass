package com.masterclass.hotel;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("hotel")
public class HotelResources {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Hotel> hotels(){
        return Hotel.listAll();
    }

    @GET
    @Path("findById")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findById(@QueryParam("id") long id){
        return Hotel.findById(id);
    }

    @GET
    @Path("findByTravelOrderId")
    @Produces(MediaType.APPLICATION_JSON)
    public Hotel findByTravelOrderId(@QueryParam("travelOrderId") long travelOrderId){
        return Hotel.findByTravelOrderId(travelOrderId);
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    public Hotel newHotel(Hotel hotel){
        hotel.id = null;
        hotel.persist();

        return hotel;
    }
}
