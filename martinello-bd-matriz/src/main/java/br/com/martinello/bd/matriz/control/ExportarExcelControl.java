/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.bd.matriz.control;

import br.com.martinello.bd.matriz.model.dao.Conexao;
import br.com.martinello.bd.matriz.model.dao.ExportarExcelDAO;
import br.com.martinello.bd.matriz.model.domain.ConsultaNegativacaoModel;
import br.com.martinello.bd.matriz.model.domain.ExtracaoTableModel;
import br.com.martinello.bd.matriz.model.domain.LogExtracaoModel;
import br.com.martinello.bd.matriz.model.domain.ParcelaModel;
import br.com.martinello.bd.matriz.model.domain.RecebimentoApp;
import br.com.martinello.util.Utilitarios;
import br.com.martinello.util.excessoes.ErroSistemaException;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author luiz.almeida
 */
public class ExportarExcelControl {

    private static String statusSpc, statusSpcAval, statusFacmat, statusExtracao;

    private PreparedStatement psStatus, psStatusSpc, psStatusSpcAval, psStatusFacmat;
    private ResultSet rsStatus, rsStatusSpc, rsStatusSpcAval, rsStatusFacmat;
    private List<ExtracaoTableModel> lMovimentoDiario, lNegativacaoesAnalitico;
    private ExportarExcelDAO exportarMovimentoDiario;
    private XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet firstSheet;
    private String PathTillProject, hora, arq;
    private FileOutputStream fileOut;
    private int i, rownum, cellnum;
    private Cell cell;
    private Row row;
    private XSSFDataFormat numberFormat;
    private XSSFDataFormat moedaFormat;
    private XSSFFont headerFont;
    private CellStyle headerStyle, headerStyle2, textStyle, textStyle2, numberStyle, moedaStyle;
    private File file;

    public ExportarExcelControl() {

    }

