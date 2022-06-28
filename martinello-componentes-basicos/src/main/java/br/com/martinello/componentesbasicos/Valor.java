/*
 * Decompiled with CFR 0_123.
 */
package br.com.martinello.componentesbasicos;

import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;

public class Valor implements Comparable {

    protected int maximoDigitosParteInteira = ConstantesGlobais.TAMANHO_VALOR - ConstantesGlobais.TAMANHO_VALOR_PARTE_DECIMAL;
    //protected int maximoDigitosParteInteira = 13 - 2;
    protected String msgErroEstourodigitos = "O valor m\u00e1ximo para o tipo do campo foi excedido.";
    protected byte severidadeValidacaoMaximoDigitos = 3;
    protected Long conteudo = new Long(0);
    protected int casasDecimais = ConstantesGlobais.TAMANHO_VALOR_PARTE_DECIMAL;
    //protected int casasDecimais = 2;
    protected boolean porcentagem = false;
    private DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(ConstantesGlobais.LOCALIDADE);
    //private DecimalFormat df = (DecimalFormat) DecimalFormat.getNumberInstance(new Locale("pt", "BR"));
    private boolean campoCalculado = false;
    public static int ARREDONDA = 1;
    public static int TRUNCA = 0;
    private int tratamentocasasDecimais = TRUNCA;
    private int pisoArredondamento = 5;
    /*Aquii**/
    protected boolean inicializouUltimoConteudoValido = false;
    private String ultimoConteudoValido = "";
    private boolean observadoresAtivos;
    private boolean validadoresAtivos;
    protected String conteudoAntigo = "";
    private PropertyChangeSupport observadores;

    public Valor() {
        //this.addValidador(new ValidadorMaximoDigitosInteiros(this.severidadeValidacaoMaximoDigitos));
        this.observadores = new PropertyChangeSupport(this);
        this.observadoresAtivos = true;
        this.validadoresAtivos = true;
        //this.atributoPersistente = true;
    }

    public Valor(Long pVal) {
        this(pVal.toString());
    }

    public Valor(String pVal) {
        String parteInteira = "";
        String parteDecimal = "";
        if (pVal.lastIndexOf(44) != -1) {
            parteDecimal = pVal.substring(pVal.lastIndexOf(44) + 1, pVal.length());
        } else if (pVal.lastIndexOf(46) != -1 && pVal.indexOf(46) == pVal.lastIndexOf(46)) {
            pVal = pVal.replaceAll("[.]", ",");
            parteDecimal = pVal.substring(pVal.lastIndexOf(44) + 1, pVal.length());
        }
        if (parteDecimal.length() > 0) {
            this.setCasasDecimais(parteDecimal.length());
            this.setConteudo(pVal);
        } else {
            this.setCasasDecimais(0);
            this.setConteudo(pVal);
        }
        //this.addValidador(new ValidadorMaximoDigitosInteiros(this.severidadeValidacaoMaximoDigitos));
    }

    public boolean isInicializouUltimoConteudoValido() {
        return inicializouUltimoConteudoValido;
    }

    public void setInicializouUltimoConteudoValido(boolean inicializouUltimoConteudoValido) {
        this.inicializouUltimoConteudoValido = inicializouUltimoConteudoValido;
    }

    public String getUltimoConteudoValido() {
        return ultimoConteudoValido;
    }

    public void setUltimoConteudoValido(String ultimoConteudoValido) {
        this.ultimoConteudoValido = ultimoConteudoValido;
    }

    public boolean isObservadoresAtivos() {
        return observadoresAtivos;
    }

    public void setObservadoresAtivos(boolean observadoresAtivos) {
        this.observadoresAtivos = observadoresAtivos;
    }

    public boolean isValidadoresAtivos() {
        return validadoresAtivos;
    }

    public void setValidadoresAtivos(boolean validadoresAtivos) {
        this.validadoresAtivos = validadoresAtivos;
    }

    public PropertyChangeSupport getObservadores() {
        return observadores;
    }

    public void setObservadores(PropertyChangeSupport observadores) {
        this.observadores = observadores;
    }

