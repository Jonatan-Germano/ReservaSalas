package com.reserva.salas.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoSala(@NotNull Long id, Integer numero, Integer capacidade, Boolean ativa) {
}
