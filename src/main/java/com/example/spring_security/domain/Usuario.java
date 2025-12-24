package com.example.spring_security.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<UsuarioPermissao> usuarioPermissaoList;

    public Usuario() {
    }

    public Usuario(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Usuario(Long id, String username, String password, List<UsuarioPermissao> usuarioPermissaoList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.usuarioPermissaoList = usuarioPermissaoList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> listaPermissoes(){
        return this.usuarioPermissaoList
                .stream()
                .filter(usuarioPermissao -> usuarioPermissao.isAutorizado() == true)
                .map(usuarioPermissao ->
                        usuarioPermissao
                                .getPermissao()
                                .getRole()
                                .toString())
                .toList();
    }
}
