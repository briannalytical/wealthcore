package com.briannalytical.wealthcore.Model.Entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


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


    public void setId(Long id) {this.id = id;}

    public void setUser(User user) {this.user = user;}

    public void setTitle(String title) {this.title = title;}

    public void setTotalCost(BigDecimal totalCost) {this.totalCost = totalCost;}

    public void setDelusionalRating(Integer delusionalRating) {this.delusionalRating = delusionalRating;}

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}

    public void setUpdatedAt(LocalDateTime updatedAt) {this.updatedAt = updatedAt;}
}
