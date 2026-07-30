package com.reserva.salas.repository;

import com.reserva.salas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Page<Usuario> findAllByAtivoTrue(Pageable pageable);

    Boolean existsByTelefone(String telefone);

    @Query("""
            select u.ativo
            from Usuario u
            where
            u.id = :id
            """)
    Boolean findAtivoById(Long id);
}
