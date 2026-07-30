package com.reserva.salas.dto;

import com.reserva.salas.model.Reserva;
import com.reserva.salas.model.Status;

public record DadosListagemReserva(Long id, Integer numero, Long idSala, Long idUsuario, Status status) {
    public DadosListagemReserva(Reserva reserva) {
        this(reserva.getId(), reserva.getNumero(), reserva.getidSala(), reserva.getidUsuario(), reserva.getStatus());
    }
}
