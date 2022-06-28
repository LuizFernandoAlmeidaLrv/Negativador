/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.sistema.view.painel;

import br.com.martinello.bd.matriz.model.domain.ContasReceberControl;
import br.com.martinello.componentesbasicos.paineis.Painel;
import br.com.martinello.consulta.Domain.ContasReceberModel;
import br.com.martinello.consulta.Domain.VendasModel;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import com.towel.swing.table.ObjectTableModel;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author luiz.almeida
 */
public class PainelContasReceber extends Painel {

    /**
     * Creates new form PainelContasReceber
     */
    private boolean resultado;
    private double valorOriginal;
    public ContasReceberControl contasReceberControl;
    public static List<ContasReceberModel> lContasReceber;
    private int tQuitacao, tParcial, tRenegociacao, tDevolucao, tTransf, tPerdida;
    private double valorTraz, valorCalc, valorAberto, valorParcela, valorVencer;

    private final ObjectTableModel<ContasReceberModel> otmContasReceber = new ObjectTableModel<>(ContasReceberModel.class, 
            "venda,tipo,dataLancamento,cliente,clienteEndereco,docSgl,numeroParcela,dataVencimento,dataPagamento,previsaoPagamento,valor,valorAberto,situcao,avalista");

    public PainelContasReceber() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pTotais = new br.com.martinello.componentesbasicos.paineis.Painel();
        pParcelasCliente = new br.com.martinello.componentesbasicos.paineis.Painel();
        rGeralCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rVencidosCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rDiasMediosCliente = new br.com.martinello.componentesbasicos.Rotulo();
        rIndDebitoCliente = new br.com.martinello.componentesbasicos.Rotulo();
        cvGeralCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvVencidosCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvDiasMedioCliente = new br.com.martinello.componentesbasicos.CampoValor();
        cvIndDebitoCliente = new br.com.martinello.componentesbasicos.CampoValor();
        rVencerCli = new br.com.martinello.componentesbasicos.Rotulo();
        rAbertoCli = new br.com.martinello.componentesbasicos.Rotulo();
        rIndAtrazoCli = new br.com.martinello.componentesbasicos.Rotulo();
        cvAbertoCli = new br.com.martinello.componentesbasicos.CampoValor();
        cvVencerCli = new br.com.martinello.componentesbasicos.CampoValor();
        cvIndAtrazoCli = new br.com.martinello.componentesbasicos.CampoValor();
        pParcelasAvalista = new br.com.martinello.componentesbasicos.paineis.Painel();
        cvVencidosAva = new br.com.martinello.componentesbasicos.CampoValor();
        rVencidosAva = new br.com.martinello.componentesbasicos.Rotulo();
        rGeralAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvGeralAva = new br.com.martinello.componentesbasicos.CampoValor();
        rDiasMediosAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvDiasMedioAva = new br.com.martinello.componentesbasicos.CampoValor();
        rVencerAva = new br.com.martinello.componentesbasicos.Rotulo();
        rAbertoAva = new br.com.martinello.componentesbasicos.Rotulo();
        cvVencerAva = new br.com.martinello.componentesbasicos.CampoValor();
        cvAbertoAva = new br.com.martinello.componentesbasicos.CampoValor();
        pContasReceber = new br.com.martinello.componentesbasicos.paineis.Painel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tpContasReceber = new br.com.martinello.componentesbasicos.TabelaPadrao();

        setLayout(new java.awt.BorderLayout());

        pTotais.setBorder(javax.swing.BorderFactory.createTitledBorder("Totais"));
        pTotais.setPreferredSize(new java.awt.Dimension(1364, 180));
        pTotais.setLayout(new java.awt.BorderLayout());

        pParcelasCliente.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcelas do Cliente"));
        pParcelasCliente.setPreferredSize(new java.awt.Dimension(650, 150));

        rGeralCliente.setText("Geral:");

        rVencidosCliente.setText("Vencidos:");

        rDiasMediosCliente.setText("Dias Médios:");

        rIndDebitoCliente.setText("Ind.Débito:");

        rVencerCli.setText("A Vencer:");

        rAbertoCli.setText("Aberto:");

        rIndAtrazoCli.setText("Ind. Atrazo:");

