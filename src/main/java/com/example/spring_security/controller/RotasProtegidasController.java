package com.example.spring_security.controller;

import com.example.spring_security.dto.PermissoesUsuarioDTO;
import com.example.spring_security.service.PermissaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teste")
public class RotasProtegidasController {

    private final PermissaoService permissaoService;

    public RotasProtegidasController(PermissaoService permissaoService) {
        this.permissaoService = permissaoService;
    }

    @PostMapping("/atribuir-permissoes")
    public void atribuirPermissoes(@RequestBody PermissoesUsuarioDTO permissoesUsuarioDTO){
        permissaoService.salvarPermissoes(permissoesUsuarioDTO);
    }

    @GetMapping("/rota-a")
    public ResponseEntity<String> testeA(){

        System.out.println("Método /rota-a foi chamado!");

        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Usuário: " + auth.getName());
        System.out.println("Roles: " + auth.getAuthorities());

        return ResponseEntity.ok("Rota A Liberada");
    }

    @GetMapping("/rota-b")
    public ResponseEntity<String> testeB(){
        return ResponseEntity.ok("Rota B Liberada");
    }

    @GetMapping("/rota-c")
    public ResponseEntity<String> testeC(){
        return ResponseEntity.ok("Rota C Liberada");
    }

    @GetMapping("/rota-d")
    public ResponseEntity<String> testeD(){
        return ResponseEntity.ok("Rota D Liberada");
    }

    @GetMapping("/rota-e")
    public ResponseEntity<String> testeE(){
        return ResponseEntity.ok("Rota E Liberada");
    }

}
