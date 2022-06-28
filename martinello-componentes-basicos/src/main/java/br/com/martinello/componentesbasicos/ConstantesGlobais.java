/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

/**
 *
 * @author Sidnei
 */
public class ConstantesGlobais {

    public static final Locale LOCALIDADE = new Locale("pt", "BR");
    public static final Color COR_BRANCO = Color.white;
    public static final Color COR_PRETO = Color.black;
    public static final Color COR_VERMELHO = new Color(16724787);
    public static final Color COR_AMARELO = Color.yellow;
    public static final Color COR_CINZA = new Color(11184810);
    public static final Color COR_CINZA_LINHAS = new Color(13421772);
    public static final Color COR_AZUL_CLARO = new Color(39372);
    public static final Color COR_AZUL_MEDIO = new Color(3368601);
    public static final Color COR_AZUL_ESCURO = new Color(0.0f, 0.0f, 0.6f);
    public static final Color COR_VERDE = new Color(32896);
    public static final Color COR_LARANJA = new Color(16777059);
    public static final Color COR_CINZA_CLARO = new Color(12303291);
    public static final ImageIcon ICO_FECHARDICAS;
    public static final ImageIcon ICO_FECHARDICASDESABILITADO;
    public static final ImageIcon ICO_ATENCAO;
    public static final ImageIcon ICO_ERRO;
    public static final ImageIcon ICO_AVISO;
    public static final Border BORDA_ERRO = javax.swing.BorderFactory.createLineBorder(Color.RED, 2);
    public static final Border BORDA_ATENCAO = javax.swing.BorderFactory.createLineBorder(Color.YELLOW, 2);
    public static final Border BORDA_AVISO = javax.swing.BorderFactory.createLineBorder(Color.BLUE, 2);

    public static final int TAMANHO_VALOR = 13;
    public static final int TAMANHO_VALOR_PARTE_DECIMAL = 2;

    public static final Font FONTE_BORDA_JPANEL;
    public static final Font FONTE_TABBED_PANE;
    public static final Font FONTE_8_NORMAL;
    public static final Font FONTE_9_NORMAL;
    public static final Font FONTE_10_NORMAL;
    public static final Font FONTE_9_BOLD;
    public static final Font FONTE_10_BOLD;
    public static final Font FONTE_11_NORMAL;
    public static final Font FONTE_11_BOLD;
    public static final Font FONTE_15_BOLD;
    public static final Font FONTE_18_BOLD;

    public static Color COR_BORDA_PAINEL_DICAS_AVISO = COR_AMARELO;
    public static Color COR_TITULO_PAINEL_DICAS_AVISO = COR_PRETO;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO = COR_VERMELHO;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO = COR_BRANCO;
    public static Color COR_BORDA_PAINEL_DICAS_ATENCAO = COR_VERDE;
    public static Color COR_TITULO_PAINEL_DICAS_ATENCAO = COR_BRANCO;
    public static Color COR_BORDA_PAINEL_DICAS_DEFAULT = COR_AZUL_CLARO;
    public static Color COR_TITULO_PAINEL_DICAS_DEFAULT = COR_BRANCO;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO_IMPEDITIVO = COR_VERMELHO;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO_IMPEDITIVO = COR_BRANCO;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO_OK_CANCELAR = COR_AMARELO;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO_OK_CANCELAR = COR_PRETO;

    static /* synthetic */ Class class$0;

    static {
        Class class_ = class$0;

        if (class_ == null) {
            try {
                class_ = ConstantesGlobais.class$0 = Class.forName("br.com.martinello.componentesbasicos.ConstantesGlobais");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }

        ICO_FECHARDICAS = new ImageIcon(class_.getClassLoader().getResource("ico_fechardicas.gif"));
        ICO_FECHARDICASDESABILITADO = new ImageIcon(class_.getClassLoader().getResource("ico_fechardicas.gif"));
        ICO_ATENCAO = new ImageIcon(class_.getClassLoader().getResource("ico_atencao.png"));
        ICO_ERRO = new ImageIcon(class_.getClassLoader().getResource("ico_erro.gif"));
        ICO_AVISO = new ImageIcon(class_.getClassLoader().getResource("ico_aviso.gif"));

        FONTE_TABBED_PANE = new Font("Verdana", 0, 11);
        FONTE_BORDA_JPANEL = new Font("Verdana", 0, 11);
        FONTE_8_NORMAL = new Font("Verdana", 0, 8);
        FONTE_9_NORMAL = new Font("Verdana", 0, 9);
        FONTE_10_NORMAL = new Font("Verdana", 0, 10);

        FONTE_9_BOLD = new Font("Verdana", 1, 9);
        FONTE_10_BOLD = new Font("Verdana", 1, 10);
        FONTE_11_NORMAL = new Font("Verdana", 0, 11);
        FONTE_11_BOLD = new Font("Verdana", 1, 11);
        FONTE_15_BOLD = new Font("Verdana", 1, 15);
        FONTE_18_BOLD = new Font("Verdana", 1, 18);

    }

}
