package com.masterclass.travelorder;

public class TravelOrderDTO {
    private String fromAirport;
    private String toAirport;
    private Integer nights;

    public TravelOrderDTO() {
    }

    public TravelOrderDTO(
            final String fromAirport,
            final String toAirport,
            final Integer nights) {

        this.fromAirport = fromAirport;
        this.toAirport = toAirport;
        this.nights = nights;
    }

    public static TravelOrderDTO of(
            TravelOrder order,
            Flight flight,
            Hotel hotel) {

        if (flight == null) {
            flight = new Flight();
        }

        if (hotel == null) {
            hotel = new Hotel();
        }

        return new TravelOrderDTO(
                flight.getFromAirport(),
                flight.getToAirport(),
                hotel.getNights());
    }

    public static TravelOrderDTO of(
            final String fromAirport,
            final String toAirport,
            final Integer nights) {

        return new TravelOrderDTO(fromAirport, toAirport, nights);
    }

    public String getFromAirport() {
        return fromAirport;
    }

    public void setFromAirport(String fromAirport) {
        this.fromAirport = fromAirport;
    }

    public String getToAirport() {
        return toAirport;
    }

    public void setToAirport(String toAirport) {
        this.toAirport = toAirport;
    }

    public Integer getNights() {
        return nights;
    }

    public void setNights(Integer nights) {
        this.nights = nights;
    }
}
