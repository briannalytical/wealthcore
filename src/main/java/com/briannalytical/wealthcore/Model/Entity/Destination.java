package com.briannalytical.wealthcore.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "destinations")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(columnDefinition = "TEXT")
    private String notes;


    public Destination() {
    }

    public Destination(Trip trip, String locationName, Integer orderIndex) {
        this.trip = trip;
        this.locationName = locationName;
        this.orderIndex = orderIndex;
    }


    public Long getId() {return id;}

    public Trip getTrip() {return trip;}

    public String getLocationName() {return locationName;}

    public LocalDate getArrivalDate() {return arrivalDate;}

    public LocalDate getDepartureDate() {return departureDate;}

    public Integer getOrderIndex() {return orderIndex;}

    public String getNotes() {return notes;}


    public void setId(Long id) {this.id = id;}

    public void setTrip(Trip trip) {this.trip = trip;}

    public void setLocationName(String locationName) {this.locationName = locationName;}

    public void setArrivalDate(LocalDate arrivalDate) {this.arrivalDate = arrivalDate;}

    public void setDepartureDate(LocalDate departureDate) {this.departureDate = departureDate;}

    public void setOrderIndex(Integer orderIndex) {this.orderIndex = orderIndex;}

    public void setNotes(String notes) {this.notes = notes;}

}