package com.udea.banco.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta con los datos de una cuenta bancaria.
 *
 * @param id            identificador interno
 * @param accountNumber número de cuenta
 * @param ownerName     nombre del titular
 * @param balance       saldo actual
 * @param createdAt     fecha de creación
 */
public record AccountResponse(
        Long id,
        String accountNumber,
        String ownerName,
        BigDecimal balance,
        LocalDateTime createdAt
) {}
