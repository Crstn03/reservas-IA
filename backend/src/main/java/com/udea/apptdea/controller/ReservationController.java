package com.udea.apptdea.controller;

import com.udea.apptdea.dto.ReservationRequest;
import com.udea.apptdea.dto.ReservationResponse;
import com.udea.apptdea.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservationController {
    private final ReservationService service;
    public ReservationController(ReservationService service) { this.service = service; }
    @GetMapping public List<ReservationResponse> findAll() {
        return service.findAll().stream().map(ReservationResponse::from).toList();
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody ReservationRequest request) {
        return ReservationResponse.from(service.create(request));
    }
    @DeleteMapping("/{id}") public ReservationResponse cancel(@PathVariable Long id) {
        return ReservationResponse.from(service.cancel(id));
    }
}
