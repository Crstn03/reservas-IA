CREATE TABLE reservations (
    id BIGSERIAL PRIMARY KEY,
    customer_name VARCHAR(120) NOT NULL,
    reservation_date DATE NOT NULL,
    reservation_time TIME WITHOUT TIME ZONE NOT NULL,
    service VARCHAR(100) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    CONSTRAINT chk_reservation_status CHECK (status IN ('ACTIVA', 'CANCELADA'))
);

CREATE INDEX idx_reservation_slot_status
    ON reservations (reservation_date, reservation_time, status);

-- También evita dobles reservas ante solicitudes simultáneas.
CREATE UNIQUE INDEX uq_active_reservation_slot
    ON reservations (reservation_date, reservation_time)
    WHERE status = 'ACTIVA';
