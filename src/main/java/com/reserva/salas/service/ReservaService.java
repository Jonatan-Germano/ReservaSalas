package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoReserva;
import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.dto.DadosCancelamentoReserva;
import com.reserva.salas.dto.DadosListagemReserva;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Reserva;
import com.reserva.salas.model.Sala;
import com.reserva.salas.model.Usuario;
import com.reserva.salas.repository.ReservaRepository;
import com.reserva.salas.repository.SalaRepository;
import com.reserva.salas.repository.UsuarioRepository;
import com.reserva.salas.validacoes.ValidadorReserva;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    private final UsuarioRepository usuarioRepository;

    private final SalaRepository salaRepository;

    private final List<ValidadorReserva> validadores;

    public ReservaService(ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, SalaRepository salaRepository, List<ValidadorReserva> validadores) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.validadores = validadores;
    }

    @Transactional
    public DadosListagemReserva criar(DadosCadastroReserva dados){
       if(reservaRepository.existsByNumero(dados.numero())){
           throw new ReservaException("Reserva ja existente");
       }
       if(!usuarioRepository.existsById(dados.idUsuario())){
           throw new ReservaException("Usuario não encontrado");
       }
       if(!salaRepository.existsById(dados.idSala())){
           throw new ReservaException("Sala não existente");
       }

       validadores.forEach(validador -> validador.validar(dados));

       Usuario usuario = usuarioRepository.getReferenceById(dados.idUsuario());
       Sala sala = salaRepository.getReferenceById(dados.idSala());

       Reserva reserva = new Reserva(dados.numero(), usuario, sala);
       reservaRepository.save(reserva);

       return new DadosListagemReserva(reserva);
    }

    @Transactional
    public ResponseEntity atualizar(DadosAtualizacaoReserva dados){
        Reserva reserva = reservaRepository.getReferenceById(dados.id());
        reserva.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemReserva(reserva));
    }

    @Transactional
    public ResponseEntity cancelar(DadosCancelamentoReserva dados){
        reservaRepository.deleteById(dados.idReserva());
        return ResponseEntity.noContent().build();
    }
}