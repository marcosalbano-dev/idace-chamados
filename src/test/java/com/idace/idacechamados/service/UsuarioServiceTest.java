package com.idace.idacechamados.service;

import com.idace.idacechamados.exception.ErroAutenticacao;
import com.idace.idacechamados.exception.RegraNegocioException;
import com.idace.idacechamados.model.entity.Usuario;
import com.idace.idacechamados.model.repository.UsuarioRepository;
import com.idace.idacechamados.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @SpyBean
    UsuarioServiceImpl service;

    @MockBean
    UsuarioRepository repository;

    @Test
    public void deveSalvarUmUsuario(){
        Assertions.assertDoesNotThrow(()->{

            //cenário
            Mockito.doNothing().when(service).validarEmail(Mockito.anyString());
            Usuario usuario = Usuario.builder()
                    .id(1l)
                    .nome("nome")
                    .email("email@email.com")
                    .senha("senha").build();

            Mockito.when(repository.save(Mockito.any(Usuario.class))).thenReturn(usuario);
            //ação
            Usuario usuarioSalvo = service.salvarUsuario(new Usuario());
            Assertions.assertNotNull(usuarioSalvo);
            Assertions.assertEquals(usuarioSalvo.getId(), 1l);
            Assertions.assertEquals(usuarioSalvo.getNome(), "nome");
            Assertions.assertEquals(usuarioSalvo.getEmail(), "email@email.com");
            Assertions.assertEquals(usuarioSalvo.getSenha(), "senha");
        });

    }

    @Test
    public void naoDeveSalvarUmUsuarioComEmailCadastrado(){
        RegraNegocioException exception = Assertions.assertThrows(RegraNegocioException.class, ()->{
            //cenário
            String email = "email@email.com";
            Usuario usuario = Usuario.builder().email(email).build();
            Mockito.doThrow(RegraNegocioException.class).when(service).validarEmail("email@email.com");
            //ação
            service.salvarUsuario(usuario);

            //verificação
            Mockito.verify(repository, Mockito.never()).save(usuario);
        });
        //assertEquals("Já existe um usuário com esse email.", exception.getMessage());
    }

    @Test
    public void deveValidarEmail(){
        assertDoesNotThrow(()->{
            //cenário
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
            //ação
            service.validarEmail("email@email.com");
        });
    }

    @Test
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado(){
        //cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //ação
        Throwable exception = catchThrowable(()->{
            service.autenticar("email@email.com", "senha");
        });

        assertThat(exception)
                .isInstanceOf(ErroAutenticacao.class)
                .hasMessage("Usuário não encontrado para o email informado!");
    }

    @Test
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado(){
        RegraNegocioException exception = assertThrows(RegraNegocioException.class, ()->{
            //cenário
            Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
            //ação
            service.validarEmail("email@email.com");
        });
        assertEquals("Já existe um usuário cadastrado com este email.", exception.getMessage());
    }

    @Test
    public void deveAutenticarUsuarioComSucesso(){
        Assertions.assertDoesNotThrow(()->{
            //cenário
            String email = "email@email.com";
            String senha = "senha";
            //ação
            Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
            Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

            //verificação
            Usuario result = service.autenticar(email, senha);
            Assertions.assertNotNull(result);
        });
    }

    @Test
    public void deveLancarErroQuandoSenhaNaoBater(){
        //cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //ação
        Throwable exception = org.assertj.core.api.Assertions.catchThrowable(()->{
            service.autenticar("email@email.com", "123");
        });

        org.assertj.core.api.Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida!");
    }
}

