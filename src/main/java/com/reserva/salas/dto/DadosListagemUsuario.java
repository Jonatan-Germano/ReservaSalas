package com.reserva.salas.dto;

import com.reserva.salas.model.Usuario;

public record DadosListagemUsuario(String nome, String telefone) {
    public DadosListagemUsuario(Usuario usuario) {
        this(usuario.getNome(), usuario.getTelefone());
    }
}
