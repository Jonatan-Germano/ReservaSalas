package com.reserva.salas.controller;

import com.reserva.salas.dto.DadosAtualizacaoReserva;
import com.reserva.salas.dto.DadosCadastroReserva;
import com.reserva.salas.dto.DadosCancelamentoReserva;
import com.reserva.salas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService service;

    public ReservaController(ReservaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid DadosCadastroReserva dados){
        service.criar(dados);
        return ResponseEntity.ok(dados);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoReserva dados){
       return ResponseEntity.ok(service.atualizar(dados));
    }

    @DeleteMapping
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoReserva dados) {
        service.cancelar(dados);
        return ResponseEntity.noContent().build();
    }
}
