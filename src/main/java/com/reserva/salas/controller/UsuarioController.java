package com.reserva.salas.controller;

import com.reserva.salas.dto.DadosAtualizacaoUsuario;
import com.reserva.salas.dto.DadosCadastroUsuario;
import com.reserva.salas.dto.DadosListagemUsuario;
import com.reserva.salas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity cadastrarUsuario (@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        service.cadastrar(dados, uriBuilder);
        return ResponseEntity.created(uriBuilder.build().toUri()).build();
    }

    @GetMapping
    public ResponseEntity listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        return ResponseEntity.ok(service.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemUsuario> detalhar(@PathVariable Long id) {
        return ResponseEntity.ok(service.detalhar(id));
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados){
       return ResponseEntity.ok(service.atualizar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity congelar(@PathVariable Long id){
        service.congelar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity excluir(@PathVariable Long id){
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
