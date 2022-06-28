/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.model.dao;

import br.com.martinello.bd.matriz.control.UsuarioController;
import br.com.martinello.bd.matriz.model.domain.UsuarioModel;
import br.com.martinello.bd.matriz.model.domain.UsuarioLogin;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.util.ArrayList;
import java.util.Base64;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//import sun.misc.BASE64Encoder;
/**
 *
 * @author Sidnei
 */
public class UsuarioLoginDAO {

    public PreparedStatement psLogin, psSelectUser, psInsertUser, psInsertLogin, psSelectUsuarios;
    public ResultSet rsLogin, rsSelectUser, rsInsertUser, rsInsertLogin, rsSelectUsuarios;
    public boolean login;
    public List<UsuarioModel> lUsuario;
    public List<UsuarioModel> lUsuarioModel;
    public String sqlSelectUser, sqlInsertUser, sqlInsertLogin, sqlUpdateLogin, sqlSelectLogins, sqlUpdateUsuario, sqlSelectUsuarios;
    int resultado;
    private long idLog;
    protected UsuarioController usuarioControl;

    public UsuarioLoginDAO() {
        idLog = 0;
    }

//    public List<UsuarioModel> UsuarioLogin(String usuario, String senha) throws ErroSistemaException {
//
//        sqlSelectLogins = "SELECT *FROM  LOGIN_USUARIO WHERE ID_LOGIN = ?";
//        sqlSelectUser = "SELECT ID_USUARIO, NOME, CONTA, STATUS, PERFIL, SENHA FROM CONTA_USUARIO WHERE CONTA = ? AND SENHA = ?";
//
//        try {
//            lUsuario = new ArrayList<>();
//            psLogin = Conexao.getConnection().prepareStatement(sqlSelectUser);
//            psLogin.setString(1, usuario);
//            psLogin.setString(2, senha);
//            rsLogin = psLogin.executeQuery();
//            if (rsLogin.next()) {
//                psSelectUser = Conexao.getConnection().prepareStatement(sqlSelectLogins);
//                psSelectUser.setInt(1, rsLogin.getInt("ID_USUARIO"));
//                rsSelectUser = psSelectUser.executeQuery();
//                if (rsSelectUser.next()) {
//                    UsuarioModel usuarioModel = new UsuarioModel();
//                    usuarioModel.setNome(rsLogin.getString("NOME"));
//                    usuarioModel.setNivelLiberacao(rsLogin.getString("PERFIL"));
//                    usuarioModel.setLogin(rsLogin.getString("CONTA"));
//                    usuarioModel.setIdUsuario(rsLogin.getInt("ID_USUARIO"));
//                    usuarioModel.setDataHoraAtualizacao(rsSelectUser.getDate("LOGIN_USER"));
//                    lUsuario.add(usuarioModel);
//                    UsuarioLogin login = new UsuarioLogin();
//                    login.setUsuario(usuarioModel);
//                    login.setDataLogin(new Date());
//                    login.setSistemaOperacional(System.getProperty("os.name"));
//                    login.setNomeUsuarioSO(System.getProperty("user.name"));
//                    login.setNomeEstacao(InetAddress.getLocalHost().getHostName());
//                    login.setDataLogin(rsSelectUser.getDate("LOGIN_USER"));
//                    usuarioModel.getLogins().add(login);
//                    usuarioControl = new UsuarioController();
//                    usuarioControl.setUsuario(usuarioModel);
//                    //   usuarioModel.getLogins().get(0).setIdLogin(rsLogin.getLong("ID_USUARIO"));
//
//                    psSelectUser.close();
//                } else {
//                    UsuarioModel usuarioModel = new UsuarioModel();
//                    usuarioModel.setNome(rsLogin.getString("NOME"));
//                    usuarioModel.setLogin(rsLogin.getString("CONTA"));
//                    usuarioModel.setIdUsuario(rsLogin.getInt("ID_USUARIO"));
//                    usuarioModel.setNivelLiberacao(rsLogin.getString("PERFIL"));
//
//                    lUsuario.add(usuarioModel);
//                }
//
//            }
//            psLogin.close();
//            rsLogin.close();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
//        } catch (SQLRecoverableException ex) {
//            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
//        } finally {
////            try {
////                psLogin.close();
////            } catch (SQLException ex) {
////                Logger.getLogger(UsuarioLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
////                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
////            }
//        }
//        return lUsuario;
//
//    }
    public UsuarioModel UsuarioLogin(String usuario) throws ErroSistemaException {
        sqlSelectLogins = "SELECT *FROM  LOGIN_USUARIO WHERE ID_LOGIN = ?";
        sqlSelectUser = "SELECT R999USU_CODUSU, \n"
                + "             R910USU_NOMCOM, \n"
                + "             R999USU_NOMUSU \n"
                + "        FROM SAPIENS.EW99USU \n"
                + "       WHERE R999USU_NOMUSU = ?";
        UsuarioModel usuarioModel = new UsuarioModel();
        lUsuario = new ArrayList<>();
        try {
            psSelectUser = Conexao.getConnection().prepareStatement(sqlSelectUser);
            psSelectUser.setString(1, usuario);
            System.out.println("Slect:" + sqlSelectUser);
            rsSelectUser = psSelectUser.executeQuery();
            if (rsSelectUser.next()) {
                usuarioModel = new UsuarioModel();
                usuarioModel.setNome(rsSelectUser.getString("R910USU_NOMCOM"));
                usuarioModel.setLogin(rsSelectUser.getString("R999USU_NOMUSU"));
                usuarioModel.setIdUsuario(rsSelectUser.getInt("R999USU_CODUSU"));
                psLogin = Conexao.getConnection().prepareStatement(sqlSelectLogins);
                psLogin.setLong(1, usuarioModel.getIdUsuario());
                rsLogin = psLogin.executeQuery();
                if (rsLogin.next()) {
                    usuarioModel.setNivelLiberacao("");
                    usuarioModel.setDataHoraAtualizacao(rsLogin.getDate("LOGIN_USER"));
                    lUsuario.add(usuarioModel);
                    UsuarioLogin login = new UsuarioLogin();
                    login.setUsuario(usuarioModel);
                    login.setDataLogin(new Date());
                    login.setSistemaOperacional(System.getProperty("os.name"));
                    login.setNomeUsuarioSO(System.getProperty("user.name"));
                    login.setNomeEstacao(InetAddress.getLocalHost().getHostName());
                    login.setDataLogin(rsLogin.getDate("LOGIN_USER"));
                    usuarioModel.getLogins().add(login);
                    usuarioControl = new UsuarioController();
                    usuarioControl.setUsuario(usuarioModel);
                    //   usuarioModel.getLogins().get(0).setIdLogin(rsLogin.getLong("ID_USUARIO"));

                    psSelectUser.close();
                } else {
                    usuarioModel.setNivelLiberacao("");
                    lUsuario.add(usuarioModel);
                }
            }
            psSelectUser.close();
            rsSelectUser.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLRecoverableException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return usuarioModel;

    }

    public int insertUser(UsuarioModel usuarioModel) throws ErroSistemaException {
        sqlInsertUser = "INSERT INTO CONTA_USUARIO(ID_USUARIO, NOME, CONTA, STATUS, SENHA, PERFIL, DATA_CADASTRO, DATA_ATUALIZACAO) VALUES(\n"
                + "ID_USER.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        sqlSelectUser = "SELECT ID_USUARIO, NOME, CONTA, STATUS, SENHA FROM CONTA_USUARIO WHERE CONTA = ?";

        try {

            psSelectUser = Conexao.getConnection().prepareStatement(sqlSelectUser);
            psSelectUser.setString(1, usuarioModel.getLogin());
            rsSelectUser = psSelectUser.executeQuery();
            if (rsSelectUser.next()) {
                resultado = psSelectUser.executeUpdate();
                if (resultado == -1) {
                    psSelectUser.close();
                    rsSelectUser.close();
                    resultado = 0;
                    return resultado;
                }
            } else {
                psInsertUser = Conexao.getConnection().prepareStatement(sqlInsertUser);
                psInsertUser.setString(1, usuarioModel.getNome());
                psInsertUser.setString(2, usuarioModel.getLogin());
                psInsertUser.setString(3, usuarioModel.getStatus());
                psInsertUser.setString(4, usuarioModel.getSenha());
                psInsertUser.setString(5, usuarioModel.getNivelLiberacao());
                psInsertUser.setDate(6, Utilitarios.converteData(new Date()));
                psInsertUser.setDate(7, Utilitarios.converteData(new Date()));
                resultado = psInsertUser.executeUpdate();
                Conexao.getConnection().commit();

                if (resultado == -1) {
                    psInsertUser.close();
                    psSelectUser.close();
                    resultado = 0;
                    return resultado;
                } else {
                    psInsertUser.close();
                    psSelectUser.close();
                    resultado = 1;
                    return resultado;
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            resultado = 0;
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }

        return resultado;

    }

    public UsuarioModel InsetLogin(UsuarioModel usuario) throws ErroSistemaException {
        sqlInsertLogin = "INSERT INTO LOGIN_USUARIO (ID_LOGIN, USUARIO, LOGIN_USER, SO, NOME_ESTACAO, NOME_USER_SO) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            psInsertLogin = Conexao.getConnection().prepareStatement(sqlInsertLogin);
            psInsertLogin.setLong(1, usuario.getIdUsuario());
            psInsertLogin.setString(2, usuario.getLogins().get(0).getUsuario().getLogin());
            psInsertLogin.setDate(3, Utilitarios.converteData(usuario.getLogins().get(0).getDataLogin()));
            psInsertLogin.setString(4, usuario.getLogins().get(0).getSistemaOperacional());
            psInsertLogin.setString(5, usuario.getLogins().get(0).getNomeEstacao());
            psInsertLogin.setString(6, usuario.getLogins().get(0).getNomeUsuarioSO());
            resultado = psInsertLogin.executeUpdate();

            if (resultado == -1) {
                psInsertLogin.close();
            } else if (resultado == 1) {
                Conexao.getConnection().commit();
                psInsertLogin.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
        }
        return usuario;
    }

    public UsuarioModel encerrarLogin(UsuarioModel usuario) throws ErroSistemaException {
        sqlUpdateLogin = "DELETE FROM LOGIN_USUARIO WHERE ID_LOGIN = ?";
        try {
            psLogin = Conexao.getConnection().prepareStatement(sqlUpdateLogin);
            psLogin.setLong(1, usuario.getIdUsuario());
            psLogin.executeUpdate();
            Conexao.getConnection().commit();
            psLogin.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
        }

        return usuario;
    }

    public UsuarioModel AtualizarLogin(UsuarioModel usuario) throws ErroSistemaException {
        String sqlAtualizaLogin = "UPDATE LOGIN_USUARIO SET LOGIN_USER = ? WHERE ID_LOGIN = ?";
        try {
            psLogin = Conexao.getConnection().prepareStatement(sqlAtualizaLogin);
            psLogin.setDate(1, Utilitarios.converteData(new Date()));
            psLogin.setLong(2, usuario.getIdUsuario());
            psLogin.executeUpdate();
            Conexao.getConnection().commit();
            psLogin.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
        }

        return usuario;
    }

    public int buscaConta(UsuarioModel usuarioModel, long conta) throws ErroSistemaException {
        sqlSelectLogins = "SELECT ID_USUARIO, NOME, CONTA, STATUS, SENHA, DATA_CADASTRO, DATA_ATUALIZACAO, PERFIL FROM CONTA_USUARIO WHERE ID_USUARIO = ?";
        try {
            psSelectUser = Conexao.getConnection().prepareStatement(sqlSelectLogins);
            psSelectUser.setLong(1, conta);
            rsSelectUser = psSelectUser.executeQuery();
            if (rsSelectUser.next()) {
                usuarioModel.setIdUsuario(rsSelectUser.getInt("ID_USUARIO"));
                usuarioModel.setNome(rsSelectUser.getString("NOME"));
                usuarioModel.setStatus(rsSelectUser.getString("STATUS"));
                usuarioModel.setSenha(rsSelectUser.getString("SENHA"));
                usuarioModel.setNivelLiberacao(rsSelectUser.getString("PERFIL"));
                usuarioModel.setLogin(rsSelectUser.getString("CONTA"));
                usuarioModel.setDataHoraAtualizacao(rsSelectUser.getDate("DATA_ATUALIZACAO"));
                usuarioModel.setDataHoraCadastro(rsSelectUser.getDate("DATA_CADASTRO"));
            } else {
                usuarioModel = new UsuarioModel();
            }

            if (resultado == -1) {
                resultado = 0;
                return resultado;
            } else {
                resultado = 1;
                return resultado;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioLoginDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psSelectUser.close();
                rsSelectUser.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }

        }

    }

    public int alteraUsuario(UsuarioModel usuarioModel) throws ErroSistemaException {
        sqlUpdateUsuario = ("UPDATE CONTA_USUARIO SET"
                + " NOME = ?,"
                + " STATUS = ?,"
                + " PERFIL = ?,"
                + " SENHA = ?,"
                + " DATA_ATUALIZACAO = ?,"
                + " USUARIO_ALTERACAO = ?"
                + " WHERE CONTA = ?");

        try {
            psInsertUser = Conexao.getConnection().prepareStatement(sqlUpdateUsuario);

            psInsertUser.setString(1, usuarioModel.getNome());
            psInsertUser.setString(2, usuarioModel.getStatus());
            psInsertUser.setString(3, usuarioModel.getNivelLiberacao());
            psInsertUser.setString(4, usuarioModel.getSenha());
            psInsertUser.setString(5, usuarioModel.getLogin());
            resultado = psInsertUser.executeUpdate();

            if (resultado == -1) {
                resultado = 0;
                return resultado;
            } else {
                resultado = 1;
                Conexao.getConnection().commit();
                return resultado;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        } finally {
            try {
                psInsertUser.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                throw new ErroSistemaException(ex.getMessage(), ex.getCause());
            }
        }

    }

    public List<UsuarioModel> buscarUsuario() throws ErroSistemaException {
        UsuarioModel usuarioModel = new UsuarioModel();
        lUsuarioModel = new ArrayList<>();
        sqlSelectUsuarios = "SELECT ID_USUARIO, NOME, CONTA, STATUS, SENHA, DATA_CADASTRO, DATA_ATUALIZACAO, PERFIL FROM CONTA_USUARIO";
        try {
            psSelectUsuarios = Conexao.getConnection().prepareStatement(sqlSelectUsuarios);
            rsSelectUsuarios = psSelectUsuarios.executeQuery();
            while (rsSelectUsuarios.next()) {
                usuarioModel = new UsuarioModel();
                usuarioModel.setIdUsuario(rsSelectUsuarios.getInt("ID_USUARIO"));
                usuarioModel.setNome(rsSelectUsuarios.getString("NOME"));
                usuarioModel.setStatus(rsSelectUsuarios.getString("STATUS"));
                //usuarioModel.setSenha(new Base64.encode(rsSelectUsuarios.getString("SENHA").getBytes()));
                usuarioModel.setSenha(Base64.getEncoder().encodeToString(rsSelectUsuarios.getString("SENHA").getBytes()));
                usuarioModel.setNivelLiberacao(rsSelectUsuarios.getString("PERFIL"));
                usuarioModel.setLogin(rsSelectUsuarios.getString("CONTA"));
                usuarioModel.setDataHoraCadastro(rsSelectUsuarios.getDate("DATA_CADASTRO"));
                usuarioModel.setDataHoraAtualizacao(rsSelectUsuarios.getDate("DATA_ATUALIZACAO"));
                lUsuarioModel.add(usuarioModel);
            }
            psSelectUsuarios.close();
            rsSelectUsuarios.close();

        } catch (SQLException ex) {
            throw new ErroSistemaException(ex.getMessage(), ex.getCause());
        }
        return lUsuarioModel;

    }

}
