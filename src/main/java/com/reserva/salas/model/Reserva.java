package com.reserva.salas.model;

import com.reserva.salas.dto.DadosAtualizacaoReserva;
import com.reserva.salas.dto.DadosCadastroReserva;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Entity(name = "Reserva")
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private Integer numero;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala Sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario Usuario;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    private Long idSala;

    private Long idUsuario;

    public Reserva() {}

    public Reserva(Integer numero, Usuario usuario, Sala sala) {
        this.numero = numero;
        this.Usuario = usuario;
        this.Sala = sala;
        this.status = Status.ATIVA;
    }

    public Reserva(DadosCadastroReserva dados) {
        this.numero = dados.numero();
        this.idUsuario = dados.idUsuario();
        this.idSala = dados.idSala();
        this.status = Status.ATIVA;
    }

    public Long getId() {
        return this.id;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Long getidSala() {
        return this.idSala;
    }

    public Long getidUsuario() {
        return this.idUsuario;
    }

    public Status getStatus() {
        return this.status;
    }

    public void atualizar(@Valid DadosAtualizacaoReserva dados) {
        if (dados.numero() != null) {
            this.numero = dados.numero();
        }
        if (dados.idSala() != null) {
            this.idSala = dados.idSala();
        }
        if (dados.idUsuario() != null) {
            this.idUsuario = dados.idUsuario();
        }
    }
}
