package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoReserva;
import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.dto.DadosCancelamentoReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Reserva;
import com.reserva.salas.model.Sala;
import com.reserva.salas.model.Usuario;
import com.reserva.salas.repository.ReservaRepository;
import com.reserva.salas.repository.SalaRepository;
import com.reserva.salas.repository.UsuarioRepository;
import com.reserva.salas.validacoes.ValidadorReserva;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @InjectMocks
    private ReservaService service;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private DadosCadastroReserva dados;

    @Mock
    private DadosAtualizacaoReserva atualizacao;

    @Mock
    private Sala sala;

    @Mock
    private Usuario usuario;

    @Mock
    private Reserva reserva;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DadosCancelamentoReserva cancelamento;

    @Spy
    private List<ValidadorReserva> validacoes = new ArrayList<>();

    @Mock
    private ValidadorReserva validador;

    @Nested
    class CriarReserva {

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve criar reserva")
            void test1() {
                given(reservaRepository.existsByNumero(dados.numero())).willReturn(false);
                given(usuarioRepository.existsById(dados.idUsuario())).willReturn(true);
                given(salaRepository.existsById(dados.idSala())).willReturn(true);
                given(usuarioRepository.getReferenceById(dados.idUsuario())).willReturn(usuario);
                given(salaRepository.getReferenceById(dados.idSala())).willReturn(sala);

                service.criar(dados);
                then(reservaRepository).should().save(any(Reserva.class));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve criar reserva")
            void test2 () {
                given(reservaRepository.existsByNumero(dados.numero())).willReturn(true);
                Assertions.assertThrows(ReservaException.class, () -> service.criar(dados));
            }
        }
    }

    @Nested
    class AtualizarReserva {

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve atualizar reserva")
            void test3() {
                given(reservaRepository.getReferenceById(atualizacao.id())).willReturn(reserva);
                Assertions.assertDoesNotThrow(() -> service.atualizar(atualizacao));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve atualizar reserva")
            void teste4() {
                given(reservaRepository.getReferenceById(atualizacao.id())).willThrow(new EntityNotFoundException());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.atualizar(atualizacao));
            }
        }
    }

    @Nested
    class ExcluirReserva {

        @Nested
        class Sucesso{
            @Test
            @DisplayName("deve cancelar reserva")
            void teste5() {
                Long id = 1L;
                reservaRepository.deleteById(id);
                Assertions.assertDoesNotThrow(() -> service.cancelar(cancelamento));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve cancelar reserva")
            void teste6() {
                given(service.cancelar(cancelamento)).willThrow(new EntityNotFoundException());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.cancelar(cancelamento));
            }
        }
    }

    @Nested
    class ChamarValidador{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve chamar validadores")
            void test7 (){
                given(reservaRepository.existsByNumero(dados.numero())).willReturn(false);
                given(usuarioRepository.existsById(dados.idUsuario())).willReturn(true);
                given(salaRepository.existsById(dados.idSala())).willReturn(true);
                validacoes.add(validador);

                service.criar(dados);
                BDDMockito.then(validador).should().validar(dados);
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve chamar validadores Erro Usuario inexistente")
            void test8 (){
                given(usuarioRepository.existsById(dados.idUsuario())).willReturn(false);
                Assertions.assertThrows(ReservaException.class, () -> service.criar(dados));
            }

            @Test
            @DisplayName("Não deve chamar validadores Erro Sala inexistente")
            void test9 (){
                given(usuarioRepository.existsById(dados.idUsuario())).willReturn(true);
                given(salaRepository.existsById(dados.idSala())).willReturn(false);
                Assertions.assertThrows(ReservaException.class, () -> service.criar(dados));
            }
        }
    }
}