    public void converte(Valor pVal) {
        this.converteQtdCasasDecimais(pVal.getCasasDecimais());
        this.setMaximoDigitosParteInteira(pVal.getMaximoDigitosParteInteira());
        this.setTratamentocasasDecimais(pVal.getTratamentocasasDecimais());
    }

    public String getParteInteira() {
        long conteudoAtual = this.conteudo;
        long fatorCasaDecimal = 1;
        for (int i = 0; i < this.casasDecimais; ++i) {
            fatorCasaDecimal *= 10;
        }

//        long valorInteiro = Math.abs(conteudoAtual / fatorCasaDecimal);
//
////        if (conteudoAtual < 0) {
////            valorInteiro = valorInteiro * -1;
////        }
////
////        System.out.println("Math.abs: " + valorInteiro);

        return String.valueOf(Math.abs(conteudoAtual / fatorCasaDecimal));
    }

    public Long getParteInteiraAsLong() {
        return new Long(String.valueOf(this.conteudo).substring(0, 1).equals("-") ? "-" + this.getParteInteira() : this.getParteInteira());
    }

    public String getParteDecimal() {
        if (this.casasDecimais == 0) {
            return "";
        }
        long conteudoAtual = this.conteudo;
        long fatorCasaDecimal = 1;
        String padraoCasasDecimais = "";
        for (int i = 0; i < this.casasDecimais; ++i) {
            fatorCasaDecimal *= 10;
            padraoCasasDecimais = padraoCasasDecimais + "0";
        }
        this.df.applyLocalizedPattern(padraoCasasDecimais);
        return this.df.format(Math.abs(conteudoAtual % fatorCasaDecimal));
    }

    public String getConteudoFormatado() {
        this.df.applyLocalizedPattern("###.###.##0");
        String negativo = "";
        String parteInteiraFormatada = this.df.format(new Long(this.getParteInteira()));
        String parteDecimalFormatada = this.getParteDecimal();
        if (this.conteudo < 0) {
            negativo = negativo + "-";
        }
        if (parteDecimalFormatada.trim().length() == 0) {
            return negativo + parteInteiraFormatada;
        }
        return negativo + parteInteiraFormatada + "," + parteDecimalFormatada;
    }

    public int getCasasDecimais() {
        return this.casasDecimais;
    }

    public void setCasasDecimais(int casasDecimais) {
        this.casasDecimais = casasDecimais;
    }

    public Long getConteudo() {
        return this.conteudo;
    }

    public void setConteudo(Long pConteudo) {
        String antigo = this.asString();
        //this.clearRetornosValidacoes();
        this.conteudo = pConteudo;
        if (!this.inicializouUltimoConteudoValido) {
            this.inicializouUltimoConteudoValido = true;
            this.setUltimoConteudoValido(this.asString());
        }
        if (this.isVazio()) {
            this.setUltimoConteudoValido("");
        }
        this.disparaObservadores(antigo);
    }

