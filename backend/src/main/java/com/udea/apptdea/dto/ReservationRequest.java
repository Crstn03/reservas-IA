package com.udea.apptdea.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
    @NotBlank @Size(max = 120) String customerName,
    @NotNull @FutureOrPresent LocalDate reservationDate,
    @NotNull LocalTime reservationTime,
    @NotBlank @Size(max = 100) String service
) {}
