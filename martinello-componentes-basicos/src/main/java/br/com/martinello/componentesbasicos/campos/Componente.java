/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.componentesbasicos.campos;

import br.com.martinello.componentesbasicos.ConstantesGlobais;
import br.com.martinello.componentesbasicos.Campo;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

/**
 *
 * @author Sidnei
 */
public abstract class Componente extends JComponent {

    protected Campo campo = null;
    private BotaoMensagem botaoMensagem = new BotaoMensagem();
    private static int CONST_CALC_TAMX_PAINELDICAS = 3;
    private static int TIPO_NORMAL = 0;
    private static int TIPO_ERRO = 3;
    private static int TIPO_AVISO = 2;
    private static int TIPO_ATENCAO = 1;
    private int tipoMensagem = TIPO_NORMAL;
    private static boolean validandoImpeditivo;
    private ActionListener preActionOkSomente;
    private ActionListener preActionOk;
    private ActionListener preActionCancel;
    protected int incrementoTamanhoFonte = 0;
    protected float tamanhoOriginal = -1.0f;
    private boolean validar = true;
    private MostrarDicas mostraDicas = null;

    public Componente() {
        this.instanciaComponentes();
        this.buildComponente();
        this.setOpaque(false);
    }

    public Componente(Campo campo) {
        this.instanciaComponentes();
        this.buildComponente();
        this.setCampo(campo);
    }

    protected void instanciaComponentes() {
    }

    protected abstract void buildComponente();

    public BotaoMensagem getBotaoMensagem() {
        return botaoMensagem;
    }

    public void setBotaoMensagem(BotaoMensagem botaoMensagem) {
        this.botaoMensagem = botaoMensagem;
    }

    public int getTipoMensagem() {
        return tipoMensagem;
    }

    public void setTipoMensagem(int tipoMensagem) {
        this.tipoMensagem = tipoMensagem;
    }

    public static boolean isValidandoImpeditivo() {
        return validandoImpeditivo;
    }

    public static void setValidandoImpeditivo(boolean validandoImpeditivo) {
        Componente.validandoImpeditivo = validandoImpeditivo;
    }

    public ActionListener getPreActionOkSomente() {
        return preActionOkSomente;
    }

    public void setPreActionOkSomente(ActionListener preActionOkSomente) {
        this.preActionOkSomente = preActionOkSomente;
    }

    public ActionListener getPreActionOk() {
        return preActionOk;
    }

    public void setPreActionOk(ActionListener preActionOk) {
        this.preActionOk = preActionOk;
    }

    public ActionListener getPreActionCancel() {
        return preActionCancel;
    }

    public void setPreActionCancel(ActionListener preActionCancel) {
        this.preActionCancel = preActionCancel;
    }

    public int getIncrementoTamanhoFonte() {
        return incrementoTamanhoFonte;
    }

    public void setIncrementoTamanhoFonte(int incrementoTamanhoFonte) {
        this.incrementoTamanhoFonte = incrementoTamanhoFonte;
    }

    public float getTamanhoOriginal() {
        return tamanhoOriginal;
    }

    public void setTamanhoOriginal(float tamanhoOriginal) {
        this.tamanhoOriginal = tamanhoOriginal;
    }

    public Campo getCampo() {
        return campo;
    }

    public void setCampo(Campo campo) {
        this.campo = campo;
    }

    public abstract JComponent getComponenteFoco();

    public abstract JComponent getComponenteEditor();

    public abstract boolean eVazio();

    public synchronized boolean chamaValidacao() {
        if (this.isValidandoImpeditivo()) {
            return false;
        }

        if (!this.validar || this.getBotaoMensagem() == null) {
            return true;
        }

        Campo campo = this.getCampo();

        int severidade = eVazio() ? 3 : 0;

        if (severidade > 0) {
            String mensagem = "ret.getMensagemValidacaoExtendida()";
            if (this.mostraDicas != null) {
                this.getBotaoMensagem().removeActionListener(this.mostraDicas);
            }
            if (severidade == 1) {
                this.getBotaoMensagem().setIcon(ConstantesGlobais.ICO_ATENCAO);
                this.mostraDicas = new MostrarDicas(severidade, campo.getDica(), mensagem);
                this.getBotaoMensagem().addActionListener(this.mostraDicas);

            } else if (severidade == 3) {
                this.getBotaoMensagem().setIcon(ConstantesGlobais.ICO_ERRO);
                this.mostraDicas = new MostrarDicas(severidade, campo.getDica(), mensagem);
                this.getBotaoMensagem().addActionListener(this.mostraDicas);

            } else if (severidade == 5) {
                this.setValidandoImpeditivo(true);
                Window parent = SwingUtilities.getWindowAncestor(this.getBotaoMensagem());
                this.getBotaoMensagem().setIcon(ConstantesGlobais.ICO_ERRO);
                this.mostraDicas = new MostrarDicas(parent, severidade, campo.getDica(), mensagem);
                SwingUtilities.invokeLater(() -> {
                    Componente.this.mostraDicas.dispara();
                });

            } else if (severidade == 4) {
                this.setValidandoImpeditivo(true);
                Window parent = SwingUtilities.getWindowAncestor(this.getBotaoMensagem());
                this.getBotaoMensagem().setIcon(ConstantesGlobais.ICO_ATENCAO);
                this.mostraDicas = new MostrarDicas(parent, severidade, campo.getDica(), mensagem);
                SwingUtilities.invokeLater(() -> {
                    Componente.this.mostraDicas.dispara();
                });

            } else {
                this.getBotaoMensagem().setIcon(ConstantesGlobais.ICO_AVISO);
                this.mostraDicas = new MostrarDicas(severidade, campo.getDica(), mensagem);
                this.getBotaoMensagem().addActionListener(this.mostraDicas);
                //this.getBotaoMensagem().setVisible(!this.isDisableButtonMensagem());
                this.getBotaoMensagem().setVisible(true);

            }

            //this.getBotaoMensagem().setVisible(!this.isDisableButtonMensagem());
            this.getBotaoMensagem().setVisible(true);

            if (SwingUtilities.getRoot(this.getBotaoMensagem()) instanceof Window) {
                this.getBotaoMensagem().getParent().repaint();
                this.repaint();
                ((Window) SwingUtilities.getRoot(this.getBotaoMensagem())).repaint();
            }
            return false;
        }

        this.getBotaoMensagem().setVisible(false);

        if (SwingUtilities.getRoot(this.getBotaoMensagem()) instanceof Window) {
            this.getBotaoMensagem().getParent().repaint();
            this.repaint();
            ((Window) SwingUtilities.getRoot(this.getBotaoMensagem())).repaint();
        }

        GerenciadorGUI.esconderPainelDicas();
        return true;
    }

