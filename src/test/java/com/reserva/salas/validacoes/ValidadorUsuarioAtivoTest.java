package com.reserva.salas.validacoes;

import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.repository.UsuarioRepository;
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
class ValidadorUsuarioAtivoTest {

    @InjectMocks
    private ValidadorUsuarioAtivo validador;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private DadosCadastroReserva dados;

    @Nested
    class ValidarUsuarioAtivo{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve validar usuario ativo")
            void teste1() {
                given(repository.findAtivoById(dados.idUsuario())).willReturn(true);
                Assertions.assertDoesNotThrow(() -> validador.validar(dados));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve validar usuario ativo")
            void teste2() {
                given(repository.findAtivoById(dados.idUsuario())).willReturn(false);
                Assertions.assertThrows(ReservaException.class, () -> validador.validar(dados));
            }
        }
    }
}