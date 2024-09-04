package com.idace.idacechamados.service.impl;

import com.idace.idacechamados.exception.RegraNegocioException;
import com.idace.idacechamados.model.entity.Lancamento;
import com.idace.idacechamados.model.enums.StatusLancamento;
import com.idace.idacechamados.model.enums.TipoLancamento;
import com.idace.idacechamados.model.repository.LancamentoRepository;
import com.idace.idacechamados.service.LancamentoService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancamentoServiceImpl implements LancamentoService {

    private LancamentoRepository repository;
    public LancamentoServiceImpl(LancamentoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Lancamento salvar(Lancamento lancamento) {
        validar(lancamento);
        lancamento.setStatus(StatusLancamento.ABERTO);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public Lancamento atualizar(Lancamento lancamento) {
        //garante que está passando um Lancamento salvo
          Objects.requireNonNull(lancamento.getId());
        validar(lancamento);
        return repository.save(lancamento);
    }

    @Override
    @Transactional
    public void deletar(Lancamento lancamento) {
        //garante que está passando um Lancamento salvo
        Objects.requireNonNull(lancamento.getId());
        repository.delete(lancamento);
    }

    public String formatarData(LocalDate data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lancamento> buscar(Lancamento lancamentoFiltro) {
//        String sql = "SELECT * FROM lancamento WHERE 1 = 1";
//        if(lancamentoFiltro.getDescicao() != null){
//            sql = sql + " and descricao = " + lancamentoFiltro.getDescicao();
//        }
        Example example = Example.of(lancamentoFiltro,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        return repository.findAll(example);
    }

    @Override
    public void atualizarStatus(Lancamento lancamento, StatusLancamento status) {
        lancamento.setStatus(status);
        atualizar(lancamento);
    }

    @Override
    public void validar(Lancamento lancamento) {
        if (lancamento.getDescricao() == null || lancamento.getDescricao().trim().equals("")) {
            throw new RegraNegocioException("Informe uma Descrição válida.");
        }
        if (lancamento.getMes() == null || lancamento.getMes().trim().equals("")) {
            throw new RegraNegocioException("Informe um Mês válido.");
        }
        if (lancamento.getAno() == null || lancamento.getAno().toString().length() != 4) {
            throw new RegraNegocioException("Informe um Ano válido.");
        }
        if (lancamento.getUsuario() == null || lancamento.getUsuario().getId() == null) {
            throw new RegraNegocioException("Informe um Usuário.");
        }
        if (lancamento.getSetor() == null || lancamento.getSetor().trim().equals("")) {
            throw new RegraNegocioException("Informe um Setor válido.");
        }
        if (lancamento.getTipo() == null) {
            throw new RegraNegocioException("Informe um tipo de Lançamento válido.");
        }
        //if(lancamento.getDataCadastro() == null  || lancamento.getDataCadastro().equals("")){
          //  throw new RegraNegocioException("Informe a data do cadastro.!");
       // }
    }

    @Override
    public Optional<Lancamento> obterPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Integer obterLancamentosPorTipoEPorUsuario(Long id) {

        Integer totalAtendidosSiga = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.SIGA, StatusLancamento.ATENDIDO);
        Integer totalAtendidosEmail = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.EMAIL, StatusLancamento.ATENDIDO);
        Integer totalAtendidosTitula = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.TITULA, StatusLancamento.ATENDIDO);
        Integer totalAtendidosRede = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.REDE, StatusLancamento.ATENDIDO);
        Integer totalAtendidosRelatorios = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.RELATORIOS, StatusLancamento.ATENDIDO);
        Integer totalAtendidosSuporte = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.SUPORTE, StatusLancamento.ATENDIDO);
        Integer totalAtendidosTopodatum = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.TOPODATUM, StatusLancamento.ATENDIDO);
        Integer totalAtendidosDBAccess = repository.obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(id, TipoLancamento.BD_ACCESS, StatusLancamento.ATENDIDO);

        Integer totalAtendimentos;

        if(totalAtendidosSiga == null){
            totalAtendidosSiga = 0;
        }

        if(totalAtendidosEmail == null){
            totalAtendidosEmail = 0;
        }

        if(totalAtendidosTitula == null){
            totalAtendidosTitula = 0;
        }

        if(totalAtendidosRede == null){
            totalAtendidosRede = 0;
        }

        if(totalAtendidosRelatorios == null){
            totalAtendidosRelatorios = 0;
        }

        if(totalAtendidosSuporte == null){
            totalAtendidosSuporte = 0;
        }

        if(totalAtendidosTopodatum == null){
            totalAtendidosTopodatum = 0;
        }

        if(totalAtendidosDBAccess == null){
            totalAtendidosDBAccess = 0;
        }

        totalAtendimentos = totalAtendidosSiga + totalAtendidosEmail + totalAtendidosTitula + totalAtendidosRede + totalAtendidosRelatorios + totalAtendidosSuporte + totalAtendidosTopodatum + totalAtendidosDBAccess;
        return totalAtendimentos;
    }
}
