package com.reserva.salas.dto;

import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoReserva(
        @NotNull
        Long idReserva) {
}
