package com.reserva.salas.controller;

import com.reserva.salas.service.UsuarioService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsuarioService service;

    @Nested
    class CadastrarUsuario{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 201 para cadastrar usuario")
            void teste1() throws Exception{
                String json = """
                {
                    "nome": "Rodrigo",
                    "telefone": "(21)0000-9090",
                    "ativo": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/usuarios")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(201, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 400 para cadastrar usuario")
            void teste2() throws Exception{
                String json ="";
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/usuarios")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(400, response.getStatus());
            }
        }
    }

    @Nested
    class ListarUsuario{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 200 para listar usuario")
            void teste3() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/usuarios")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 400 para listar usuario")
            void teste4() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                get("/usuariossalas"))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class DetalharUsuario{

        @Nested
        class Sucesso{
            @Test
            @DisplayName("deve retornar 200 para detalhar usuario")
            void teste5() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/usuarios/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha{
            @Test
            @DisplayName("deve retornar 404 para detalhar usuario")
            void teste6() throws Exception{
                when(service.detalhar(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/usuarios/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class AtualizarUsuario{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 200 para atualizar usuario")
            void teste7() throws Exception{
                String json = """
                {
                    "id": "1",
                    "nome": "Rodrigo",
                    "telefone": "(21)0000-9090",
                    "ativo": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/usuarios")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 400 para atualizar usuario")
            void teste8() throws Exception{
                String json = """
                {
                    "nome": "Rodrigo",
                    "telefone": "(21)0000-9090",
                    "ativo": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/usuarios")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(400, response.getStatus());
            }
        }
    }

    @Nested
    class CongelarUsuario{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 204 para congelar usuario")
            void teste9() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/usuarios/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(204, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 404 para congelar usuario")
            void teste10() throws Exception{
                when(service.congelar(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/usuarios/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class ExcluirUsuario{

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 204 para excluir usuario")
            void teste11() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/usuarios/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(204, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 404 para excluir usuario")
            void teste12() throws Exception{
                when(service.excluir(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/usuarios/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }
}