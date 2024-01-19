package com.idace.idacechamados.model.repository;

import com.idace.idacechamados.model.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
