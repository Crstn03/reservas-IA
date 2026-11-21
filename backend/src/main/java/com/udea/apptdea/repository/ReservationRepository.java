package com.udea.apptdea.repository;

import com.udea.apptdea.entity.Reservation;
import com.udea.apptdea.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByReservationDateAndReservationTimeAndStatus(LocalDate reservationDate, LocalTime reservationTime, ReservationStatus status);
}