    class Run implements Runnable {

        private boolean foco;

        public Run(boolean mudarIdentificacaoFoco) {
            this.foco = mudarIdentificacaoFoco;
        }

        @Override
        public void run() {
            //Componente.this.setIdentificacaoFoco(this.foco);
            Componente.this.getComponenteFoco().requestFocusInWindow();
        }
    }

    class MostrarDicas implements ActionListener {

        private String msg;
        private String titulo;
        private int tipo;
        private Window parent;

        public MostrarDicas(int tipo, String titulo, String msg) {
            this.parent = null;
            this.msg = msg;
            this.titulo = titulo;
            this.tipo = tipo;
        }

        public MostrarDicas(Window parent, int severidade, String nomeCampoCurto, String mensagem) {
            this(severidade, nomeCampoCurto, mensagem);
            this.parent = parent;
        }

        public void disparaValidacaoImpeditiva() {
            //int tamMax = JCampo.this.getInformacao().getNomeCampoCurto().length() * CONST_CALC_TAMX_PAINELDICAS;
            int tamMax = "Campo Obrigatório Não Preenchido.".length() * CONST_CALC_TAMX_PAINELDICAS;
            int n = tamMax = tamMax > 90 ? tamMax : 90;
            if (this.tipo == 5) {
                GerenciadorGUI.exibirPainelMensagensModal((Window) this.parent, (int) this.tipo, (String) this.titulo, (String) this.msg, (Component) Componente.this.getComponenteEditor(), (int) tamMax);
                Componente.this.setValidandoImpeditivo(false);
                Componente.this.getBotaoMensagem().setVisible(false);
            } else if (this.tipo == 4) {
                GerenciadorGUI.exibirPainelMensagensModal((Window) this.parent, (int) this.tipo, (String) this.titulo, (String) this.msg, (Component) Componente.this.getComponenteEditor(), (int) tamMax);
                Componente.this.setValidandoImpeditivo(false);
                Componente.this.getBotaoMensagem().setVisible(false);
            }
        }

        public void dispara() {
            //int tamMax = JCampo.this.getInformacao().getNomeCampoCurto().length() * CONST_CALC_TAMX_PAINELDICAS;
            int tamMax = "Campo Obrigatório Não Preenchido.".length() * CONST_CALC_TAMX_PAINELDICAS;
            int n = tamMax = tamMax > 90 ? tamMax : 90;
            if (this.tipo == 5) {
                GerenciadorGUI.exibirPainelMensagensModal((Window) this.parent, (int) this.tipo, (String) this.titulo, (String) this.msg, (JComponent) Componente.this.getComponenteEditor(), (int) tamMax, (ActionListener) Componente.this.getPreActionOkSomente());
                Componente.this.setValidandoImpeditivo(false);
                Componente.this.getBotaoMensagem().setVisible(false);
            } else if (this.tipo == 4) {
                GerenciadorGUI.exibirPainelMensagensModal((Window) this.parent, (int) this.tipo, (String) this.titulo, (String) this.msg, (Component) Componente.this.getComponenteEditor(), (int) tamMax, (ActionListener) Componente.this.getPreActionOk(), (ActionListener) Componente.this.getPreActionCancel());
                Componente.this.setValidandoImpeditivo(false);
                Componente.this.getBotaoMensagem().setVisible(false);
            } else if (SwingUtilities.getRoot(Componente.this.getBotaoMensagem()) instanceof JDialog) {
                GerenciadorGUI.exibirPainelMensagens((Window) ((Window) SwingUtilities.getRoot(Componente.this.getBotaoMensagem())), (int) this.tipo, (String) this.titulo, (String) this.msg, (Component) Componente.this.getBotaoMensagem(), (int) tamMax);
            } else {
                GerenciadorGUI.exibirPainelMensagens((int) this.tipo, (String) this.titulo, (String) this.msg, (Component) Componente.this.getBotaoMensagem(), (int) tamMax);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.dispara();
        }
    }

}
