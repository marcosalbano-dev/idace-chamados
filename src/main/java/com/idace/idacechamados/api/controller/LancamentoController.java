package com.idace.idacechamados.api.controller;

import com.idace.idacechamados.api.dto.AtualizaStatusDTO;
import com.idace.idacechamados.api.dto.LancamentoDTO;
import com.idace.idacechamados.exception.RegraNegocioException;
import com.idace.idacechamados.model.entity.Lancamento;
import com.idace.idacechamados.model.entity.Usuario;
import com.idace.idacechamados.model.enums.StatusLancamento;
import com.idace.idacechamados.model.enums.TipoLancamento;
import com.idace.idacechamados.service.LancamentoService;
import com.idace.idacechamados.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/lancamentos")
@RequiredArgsConstructor
public class LancamentoController {

    private final LancamentoService service;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity salvar(@RequestBody LancamentoDTO dto) {
        try {
            Lancamento entity = converter(dto);
            entity = service.salvar(entity);
            return new ResponseEntity(entity, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody LancamentoDTO dto) {
        return service.obterPorId(id).map(entity -> {
            try {
                Lancamento lancamento = converter(dto);
                lancamento.setId(entity.getId());
                service.atualizar(lancamento);
                return ResponseEntity.ok(lancamento);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados!", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.obterPorId(id).map(entity -> {
            service.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> new ResponseEntity("Lançamento não encontrado na base de dados!", HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity buscar(
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "mes", required = false) String mes,
            //@RequestParam(value = "tipo", required = false) TipoLancamento tipo,
            @RequestParam(value = "ano", required = false) Integer ano,
            @RequestParam(value = "setor", required = false) String setor,
            @RequestParam(value = "data_cadastro", required = false) LocalDate dataCadastro,
            @RequestParam(value = "usuario") Long idUsuario
    ){
        Lancamento lancamentoFiltro = new Lancamento();
        lancamentoFiltro.setDescricao(descricao);
        lancamentoFiltro.setMes(mes);
        //lancamentoFiltro.setTipo(tipo);
        lancamentoFiltro.setAno(ano);
        lancamentoFiltro.setSetor(setor);
        lancamentoFiltro.setDataCadastro(dataCadastro);

        Optional<Usuario> usuario = usuarioService.obterPorId(idUsuario);
        if(!usuario.isPresent()){
            return ResponseEntity.badRequest().body("Não foi possível realizar a consulta. Usuário não encontrado para o Id informado");
        } else {
            lancamentoFiltro.setUsuario(usuario.get());
        }
        List<Lancamento> lancamentos = service.buscar(lancamentoFiltro);
        return ResponseEntity.ok(lancamentos);
    }

    @GetMapping("{id}")
    public ResponseEntity obterLancamento( @PathVariable("id") Long id ){
        return service.obterPorId(id)
                .map( lancamento -> new ResponseEntity(converter(lancamento), HttpStatus.OK))
                .orElseGet( () -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PutMapping("{id}/atualiza-status")
    public ResponseEntity atualizarStatus(@PathVariable("id") Long id, @RequestBody AtualizaStatusDTO dto){
        return service.obterPorId(id).map( entity -> {
            StatusLancamento statusSelecionado = StatusLancamento.valueOf(dto.getStatus());
            if(statusSelecionado == null){
                return ResponseEntity.badRequest().body("Não foi possível atualizar o status do lançamento, envie um status válido.");
            }
            try{
                entity.setStatus(statusSelecionado);
                service.atualizar(entity);
                return ResponseEntity.ok(entity);
            }catch (RegraNegocioException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(()-> new ResponseEntity("Lançamento não encontrado na base de dados!", HttpStatus.BAD_REQUEST));
    }

    private LancamentoDTO converter(Lancamento lancamento){
        return LancamentoDTO.builder()
                .id(lancamento.getId())
                .descricao(lancamento.getDescricao())
                .setor(lancamento.getSetor())
                .mes((lancamento.getMes()))
                .ano(lancamento.getAno())
                .status(lancamento.getStatus().name())
                .tipo(lancamento.getTipo().name())
                .dataCadastro(lancamento.getDataCadastro())
                .usuario(lancamento.getUsuario().getId())
                .build();
    }

    private Lancamento converter(LancamentoDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setId(dto.getId());
        lancamento.setDescricao(dto.getDescricao());
        lancamento.setAno(dto.getAno());
        lancamento.setMes(dto.getMes());
        lancamento.setSetor(dto.getSetor());
        lancamento.setDataCadastro(dto.getDataCadastro());

        Usuario usuario = usuarioService
                .obterPorId(dto.getUsuario())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o Id informado"));

        lancamento.setUsuario(usuario);
        if(dto.getTipo() != null){
            lancamento.setTipo(TipoLancamento.valueOf(dto.getTipo()));
        }
        if(dto.getStatus() != null){
            lancamento.setStatus(StatusLancamento.valueOf(dto.getStatus()));
        }

        return lancamento;

    }
}
