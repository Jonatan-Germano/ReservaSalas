package com.reserva.salas.repository;

import com.reserva.salas.factory.UsuarioFactory;
import com.reserva.salas.model.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static com.reserva.salas.factory.UsuarioFactory.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository repository;

    @Nested
    class FindAllByAtivoTrue{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deveRetornarSomenteUsuariosAtivos")
            void test1() {
                repository.save(usuarioAtivo());
                repository.save(usuarioInativo());

                Page<Usuario> pagina =
                        repository.findAllByAtivoTrue(PageRequest.of(0, 10));

                assertEquals(1, pagina.getTotalElements());
                assertTrue(pagina.getContent().get(0).getAtivo());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("pagina vazia")
            void test2() {
                repository.save(
                        UsuarioFactory.usuarioInativo());

                Page<Usuario> pagina =
                        repository.findAllByAtivoTrue(PageRequest.of(0, 10));

                assertTrue(pagina.isEmpty());
            }
        }
    }


    @Nested
    class FindAtivoById{
        @Nested
        class Sucesso{

            @Test
            @DisplayName("retornaria true status ativo pelo id")
            void test4() {
                Usuario usuario = repository.save(usuarioAtivo());
                Boolean ativo = repository.findAtivoById(usuario.getId());
                assertNotNull(ativo);
                assertTrue(ativo);
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("retornaria null quando usuario não existir")
            void test3() {
                Boolean ativo = repository.findAtivoById(usuarioAtivo().getId());
                assertNull(ativo);
            }
        }
    }

    @Nested
    class ExistsByTelefone{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("retornaria true quando tel existir")
            void test5() {
                repository.save(usuarioAtivo());
                assertTrue(repository.existsByTelefone("11999999999"));
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("retornaria false quando tel não existir")
            void test6() {
                repository.save(usuarioAtivo());
                assertFalse(repository.existsByTelefone("11777777777"));
            }
        }
    }

    @Nested
    class ErroTelDuplicado{

        @Nested
        class Falha{
            @Test
            @DisplayName("deve retornar erro de tel duplicado")
            void test7() {
                repository.save(usuarioAtivo());
                Usuario outro = build("Maria", "11999999999", true);

                assertThrows(DataIntegrityViolationException.class, () -> {
                    repository.saveAndFlush(outro);
                });
            }
        }
    }

    @Nested
    class PaginacaoCorreta{

        @Nested
        class Sucesso{
            @Test
            @DisplayName("paginacao correta")
            void test8() {
                repository.save(UsuarioFactory.build("Fulano", "111", true));
                repository.save(UsuarioFactory.build("Siclano", "222", true));
                repository.save(UsuarioFactory.build("Beltrano", "333", true));

                Page<Usuario> pagina =
                        repository.findAllByAtivoTrue(PageRequest.of(0, 2));

                assertEquals(2, pagina.getContent().size());
                assertEquals(3, pagina.getTotalElements());
            }
        }
    }
}
