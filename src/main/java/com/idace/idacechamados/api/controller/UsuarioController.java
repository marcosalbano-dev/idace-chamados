package com.idace.idacechamados.api.controller;

import com.idace.idacechamados.api.dto.UsuarioDTO;
import com.idace.idacechamados.exception.ErroAutenticacao;
import com.idace.idacechamados.exception.RegraNegocioException;
import com.idace.idacechamados.model.entity.Usuario;
import com.idace.idacechamados.service.LancamentoService;
import com.idace.idacechamados.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;
    private final LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(dto.getSenha()).build();
        try {
            Usuario usuarioSalvo = service.salvarUsuario(usuario);
            return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/autenticar")
    public ResponseEntity<?> autenticar(@RequestBody UsuarioDTO dto){
        try {
            Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
//            String token = jwtService.gerarToken(usuarioAutenticado);
//            TokenDTO tokenDTO = new TokenDTO(usuarioAutenticado.getNome(), token);
            return ResponseEntity.ok(usuarioAutenticado);
        }catch (ErroAutenticacao e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("{id}/lancamentos-atendidos")
//    public ResponseEntity obterLancamentos(@PathVariable("id") Long id){
//        Optional<Usuario> usuario = service.obterPorId(id);
//
//        if(!usuario.isPresent()){
//            return new ResponseEntity(HttpStatus.NOT_FOUND);
//        }
////        Integer totalLancamentos = lancamentoService.obterLancamentosPorTipoEPorUsuario();
////        return ResponseEntity.ok(totalLancamentos);
//    }


}
