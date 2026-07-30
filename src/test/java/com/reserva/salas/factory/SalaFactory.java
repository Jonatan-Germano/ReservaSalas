package com.reserva.salas.factory;

import com.reserva.salas.dto.DadosCadastroSala;
import com.reserva.salas.model.Sala;

public final class SalaFactory {
    private SalaFactory() {
    }

    public static Sala build(Integer numero, Integer capacidade, Boolean ativa) {
        DadosCadastroSala dados =
                new DadosCadastroSala(numero, capacidade, ativa);

        return new Sala(dados);
    }

    public static Sala salaAtiva() {
        return build(5, 50, true);
    }

    public static Sala salaInativa() {
        return build(7, 60, false);
    }
}

