package com.idace.idacechamados.service;

import com.idace.idacechamados.model.entity.Lancamento;
import com.idace.idacechamados.model.enums.StatusLancamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LancamentoService {
    Lancamento salvar(Lancamento lancamento);
    Lancamento atualizar(Lancamento lancamento);
    void deletar(Lancamento lancamento);
    List<Lancamento> buscar(Lancamento lancamentoFiltro);
    void atualizarStatus(Lancamento lancamento, StatusLancamento status);
    void validar(Lancamento lancamento);
    Optional<Lancamento> obterPorId(Long id);
    Integer obterLancamentosPorTipoEPorUsuario(Long id);
//    String formatarData(LocalDate data);
}
