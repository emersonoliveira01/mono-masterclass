package com.masterclass.travelorder;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Path("/travel-order")
public class TravelOrderResources {

    @Inject
    @RestClient
    FlightService flightService;

    @Inject
    @RestClient
    HotelService hotelService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TravelOrderDTO> orders() {
        return TravelOrder.<TravelOrder>listAll().stream()
                .map(
                        order -> TravelOrderDTO.of(
                                order,
                                flightService.findByTravelOrderId(order.id),
                                hotelService.findByTravelOrderId(order.id)
                        )).collect(Collectors.toList());
    }

    @GET
    @Path("findById")
    public TravelOrder findById(@QueryParam("id") long id) {
        return TravelOrder.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public TravelOrder newTravelOrder(TravelOrderDTO orderDto) {
        TravelOrder order = new TravelOrder();
        order.id = null;
        order.persist();

        Flight flight = new Flight();
        flight.setFromAirport(orderDto.getFromAirport());
        flight.setToAirport(orderDto.getToAirport());
        flight.setId(order.id);
        flightService.newFlight(flight);

        Hotel hotel = new Hotel();
        hotel.setNights(orderDto.getNights());
        hotel.setTravelOrderId(order.id);
        hotelService.newHotel(hotel);

        return order;
    }
}
