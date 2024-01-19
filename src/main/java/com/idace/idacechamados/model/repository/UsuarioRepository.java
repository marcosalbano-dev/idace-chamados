package com.idace.idacechamados.model.repository;

import com.idace.idacechamados.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
