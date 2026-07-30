package com.reserva.salas.controller;

import com.reserva.salas.service.ReservaService;
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

@SpringBootTest
@AutoConfigureMockMvc
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservaService service;

    @Nested
    class CriarReserva {

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 200 para cadastrar reserva")
            void teste1() throws Exception {
                String json = """
                {
                    "numero": "1",
                    "idUsuario": "1",
                    "idSala": "1"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/reservas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(200, response.getStatus());
            }
        }

        @Nested
        class Falha{

            @Test
            @DisplayName("deve retornar 400 para cadastrar reserva")
            void teste2() throws Exception{
                String json ="";
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.post("/reservas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(400, response.getStatus());
            }
        }
    }

   @Nested
   class AtualizarReserva {

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 200 para atualizar reserva")
            void teste3() throws Exception{
                String json = """
                {
                    "id": "1",
                    "numero": "1",
                    "usuario": "1",
                    "sala": "1"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.put("/reservas")
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
           void teste4() throws Exception{
               String json = """
                {
                    "numero": "1",
                    "usuario": "1",
                    "sala": "1"
                }
                """;
               MockHttpServletResponse response = mockMvc.perform(
                               MockMvcRequestBuilders.put("/reservas")
                                       .content(json)
                                       .contentType(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
               Assertions.assertEquals(400, response.getStatus());
           }
       }
   }

   @Nested
   class ExcluirReserva {

        @Nested
        class Sucesso{

            @Test
            @DisplayName("deve retornar 204 para excluir reserva")
            void teste5() throws Exception{
                String json = """
                {
                    "idReserva": "1"
                }
                """;
                MockHttpServletResponse response = mockMvc.perform(
                                MockMvcRequestBuilders.delete("/reservas")
                                        .content(json)
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andReturn().getResponse();
                Assertions.assertEquals(204, response.getStatus());
            }
        }

       @Nested
        class Falha{

           @Test
           @DisplayName("deve retornar 400 para excluir reserva")
           void teste6() throws Exception{
               String json = "";
               MockHttpServletResponse response = mockMvc.perform(
                               MockMvcRequestBuilders.delete("/reservas")
                                       .content(json)
                                       .contentType(MediaType.APPLICATION_JSON))
                       .andReturn().getResponse();
               Assertions.assertEquals(400, response.getStatus());
           }
       }
   }
}
