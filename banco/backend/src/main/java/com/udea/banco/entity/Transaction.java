package com.udea.banco.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa un traslado de dinero entre dos cuentas.
 * Almacena el QR en base64 para su consulta posterior.
 */
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Cuenta que envía el dinero. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    /** Cuenta que recibe el dinero. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "target_account_id", nullable = false)
    private Account targetAccount;

    /** Monto trasladado (siempre positivo). */
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    /** Descripción opcional del traslado. */
    @Column(length = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionStatus status = TransactionStatus.COMPLETADA;

    /** Fecha y hora de ejecución. */
    @Column(name = "executed_at", nullable = false, updatable = false)
    private LocalDateTime executedAt = LocalDateTime.now();

    /** Código QR en formato Base64 (PNG). */
    @Column(name = "qr_code", columnDefinition = "TEXT")
    private String qrCode;

    protected Transaction() {}

    public Transaction(Account sourceAccount, Account targetAccount,
                       BigDecimal amount, String description) {
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
        this.amount        = amount;
        this.description   = description;
    }

    public Long getId()                     { return id; }
    public Account getSourceAccount()       { return sourceAccount; }
    public Account getTargetAccount()       { return targetAccount; }
    public BigDecimal getAmount()           { return amount; }
    public String getDescription()          { return description; }
    public TransactionStatus getStatus()    { return status; }
    public LocalDateTime getExecutedAt()    { return executedAt; }
    public String getQrCode()              { return qrCode; }

    public void setStatus(TransactionStatus status) { this.status = status; }
    public void setQrCode(String qrCode)            { this.qrCode = qrCode; }
}
