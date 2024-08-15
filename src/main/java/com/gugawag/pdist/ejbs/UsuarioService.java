package com.gugawag.pdist.ejbs;

import com.gugawag.pdist.model.Mensagem;
import com.gugawag.pdist.model.Usuario;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.List;

@Stateless(name = "usuarioService")
@Remote
public class UsuarioService {

    @EJB
    private UsuarioDAO usuarioDao;

    @EJB
    private MensagemDAO mensagemDao;

    private static final List<String> PALAVROES = Arrays.asList("porra", "fdp");

    public List<Usuario> listar() {
        return usuarioDao.listar();
    }

    public List<Mensagem> listarMensagens() {
        return mensagemDao.listar();
    }

    public void inserir(long id, String nome, String texto) {


        if (contémPalavrão(texto)) {
            throw new RuntimeException("Mensagem contém palavroes");
        }

        Mensagem mensagem = new Mensagem(id, texto);
        mensagemDao.inserir(mensagem);

        Usuario novoUsuario = new Usuario(id, nome);

        usuarioDao.inserir(novoUsuario);


        if (id == 3L) {
            throw new RuntimeException("Menor de idade não permitido!");
        }
        if (id == 4L) {
            novoUsuario.setNome(nome + " alterado");
        }
    }

    private boolean contémPalavrão(String texto) {
        if (texto == null || texto.isEmpty()) {
            return false;
        }

        String textoLowerCase = texto.toLowerCase();
        for (String palavrão : PALAVROES) {
            if (textoLowerCase.contains(palavrão)) {
                return true;
            }
        }
        return false;
    }
}
