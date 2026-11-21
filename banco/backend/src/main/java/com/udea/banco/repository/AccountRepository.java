package com.udea.banco.repository;

import com.udea.banco.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio Spring Data JPA para la entidad {@link Account}.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Busca una cuenta por su número único.
     * @param accountNumber número de cuenta
     * @return Optional con la cuenta, o vacío si no existe
     */
    Optional<Account> findByAccountNumber(String accountNumber);
}
