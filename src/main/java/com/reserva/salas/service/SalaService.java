package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoSala;
import com.reserva.salas.dto.DadosCadastroSala;
import com.reserva.salas.dto.DadosListagemSala;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Sala;
import com.reserva.salas.repository.SalaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class SalaService {

    private final SalaRepository repository;

    public SalaService(SalaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<DadosListagemSala> cadastrar(DadosCadastroSala dados, UriComponentsBuilder uriBuilder){
        if(repository.existsByNumero(dados.numero())){
            throw new ReservaException("Sala já cadastrada com este numero");
        }
        Sala sala = new Sala(dados);
        repository.save(sala);
        URI uri = uriBuilder.path("/salas/{id}").buildAndExpand(sala.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemSala(sala));
    }

    public ResponseEntity<Page<DadosListagemSala>> listar(@PageableDefault(size = 10, sort = {"numero"}) Pageable pageable){
        Page page = repository.findAllByAtivaTrue(pageable).map(DadosListagemSala::new);
        return ResponseEntity.ok(page);
    }

    public DadosListagemSala detalhar(Long id){
        Sala sala = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new DadosListagemSala(sala);
    }

    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoSala dados){
        Sala sala = repository.findById(dados.id())
                .orElseThrow(EntityNotFoundException::new);
        sala.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemSala(sala));
    }

    @Transactional
    public ResponseEntity congelar(@PathVariable Long id){
        Sala sala = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        sala.congelar();
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

