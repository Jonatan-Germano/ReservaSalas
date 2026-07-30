package com.reserva.salas.repository;

import com.reserva.salas.model.Sala;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    Page<Sala> findAllByAtivaTrue(Pageable pageable);

    Boolean existsByNumero(Integer numero);

    @Query("""
            select s.ativa
            from Sala s
            where
            s.id = :id
            """)
    Boolean findAtivaById(Long id);
}
