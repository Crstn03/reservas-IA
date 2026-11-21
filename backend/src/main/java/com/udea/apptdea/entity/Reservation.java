package com.udea.apptdea.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservations", indexes = @Index(name = "idx_reservation_slot_status", columnList = "reservation_date,reservation_time,status"))
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "customer_name", nullable = false, length = 120)
    private String customerName;
    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;
    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;
    @Column(nullable = false, length = 100)
    private String service;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private ReservationStatus status = ReservationStatus.ACTIVA;

    protected Reservation() {}
    public Reservation(String customerName, LocalDate reservationDate, LocalTime reservationTime, String service) {
        this.customerName = customerName; this.reservationDate = reservationDate;
        this.reservationTime = reservationTime; this.service = service; this.status = ReservationStatus.ACTIVA;
    }
    public Long getId() { return id; }
    public String getCustomerName() { return customerName; }
    public LocalDate getReservationDate() { return reservationDate; }
    public LocalTime getReservationTime() { return reservationTime; }
    public String getService() { return service; }
    public ReservationStatus getStatus() { return status; }
    public void cancel() { this.status = ReservationStatus.CANCELADA; }
}
