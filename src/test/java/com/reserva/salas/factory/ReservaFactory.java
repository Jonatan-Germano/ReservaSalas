package com.reserva.salas.factory;

import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.model.Reserva;

public final class ReservaFactory {

    private ReservaFactory() {}

    public static Reserva build(Integer numero, Long usuario, Long sala) {
        DadosCadastroReserva dados =
                new DadosCadastroReserva(numero, usuario, sala);
        return new Reserva(dados);
    }

    public static Reserva reservaAtiva() {
        return build(7, 1l, 1l);
        }
}
