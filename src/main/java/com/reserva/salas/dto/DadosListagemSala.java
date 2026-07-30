package com.reserva.salas.dto;

import com.reserva.salas.model.Sala;

public record DadosListagemSala(Integer numero, Integer capacidade, Boolean ativa) {
    public DadosListagemSala(Sala sala) {
        this(sala.getNumero(), sala.getCapacidade(), sala.getAtiva());
    }
}
