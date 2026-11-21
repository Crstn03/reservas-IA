package com.udea.banco.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO de solicitud para realizar un traslado bancario.
 *
 * @param sourceAccountNumber número de cuenta origen
 * @param targetAccountNumber número de cuenta destino
 * @param amount              monto a trasladar (positivo, máx 500 millones)
 * @param description         descripción opcional del traslado
 */
public record TransferRequest(
        @NotBlank(message = "El número de cuenta origen es obligatorio")
        String sourceAccountNumber,

        @NotBlank(message = "El número de cuenta destino es obligatorio")
        String targetAccountNumber,

        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
        @DecimalMax(value = "500000000.00", message = "El monto no puede superar $500,000,000")
        BigDecimal amount,

        @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
        String description
) {}
