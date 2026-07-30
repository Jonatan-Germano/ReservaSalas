package com.reserva.salas.service;

import com.reserva.salas.dto.DadosAtualizacaoUsuario;
import com.reserva.salas.dto.DadosCadastroUsuario;
import com.reserva.salas.dto.DadosListagemUsuario;
import com.reserva.salas.exception.ReservaException;
import com.reserva.salas.model.Usuario;
import com.reserva.salas.repository.UsuarioRepository;
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
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public ResponseEntity<DadosListagemUsuario> cadastrar(@RequestBody @Valid DadosCadastroUsuario dados, UriComponentsBuilder uriBuilder){
        if(repository.existsByTelefone(dados.telefone())){
            throw new ReservaException("Usuário com este telefone já cadastrado");
        }
        Usuario usuario = new Usuario(dados);
        repository.save(usuario);
        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosListagemUsuario(usuario));
    }

    public ResponseEntity<Page<DadosListagemUsuario>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable){
        Page page = repository.findAllByAtivoTrue(pageable).map(DadosListagemUsuario::new);
        return ResponseEntity.ok(page);
    }

    public DadosListagemUsuario detalhar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new DadosListagemUsuario(usuario);
    }

    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoUsuario dados){
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(EntityNotFoundException::new);
        usuario.atualizar(dados);
        return ResponseEntity.ok(new DadosListagemUsuario(usuario));
    }

    @Transactional
    public ResponseEntity congelar(@PathVariable Long id){
        Usuario usuario = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        usuario.congelar();
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
