package com.reserva.salas.validacoes;

import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorSalaAtiva implements ValidadorReserva {

    @Autowired
    private SalaRepository repository;

    @Override
    public void validar(DadosCadastroReserva dados) {
        if(!repository.findAtivaById(dados.idSala())){
            throw new ReservaException("Sala não disponível");
        }
    }
}
