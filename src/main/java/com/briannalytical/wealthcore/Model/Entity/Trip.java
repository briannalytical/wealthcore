package com.briannalytical.wealthcore.Model.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "total_cost", precision = 15, scale = 2)
    private BigDecimal totalCost;

    @Column(name = "delusional_rating")
    private Integer delusionalRating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "arrival_date")
    private LocalDate arrivalDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Destination> destinations = new ArrayList<>();


    public Trip() {
        this.totalCost = BigDecimal.ZERO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Trip(User user, String title) {
        this();
        this.user = user;
        this.title = title;
    }


    public Long getId() {return id;}

    public User getUser() {return user;}

    public String getTitle() {return title;}

    public BigDecimal getTotalCost() {return totalCost;}

    public Integer getDelusionalRating() {return delusionalRating;}

    public LocalDateTime getCreatedAt() {return createdAt;}

    public LocalDateTime getUpdatedAt() {return updatedAt;}

    public LocalDate getDepartureDate() {return departureDate;}

    public LocalDate getArrivalDate() {return arrivalDate;}

    public List<Destination> getDestinations() {return destinations;}


    public void setId(Long id) {this.id = id;}

    public void setUser(User user) {this.user = user;}

    public void setTitle(String title) {this.title = title;}

    public void setTotalCost(BigDecimal totalCost) {this.totalCost = totalCost;}

    public void setDelusionalRating(Integer delusionalRating) {this.delusionalRating = delusionalRating;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}

    public void setArrivalDate(LocalDate arrivalDate) {this.arrivalDate = arrivalDate;}

    public void setDepartureDate(LocalDate departureDate) {this.departureDate = departureDate;}

    public void setDestinations(List<Destination> destinations) {this.destinations = destinations;}


    // add a destination
    public void addDestination(Destination destination) {
        destinations.add(destination);
        destination.setTrip(this);
    }

    // remove a destination
    public void removeDestination(Destination destination) {
        destinations.remove(destination);
        destination.setTrip(null);
    }
}
