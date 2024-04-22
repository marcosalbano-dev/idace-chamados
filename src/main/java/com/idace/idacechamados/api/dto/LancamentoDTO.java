package com.idace.idacechamados.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
    private Long id;
    private String descricao;
    private Integer mes;
    private Integer ano;
    private String setor;
    private Long usuario;
    private String tipo;
    private String status;
    private LocalDate dataCadastro;
}
