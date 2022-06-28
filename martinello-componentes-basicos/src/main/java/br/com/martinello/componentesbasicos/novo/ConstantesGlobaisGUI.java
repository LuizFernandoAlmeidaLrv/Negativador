/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.novo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class ConstantesGlobaisGUI {

    public static final ImageIcon ICO_AVANCAR;
    public static final ImageIcon ICO_AVANCAR_PRES;
    public static final ImageIcon ICO_AVANCAR_SEL;
    public static final ImageIcon ICO_INFORMA;
    public static final ImageIcon ICO_INFORMA_PRES;
    public static final ImageIcon ICO_INFORMA_SEL;
    public static final ImageIcon ICO_PLAY;
    public static final ImageIcon ICO_PLAY_PRES;
    public static final ImageIcon ICO_PLAY_SEL;
    public static final ImageIcon ICO_STOP;
    public static final ImageIcon ICO_STOP_PRES;
    public static final ImageIcon ICO_STOP_SEL;
    public static final ImageIcon ICO_VAZIO;
    public static final ImageIcon ICO_ERRO;
    public static final ImageIcon ICO_AVISO;
    public static final ImageIcon ICO_ATENCAO;
    public static final ImageIcon ICO_VISUALIZAR;
    public static final ImageIcon ICO_VISUALIZAR_SEL;
    public static final ImageIcon ICO_VISUALIZAR_PRES;
    public static final int TAM_TELA = 800;
    public static final int ALT_TELA = 600;
    public static final int ALT_BARRA_TITULO = 31;
    public static final int AJUSTE_TAM_TELA = 12;
    public static final int ALT_AREA_CABECALHO = 85;
    public static final int ALT_AREA_RODAPE = 85;
    public static final int TAM_AREA_FERRAMENTAS = 120;
    public static final int ALT_AREA_NAVEGADOR = 569;
    public static final int ALT_AREA_APLICACAO = 399;
    public static final int ALT_AREA_FERRAMENTAS = 284;
    public static final int TAM_AREA_UTILITARIOS = 120;
    public static final int ALT_AREA_UTILITARIOS = 284;
    public static final String NOME_JANELA_PRINCIPAL = "PPGD_JANELA_PRINCIPAL";
    public static final Dimension HGAP2;
    public static final Dimension VGAP2;
    public static final Dimension HGAP5;
    public static final Dimension VGAP5;
    public static final Dimension HGAP10;
    public static final Dimension VGAP10;
    public static final Dimension HGAP15;
    public static final Dimension VGAP15;
    public static final Dimension HGAP20;
    public static final Dimension VGAP20;
    public static final Dimension HGAP25;
    public static final Dimension VGAP25;
    public static final Dimension HGAP30;
    public static final Dimension HGAP35;
    public static final Dimension VGAP30;
    public static final Dimension HGAP55;
    public static final Dimension VGAP80;
    public static final Dimension HGAP100;
    public static final Dimension VGAP100;
    public static final Dimension HGAP200;
    public static final Dimension VGAP200;
    public static final Dimension HGAP230;
    public static final Dimension HGAP250;
    public static final int MULTIPLICADOR_COMPRIMENTO = 10;
    public static final int ALTURA_CONTROLES = 20;
    public static final Color COR_BRANCO;
    public static final Color COR_PRETO;
    public static final Color COR_VERMELHO;
    public static final Color COR_AMARELO;
    public static final Color COR_CINZA;
    public static final Color COR_CINZA_LINHAS;
    public static final Color COR_AZUL_CLARO;
    public static final Color COR_AZUL_MEDIO;
    public static final Color COR_AZUL_ESCURO;
    public static final Color COR_VERDE;
    public static final Color COR_LARANJA;
    public static final Color COR_CINZA_CLARO;
    public static final String COR_HTML_AZUL_SUBLINHADO = "<FONT COLOR=#336699><u>";
    public static final String COR_HTML_BLACK = "<FONT COLOR=black>";
    public static final Font FONTE_8_NORMAL;
    public static final Font FONTE_9_NORMAL;
    public static final Font FONTE_10_NORMAL;
    public static final Font FONTE_9_BOLD;
    public static final Font FONTE_10_BOLD;
    public static final Font FONTE_11_NORMAL;
    public static final Font FONTE_11_BOLD;
    public static final Font FONTE_15_BOLD;
    public static final Font FONTE_18_BOLD;
    public static final String FONTE_HTML_AZUL_PEQUENA = "<FONT FACE=Verdana COLOR=#336699 SIZE=1>";
    public static final String FONTE_HTML_AZUL = "<FONT FACE=Verdana COLOR=#336699 SIZE=2>";
    public static final String FONTE_HTML_LARANJA = "<FONT FACE=Verdana COLOR=#FFFF63 SIZE=2>";
    public static final String FONTE_HTML_VERDE = "<FONT FACE=Verdana COLOR=#008080 SIZE=2>";
    public static final String FONTE_HTML_AZUL_GRANDE = "<FONT FACE=Verdana COLOR=#336699 SIZE=4>";
    public static final String FONTE_HTML_BLACK = "<FONT FACE=Verdana COLOR=black SIZE=2>";
    public static final Border BORDA_VAZIA;
    public static final Border BORDA_BOTAO;
    public static final Border BORDA_COMUM;
    public static final Border BORDA_GROSSA;
    public static final Border BORDA_EDIT_CAMPO;
    public static final Border BORDA_LINHA_RODAPE;
    public static final Border BORDA_LINHA_TOPO;
    public static final Border BORDA_LINHA_VERTICAL;
    public static final String LABEL_ERRO_ABRIR_DECL = "N\u00e3o foi poss\u00edvel realizar a opera\u00e7\u00e3o requerida (arquivo da declara\u00e7\u00e3o corrompido).\nFavor restaurar a copia de seguran\u00e7a da declara\u00e7\u00e3o.";
    public static final ImageIcon ICO_FECHARDICAS;
    public static final ImageIcon ICO_FECHARDICASDESABILITADO;
    public static Color COR_BORDA_PAINEL_DICAS_AVISO;
    public static Color COR_TITULO_PAINEL_DICAS_AVISO;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO;
    public static Color COR_BORDA_PAINEL_DICAS_ATENCAO;
    public static Color COR_TITULO_PAINEL_DICAS_ATENCAO;
    public static Color COR_BORDA_PAINEL_DICAS_DEFAULT;
    public static Color COR_TITULO_PAINEL_DICAS_DEFAULT;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO_IMPEDITIVO;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO_IMPEDITIVO;
    public static Color COR_BORDA_PAINEL_DICAS_ERRO_OK_CANCELAR;
    public static Color COR_TITULO_PAINEL_DICAS_ERRO_OK_CANCELAR;
    static /* synthetic */ Class class$0;
    static /* synthetic */ Class class$1;

    static {
        Class class_;
        Class class_2;
        Class class_3;
        Class class_4;
        Class class_5;
        Class class_6;
        Class class_7;
        Class class_8;
        Class class_9;
        Class class_10;
        Class class_11;
        Class class_12;
        Class class_13;
        Class class_14;
        Class class_15;
        Class class_16;
        Class class_17;
        Class class_18;
        Class class_19;
        Class class_20;
        Class class_21;
        class_21 = class$0;
        if (class_21 == null) {
            try {
                class_21 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_AVANCAR = new ImageIcon(class_21.getResource("/imagens/bot_avancar.gif"));
        class_20 = class$0;
        if (class_20 == null) {
            try {
                class_20 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_AVANCAR_PRES = new ImageIcon(class_20.getResource("/imagens/bot_avancarpressionado.gif"));
        class_19 = class$0;
        if (class_19 == null) {
            try {
                class_19 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_AVANCAR_SEL = new ImageIcon(class_19.getResource("/imagens/bot_avancarselecionado.gif"));
        class_18 = class$0;
        if (class_18 == null) {
            try {
                class_18 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_INFORMA = new ImageIcon(class_18.getResource("/imagens/bot_informa.gif"));
        class_17 = class$0;
        if (class_17 == null) {
            try {
                class_17 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_INFORMA_PRES = new ImageIcon(class_17.getResource("/imagens/bot_informapressionado.gif"));
        class_16 = class$0;
        if (class_16 == null) {
            try {
                class_16 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_INFORMA_SEL = new ImageIcon(class_16.getResource("/imagens/bot_informaselecionado.gif"));
        class_15 = class$0;
        if (class_15 == null) {
            try {
                class_15 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_PLAY = new ImageIcon(class_15.getResource("/imagens/bot_play.gif"));
        class_14 = class$0;
        if (class_14 == null) {
            try {
                class_14 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_PLAY_PRES = new ImageIcon(class_14.getResource("/imagens/bot_playpressionado.gif"));
        class_13 = class$0;
        if (class_13 == null) {
            try {
                class_13 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_PLAY_SEL = new ImageIcon(class_13.getResource("/imagens/bot_playselecionado.gif"));
        class_12 = class$0;
        if (class_12 == null) {
            try {
                class_12 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_STOP = new ImageIcon(class_12.getResource("/imagens/bot_stop.gif"));
        class_11 = class$0;
        if (class_11 == null) {
            try {
                class_11 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_STOP_PRES = new ImageIcon(class_11.getResource("/imagens/bot_stoppressionado.gif"));
        class_10 = class$0;
        if (class_10 == null) {
            try {
                class_10 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_STOP_SEL = new ImageIcon(class_10.getResource("/imagens/bot_stopselecionado.gif"));
        class_9 = class$0;
        if (class_9 == null) {
            try {
                class_9 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_VAZIO = new ImageIcon(class_9.getResource("/imagens/ico_vazio.gif"));
        class_8 = class$0;
        if (class_8 == null) {
            try {
                class_8 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_ERRO = new ImageIcon(class_8.getResource("/imagens/ico_erro.gif"));
        class_7 = class$0;
        if (class_7 == null) {
            try {
                class_7 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_AVISO = new ImageIcon(class_7.getResource("/imagens/ico_aviso.gif"));
        class_6 = class$0;
        if (class_6 == null) {
            try {
                class_6 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_ATENCAO = new ImageIcon(class_6.getResource("/imagens/ico_atencao.png"));
        class_5 = class$0;
        if (class_5 == null) {
            try {
                class_5 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_VISUALIZAR = new ImageIcon(class_5.getResource("/imagens/bot_view.gif"));
        class_4 = class$0;
        if (class_4 == null) {
            try {
                class_4 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_VISUALIZAR_SEL = new ImageIcon(class_4.getResource("/imagens/bot_viewselecionado.gif"));
        class_3 = class$0;
        if (class_3 == null) {
            try {
                class_3 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_VISUALIZAR_PRES = new ImageIcon(class_3.getResource("/imagens/bot_viewpressionado.gif"));
        HGAP2 = new Dimension(2, 1);
        VGAP2 = new Dimension(1, 2);
        HGAP5 = new Dimension(5, 1);
        VGAP5 = new Dimension(1, 5);
        HGAP10 = new Dimension(10, 1);
        VGAP10 = new Dimension(1, 10);
        HGAP15 = new Dimension(15, 1);
        VGAP15 = new Dimension(1, 15);
        HGAP20 = new Dimension(20, 1);
        VGAP20 = new Dimension(1, 20);
        HGAP25 = new Dimension(25, 1);
        VGAP25 = new Dimension(1, 25);
        HGAP30 = new Dimension(30, 1);
        HGAP35 = new Dimension(35, 1);
        VGAP30 = new Dimension(1, 30);
        HGAP55 = new Dimension(55, 1);
        VGAP80 = new Dimension(1, 80);
        HGAP100 = new Dimension(100, 1);
        VGAP100 = new Dimension(1, 100);
        HGAP200 = new Dimension(200, 1);
        VGAP200 = new Dimension(1, 200);
        HGAP230 = new Dimension(230, 1);
        HGAP250 = new Dimension(250, 1);
        COR_BRANCO = Color.white;
        COR_PRETO = Color.black;
        COR_VERMELHO = new Color(16724787);
        COR_AMARELO = Color.yellow;
        COR_CINZA = new Color(11184810);
        COR_CINZA_LINHAS = new Color(13421772);
        COR_AZUL_CLARO = new Color(39372);
        COR_AZUL_MEDIO = new Color(3368601);
        COR_AZUL_ESCURO = new Color(0.0f, 0.0f, 0.6f);
        COR_VERDE = new Color(32896);
        COR_LARANJA = new Color(16777059);
        COR_CINZA_CLARO = new Color(12303291);
        FONTE_8_NORMAL = new Font("Verdana", 0, 8);
        FONTE_9_NORMAL = new Font("Verdana", 0, 9);
        FONTE_10_NORMAL = new Font("Verdana", 0, 10);
        FONTE_9_BOLD = new Font("Verdana", 1, 9);
        FONTE_10_BOLD = new Font("Verdana", 1, 10);
        FONTE_11_NORMAL = new Font("Verdana", 0, 11);
        FONTE_11_BOLD = new Font("Verdana", 1, 11);
        FONTE_15_BOLD = new Font("Verdana", 1, 15);
        FONTE_18_BOLD = new Font("Verdana", 1, 18);
        BORDA_VAZIA = BorderFactory.createEmptyBorder();
        BORDA_BOTAO = BorderFactory.createRaisedBevelBorder();
        BORDA_COMUM = BorderFactory.createEtchedBorder();
        BORDA_GROSSA = BorderFactory.createMatteBorder(2, 2, 2, 2, COR_PRETO);
        BORDA_EDIT_CAMPO = BorderFactory.createBevelBorder(0, Color.GRAY, Color.LIGHT_GRAY);
        BORDA_LINHA_RODAPE = BorderFactory.createMatteBorder(0, 0, 1, 0, COR_CINZA_LINHAS);
        BORDA_LINHA_TOPO = BorderFactory.createMatteBorder(1, 0, 0, 0, COR_CINZA_LINHAS);
        BORDA_LINHA_VERTICAL = BorderFactory.createMatteBorder(0, 0, 0, 1, COR_CINZA_LINHAS);
        class_2 = class$0;
        if (class_2 == null) {
            try {
                class_2 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_FECHARDICAS = new ImageIcon(class_2.getResource("/imagens/ico_fechardicas.gif"));
        class_ = class$0;
        if (class_ == null) {
            try {
                class_ = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
            } catch (ClassNotFoundException classNotFoundException) {
                throw new NoClassDefFoundError(classNotFoundException.getMessage());
            }
        }
        ICO_FECHARDICASDESABILITADO = new ImageIcon(class_.getResource("/imagens/ico_fechardicasdesabilitado.gif"));
        COR_BORDA_PAINEL_DICAS_AVISO = COR_AMARELO;
        COR_TITULO_PAINEL_DICAS_AVISO = COR_PRETO;
        COR_BORDA_PAINEL_DICAS_ERRO = COR_VERMELHO;
        COR_TITULO_PAINEL_DICAS_ERRO = COR_BRANCO;
        COR_BORDA_PAINEL_DICAS_ATENCAO = COR_VERDE;
        COR_TITULO_PAINEL_DICAS_ATENCAO = COR_BRANCO;
        COR_BORDA_PAINEL_DICAS_DEFAULT = COR_AZUL_CLARO;
        COR_TITULO_PAINEL_DICAS_DEFAULT = COR_BRANCO;
        COR_BORDA_PAINEL_DICAS_ERRO_IMPEDITIVO = COR_VERMELHO;
        COR_TITULO_PAINEL_DICAS_ERRO_IMPEDITIVO = COR_BRANCO;
        COR_BORDA_PAINEL_DICAS_ERRO_OK_CANCELAR = COR_AMARELO;
        COR_TITULO_PAINEL_DICAS_ERRO_OK_CANCELAR = COR_PRETO;
        Color corBordaAviso = null;
        Color corTituloAviso = null;
        Color corBordaErro = null;
        Color corTituloErro = null;
        Color corBordaAtencao = null;
        Color corTituloAtencao = null;
        Color corBordaDefault = null;
        Color corTituloDefault = null;
        Color corBordaErroImpeditivo = null;
        Color corTituloErroImpeditivo = null;
        Color corBordaErroOkCancelar = null;
        Color corTituloErroOkCancelar = null;
        COR_BORDA_PAINEL_DICAS_AVISO = corBordaAviso == null ? COR_AMARELO : corBordaAviso;
        COR_TITULO_PAINEL_DICAS_AVISO = corTituloAviso == null ? COR_PRETO : corTituloAviso;
        COR_BORDA_PAINEL_DICAS_ERRO = corBordaErro == null ? COR_VERMELHO : corBordaErro;
        COR_TITULO_PAINEL_DICAS_ERRO = corTituloErro == null ? COR_BRANCO : corTituloErro;
        COR_BORDA_PAINEL_DICAS_ATENCAO = corBordaAtencao == null ? COR_VERDE : corBordaAtencao;
        COR_TITULO_PAINEL_DICAS_ATENCAO = corTituloAtencao == null ? COR_BRANCO : corTituloAtencao;
        COR_BORDA_PAINEL_DICAS_DEFAULT = corBordaDefault == null ? COR_AZUL_CLARO : corBordaDefault;
        COR_TITULO_PAINEL_DICAS_DEFAULT = corTituloDefault == null ? COR_BRANCO : corTituloDefault;
        COR_BORDA_PAINEL_DICAS_ERRO_IMPEDITIVO = corBordaErroImpeditivo == null ? COR_VERMELHO : corBordaErroImpeditivo;
        COR_TITULO_PAINEL_DICAS_ERRO_IMPEDITIVO = corTituloErroImpeditivo == null ? COR_BRANCO : corTituloErroImpeditivo;
        COR_BORDA_PAINEL_DICAS_ERRO_OK_CANCELAR = corBordaErroOkCancelar == null ? COR_AMARELO : corBordaErroOkCancelar;
        COR_TITULO_PAINEL_DICAS_ERRO_OK_CANCELAR = corTituloErroOkCancelar == null ? COR_PRETO : corTituloErroOkCancelar;
    }

    public static Color getColor(String def) {
        block41:
        {
            Field field32;
            block37:
            {
                if (def == null || def.trim().equals("")) {
                    return null;
                }
                try {
                    Class class_;
                    Class class_2;
                    Class class_3;
                    class_3 = class$1;
                    if (class_3 == null) {
                        try {
                            class_3 = ConstantesGlobaisGUI.class$1 = Class.forName("java.awt.Color");
                        } catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    field32 = class_3.getField(def.trim());
                    class_2 = class$1;
                    if (class_2 == null) {
                        try {
                            class_2 = ConstantesGlobaisGUI.class$1 = Class.forName("java.awt.Color");
                        } catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    if (!class_2.equals(field32.getType()) || !Modifier.isStatic(field32.getModifiers())) {
                        break block37;
                    }
                    class_ = class$1;
                    if (class_ == null) {
                        try {
                            class_ = ConstantesGlobaisGUI.class$1 = Class.forName("java.awt.Color");
                        } catch (ClassNotFoundException classNotFoundException) {
                            throw new NoClassDefFoundError(classNotFoundException.getMessage());
                        }
                    }
                    return (Color) field32.get(class_);
                } catch (NoSuchFieldException | SecurityException | IllegalAccessException ex) {
                }
                // empty catch block

            }
            try {
                Class class_;
                Class class_4;
                Class class_5;
                class_5 = class$0;
                if (class_5 == null) {
                    try {
                        class_5 = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
                    } catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                field32 = class_5.getField(def.trim());
                class_4 = class$1;
                if (class_4 == null) {
                    try {
                        class_4 = ConstantesGlobaisGUI.class$1 = Class.forName("java.awt.Color");
                    } catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                if (!class_4.equals(field32.getType()) || !Modifier.isStatic(field32.getModifiers())) {
                    break block41;
                }
                class_ = class$0;
                if (class_ == null) {
                    try {
                        class_ = ConstantesGlobaisGUI.class$0 = Class.forName("serpro.ppgd.gui.ConstantesGlobaisGUI");
                    } catch (ClassNotFoundException classNotFoundException) {
                        throw new NoClassDefFoundError(classNotFoundException.getMessage());
                    }
                }
                return (Color) field32.get(class_);
            } catch (NoSuchFieldException field4) {
            } catch (SecurityException field4) {
            } catch (IllegalAccessException field4) {
                // empty catch block
            }
        }
        try {
            StringTokenizer st = new StringTokenizer(def, ",");
            if (1 == st.countTokens()) {
                try {
                    return new Color(Integer.parseInt(st.nextToken().trim(), 16));
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            int[] para = ConstantesGlobaisGUI.ia(st);
            if (4 <= para.length) {
                return new Color(para[0], para[1], para[2], para[3]);
            }
            if (3 <= para.length) {
                return new Color(para[0], para[1], para[2]);
            }
            if (1 <= para.length) {
                return new Color(para[0]);
            }
        } catch (Exception e) {
        }
        return null;
    }

    static int[] ia(StringTokenizer st) {
        int size = st != null ? st.countTokens() : 0;
        int[] a = new int[size];
        int i = 0;
        while (st != null && st.hasMoreTokens()) {
            try {
                a[i] = Integer.parseInt(st.nextToken().trim());
                ++i;
            } catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        int[] b = new int[i];
        System.arraycopy(a, 0, b, 0, i);
        return b;
    }
}
