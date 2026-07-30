package com.reserva.salas.controller;

import com.reserva.salas.dto.DadosAtualizacaoSala;
import com.reserva.salas.dto.DadosCadastroSala;
import com.reserva.salas.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/salas")
public class SalaController {

    private final SalaService service;

    public SalaController(SalaService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarSala (@RequestBody @Valid DadosCadastroSala dados, UriComponentsBuilder uriBuilder){
        service.cadastrar(dados, uriBuilder);
        return ResponseEntity.created(uriBuilder.build().toUri()).build();
    }

    @GetMapping
    public ResponseEntity listar(@PageableDefault(size = 10, sort = {"numero"}) Pageable pageable){
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
          return ResponseEntity.ok(service.detalhar(id));
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoSala dados){
          return ResponseEntity.ok(service.atualizar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity congelar(@PathVariable Long id){
        service.congelar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
