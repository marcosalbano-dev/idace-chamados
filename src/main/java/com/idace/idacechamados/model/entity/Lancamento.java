package com.idace.idacechamados.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.idace.idacechamados.model.enums.StatusLancamento;
import com.idace.idacechamados.model.enums.TipoLancamento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lancamento", schema = "chamados")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lancamento {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "ano")
    private Integer ano;
    @Column(name = "setor")
    private String setor;
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;
    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private TipoLancamento tipo;
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private StatusLancamento status;

}
