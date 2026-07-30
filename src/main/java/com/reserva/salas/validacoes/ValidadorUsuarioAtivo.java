package com.reserva.salas.validacoes;

import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuarioAtivo implements ValidadorReserva{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validar(DadosCadastroReserva dados) {
        if(!repository.findAtivoById(dados.idUsuario())){
            throw new ReservaException("Usuário com conta desativada");
        }
    }
}
