package com.reserva.salas.dto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(@NotNull Long id, String nome, String telefone, Boolean ativo) {
}
