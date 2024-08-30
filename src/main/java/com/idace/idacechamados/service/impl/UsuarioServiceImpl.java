package com.idace.idacechamados.service.impl;

import com.idace.idacechamados.exception.ErroAutenticacao;
import com.idace.idacechamados.exception.RegraNegocioException;
import com.idace.idacechamados.model.entity.Usuario;
import com.idace.idacechamados.model.repository.UsuarioRepository;
import com.idace.idacechamados.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/* @Service: Diz para o container do spring que gerencie uma instância
dessa classe (cria uma instância e adiciona um container para poder injetar em outras classes) */
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private UsuarioRepository repository;

    private PasswordEncoder encoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepository repository, PasswordEncoder encoder) {
        super();
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public Usuario autenticar(String email, String senha) {
        Optional<Usuario> usuario = repository.findByEmail(email);

        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado para o email informado.");
        }

        boolean senhasBatem = encoder.matches(senha, usuario.get().getSenha());

        if(!senhasBatem){
            throw new ErroAutenticacao("Senha inválida!");
        }

        return usuario.get();
    }

    @Override
    public Usuario salvarUsuario(Usuario usuario) {
        validarEmail(usuario.getEmail());
        criptografarSenha(usuario);
        return repository.save(usuario);
    }

    private void criptografarSenha(Usuario usuario) {
        String senha = usuario.getSenha();
        String senhaCripto = encoder.encode(senha);
        usuario.setSenha(senhaCripto);
    }


    @Override
    public void validarEmail(String email) {
        boolean existe = repository.existsByEmail(email);
        if(existe){
            throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
        }
    }

    @Override
    public Optional<Usuario> obterPorId(Long id) {
        return repository.findById(id);
    }
}
