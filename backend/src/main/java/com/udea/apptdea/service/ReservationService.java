package com.udea.apptdea.service;

import com.udea.apptdea.dto.ReservationRequest;
import com.udea.apptdea.entity.Reservation;
import com.udea.apptdea.entity.ReservationStatus;
import com.udea.apptdea.exception.ReservationConflictException;
import com.udea.apptdea.exception.ReservationNotFoundException;
import com.udea.apptdea.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ReservationService {
    private final ReservationRepository repository;
    public ReservationService(ReservationRepository repository) { this.repository = repository; }

    @Transactional(readOnly = true)
    public List<Reservation> findAll() { return repository.findAll().stream().sorted((a,b) -> {
        int date = a.getReservationDate().compareTo(b.getReservationDate());
        return date != 0 ? date : a.getReservationTime().compareTo(b.getReservationTime());
    }).toList(); }

    public Reservation create(ReservationRequest request) {
        if (repository.existsByReservationDateAndReservationTimeAndStatus(
                request.reservationDate(), request.reservationTime(), ReservationStatus.ACTIVA)) {
            throw new ReservationConflictException("Ya existe una reserva activa para esa fecha y hora.");
        }
        return repository.save(new Reservation(request.customerName().trim(), request.reservationDate(),
                request.reservationTime(), request.service().trim()));
    }

    public Reservation cancel(Long id) {
        Reservation reservation = repository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
        if (reservation.getStatus() == ReservationStatus.CANCELADA) {
            throw new ReservationConflictException("La reserva ya está cancelada.");
        }
        reservation.cancel();
        return repository.save(reservation);
    }
}
