/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.UsuarioLoginDAO;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Sidnei
 */
public class UsuarioController {
    ZoneId fusoHorarioDeSaoPaulo;
    protected UsuarioModel usuario;
    private UsuarioLoginDAO usuarioDAO = new UsuarioLoginDAO();
    protected UsuarioModel usuarioLogin;

//    public Usuario find(Usuario usuarioFind) throws ErroSistemaException {
//        EntityManager em = ConexaoPostgres.getEntityManager();
//        try {
//            CrudDao<Usuario> usuarioDao = new CrudDaoImpl<>(new UsuarioDaoImpl(em));
//
//            usuarioFind = usuarioDao.find(usuarioFind);
//
//            return usuarioFind;
//        } catch (PersistenceException e) {
//            throw new ErroSistemaException(e.getMessage());
//        } finally {
//            em.close();
//        }
//    }
    public List<UsuarioModel> listarUsuarios() throws ErroSistemaException {
        return this.usuarioDAO.buscarUsuario();
    
    }

    public int salvarAtualizar(UsuarioModel usuarioModel) throws ErroSistemaException {
        return this.usuarioDAO.insertUser(usuarioModel);
    }

//    public UsuarioModel login(String usuario, String senha) throws ErroSistemaException {
//        try {
//            UsuarioLoginDAO usuarioDao = new UsuarioLoginDAO();
//            List<UsuarioModel> lUsuarios = usuarioDao.UsuarioLogin(usuario, senha);
//
//            Optional<UsuarioModel> oUsuario = lUsuarios.stream().filter(u -> u.getLogin().toString().toUpperCase().equals(usuario.toUpperCase())).findFirst();
//
//            if (oUsuario.isPresent()) {
//                usuarioLogin = oUsuario.get();
//                return usuarioLogin;
//            }
//            return null;
//        } catch (ErroSistemaException e) {
//            throw new ErroSistemaException(e.getMessage());
//        } finally {
//        }
//    }
      public UsuarioModel login(String usuario) throws ErroSistemaException {
        try {
            UsuarioLoginDAO usuarioDao = new UsuarioLoginDAO();
            UsuarioModel usuarioModel = usuarioDao.UsuarioLogin(usuario);

            return usuarioModel;

        } catch (ErroSistemaException e) {
            throw new ErroSistemaException(e.getMessage());
        } finally {
        }
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public void salvarAtualizarLogin() throws ErroSistemaException, SQLException {
        boolean novoUsuario = usuario.getIdUsuario() == 0;
        usuario = usuarioDAO.InsetLogin(usuario);
    }

    public void AtualizarLogin() throws ErroSistemaException, SQLException {
        boolean novoUsuario = usuario.getIdUsuario() == 0;
        usuario = usuarioDAO.encerrarLogin(usuario);
    }

    public int buscarConta(UsuarioModel usuarioModel, long conta) throws SQLException, ErroSistemaException {
        return this.usuarioDAO.buscaConta(usuarioModel, conta);
    }

    public int salvarAlteracao(UsuarioModel usuarioModel) throws SQLException, ErroSistemaException {
        return this.usuarioDAO.alteraUsuario(usuarioModel);
    }
    public void AtualizaLogin() throws ErroSistemaException{
        usuario = usuarioDAO.AtualizarLogin(usuario);
    }

}
