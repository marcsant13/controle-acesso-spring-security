package com.example.spring_security.controller;

import com.example.spring_security.domain.Permissao;
import com.example.spring_security.domain.Usuario;
import com.example.spring_security.domain.UsuarioPermissao;
import com.example.spring_security.repository.PermissaoRepository;
import com.example.spring_security.repository.UsuarioPermissaoRepository;
import com.example.spring_security.repository.UsuarioRepository;
import com.example.spring_security.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final UsuarioPermissaoRepository usuarioPermissaoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, UsuarioPermissaoRepository usuarioPermissaoRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.usuarioPermissaoRepository = usuarioPermissaoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Usuario usuario){

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        usuario.setUsername(usuario.getUsername());

        List<UsuarioPermissao> usuarioPermissaoList = new ArrayList<>();

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        for (Permissao permissao: permissaoRepository.findAll()){
            usuarioPermissaoList.add(
                    new UsuarioPermissao(
                            null,
                            usuarioSalvo,
                            permissao,
                            false
                    )
            );
        }

        usuarioPermissaoRepository.saveAll(usuarioPermissaoList);

        return ResponseEntity.ok("Usuario Registrado");

    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Usuario usuario){

        Usuario user = usuarioRepository.findByUsername(usuario.getUsername()).orElseThrow();

        if (passwordEncoder.matches(user.getPassword(), usuario.getPassword())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = jwtService.gerarToken(usuario.getUsername());

        return ResponseEntity.ok(token);
    }

}