        javax.swing.GroupLayout pParcelasClienteLayout = new javax.swing.GroupLayout(pParcelasCliente);
        pParcelasCliente.setLayout(pParcelasClienteLayout);
        pParcelasClienteLayout.setHorizontalGroup(
            pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pParcelasClienteLayout.createSequentialGroup()
                        .addComponent(rIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cvIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pParcelasClienteLayout.createSequentialGroup()
                        .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rDiasMediosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvDiasMedioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                                .addComponent(rAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        pParcelasClienteLayout.setVerticalGroup(
            pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvGeralCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvAbertoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencidosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencerCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDiasMediosCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDiasMedioCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvIndAtrazoCli, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvIndDebitoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pTotais.add(pParcelasCliente, java.awt.BorderLayout.WEST);

        pParcelasAvalista.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcelas do Avalista"));
        pParcelasAvalista.setPreferredSize(new java.awt.Dimension(650, 150));

        rVencidosAva.setText("Vencidos:");

        rGeralAva.setText("Geral:");

        rDiasMediosAva.setText("Dias Médios:");

        rVencerAva.setText("A Vencer:");

        rAbertoAva.setText("Aberto:");

        javax.swing.GroupLayout pParcelasAvalistaLayout = new javax.swing.GroupLayout(pParcelasAvalista);
        pParcelasAvalista.setLayout(pParcelasAvalistaLayout);
        pParcelasAvalistaLayout.setHorizontalGroup(
            pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                        .addComponent(rDiasMediosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cvDiasMedioAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                        .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                                .addComponent(rAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(cvAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(118, Short.MAX_VALUE))
        );
        pParcelasAvalistaLayout.setVerticalGroup(
            pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParcelasAvalistaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvGeralAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvAbertoAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencidosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvVencerAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pParcelasAvalistaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rDiasMediosAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvDiasMedioAva, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        pTotais.add(pParcelasAvalista, java.awt.BorderLayout.EAST);

        add(pTotais, java.awt.BorderLayout.SOUTH);

        pContasReceber.setPreferredSize(new java.awt.Dimension(1364, 550));
        pContasReceber.setLayout(new java.awt.BorderLayout());

        tpContasReceber.setModel(otmContasReceber);
        jScrollPane7.setViewportView(tpContasReceber);

        pContasReceber.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        add(pContasReceber, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private br.com.martinello.componentesbasicos.CampoValor cvAbertoAva;
    private br.com.martinello.componentesbasicos.CampoValor cvAbertoCli;
    private br.com.martinello.componentesbasicos.CampoValor cvDiasMedioAva;
    private br.com.martinello.componentesbasicos.CampoValor cvDiasMedioCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvGeralAva;
    private br.com.martinello.componentesbasicos.CampoValor cvGeralCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvIndAtrazoCli;
    private br.com.martinello.componentesbasicos.CampoValor cvIndDebitoCliente;
    private br.com.martinello.componentesbasicos.CampoValor cvVencerAva;
    private br.com.martinello.componentesbasicos.CampoValor cvVencerCli;
    private br.com.martinello.componentesbasicos.CampoValor cvVencidosAva;
    private br.com.martinello.componentesbasicos.CampoValor cvVencidosCliente;
    private javax.swing.JScrollPane jScrollPane7;
    private br.com.martinello.componentesbasicos.paineis.Painel pContasReceber;
    private br.com.martinello.componentesbasicos.paineis.Painel pParcelasAvalista;
    private br.com.martinello.componentesbasicos.paineis.Painel pParcelasCliente;
    private br.com.martinello.componentesbasicos.paineis.Painel pTotais;
    private br.com.martinello.componentesbasicos.Rotulo rAbertoAva;
    private br.com.martinello.componentesbasicos.Rotulo rAbertoCli;
    private br.com.martinello.componentesbasicos.Rotulo rDiasMediosAva;
    private br.com.martinello.componentesbasicos.Rotulo rDiasMediosCliente;
    private br.com.martinello.componentesbasicos.Rotulo rGeralAva;
    private br.com.martinello.componentesbasicos.Rotulo rGeralCliente;
    private br.com.martinello.componentesbasicos.Rotulo rIndAtrazoCli;
    private br.com.martinello.componentesbasicos.Rotulo rIndDebitoCliente;
    private br.com.martinello.componentesbasicos.Rotulo rVencerAva;
    private br.com.martinello.componentesbasicos.Rotulo rVencerCli;
    private br.com.martinello.componentesbasicos.Rotulo rVencidosAva;
    private br.com.martinello.componentesbasicos.Rotulo rVencidosCliente;
    private br.com.martinello.componentesbasicos.TabelaPadrao tpContasReceber;
    // End of variables declaration//GEN-END:variables

    public boolean buscarContasReceber(String sCodFil, int codCli) throws ErroSistemaException {
        resultado = false;
        valorOriginal = 0;
        try {
            contasReceberControl = new ContasReceberControl();

            lContasReceber = new LinkedList<>();

            lContasReceber = contasReceberControl.buscarContasReceber(sCodFil, codCli);
            List<VendasModel> listaVendas = PainelVenda.lVendas;
              for (VendasModel venda : listaVendas) {
                if (venda.getDevolvida().equals("N") && venda.getTipoVenda().equals("P")) {
                    valorOriginal += Double.valueOf(venda.getVlrAprazo());
                } else if (venda.getDevolvida().equals("N") && venda.getTipoVenda().equals("V")) {
                    valorOriginal += Double.valueOf(venda.getVlrAvista());
                }
            }
            
            otmContasReceber.setData(lContasReceber);
            otmContasReceber.fireTableDataChanged();
            if (tpContasReceber.getRowCount() > 0) {
                tpContasReceber.packAll();
                tpContasReceber.addRowSelectionInterval(tpContasReceber.convertRowIndexToView(0), tpContasReceber.convertRowIndexToView(0));
                tpContasReceber.grabFocus();
            }
            totalizadorContasReceber(lContasReceber);
            resultado = true;
        } catch (ErroSistemaException ex) {

        }
        return resultado;
    }

    private void totalizadorContasReceber(List<ContasReceberModel> lContasReceber) {
        tQuitacao = 0;
        tParcial = 0;
        tRenegociacao = 0;
        tDevolucao = 0;
        tTransf = 0;
        tPerdida = 0;
        int dias = 0;
        valorTraz = 0;
        valorCalc = 0;
        valorAberto = 0;
        valorParcela = 0;
        valorVencer = 0;
        Date dataFinal = new Date();
        Calendar calendarData = Calendar.getInstance();
        calendarData.setTime(dataFinal);
        calendarData.add(Calendar.DATE, 0);
        Date sysdate = calendarData.getTime();
        for (ContasReceberModel contaReceber : lContasReceber) {
            String situacao = contaReceber.getSitucao();

            if (situacao.equals("A") | (situacao.equals("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N")))) {
                valorParcela += contaReceber.getValor();
            }
            if (contaReceber.getDataVencimento().before(sysdate)) {
                if (contaReceber.getDataPagamento().equals("31/12/1900")
                        || (situacao.equalsIgnoreCase("A") && (contaReceber.getTipoBaixa().equals("T") | contaReceber.getTipoBaixa().equals("P"))) | contaReceber.getTipoBaixa().equals(" ")) {
                    dias = contaReceber.getDias();
                    valorTraz += contaReceber.getValor();
                } else if (situacao.equalsIgnoreCase("L") && contaReceber.getTipoBaixa().equals("D")) {
                    dias = 0;
                    valorCalc += contaReceber.getValor() * dias;
                } else if (situacao.equalsIgnoreCase("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N"))) {
                    dias = contaReceber.getDias();
                    valorCalc += contaReceber.getValor() * dias;
                }

            } else {
                if (contaReceber.getDataPagamento().after(Utilitarios.getDataZero())) {
                    if (situacao.equals("L") && contaReceber.getTipoBaixa().equals("D")) {
                        dias = 0;
                    } else if (situacao.equals("L") && (contaReceber.getTipoBaixa().equals("Q") | contaReceber.getTipoBaixa().equals("N"))) {
                        valorCalc += contaReceber.getValor() * dias;
                    }
                }
                if (situacao.equals("A")) {
                    valorVencer += contaReceber.getValor();
                }
            }
            if (situacao.equals("A")) {
                valorAberto += contaReceber.getValor();
            }
        }
        cvDiasMedioCliente.setDouble(valorCalc / valorParcela);
        cvIndDebitoCliente.setDouble((valorAberto / valorOriginal) * 100);
        cvIndAtrazoCli.setDouble((valorTraz / valorOriginal) * 100);
        cvAbertoCli.setDouble(valorAberto);
        cvGeralCliente.setDouble(valorOriginal);
        cvVencerCli.setDouble(valorVencer);
        cvVencidosCliente.setDouble(valorTraz);
    }
}