    public void setConteudo(Double valor) {
        if (valor != null) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            setConteudo(decimalFormat.format(valor));
        } else {
            setConteudo("0,00");
        }

    }

    protected void disparaObservadores(Object aConteudoAntigo) {
        if (aConteudoAntigo != null && !aConteudoAntigo.equals("-")) {
            this.setConteudoAntigo((String) aConteudoAntigo);
        }
        if (this.isObservadoresAtivos()) {
            getObservadores().firePropertyChange("Valor", aConteudoAntigo, this.asString());
        }
    }

    public void setConteudoAntigo(String valorAntigo) {
        if (!valorAntigo.equals(this.asString())) {
            this.conteudoAntigo = valorAntigo;
        }
    }

    public Long getConteudoAbsoluto() {
        return new Long(Math.abs(this.conteudo));
    }

    public boolean isPorcentagem() {
        return this.porcentagem;
    }

    public void setPorcentagem(boolean porcentagem) {
        this.porcentagem = porcentagem;
    }

    public void setConteudo(String pConteudo) {
        String parteDecimal = "";
        if (pConteudo.lastIndexOf(44) == -1) {
            for (int i = 0; i < this.getCasasDecimais(); ++i) {
                parteDecimal = parteDecimal + "0";
            }
        } else {
            String parteDecimalPassada = pConteudo.substring(pConteudo.lastIndexOf(44) + 1, pConteudo.length());
            for (int i = parteDecimalPassada.length(); i < this.getCasasDecimais(); ++i) {
                parteDecimal = parteDecimal + "0";
            }
        }
        String conteudoLimpo = pConteudo.replaceAll("[.,]", "");
        Long valConteudo = null;
        try {
            valConteudo = new Long(conteudoLimpo + parteDecimal);
        } catch (NumberFormatException e) {
            valConteudo = new Long(0);
        }
        this.setConteudo(valConteudo);
    }

    public void setConteudo(Valor pConteudo) {
        Valor copia = new Valor(pConteudo.asString());
        copia.converte(this);
        this.setConteudo(copia.getConteudo());
    }

    public void clear() {
        this.setConteudo(new Long(0));
    }

    public boolean isVazio() {
        return this.conteudo == 0;
    }

    public void converteQtdCasasDecimais(int pCasasDecimais) {
        if (this.getCasasDecimais() == pCasasDecimais) {
            return;
        }
        long conteudoAtual = this.conteudo;
        long fatorCasaDecimal = 1;
        int diferenca = Math.abs(this.getCasasDecimais() - pCasasDecimais);
        for (int i = 0; i < diferenca; ++i) {
            fatorCasaDecimal *= 10;
        }
        conteudoAtual = this.getCasasDecimais() < pCasasDecimais ? (conteudoAtual *= fatorCasaDecimal) : (conteudoAtual /= fatorCasaDecimal);
        this.setCasasDecimais(pCasasDecimais);
        this.setConteudo(new Long(conteudoAtual));
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Valor)) {
            return -1;
        }
        int retorno = 0;
        Valor parametro = (Valor) o;
        int qtdCasasDecimaisParametroAnterior = parametro.getCasasDecimais();
        int qtdCasasDecimaisAnterior = this.getCasasDecimais();
        if (parametro.getCasasDecimais() > this.getCasasDecimais()) {
            this.converteQtdCasasDecimais(parametro.getCasasDecimais());
        } else {
            parametro.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        if (this.conteudo > parametro.getConteudo()) {
            retorno = 1;
        } else if (this.conteudo < parametro.getConteudo()) {
            retorno = -1;
        }
        this.converteQtdCasasDecimais(qtdCasasDecimaisAnterior);
        parametro.converteQtdCasasDecimais(qtdCasasDecimaisParametroAnterior);
        return retorno;
    }

    public void arredonda(int pQtdCasas, int piso) {
        String parteInteira = this.getParteInteira();
        String parteDecimal = "";
        if (this.getParteDecimal().length() > pQtdCasas) {
            int fatorTruncamento;
            if (pQtdCasas > 0) {
                parteDecimal = this.getParteDecimal().substring(0, pQtdCasas - 1);
            }
            if ((fatorTruncamento = Integer.parseInt(this.getParteDecimal().substring(pQtdCasas, pQtdCasas + 1))) >= piso && fatorTruncamento <= 9) {
                Valor acrescimo = new Valor();
                acrescimo.setCasasDecimais(pQtdCasas);
                acrescimo.setConteudo(new Long(1));
                this.setConteudo(this.soma(acrescimo));
            }
            this.converteQtdCasasDecimais(pQtdCasas);
        }
    }

    private Valor soma(Valor parametro) {
        Valor retorno = new Valor();
        int qtdCasasDecimaisParametroAnterior = parametro.getCasasDecimais();
        int qtdCasasDecimaisAnterior = this.getCasasDecimais();
        if (parametro.getCasasDecimais() > this.getCasasDecimais()) {
            this.converteQtdCasasDecimais(parametro.getCasasDecimais());
        } else {
            parametro.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        retorno.setCasasDecimais(this.getCasasDecimais());
        long resultado = this.getConteudo() + parametro.getConteudo();
        retorno.setConteudo(new Long(resultado));
        this.converteQtdCasasDecimais(qtdCasasDecimaisAnterior);
        parametro.converteQtdCasasDecimais(qtdCasasDecimaisParametroAnterior);
        if (this.getTratamentocasasDecimais() == ARREDONDA) {
            retorno.arredonda(this.getCasasDecimais(), this.getPisoArredondamento());
        }
        return retorno;
    }

    private Valor soma(Long val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.soma(param);
    }

    private Valor soma(String val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.soma(param);
    }

    private Valor subtrai(Valor parametro) {
        Valor retorno = new Valor();
        int qtdCasasDecimaisParametroAnterior = parametro.getCasasDecimais();
        int qtdCasasDecimaisAnterior = this.getCasasDecimais();
        if (parametro.getCasasDecimais() > this.getCasasDecimais()) {
            this.converteQtdCasasDecimais(parametro.getCasasDecimais());
        } else {
            parametro.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        retorno.setCasasDecimais(this.getCasasDecimais());
        long resultado = this.getConteudo() - parametro.getConteudo();
        retorno.setConteudo(new Long(resultado));
        this.converteQtdCasasDecimais(qtdCasasDecimaisAnterior);
        parametro.converteQtdCasasDecimais(qtdCasasDecimaisParametroAnterior);
        if (this.getTratamentocasasDecimais() == ARREDONDA) {
            retorno.arredonda(this.getCasasDecimais(), this.getPisoArredondamento());
        }
        return retorno;
    }

    private Valor subtrai(Long val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.subtrai(param);
    }

    private Valor subtrai(String val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.subtrai(param);
    }

    private Valor multiplica(Valor parametro) {
        Valor retorno = new Valor();
        long resultado = this.getConteudo() * parametro.getConteudo();
        retorno.setCasasDecimais(this.getCasasDecimais() + parametro.getCasasDecimais());
        retorno.setConteudo(new Long(resultado));
        if (this.getTratamentocasasDecimais() == ARREDONDA) {
            retorno.arredonda(this.getCasasDecimais(), this.getPisoArredondamento());
        } else {
            retorno.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        return retorno;
    }

    private Valor multiplica(Long val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.multiplica(param);
    }

    private Valor divide(Valor parametro) {
        Valor retorno = new Valor();
        int qtdCasasDecimaisParametroAnterior = parametro.getCasasDecimais();
        int qtdCasasDecimaisAnterior = this.getCasasDecimais();
        if (parametro.getCasasDecimais() > this.getCasasDecimais()) {
            this.converteQtdCasasDecimais(parametro.getCasasDecimais());
        } else {
            parametro.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        long resultado = 0;
        if (parametro.getConteudo() != 0) {
            long dividendo;
            long divisor = this.getConteudo();
            if (divisor < (dividendo = parametro.getConteudo().longValue())) {
                retorno.converteQtdCasasDecimais(this.getCasasDecimais() + 2);
                resultado = this.efetuaDivisaoComDividendoMaior(divisor, dividendo, this.getCasasDecimais() + 2);
            } else {
                retorno.converteQtdCasasDecimais(this.getCasasDecimais());
                resultado = this.efetuaDivisaoComDividendoMenor(divisor, dividendo);
            }
        }
        retorno.setConteudo(new Long(resultado));
        this.converteQtdCasasDecimais(qtdCasasDecimaisAnterior);
        parametro.converteQtdCasasDecimais(qtdCasasDecimaisParametroAnterior);
        if (this.getTratamentocasasDecimais() == ARREDONDA) {
            retorno.arredonda(this.getCasasDecimais(), this.getPisoArredondamento());
        } else {
            retorno.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        return retorno;
    }

    protected long efetuaDivisaoComDividendoMenor(long divisor, long dividendo) {
        long fatorCasaDecimal = 1;
        for (int i = 0; i < this.getCasasDecimais(); ++i) {
            fatorCasaDecimal *= 10;
        }
        return (divisor *= fatorCasaDecimal) / dividendo;
    }

    protected long efetuaDivisaoComDividendoMaior(long divisor, long dividendo, int qtdCasas) {
        long retorno = 0;
        long novoDivisor = divisor;
        int fatorResultado = qtdCasas - 1;
        for (int i = 1; i <= qtdCasas; ++i) {
            novoDivisor = divisor * this.elevaPotencia(10, i);
            retorno = novoDivisor / dividendo * this.elevaPotencia(10, fatorResultado);
            novoDivisor %= dividendo;
            --fatorResultado;
        }
        return retorno;
    }

    protected long elevaPotencia(long val, int potencia) {
        long retorno = 1;
        for (int i = 1; i <= potencia; ++i) {
            retorno *= val;
        }
        return retorno;
    }

    private Valor divide(Long val) {
        Valor param = new Valor();
        param.converte(this);
        param.setConteudo(val);
        return this.divide(param);
    }

    private Valor resto(Valor parametro) {
        Valor retorno = new Valor();
        int qtdCasasDecimaisParametroAnterior = parametro.getCasasDecimais();
        int qtdCasasDecimaisAnterior = this.getCasasDecimais();
        if (parametro.getCasasDecimais() > this.getCasasDecimais()) {
            this.converteQtdCasasDecimais(parametro.getCasasDecimais());
        } else {
            parametro.converteQtdCasasDecimais(this.getCasasDecimais());
        }
        retorno.setCasasDecimais(this.getCasasDecimais());
        long resultado = 0;
        if (parametro.getConteudo() != 0) {
            resultado = this.getConteudo();
            resultado %= parametro.getConteudo().longValue();
        }
        retorno.setConteudo(new Long(resultado));
        this.converteQtdCasasDecimais(qtdCasasDecimaisAnterior);
        parametro.converteQtdCasasDecimais(qtdCasasDecimaisParametroAnterior);
        if (this.getTratamentocasasDecimais() == ARREDONDA) {
            retorno.arredonda(this.getCasasDecimais(), this.getPisoArredondamento());
        }
        return retorno;
    }

    private boolean isMaior(Valor pObj) {
        return this.compareTo(pObj) == 1;
    }

    private boolean isMaiorOuIgual(Valor pObj) {
        return this.compareTo(pObj) == 1 || this.compareTo(pObj) == 0;
    }

    private boolean isMenor(Valor pObj) {
        return this.compareTo(pObj) == -1;
    }

    public String asString() {
        String negativo = "";
        if (this.conteudo < 0) {
            negativo = negativo + "-";
        }
        String parteInteira = this.getParteInteira();
        String parteDecimal = this.getParteDecimal();
        if (!parteDecimal.trim().equals("")) {
            parteDecimal = "," + parteDecimal;
        }
        return negativo + parteInteira + parteDecimal;
    }

    public boolean equals(Object obj) {
        return this.compareTo(obj) == 0;
    }

    private boolean isMenorOuIgual(Valor pObj) {
        return this.compareTo(pObj) == -1 || this.compareTo(pObj) == 0;
    }

    public Valor operacao(char pOperacao, String pVal) {
        Valor m = new Valor(pVal);
        m.converte(this);
        return this.operacao(pOperacao, m);
    }

    public Valor operacao(char pOperacao, Valor pVal) {
        Valor retorno = null;
        pVal.setObservadoresAtivos(false);
        pVal.setValidadoresAtivos(false);
        switch (pOperacao) {
            case '+': {
                retorno = this.soma(pVal);
                break;
            }
            case '-': {
                retorno = this.subtrai(pVal);
                break;
            }
            case '*': {
                retorno = this.multiplica(pVal);
                break;
            }
            case '/': {
                retorno = this.divide(pVal);
                break;
            }
            case '%': {
                retorno = this.resto(pVal);
                break;
            }
            default: {
                throw new IllegalArgumentException("Sinal de Opera\u00e7\u00e3o" + pOperacao + " inv\u00e1lido!!!");
            }
        }
        pVal.setObservadoresAtivos(true);
        pVal.setValidadoresAtivos(true);
        return retorno;
    }

    public boolean comparacao(String pComp, Valor pVal) {
        boolean retorno = false;
        this.setObservadoresAtivos(false);
        this.setValidadoresAtivos(false);
        pVal.setObservadoresAtivos(false);
        pVal.setValidadoresAtivos(false);
        if (pComp.equals(">")) {
            retorno = this.isMaior(pVal);
        } else if (pComp.equals(">=")) {
            retorno = this.isMaiorOuIgual(pVal);
        } else if (pComp.equals("<")) {
            retorno = this.isMenor(pVal);
        } else if (pComp.equals("<=")) {
            retorno = this.isMenorOuIgual(pVal);
        } else if (pComp.equals("=")) {
            retorno = this.equals(pVal);
        } else if (pComp.equals("!=") || pComp.equals("<>")) {
            retorno = !this.equals(pVal);
        }
        this.setObservadoresAtivos(true);
        this.setValidadoresAtivos(true);
        this.setValidadoresAtivos(true);
        pVal.setObservadoresAtivos(true);
        pVal.setValidadoresAtivos(true);
        return retorno;
    }

    public boolean comparacao(String pComp, String pVal) {
        Valor m = new Valor(pVal);
        return this.comparacao(pComp, m);
    }

    public void append(char pOperacao, Valor pVal) {
        Valor operado = this.operacao(pOperacao, pVal);
        this.setConteudo(operado);
    }

    public void append(char pOperacao, String pVal) {
        this.setConteudo(this.operacao(pOperacao, pVal));
    }

    public String asTxt() {
        String negativo = "";
        if (this.conteudo < 0) {
            negativo = negativo + "-";
        }
        return negativo + this.getParteInteira() + this.getParteDecimal();
    }

    public Valor getValorAbsoluto() {
        Valor result = new Valor();
        result.setCasasDecimais(this.getCasasDecimais());
        result.setConteudo(new Long(Math.abs(this.conteudo)));
        return result;
    }

    public static void main(String[] args) {
        Valor m = new Valor("23,55");
        Valor n = new Valor("100,00");
        m.setTratamentocasasDecimais(ARREDONDA);
        System.out.println("r->" + m.operacao('/', n).getConteudoFormatado());
    }

    public static Valor soma(Collection pCol, int pCasasDecimais) {
        Valor retorno = new Valor();
        retorno.setCasasDecimais(pCasasDecimais);
        Iterator itMoedaes = pCol.iterator();
        while (itMoedaes.hasNext()) {
            Valor m = (Valor) itMoedaes.next();
            retorno.setConteudo(retorno.soma(m));
        }
        return retorno;
    }

    public boolean isCampoCalculado() {
        return this.campoCalculado;
    }

    public void setCampoCalculado(boolean campoCalculado) {
        this.campoCalculado = campoCalculado;
    }

    public int getMaximoDigitosParteInteira() {
        return this.maximoDigitosParteInteira;
    }

    public void setMaximoDigitosParteInteira(int maximoParteInteira) {
        this.maximoDigitosParteInteira = maximoParteInteira;
    }

    public String getMsgErroEstourodigitos() {
        return this.msgErroEstourodigitos;
    }

    public void setMsgErroEstourodigitos(String msgErroEstourodigitos) {
        this.msgErroEstourodigitos = msgErroEstourodigitos;
    }

    public byte getSeveridadeValidacaoMaximoDigitos() {
        return this.severidadeValidacaoMaximoDigitos;
    }

    public void setSeveridadeValidacaoMaximoDigitos(byte severidadeValidacaoMaximoDigitos) {
        this.severidadeValidacaoMaximoDigitos = severidadeValidacaoMaximoDigitos;
    }

    public int getTratamentocasasDecimais() {
        return this.tratamentocasasDecimais;
    }

    public void setTratamentocasasDecimais(int tratamentocasasDecimais) {
        this.tratamentocasasDecimais = tratamentocasasDecimais;
    }

    public int getPisoArredondamento() {
        return this.pisoArredondamento;
    }

    public void setPisoArredondamento(int pisoArredondamento) {
        this.pisoArredondamento = pisoArredondamento;
    }
}
