package com.briannalytical.wealthcore.Model.Entity;

import com.briannalytical.wealthcore.Model.Enum.ItemType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "itinerary_items")
public class ItineraryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destination;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false)
    private ItemType itemType;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_datetime")
    private LocalDateTime startDatetime;

    @Column(name = "end_datetime")
    private LocalDateTime endDatetime;

    @Column(precision = 15, scale = 2)
    private BigDecimal cost;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(columnDefinition = "TEXT")
    private String details;


    public ItineraryItem() {
        this.cost = BigDecimal.ZERO;
    }

    public ItineraryItem(Destination destination, ItemType itemType, String title, Integer orderIndex) {
        this();
        this.destination = destination;
        this.itemType = itemType;
        this.title = title;
        this.orderIndex = orderIndex;
    }


    public Long getId() {return id;}

    public Destination getDestination() {return destination;}

    public ItemType getItemType() {return itemType;}

    public String getTitle() {return title;}

    public String getDescription() {return description;}

    public LocalDateTime getStartDatetime() {return startDatetime;}

    public LocalDateTime getEndDatetime() {return endDatetime;}

    public BigDecimal getCost() {return cost;}

    public Integer getOrderIndex() {return orderIndex;}

    public String getDetails() {return details;}


    public void setId(Long id) {this.id = id;}

    public void setDestination(Destination destination) {this.destination = destination;}

    public void setItemType(ItemType itemType) {this.itemType = itemType;}

    public void setTitle(String title) {this.title = title;}

    public void setDescription(String description) {this.description = description;}

    public void setStartDatetime(LocalDateTime startDatetime) {this.startDatetime = startDatetime;}

    public void setEndDatetime(LocalDateTime endDatetime) {this.endDatetime = endDatetime;}

    public void setCost(BigDecimal cost) {this.cost = cost;}

    public void setOrderIndex(Integer orderIndex) {this.orderIndex = orderIndex;}

    public void setDetails(String details) {this.details = details;}
}