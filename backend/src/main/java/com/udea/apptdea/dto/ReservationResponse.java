package com.udea.apptdea.dto;

import com.udea.apptdea.entity.Reservation;
import com.udea.apptdea.entity.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(Long id, String customerName, LocalDate reservationDate,
                                  LocalTime reservationTime, String service, ReservationStatus status) {
    public static ReservationResponse from(Reservation r) {
        return new ReservationResponse(r.getId(), r.getCustomerName(), r.getReservationDate(),
            r.getReservationTime(), r.getService(), r.getStatus());
    }
}