    public void exportarDadosGerais(List<ExtracaoTableModel> extracaoTableModelList, String diretorio) throws ErroSistemaException {
        carregarStyleExcel();
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            arq = diretorio + "PlanilhaGrid" + hora + ".xlsx";
            fileOut = new FileOutputStream(diretorio + "PlanilhaGrid" + hora + ".xlsx");
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório Planilha Grid Dados Gerais");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    15 //last column  (0-based)
            ));
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Cliente");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Documento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pagamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Contrato");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Lançamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Vencimento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Avalista");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome Avalista");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status Extração");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status Spc");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status Spc Aval");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status Facmat");
            i = 1;
            for (ExtracaoTableModel extracaoTableModel : extracaoTableModelList) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + extracaoTableModel.getIdFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getCodigoCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getCpfCnpj());
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getNome());
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getStatus());
                cell = row.createCell(cellnum++);
                if (extracaoTableModel.getTipoAcao().equals("I")) {
                    cell.setCellValue("N");
                } else {
                    cell.setCellValue("P");
                }
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getNumeroDoc());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(extracaoTableModel.getDataLancamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(extracaoTableModel.getDataVencimento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getCodAvalista());
                cell = row.createCell(cellnum++);
                cell.setCellValue(extracaoTableModel.getNomeAvalista());
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(extracaoTableModel.getValor());
                statusRegistrosExtracao(extracaoTableModel.getIdExtracao());
                statusRegistrosSpc(extracaoTableModel.getIdExtracao());
                statusRegistrosSpcAval(extracaoTableModel.getIdExtracao());
                statusRegistrosFacmat(extracaoTableModel.getIdExtracao());
                cell = row.createCell(cellnum++);
                cell.setCellValue(statusExtracao);
                cell = row.createCell(cellnum++);
                cell.setCellValue(statusSpc);
                cell = row.createCell(cellnum++);
                cell.setCellValue(statusSpcAval);
                cell = row.createCell(cellnum++);
                cell.setCellValue(statusFacmat);
            }
            firstSheet.setColumnWidth(0, 20 * 150);
            firstSheet.setColumnWidth(1, 20 * 150);
            firstSheet.setColumnWidth(2, 20 * 150);
            firstSheet.setColumnWidth(3, 20 * 600);
            firstSheet.setColumnWidth(4, 20 * 200);
            firstSheet.setColumnWidth(5, 20 * 200);
            firstSheet.setColumnWidth(6, 20 * 200);
            firstSheet.setColumnWidth(7, 20 * 200);
            firstSheet.setColumnWidth(8, 20 * 200);
            firstSheet.setColumnWidth(9, 20 * 200);
            firstSheet.setColumnWidth(10, 20 * 500);
            firstSheet.setColumnWidth(11, 20 * 200);
            firstSheet.setColumnWidth(12, 20 * 200);

            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void statusRegistrosExtracao(String idExtrator) {
        statusExtracao = "";
        String selectStatusExtracao = "SELECT DESCRICAO FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + " WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "   AND ID_EXTRATOR = " + idExtrator + "\n"
                + "  AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                    AND TIPO = 'E'\n"
                + "   AND LOG1.STATUS = 'E')";

        try {
            psStatus = Conexao.getConnection().prepareStatement(selectStatusExtracao);

            rsStatus = psStatus.executeQuery();
            if (rsStatus.next()) {
                statusExtracao = rsStatus.getString("DESCRICAO");
            }
            psStatus.close();
            rsStatus.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void statusRegistrosFacmat(String idExtrator) {
        statusFacmat = "";
        String selectStatusFacmat = "SELECT DESCRICAO FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + " WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "   AND ID_EXTRATOR = " + idExtrator + "\n"
                + "   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                    AND TIPO IN ('B', 'C')\n"
                + "   AND LOG1.STATUS = 'E')";
        try {

            psStatusFacmat = Conexao.getConnection().prepareStatement(selectStatusFacmat);
            rsStatusFacmat = psStatusFacmat.executeQuery();
            if (rsStatusFacmat.next()) {
                statusFacmat = rsStatusFacmat.getString("DESCRICAO");
            }
            psStatusFacmat.close();
            rsStatusFacmat.close();

        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void statusRegistrosSpc(String idExtrator) {
        statusSpc = "";
        String selectStatusSpc = "SELECT DESCRICAO FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + "WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "  AND ID_EXTRATOR = " + idExtrator + "\n"
                + "   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                   AND TIPO = 'S'\n"
                + "                    AND LOG1.STATUS = 'E')";

        try {
            psStatusSpc = Conexao.getConnection().prepareStatement(selectStatusSpc);
            rsStatusSpc = psStatusSpc.executeQuery();
            if (rsStatusSpc.next()) {
                statusSpc = rsStatusSpc.getString("DESCRICAO");
            }

            psStatusSpc.close();
            rsStatusSpc.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void statusRegistrosSpcAval(String idExtrator) {
        statusSpcAval = "";
        String selectStatusSpcAval = "SELECT DESCRICAO FROM LOG_EXTRATOR, RETORNOS_INTEGRACAO\n"
                + "WHERE RETORNOS_INTEGRACAO.ID_RETORNO = LOG_EXTRATOR.CODIGO_RETORNO\n"
                + "  AND ID_EXTRATOR = " + idExtrator + "\n"
                + "   AND ID_LOG = (SELECT MAX(ID_LOG) FROM LOG_EXTRATOR LOG1\n"
                + "                  WHERE LOG1.ID_EXTRATOR = LOG_EXTRATOR.ID_EXTRATOR\n"
                + "                   AND TIPO IN ('A', 'S')\n"
                + "   AND LOG1.STATUS = 'E')";

        try {
            psStatusSpcAval = Conexao.getConnection().prepareStatement(selectStatusSpcAval);
            rsStatusSpcAval = psStatusSpcAval.executeQuery();
            if (rsStatusSpcAval.next()) {
                statusSpcAval = rsStatusSpcAval.getString("DESCRICAO");
            }

            psStatusSpcAval.close();
            rsStatusSpcAval.close();
        } catch (ErroSistemaException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void exportarMovimentoDiario(String diretorio) throws ErroSistemaException {
        lMovimentoDiario = new ArrayList<>();
        exportarMovimentoDiario = new ExportarExcelDAO();
        lMovimentoDiario = exportarMovimentoDiario.MovimentoDiario();
        carregarStyleExcel();
        PathTillProject = System.getProperty("user.home");
        fileOut = null;
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            //   fileOut = new FileOutputStream(PathTillProject + diretorio + "Movimento-Diario" + hora + ".xlsx");
            fileOut = new FileOutputStream(diretorio + "Movimento-Diario" + hora + ".xlsx");
            arq = diretorio + "Movimento-Diario" + hora + ".xlsx";
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório de Movimento Diario Negativações");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    11 //last column  (0-based)
            ));
            // Configurando Header
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Cliente");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Documento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pagamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Contrato");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Lançamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Vencimento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome Avalista");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("DataPag");
            i = 1;
            for (ExtracaoTableModel movDiario : lMovimentoDiario) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + movDiario.getIdFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getCodigoCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getCpfCnpj());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNome());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getStatus());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getPago());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNumeroDoc());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataLancamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataVencimento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(movDiario.getValor());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNomeAvalista());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataPagamento(), "dd/MM/yyyy"));
            }
            firstSheet.setColumnWidth(0, 20 * 150);
            firstSheet.setColumnWidth(1, 20 * 150);
            firstSheet.setColumnWidth(2, 20 * 200);
            firstSheet.setColumnWidth(3, 20 * 600);
            firstSheet.setColumnWidth(4, 20 * 150);
            firstSheet.setColumnWidth(5, 20 * 150);
            firstSheet.setColumnWidth(6, 20 * 200);
            firstSheet.setColumnWidth(7, 20 * 200);
            firstSheet.setColumnWidth(8, 20 * 200);
            firstSheet.setColumnWidth(9, 20 * 200);
            firstSheet.setColumnWidth(10, 20 * 500);
            firstSheet.setColumnWidth(11, 20 * 200);
            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void exportarLogExtracao(List<LogExtracaoModel> logExtracaoModelList, String diretorio) throws ErroSistemaException {
        carregarStyleExcel();
        PathTillProject = System.getProperty("user.home");
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            fileOut = new FileOutputStream(diretorio + "Log_Extracao" + hora + ".xlsx");
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(i);
            row.createCell(0).setCellValue("Id Extracao");
            row.createCell(1).setCellValue("Id Extrator");
            row.createCell(2).setCellValue("Filial");
            row.createCell(3).setCellValue("Cliente");
            row.createCell(4).setCellValue("Contrato");
            row.createCell(5).setCellValue("Vencimento");
            row.createCell(6).setCellValue("Status");
            row.createCell(7).setCellValue("Mensagem Status");
            row.createCell(8).setCellValue("Data Extração");

            for (LogExtracaoModel logExtracaoModel : logExtracaoModelList) {
                i++;
                Date dataExtracao;
                dataExtracao = Utilitarios.stringParaData(logExtracaoModel.getDataExtracao().substring(0, 10), "yyyy-MM-dd");

                row = firstSheet.createRow(i);
                row.createCell(0).setCellValue(logExtracaoModel.getIdExtracao());
                row.createCell(1).setCellValue(logExtracaoModel.getIdExtrator());
                row.createCell(2).setCellValue(logExtracaoModel.getFilial());
                row.createCell(3).setCellValue(logExtracaoModel.getCliente());
                row.createCell(4).setCellValue(logExtracaoModel.getContrato());
                row.createCell(5).setCellValue(logExtracaoModel.getVencimento());
                row.createCell(6).setCellValue(logExtracaoModel.getStatus());
                row.createCell(7).setCellValue(logExtracaoModel.getMenssagem());
                row.createCell(8).setCellValue(Utilitarios.converteDataString(dataExtracao, "dd/MM/yyyy"));

            }
            workbook.write(fileOut);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void expNegativacoesAnalitico(List<ExtracaoTableModel> data, String diretorio) throws ErroSistemaException {
        carregarStyleExcel();
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            //   fileOut = new FileOutputStream(PathTillProject + diretorio + "Movimento-Diario" + hora + ".xlsx");
            arq = diretorio + "Consulta_Negativacoes_Cliente-" + hora + ".xlsx";
            fileOut = new FileOutputStream(diretorio + "Consulta_Negativacoes_Cliente-" + hora + ".xlsx");
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório de Consulta Negativações Analítico");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    13 //last column  (0-based)
            ));
