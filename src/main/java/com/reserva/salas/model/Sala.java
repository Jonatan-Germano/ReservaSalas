package com.reserva.salas.model;

import com.reserva.salas.dto.DadosAtualizacaoSala;
import com.reserva.salas.dto.DadosCadastroSala;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity(name = "Sala")
@Table(name = "salas")
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer numero;

    private Integer capacidade;

    private Boolean ativa;

    @ManyToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;

    public Sala() {}

    public Sala(DadosCadastroSala dados) {
        this.numero = dados.numero();
        this.capacidade = dados.capacidade();
        this.ativa = dados.ativa();
    }

    public Long getId() {
        return this.id;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Integer getCapacidade() {
        return this.capacidade;
    }

    public Boolean getAtiva() {
        return this.ativa;
    }

    public void atualizar(@Valid DadosAtualizacaoSala dados) {
        if (dados.numero() != null){
            this.numero = dados.numero();
        }
        if (dados.capacidade() != null){
            this.capacidade = dados.capacidade();
        }
        if (dados.ativa() != null){
            this.ativa = dados.ativa();
        }
    }

    public void congelar() {
        this.ativa = false;
    }
}
