//package com.idace.idacechamados.model.repository;
//
//import com.idace.idacechamados.model.entity.Lancamento;
//import com.idace.idacechamados.model.enums.StatusLancamento;
//import com.idace.idacechamados.model.enums.TipoLancamento;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class LancamentoRepositoryTest {
//
//    @Autowired
//    LancamentoRepository repository;
//    @Autowired
//    TestEntityManager entityManager;
//
//    @Test
//    public void deveSalvarUmLancamento(){
//        Lancamento lancamento = criarLancamento();
//
//        lancamento = repository.save(lancamento);
//
//        Assertions.assertThat(lancamento.getId()).isNotNull();
//    }
//
//    @Test
//    public void deveDeletarUmLancamento(){
//        Lancamento lancamento = criarEPersistirUmLancamento();
//
//        lancamento = entityManager.find(Lancamento.class, lancamento.getId());
//
//        repository.delete(lancamento);
//
//        Lancamento lancamentoInexistente = entityManager.find(Lancamento.class, lancamento.getId());
//        Assertions.assertThat(lancamentoInexistente).isNull();
//    }
//
//    @Test
//    public void deveAtualizarUmLancamento(){
//        Lancamento lancamento = criarEPersistirUmLancamento();
//        lancamento.setAno(2024);
//        lancamento.setDescricao("Teste atualizar");
//        lancamento.setStatus(StatusLancamento.ATENDIDO);
//
//        repository.save(lancamento);
//
//        Lancamento lancamentoAtualizado = entityManager.find(Lancamento.class, lancamento.getId());
//
//        Assertions.assertThat(lancamentoAtualizado.getAno()).isEqualTo(2024);
//        Assertions.assertThat(lancamentoAtualizado.getDescricao()).isEqualTo("Teste atualizar");
//        Assertions.assertThat(lancamentoAtualizado.getStatus()).isEqualTo(StatusLancamento.ATENDIDO);
//
//    }
//
//    @Test
//    public void deveBuscarUmLancamentoPorId(){
//        Lancamento lancamento = criarEPersistirUmLancamento();
//
//        Optional<Lancamento> lancamentoEncontrado = repository.findById(lancamento.getId());
//
//        Assertions.assertThat(lancamentoEncontrado.isPresent()).isTrue();
//    }
//
//    private Lancamento criarEPersistirUmLancamento() {
//        Lancamento lancamento = criarLancamento();
//        entityManager.persist(lancamento);
//        return lancamento;
//    }
//
//    public static Lancamento criarLancamento() {
//        return Lancamento.builder()
//                .ano(2024)
//                .mes("Janeiro")
//                .mes(1)
//                .descricao("Lançamento qualquer")
//                .setor("NUGEO")
//                .tipo(TipoLancamento.SUPORTE)
//                .status(StatusLancamento.ABERTO)
//                .dataCadastro(LocalDate.now())
//                .build();
//    }
//}
