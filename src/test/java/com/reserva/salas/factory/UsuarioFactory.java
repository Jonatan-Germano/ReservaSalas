package com.reserva.salas.factory;

import com.reserva.salas.dto.DadosCadastroUsuario;
import com.reserva.salas.model.Usuario;

public final class UsuarioFactory {

    private UsuarioFactory() {
    }

    public static Usuario build(String nome, String telefone, Boolean ativo) {
        DadosCadastroUsuario dados =
                new DadosCadastroUsuario(nome, telefone, ativo);

        return new Usuario(dados);
    }

    public static Usuario usuarioAtivo() {
        return build("João", "11999999999", true);
    }

    public static Usuario usuarioInativo() {
        return build("Maria", "11888888888", false);
    }
}