package com.reserva.salas.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoReserva(@NotNull Long id, Integer numero, Long idSala, Long idUsuario) {
}
