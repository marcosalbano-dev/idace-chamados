package com.idace.idacechamados.model.repository;

import com.idace.idacechamados.model.entity.Lancamento;
import com.idace.idacechamados.model.enums.StatusLancamento;
import com.idace.idacechamados.model.enums.TipoLancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
//    @Query(value = "SELECT count(l.id) FROM Lancamento l JOIN l.usuario u " +
//            " WHERE u.id =:idUsuario AND l.tipo =:tipo AND l.status =:status group by u")
//    Integer obterTotalLancamentosPorTipoLancamentoEUsuarioEStatus(
//            @Param("idUsuario") Long idUsuario,
//            @Param("tipo") TipoLancamento tipo,
//            @Param("status") StatusLancamento status
//    );
}
