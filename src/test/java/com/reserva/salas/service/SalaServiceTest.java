package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoSala;
import com.reserva.salas.dto.DadosCadastroSala;
import com.reserva.salas.dto.DadosListagemSala;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Sala;
import com.reserva.salas.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class SalaServiceTest {

    @InjectMocks
    private SalaService service;

    @Mock
    private SalaRepository repository;

    @Mock
    private DadosCadastroSala dados;

    private final UriComponentsBuilder uri = UriComponentsBuilder.newInstance();

    @Mock
    private Sala sala;

    @Mock
    private DadosAtualizacaoSala atualizacao;

    @Nested
    class CadastrarSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve cadastrar sala")
            void test1 (){
                DadosCadastroSala dados =
                        new DadosCadastroSala(
                                1,
                                60,
                                true
                        );
                given(repository.existsByNumero(dados.numero())).willReturn(false);
                Assertions.assertDoesNotThrow(() -> service.cadastrar(dados, uri));
                then(repository).should().save(any(Sala.class));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve cadastrar sala")
            void test2 (){
                given(repository.existsByNumero(any())).willReturn(true);
                Assertions.assertThrows(ReservaException.class, () -> service.cadastrar(dados, uri));
            }
        }
    }

    @Nested
    class ListarSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve listar sala com ativo true")
            void test3 (){
                Pageable pageable = PageRequest.of(0, 10, Sort.by("numero"));
                Sala sala = new Sala(dados);
                Page<Sala> page = new PageImpl<>(List.of(sala));
                given(repository.findAllByAtivaTrue(pageable)).willReturn(page);

                var response = service.listar(pageable);
                var body = response.getBody();

                assertNotNull(body);
                assertEquals(1, body.getTotalElements());
                then(repository).should().findAllByAtivaTrue(pageable);
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve listar sala com ativo true")
            void test4 (){
                Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
                given(repository.findAllByAtivaTrue(pageable)).willReturn(Page.empty());
                var response = service.listar(pageable);
                var body = response.getBody();

                assertEquals(Page.empty(), body);
            }
        }
    }

    @Nested
    class DetalharSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve detalhar sala com id existente")
            void test5 (){
                Long id = 1L;
                given(repository.findById(id)).willReturn(Optional.of(sala));
                Assertions.assertDoesNotThrow(() -> service.detalhar(id));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("ao detalhar Não deve retornar notFound sala")
            void test6 (){
                Long id = 999L;
                given(repository.findById(id)).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.detalhar(id));
            }
        }
    }

    @Nested
    class AtualizarSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve atualizar sala")
            void test7 (){
                given(repository.findById(atualizacao.id())).willReturn(Optional.of(sala));
                Assertions.assertDoesNotThrow(() -> service.atualizar(atualizacao));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("Não deve atualizar usuarios com id existente")
            void test8 (){
                given(repository.findById(atualizacao.id())).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.atualizar(atualizacao));
            }
        }
    }

    @Nested
    class CongelarSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 204 para congelar sala")
            void test9 (){
                Long id = 1L;
                given(repository.findById(id)).willReturn(Optional.of(sala));
                service.congelar(id);
                then(sala).should().congelar();
                Assertions.assertDoesNotThrow(() -> service.congelar(id));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 404 para congelar sala")
            void test10 (){
                Long id = 999L;
                given(repository.findById(id)).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.congelar(id));
            }
        }
    }

    @Nested
    class ExcluirSala{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 204 para excluir sala")
            void test11 (){
                Long id = 1L;
                repository.deleteById(id);
                Assertions.assertDoesNotThrow(() -> service.excluir(id));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 404 para excluir sala")
            void test12 (){
                Long id = 999L;
                given(service.excluir(id)).willThrow(new EntityNotFoundException());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.excluir(id));
            }
        }
    }
}