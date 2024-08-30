//package com.idace.idacechamados.service;
//
//import com.idace.idacechamados.exception.RegraNegocioException;
//import com.idace.idacechamados.model.entity.Lancamento;
//import com.idace.idacechamados.model.entity.Usuario;
//import com.idace.idacechamados.model.enums.StatusLancamento;
//import com.idace.idacechamados.model.repository.LancamentoRepository;
//import com.idace.idacechamados.model.repository.LancamentoRepositoryTest;
//import com.idace.idacechamados.service.impl.LancamentoServiceImpl;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.data.domain.Example;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.ThrowableAssert.catchThrowable;
//
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//public class LancamentoServiceTest {
//
//    @SpyBean
//    LancamentoServiceImpl service;
//    @MockBean
//    LancamentoRepository repository;
//
//    @Test
//    public void deveSalvarUmLancamento(){
//        //cenário
//        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
//        Mockito.doNothing().when(service).validar(lancamentoASalvar);
//
//        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
//        lancamentoSalvo.setId(1l);
//        lancamentoSalvo.setStatus(StatusLancamento.ABERTO);
//        Mockito.when(repository.save(lancamentoASalvar)).thenReturn(lancamentoSalvo);
//
//        //execução
//        Lancamento lancamento = service.salvar(lancamentoASalvar);
//
//        //verificação
//        assertThat(lancamento.getId()).isEqualTo(lancamentoSalvo.getId());
//        assertThat(lancamento.getStatus()).isEqualTo(StatusLancamento.ABERTO);
//    }
//
//    @Test
//    public void deveAtualizarUmLancamento(){
//        //cenário
//        Lancamento lancamentoSalvo = LancamentoRepositoryTest.criarLancamento();
//        lancamentoSalvo.setId(1l);
//        lancamentoSalvo.setStatus(StatusLancamento.ABERTO);
//
//        Mockito.doNothing().when(service).validar(lancamentoSalvo);
//        Mockito.when(repository.save(lancamentoSalvo)).thenReturn(lancamentoSalvo);
//
//        //execução
//        service.salvar(lancamentoSalvo);
//
//        //verificação
//        Mockito.verify(repository, Mockito.times(1)).save(lancamentoSalvo);
//    }
//
//    @Test
//    public void naoDeveSalvarUmLancamentoQuandoHouverErroDeValidacao(){
//        //cenário
//        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
//        Mockito.doThrow(RegraNegocioException.class).when(service).validar(lancamentoASalvar);
//        //execução
//        Assertions.catchThrowableOfType(() -> service.salvar(lancamentoASalvar), RegraNegocioException.class);
//        //verificação
//        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
//    }
//
//    @Test
//    public void deveLancarErroAoTentarAtualizarUmLancamentoQueNaoFoiSalvo(){
//        //cenário
//        Lancamento lancamentoASalvar = LancamentoRepositoryTest.criarLancamento();
//        //execução
//        Assertions.catchThrowableOfType(() -> service.atualizar(lancamentoASalvar), NullPointerException.class);
//        //verificação
//        Mockito.verify(repository, Mockito.never()).save(lancamentoASalvar);
//    }
//
//    @Test
//    public void deveDeletarUmLancamento(){
//        //cenário
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//
//        //execução
//        service.deletar(lancamento);
//
//        //verificação
//        Mockito.verify(repository).delete(lancamento);
//    }
//    @Test
//    public void deveLancarErroAoTentarDeletarUmLancamentoQueAindaNaoFoiSalvo(){
//        //cenário
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//
//        //execução
//        Assertions.catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);
//
//        //verificação
//        Mockito.verify(repository, Mockito.never()).delete(lancamento);
//    }
//
//    @Test
//    public void deveFiltrarLancamento(){
//        //cenário
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//        lancamento.setId(1l);
//
//        List<Lancamento> lista = Arrays.asList(lancamento);
//        Mockito.when(repository.findAll(Mockito.any(Example.class))).thenReturn(lista);
//
//        //execução
//        List<Lancamento> resultado = service.buscar(lancamento);
//
//        //verificação
//        assertThat(resultado)
//                .isNotEmpty()
//                .hasSize(1)
//                .contains(lancamento);
//    }
//
//    @Test
//    public void deveAtualizarUmStatusDeUmLancamento(){
//        //cenário
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//        lancamento.setId(1l);
//        lancamento.setStatus(StatusLancamento.ABERTO);
//
//        StatusLancamento novoStatus = StatusLancamento.ATENDIDO;
//        Mockito.doReturn(lancamento).when(service).atualizar(lancamento);
//
//        //execução
//        service.atualizarStatus(lancamento, novoStatus);
//
//        //verificação
//        assertThat(lancamento.getStatus()).isEqualTo(novoStatus);
//        Mockito.verify(service).atualizar(lancamento);
//    }
//    @Test
//    public void deveObterUmLancamentoPorId(){
//        //cenário
//        Long id = 1l;
//
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//        lancamento.setId(id);
//
//        Mockito.when(repository.findById(id)).thenReturn(Optional.of(lancamento));
//
//        //execução
//        Optional<Lancamento> resultado = service.obterPorId(id);
//
//        //verificação
//        assertThat(resultado.isPresent()).isTrue();
//    }
//
//    @Test
//    public void deveRetornarVazioQuandoUmLancamentoNaoExiste(){
//        //cenário
//        Long id = 1l;
//
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//        lancamento.setId(id);
//
//        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
//
//        //execução
//        Optional<Lancamento> resultado = service.obterPorId(id);
//
//        //verificação
//        assertThat(resultado.isPresent()).isFalse();
//    }
//
//    @Test
//    public void deveLancarErroAoValidarUmLancamento(){
//        //cenário
//        Lancamento lancamento = LancamentoRepositoryTest.criarLancamento();
//
//        //execução
//        Assertions.catchThrowableOfType(() -> service.deletar(lancamento), NullPointerException.class);
//
//        //verificação
//        Mockito.verify(repository, Mockito.never()).delete(lancamento);
//    }
//    @Test
//    public void deveLancarErrosAoValidarUmLancamento() {
//        Lancamento lancamento = new Lancamento();
//
//        Throwable erro = Assertions.catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descrição válida.");
//
//        lancamento.setDescricao("");
//
//        erro = Assertions.catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe uma Descrição válida.");
//
//        lancamento.setDescricao("Relatório");
//
//        erro = Assertions.catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");
//
//        lancamento.setAno(0);
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");
//
//        lancamento.setAno(13);
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Mês válido.");
//
//        lancamento.setMes("Janeiro");
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");
//
//        lancamento.setAno(202);
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Ano válido.");
//
//        lancamento.setAno(2020);
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");
//
//        lancamento.setUsuario(new Usuario());
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Usuário.");
//
//        lancamento.getUsuario().setId(1l);
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Setor válido.");
//
//        lancamento.setSetor("");
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um Setor válido.");
//
//        lancamento.setSetor("TESTE");
//
//        erro = catchThrowable( () -> service.validar(lancamento) );
//        assertThat(erro).isInstanceOf(RegraNegocioException.class).hasMessage("Informe um tipo de Lançamento válido.");
//
//    }
//
//
//
//
//
//}
