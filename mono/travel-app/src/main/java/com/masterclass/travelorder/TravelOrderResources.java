package com.masterclass.travelorder;

import com.masterclass.flight.Flight;
import com.masterclass.flight.FlightResources;
import com.masterclass.hotel.Hotel;
import com.masterclass.hotel.HotelResources;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Path("/travel-order")
public class TravelOrderResources {

    @Inject
    FlightResources flightResource;

    @Inject
    HotelResources hotelResource;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelOrderDTO> orders(){
        return TravelOrder.<TravelOrder>listAll().stream()
                .map(
                        order -> TravelOrderDTO.of(
                                order,
                                flightResource.findByTravelOrderId(order.id),
                                hotelResource.findByTravelOrderId(order.id)
                        )
                ).collect(Collectors.toList());
    }

    @GET
    @Path("findById")
    public TravelOrder findById(@QueryParam("id") long id) {
        return TravelOrder.findById(id);
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TravelOrder newTravelOrder(TravelOrderDTO orderDto) {
        TravelOrder order = new TravelOrder();
        order.id = null;
        order.persist();

        Flight flight = new Flight();
        flight.fromAirport = orderDto.getFromAirport();
        flight.toAirport = orderDto.getToAirport();
        flight.travelOrderId = order.id;
        flightResource.newFlight(flight);

        Hotel hotel = new Hotel();
        hotel.nights = orderDto.getNights();
        hotel.travelOrderId = order.id;
        hotelResource.newHotel(hotel);

        return order;
    }
}
