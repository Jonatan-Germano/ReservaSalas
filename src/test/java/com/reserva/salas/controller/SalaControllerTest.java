package com.reserva.salas.controller;

import com.reserva.salas.service.SalaService;
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
class SalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SalaService service;

    @Nested
    class CadastrarSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 201 para cadastrar sala")
            void teste1() throws Exception{
                String json = """
                {
                    "numero": "1",
                    "capacidade": "50",
                    "ativa": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/salas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(201, response.getStatus());
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 400 para cadastrar sala")
            void teste2() throws Exception{
                String json ="";
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/salas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(400, response.getStatus());
            }
        }
    }

    @Nested
    class ListarSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 200 para listar sala")
            void teste3() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/salas")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 400 para listar sala")
            void teste4() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                get("/salasusuarios"))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class DetalharSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 200 para detalhar sala")
            void teste5() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/salas/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 404 para detalhar sala")
            void teste6() throws Exception{
                when(service.detalhar(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.get("/salas/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class AtualizarSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 200 para atualizar sala")
            void teste7() throws Exception{
                String json = """
                {
                    "id": "1",
                    "numero": "1",
                    "capacidade": "50",
                    "ativa": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/salas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha {
            @Test
            @DisplayName("deve retornar 400 para atualizar sala")
            void teste8() throws Exception{
                String json = """
                {
                    "numero": "1",
                    "capacidade": "50",
                    "ativa": "true"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/salas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(400, response.getStatus());
            }
        }
    }

    @Nested
    class CongelarSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 204 para congelar sala")
            void teste9() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/salas/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(204, response.getStatus());
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 404 para congelar sala")
            void teste10() throws Exception{
                when(service.congelar(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/salas/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }

    @Nested
    class ExcluirSala {

        @Nested
        class Sucesso {

            @Test
            @DisplayName("deve retornar 204 para excluir sala")
            void teste11() throws Exception{
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/salas/{id}", "1")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(204, response.getStatus());
            }
        }

        @Nested
        class Falha {

            @Test
            @DisplayName("deve retornar 404 para excluir sala")
            void teste12() throws Exception{
                when(service.excluir(999L))
                        .thenThrow(new EntityNotFoundException());
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/salas/{id}", "999")
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(404, response.getStatus());
            }
        }
    }
}