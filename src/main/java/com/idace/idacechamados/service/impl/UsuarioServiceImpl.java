package com.idace.idacechamados.service.impl;

import com.idace.idacechamados.model.entity.Usuario;
import com.idace.idacechamados.model.repository.UsuarioRepository;
import com.idace.idacechamados.service.UsuarioService;

import java.util.Optional;

public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    @Override
    public Usuario autenticar(String email, String senha) {
        return null;
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        return null;
    }

    @Override
    public void validarEmail(String email) {

    }
}
