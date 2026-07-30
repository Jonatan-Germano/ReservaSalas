package com.reserva.salas.repository;

import com.reserva.salas.factory.ReservaFactory;
import com.reserva.salas.model.Reserva;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static com.reserva.salas.factory.ReservaFactory.reservaAtiva;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReservaRepositoryTest {

    @Autowired
    private ReservaRepository repository;

    @Nested
    class ExistsByNumero{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("retornaria true quando numero existir")
            void test1(){
                repository.save(reservaAtiva());
                assertTrue(repository.existsByNumero(7));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("retornaria null quando numero não existir")
            void test2(){
                repository.save(reservaAtiva());
                assertFalse(repository.existsByNumero(12));
            }

            @Test
            @DisplayName("retornaria erro de numero duplicado")
            void test3(){
                repository.save(reservaAtiva());
                Reserva reserva = ReservaFactory.build(7, 40l, 40l);

                assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(reserva));
            }
        }
    }
}