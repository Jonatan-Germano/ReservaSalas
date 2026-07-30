package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoUsuario;
import com.reserva.salas.dto.DadosCadastroUsuario;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Usuario;
import com.reserva.salas.repository.UsuarioRepository;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private DadosCadastroUsuario dados;

    private final UriComponentsBuilder uri = UriComponentsBuilder.newInstance();

    @Mock
    private Usuario usuario;

    @Mock
    private DadosAtualizacaoUsuario atualizacao;

    @Nested
    class CadastrarUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve cadastrar usuario")
            void teste1 (){
                DadosCadastroUsuario dados =
                        new DadosCadastroUsuario(
                                "João",
                                "41999999999",
                                true
                        );
                given(repository.existsByTelefone(dados.telefone())).willReturn(false);
                Assertions.assertDoesNotThrow(() -> service.cadastrar(dados, uri));
                then(repository).should().save(any(Usuario.class));
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("Não deve cadastrar usuario com tel já cadastrado")
            void teste2 (){
                given(repository.existsByTelefone(dados.telefone())).willReturn(true);
                Assertions.assertThrows(ReservaException.class, () -> service.cadastrar(dados, uri));
            }
        }
    }

    @Nested
    class ListarUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve listar usuarios com ativo true")
            void teste3 (){
                Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
                Usuario usuario = new Usuario(dados);
                Page<Usuario> page = new PageImpl<>(List.of(usuario));
                given(repository.findAllByAtivoTrue(pageable)).willReturn(page);

                var response = service.listar(pageable);
                var body = response.getBody();

                assertNotNull(body);
                assertEquals(1, body.getTotalElements());
                then(repository).should().findAllByAtivoTrue(pageable);
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("Não deve listar usuarios com ativo true")
            void teste4 (){
                Pageable pageable = PageRequest.of(0, 10, Sort.by("nome"));
                given(repository.findAllByAtivoTrue(pageable)).willReturn(Page.empty());

                var response = service.listar(pageable);
                var body = response.getBody();

                assertEquals(Page.empty(), body);
            }
        }
    }

    @Nested
    class DetalharUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve detalhar usuarios com id existente")
            void teste5 (){
                Long id = 1L;
                given(repository.findById(id)).willReturn(Optional.of(usuario));
                Assertions.assertDoesNotThrow(() -> service.detalhar(id));
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("ao detalhar Não deve retornar notFound usuario")
            void teste6 (){
                Long id = 999L;
                given(repository.findById(id)).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.detalhar(id));
            }
        }
    }

    @Nested
    class AtualizarUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve atualizar usuario")
            void teste7 (){
                given(repository.findById(atualizacao.id())).willReturn(Optional.of(usuario));
                Assertions.assertDoesNotThrow(() -> service.atualizar(atualizacao));
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("Não deve atualizar usuarios com id existente")
            void teste8 (){
                given(repository.findById(atualizacao.id())).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.atualizar(atualizacao));
            }
        }
    }

    @Nested
    class CongelarUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 204 para congelar usuario")
            void teste9 (){
                Long id = 1L;
                given(repository.findById(id)).willReturn(Optional.of(usuario));
                service.congelar(id);
                then(usuario).should().congelar();
                Assertions.assertDoesNotThrow(() -> service.congelar(id));
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 404 para congelar usuario")
            void teste10 (){
                Long id = 999L;
                given(repository.findById(id)).willReturn(Optional.empty());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.congelar(id));
            }
        }
    }

    @Nested
    class ExcluirUsuario {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 204 para excluir usuario")
            void teste11 (){
                Long id = 1L;
                repository.deleteById(id);
                Assertions.assertDoesNotThrow(() -> service.excluir(id));
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 404 para excluir usuario")
            void test12 (){
                Long id = 999L;
                given(service.excluir(id)).willThrow(new EntityNotFoundException());
                Assertions.assertThrows(EntityNotFoundException.class, () -> service.excluir(id));
            }
        }
    }
}