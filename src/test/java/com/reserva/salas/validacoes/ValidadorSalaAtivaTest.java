package com.reserva.salas.validacoes;

import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.repository.SalaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ValidadorSalaAtivaTest {

    @InjectMocks
    private ValidadorSalaAtiva validador;

    @Mock
    private SalaRepository repository;

    @Mock
    private DadosCadastroReserva dados;

    @Nested
    class ValidarSalaAtiva{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve validar sala ativa/disponível")
            void test1 () {
                given(repository.findAtivaById(dados.idSala())).willReturn(true);
                Assertions.assertDoesNotThrow(() -> validador.validar(dados));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve validar sala ativa/disponível")
            void test2 () {
                given(repository.findAtivaById(dados.idSala())).willReturn(false);
                Assertions.assertThrows(ReservaException.class, () -> validador.validar(dados));
            }
        }
    }
}