// Configurando Header
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Cliente");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Documento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Status");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Pagamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Contrato");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Lançamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Vencimento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Negativação");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Baixa");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome Avalista");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("DataPag");
            i = 1;
            for (ExtracaoTableModel movDiario : data) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + movDiario.getIdFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getCodigoCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getCpfCnpj());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNome());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getStatus());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getPago());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNumeroDoc());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataLancamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataVencimento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataNegativada(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataBaixa(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(movDiario.getValor());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getNomeAvalista());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(movDiario.getDataPagamento(), "dd/MM/yyyy"));
            }
            firstSheet.setColumnWidth(1, 20 * 200);
            firstSheet.setColumnWidth(2, 20 * 200);
            firstSheet.setColumnWidth(3, 20 * 800);
            firstSheet.setColumnWidth(4, 20 * 200);
            firstSheet.setColumnWidth(5, 20 * 200);
            firstSheet.setColumnWidth(6, 20 * 200);
            firstSheet.setColumnWidth(7, 20 * 200);
            firstSheet.setColumnWidth(8, 20 * 200);
            firstSheet.setColumnWidth(9, 20 * 200);
            firstSheet.setColumnWidth(10, 20 * 200);
            firstSheet.setColumnWidth(11, 20 * 200);
            firstSheet.setColumnWidth(12, 20 * 500);

            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }

    public void expNegativacoesSint(List<ConsultaNegativacaoModel> data, String diretorio, String venc, String ext, String tipo) throws ErroSistemaException {
        PathTillProject = System.getProperty("user.home");
        carregarStyleExcel();
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            //   fileOut = new FileOutputStream(PathTillProject + diretorio + "Movimento-Diario" + hora + ".xlsx");
            arq = diretorio + "Consulta_Negativacoes_Cliente-" + hora + ".xlsx";
            fileOut = new FileOutputStream(diretorio + "Consulta_Negativacoes_Cliente-" + hora + ".xlsx");
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Relatório Geral de Consulta Negativações");
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    5 //last column  (0-based)
            ));
            row = firstSheet.createRow(1);
            cell = row.createCell(0);
            cell.setCellStyle(textStyle2);
            cell.setCellValue("Vencimento: " + venc);
            firstSheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    0, //first column (0-based)
                    1 //last column  (0-based)
            ));
            cell = row.createCell(2);
            cell.setCellStyle(textStyle2);
            cell.setCellValue("Extração: " + ext);
            firstSheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    2, //first column (0-based)
                    3 //last column  (0-based)
            ));
            cell = row.createCell(4);
            cell.setCellValue("Tipo: " + tipo);
            cell.setCellStyle(textStyle2);
            firstSheet.addMergedRegion(new CellRangeAddress(
                    1, //first row (0-based)
                    1, //last row  (0-based)
                    4, //first column (0-based)
                    5 //last column  (0-based)
            ));
            // Configurando Header
            cellnum = 0;
            row = firstSheet.createRow(2);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Id_Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Quantidade");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Qtd. Clientes");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Média Cont/Cli");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            i = 2;
            for (ConsultaNegativacaoModel movDiario : data) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + movDiario.getId_Filial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getQuantidade());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getQtdCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(movDiario.getMediaClienteParcela().toString());
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(movDiario.getValor());

            }
            firstSheet.setColumnWidth(1, 20 * 800);
            firstSheet.setColumnWidth(2, 20 * 200);
            firstSheet.setColumnWidth(3, 20 * 300);
            firstSheet.setColumnWidth(4, 20 * 300);
            firstSheet.setColumnWidth(5, 20 * 500);
            firstSheet.setColumnWidth(6, 20 * 500);

            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }

        }
    }

    public void carregarStyleExcel() {
        workbook = new XSSFWorkbook();
        firstSheet = workbook.createSheet("Aba1");
        /* pegar o diretório do usuário e criar um arquivo com o determinado nome */
        PathTillProject = System.getProperty("user.home");
        fileOut = null;
        firstSheet.setDefaultColumnWidth(15);
        firstSheet.setDefaultRowHeight((short) 300);
        rownum = 0;
        cellnum = 0;
        i = 0;

        //Configurando estilos de células (Cores, alinhamento, formatação, etc..)
        numberFormat = workbook.createDataFormat();
        moedaFormat = workbook.createDataFormat();
        headerFont = workbook.createFont();
        headerFont.setBoldweight(headerFont.BOLDWEIGHT_BOLD);
        headerFont.setFontHeightInPoints((short) 14);
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headerStyle2 = workbook.createCellStyle();
        headerStyle2.setFont(headerFont);
        headerStyle2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle2.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle2.setVerticalAlignment(CellStyle.ALIGN_CENTER);

        textStyle = workbook.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        textStyle2 = workbook.createCellStyle();
        textStyle2.setFillForegroundColor(IndexedColors.BLUE_GREY.getIndex());
        textStyle2.setAlignment(CellStyle.ALIGN_LEFT);
        textStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        numberStyle = workbook.createCellStyle();
        numberStyle.setDataFormat(numberFormat.getFormat("#,##0.00"));
        numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        moedaStyle = workbook.createCellStyle();
        moedaStyle.setDataFormat(moedaFormat.getFormat("R$#,##0.00;[Red]R$#,##0.00"));
        moedaStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

    }

    public void exportarExtrato(List<ParcelaModel> data, String Cliente, String diretorio) throws ErroSistemaException {
        carregarStyleExcel();
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
        try {
            arq = diretorio + "Extrato Cliente" + hora + ".xlsx";
            fileOut = new FileOutputStream(diretorio + "Extrato Cliente" + hora + ".xlsx");
            /* Definir o Cabeçalho da Planilha */
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Extrato Cliente:  " + Cliente);
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    11 //last column  (0-based)
            ));
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Cliente");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Documento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Contrato");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Lançamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Vencimento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Negativacao");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Baixa Negativacao");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Pagamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Capital Pago");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Situação Parcela");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Avalista");

            i = 1;
            for (ParcelaModel parcelaModel : data) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + parcelaModel.getPontoFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getCodCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getCpfCnpj());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getNomeDoDevedor());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getNumeroDoContrato());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataLancamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataVencimento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataNegativacao(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataBaixaNegativacao(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(parcelaModel.getValorParcela());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataPagamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(parcelaModel.getCapitalPago());
                 cell = row.createCell(cellnum++);
                 cell.setCellValue(parcelaModel.getSituacaoParcela());

                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getAvalista());

            }
            firstSheet.setColumnWidth(0, 20 * 150);
            firstSheet.setColumnWidth(1, 20 * 150);
            firstSheet.setColumnWidth(2, 20 * 150);
            firstSheet.setColumnWidth(3, 20 * 600);
            firstSheet.setColumnWidth(4, 20 * 200);
            firstSheet.setColumnWidth(5, 20 * 200);
            firstSheet.setColumnWidth(6, 20 * 200);
            firstSheet.setColumnWidth(7, 20 * 200);
            firstSheet.setColumnWidth(8, 20 * 200);
            firstSheet.setColumnWidth(9, 20 * 200);
             firstSheet.setColumnWidth(10, 20 * 200);
            firstSheet.setColumnWidth(11, 20 * 200);

            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        }
    }
    public void exportarRecebimento(List<RecebimentoApp> data, String Cliente, String diretorio) throws ErroSistemaException {
        carregarStyleExcel();
        hora = Utilitarios.dataHoraAtual().replaceAll("[/]", "-").replaceAll("[:]", "-").replaceAll("[.]", "-");
        System.out.println("Hora " + hora);
          /* Definir o Cabeçalho da Planilha */
      /*    try {
            arq = diretorio + "Extrato Cliente" + hora + ".xlsx";
            fileOut = new FileOutputStream(diretorio + "Extrato Cliente" + hora + ".xlsx");
          
            row = firstSheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(headerStyle2);
            cell.setCellValue("Extrato Cliente:  " + Cliente);
            firstSheet.addMergedRegion(new CellRangeAddress(
                    0, //first row (0-based)
                    0, //last row  (0-based)
                    0, //first column (0-based)
                    11 //last column  (0-based)
            ));
            row = firstSheet.createRow(1);
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Filial");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Cliente");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Documento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Nome");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Contrato");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Lançamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Vencimento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Negativacao");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Baixa Negativacao");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Valor");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Data Pagamento");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Capital Pago");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Situação Parcela");
            cell = row.createCell(cellnum++);
            cell.setCellStyle(headerStyle);
            cell.setCellValue("Cod.Avalista");

            i = 1;
          for (ParcelaModel parcelaModel : data) {
                i++;
                cellnum = 0;
                row = firstSheet.createRow(i);
                cell = row.createCell(cellnum++);
                cell.setCellValue("F" + parcelaModel.getPontoFilial());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getCodCliente());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getCpfCnpj());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getNomeDoDevedor());
                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getNumeroDoContrato());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataLancamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataVencimento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataNegativacao(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataBaixaNegativacao(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(parcelaModel.getValorParcela());
                cell = row.createCell(cellnum++);
                cell.setCellValue(Utilitarios.converteDataString(parcelaModel.getDataPagamento(), "dd/MM/yyyy"));
                cell = row.createCell(cellnum++);
                cell.setCellStyle(moedaStyle);
                cell.setCellValue(parcelaModel.getCapitalPago());
                 cell = row.createCell(cellnum++);
                 cell.setCellValue(parcelaModel.getSituacaoParcela());

                cell = row.createCell(cellnum++);
                cell.setCellValue(parcelaModel.getAvalista());

            }
            firstSheet.setColumnWidth(0, 20 * 150);
            firstSheet.setColumnWidth(1, 20 * 150);
            firstSheet.setColumnWidth(2, 20 * 150);
            firstSheet.setColumnWidth(3, 20 * 600);
            firstSheet.setColumnWidth(4, 20 * 200);
            firstSheet.setColumnWidth(5, 20 * 200);
            firstSheet.setColumnWidth(6, 20 * 200);
            firstSheet.setColumnWidth(7, 20 * 200);
            firstSheet.setColumnWidth(8, 20 * 200);
            firstSheet.setColumnWidth(9, 20 * 200);
             firstSheet.setColumnWidth(10, 20 * 200);
            firstSheet.setColumnWidth(11, 20 * 200);

            workbook.write(fileOut);
            file = new File(arq);
            Desktop.getDesktop().open(file);
            file.deleteOnExit();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErroSistemaException(e.getMessage(), e.getCause());
        } finally {
            try {
                fileOut.flush();
                fileOut.close();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ErroSistemaException(e.getMessage(), e.getCause());
            }
        } */
    }
}
