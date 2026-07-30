package com.reserva.salas.repository;

import com.reserva.salas.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
      Boolean existsByNumero(Integer numero);
}
