package com.reserva.salas.model;

import com.reserva.salas.dto.DadosAtualizacaoUsuario;
import com.reserva.salas.dto.DadosCadastroUsuario;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(unique = true)
    private String telefone;

    private Boolean ativo;

    @OneToMany(mappedBy = "Usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reserva> reservas;

    public Usuario() {}

    public Long getId() {
        return this.id;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public Usuario(DadosCadastroUsuario dados) {
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.ativo = dados.ativo();
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void atualizar(@Valid DadosAtualizacaoUsuario dados) {
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.ativo() != null) {
            this.ativo = dados.ativo();
        }
    }
    public void congelar() {
        this.ativo = false;
    }
}
