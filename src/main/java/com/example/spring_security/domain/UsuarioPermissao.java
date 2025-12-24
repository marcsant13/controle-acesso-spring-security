package com.example.spring_security.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_usuario_role")
public class UsuarioPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_permissao")
    private Permissao permissao;

    private boolean autorizado;

    public UsuarioPermissao() {
    }

    public UsuarioPermissao(Long id, Usuario usuario, Permissao permissao, boolean autorizado) {
        this.id = id;
        this.usuario = usuario;
        this.permissao = permissao;
        this.autorizado = autorizado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
}
