package com.udea.banco.repository;

import com.udea.banco.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Repositorio Spring Data JPA para la entidad {@link Transaction}.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Retorna todas las transacciones ordenadas por fecha descendente,
     * con las cuentas cargadas en un solo JOIN para evitar N+1 queries.
     */
    @Query("SELECT t FROM Transaction t " +
           "JOIN FETCH t.sourceAccount " +
           "JOIN FETCH t.targetAccount " +
           "ORDER BY t.executedAt DESC")
    List<Transaction> findAllOrderByDateDesc();
}
