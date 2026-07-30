package com.reserva.salas.repository;

import com.reserva.salas.model.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.reserva.salas.factory.SalaFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SalaRepositoryTest {

    @Autowired
    private SalaRepository repository;

    @Nested
    class FindAllByAtivaTrue{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar salas ativas")
            void test1(){
                repository.save(salaAtiva());
                repository.save(salaInativa());

                Page<Sala> salas = repository.findAllByAtivaTrue(PageRequest.of(0, 10));

                assertEquals(1, salas.getTotalElements());
                assertTrue(salas.getContent().get(0).getAtiva());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("pagina vazia")
            void test2(){
                repository.save(salaInativa());
                Page<Sala> salas = repository.findAllByAtivaTrue(PageRequest.of(0, 10));
                assertTrue(salas.isEmpty());
            }
        }
    }

    @Nested
    class FindAtivaById{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("retornaria true quando sala existir")
            void test3(){
                Sala sala = repository.save(salaAtiva());
                Boolean ativa = repository.findAtivaById(sala.getId());
                assertNotNull(ativa);
                assertTrue(ativa);
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("retornaria null ativa pelo id")
            void test4(){
                Boolean ativa = repository.findAtivaById(salaAtiva().getId());
                assertNull(ativa);
            }
        }
    }

    @Nested
    class ExistsByNumero{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("retornaria true quando numero existir")
            void test5(){
                repository.save(salaAtiva());
                assertTrue(repository.existsByNumero(5));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("retornaria null quando numero não existir")
            void test6(){
                repository.save(salaAtiva());
                assertFalse(repository.existsByNumero(12));
            }
        }
    }

    @Test
    @DisplayName("retornaria erro de numero duplicado")
    void test7(){
        repository.save(salaAtiva());
        Sala sala = build(5, 40, true);
        assertThrows(DataIntegrityViolationException.class, () -> repository.saveAndFlush(sala));
    }

    @Test
    @DisplayName("paginacao correta")
    void test8(){
        repository.save(build(12, 40, true));
        repository.save(build(13, 60, true));
        repository.save(build(14, 60, true));

        Page<Sala> salas = repository.findAllByAtivaTrue(PageRequest.of(0, 2));

        assertEquals(2, salas.getContent().size());
        assertEquals(3, salas.getTotalElements());
    }
}