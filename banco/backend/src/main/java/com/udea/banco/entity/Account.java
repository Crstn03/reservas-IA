package com.udea.banco.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa una cuenta bancaria en el sistema.
 */
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Número único de cuenta (p. ej. 001-100-001). */
    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    /** Nombre completo del titular. */
    @Column(name = "owner_name", nullable = false, length = 120)
    private String ownerName;

    /** Saldo disponible en pesos colombianos. */
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    protected Account() {}

    public Account(String accountNumber, String ownerName, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.ownerName     = ownerName;
        this.balance       = balance;
    }

    public Long getId()                   { return id; }
    public String getAccountNumber()      { return accountNumber; }
    public String getOwnerName()          { return ownerName; }
    public BigDecimal getBalance()        { return balance; }
    public LocalDateTime getCreatedAt()   { return createdAt; }

    /**
     * Debita el monto de la cuenta origen.
     * @param amount monto a debitar (positivo)
     */
    public void debit(BigDecimal amount)  { this.balance = this.balance.subtract(amount); }

    /**
     * Acredita el monto a la cuenta destino.
     * @param amount monto a acreditar (positivo)
     */
    public void credit(BigDecimal amount) { this.balance = this.balance.add(amount); }
}
