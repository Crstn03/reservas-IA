package com.udea.banco.dto;

import com.udea.banco.entity.Transaction;
import com.udea.banco.entity.TransactionStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta con los datos de una transacción bancaria, incluido el QR en Base64.
 *
 * @param id                  identificador de la transacción
 * @param sourceAccountNumber número de cuenta origen
 * @param sourceOwnerName     titular de la cuenta origen
 * @param targetAccountNumber número de cuenta destino
 * @param targetOwnerName     titular de la cuenta destino
 * @param amount              monto trasladado
 * @param description         descripción del traslado
 * @param status              estado de la transacción
 * @param executedAt          fecha y hora de ejecución
 * @param qrCode              imagen QR en Base64 (data:image/png;base64,...)
 */
public record TransactionResponse(
        Long id,
        String sourceAccountNumber,
        String sourceOwnerName,
        String targetAccountNumber,
        String targetOwnerName,
        BigDecimal amount,
        String description,
        TransactionStatus status,
        LocalDateTime executedAt,
        String qrCode
) {
    /**
     * Convierte una entidad {@link Transaction} a su DTO de respuesta.
     *
     * @param t entidad de transacción
     * @return DTO con todos los datos de la transacción
     */
    public static TransactionResponse from(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getSourceAccount().getAccountNumber(),
                t.getSourceAccount().getOwnerName(),
                t.getTargetAccount().getAccountNumber(),
                t.getTargetAccount().getOwnerName(),
                t.getAmount(),
                t.getDescription(),
                t.getStatus(),
                t.getExecutedAt(),
                t.getQrCode()
        );
    }
}
