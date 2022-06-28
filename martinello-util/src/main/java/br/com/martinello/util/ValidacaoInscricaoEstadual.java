/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

/**
 *
 * @author Sidnei
 */
public class ValidacaoInscricaoEstadual {

    private static String removeMascara(String ie) {
        String strIE = "";
        for (int i = 0; i < ie.length(); i++) {
            if (Character.isDigit(ie.charAt(i))) {
                strIE += ie.charAt(i);
            }
        }
        return strIE;
    }

    public static void valida(String inscricaoEstadual, String siglaUf) throws ValidacaoException {
        String strIE = removeMascara(inscricaoEstadual);

        siglaUf = siglaUf.toUpperCase();

        switch (siglaUf) {
            case "AC":
                validaIEAcre(strIE);
                break;
            case "AL":
                validaIEAlagoas(strIE);
                break;
            case "AP":
                validaIEAmapa(strIE);
                break;
            case "AM":
                validaIEAmazonas(strIE);
                break;
            case "BA":
                validaIEBahia(strIE);
                break;
            case "CE":
                validaIECeara(strIE);
                break;
            case "ES":
                validaIEEspiritoSanto(strIE);
                break;
            case "GO":
                validaIEGoias(strIE);
                break;
            case "MA":
                validaIEMaranhao(strIE);
                break;
            case "MT":
                validaIEMatoGrosso(strIE);
                break;
            case "MS":
                validaIEMatoGrossoSul(strIE);
                break;
            case "MG":
                validaIEMinasGerais(strIE);
                break;
            case "PA":
                validaIEPara(strIE);
                break;
            case "PB":
                validaIEParaiba(strIE);
                break;
            case "PR":
                validaIEParana(strIE);
                break;
            case "PE":
                validaIEPernambuco(strIE);
                break;
            case "PI":
                validaIEPiaui(strIE);
                break;
            case "RJ":
                validaIERioJaneiro(strIE);
                break;
            case "RN":
                validaIERioGrandeNorte(strIE);
                break;
            case "RS":
                validaIERioGrandeSul(strIE);
                break;
            case "RO":
                validaIERondonia(strIE);
                break;
            case "RR":
                validaIERoraima(strIE);
                break;
            case "SC":
                validaIESantaCatarina(strIE);
                break;
            case "SP":
                if (inscricaoEstadual.charAt(0) == 'P') {
                    strIE = "P" + strIE;
                }
                validaIESaoPaulo(strIE);
                break;
            case "SE":
                validaIESergipe(strIE);
                break;
            case "TO":
                validaIETocantins(strIE);
                break;
            case "DF":
                validaIEDistritoFederal(strIE);
                break;
            default:
                throw new ValidacaoException("Estado não encontrado : " + siglaUf);
        }
    }

    /**
     * Valida inscrição estadual do estado do Acre
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEAcre(String ie) throws ValidacaoException { //inicío do validaIEAcre()
        //valida a quantidade de dígitos
        if (ie.length() != 13) {
            throw new ValidacaoException("Quantidade de digitos inválida.");
        }

        //valida os dois primeiros digitos - devem ser iguais a 01
        for (int i = 0; i < 2; i++) {
            if (Integer.parseInt(String.valueOf(ie.charAt(i))) != i) {
                throw new ValidacaoException("Inscrição Estadual inválida");
            }
        }

        int soma = 0;
        int pesoInicial = 4;
        int pesoFinal = 9;
        int d1 = 0; //primeiro digito verificador
        int d2 = 0; //segundo digito verificador

        //calcula o primeiro digito
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }
        d1 = 11 - (soma % 11);
        if (d1 == 10 || d1 == 11) {
            d1 = 0;
        }

        //calcula o segundo digito
        soma = d1 * 2;
        pesoInicial = 5;
        pesoFinal = 9;
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 4) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 10 || d2 == 11) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    } //fim do validaIEAcre()

    /**
     * Valida inscrição estadual do estado do Alagoas
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEAlagoas(String ie) throws ValidacaoException {
        //valida quantidade de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de dígitos invílida.");
        }

        //valida os dois primeiros dígitos - deve ser iguais a 24
        if (!ie.substring(0, 2).equals("24")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //valida o terceiro dígito - deve ser 0,3,5,7,8
        int[] digits = {0, 3, 5, 7, 8};
        boolean check = false;
        for (int i = 0; i < digits.length; i++) {
            if (Integer.parseInt(String.valueOf(ie.charAt(2))) == digits[i]) {
                check = true;
                break;
            }
        }
        if (!check) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = 0; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }
        d = ((soma * 10) % 11);
        if (d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Amapí
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEAmapa(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //verifica os dois primeiros dígitos - deve ser igual 03
        if (!ie.substring(0, 2).equals("03")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //calcula o dígito verificador
        int d1 = -1;
        int soma = -1;
        int peso = 9;

        //configura o valor do dígito verificador e da soma de acordo com faixa das inscriííes
        long x = Long.parseLong(ie.substring(0, ie.length() - 1)); //x = inscrição estadual sem o dígito verificador
        if (x >= 3017001L && x <= 3019022L) {
            d1 = 1;
            soma = 9;
        } else if (x >= 3000001L && x <= 3017000L) {
            d1 = 0;
            soma = 5;
        } else if (x >= 3019023L) {
            d1 = 0;
            soma = 0;
        }

        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int d = 11 - ((soma % 11)); //d = armazena o dígito verificador apís cílculo
        if (d == 10) {
            d = 0;
        } else if (d == 11) {
            d = d1;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Amazonas
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEAmazonas(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        if (soma < 11) {
            d = 11 - soma;
        } else if ((soma % 11) <= 1) {
            d = 0;
        } else {
            d = 11 - (soma % 11);
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Bahia
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEBahia(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 8 && ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas." + ie);
        }

        //Cílculo do mídulo de acordo com o primeiro dígito da inscrição Estadual
        int modulo = 10;
        int firstDigit = Integer.parseInt(String.valueOf(ie.charAt(ie.length() == 8 ? 0 : 1)));
        if (firstDigit == 6 || firstDigit == 7 || firstDigit == 9) {
            modulo = 11;
        }

        //Cílculo do segundo dígito
        int d2 = -1; //segundo dígito verificador
        int soma = 0;
        int peso = ie.length() == 8 ? 7 : 8;
        for (int i = 0; i < ie.length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)) {
            d2 = 0;
        } else {
            d2 = modulo - resto;
        }

        //Cílculo do primeiro dígito
        int d1 = -1; //primeiro dígito verificador
        soma = d2 * 2;
        peso = ie.length() == 8 ? 8 : 9;
        for (int i = 0; i < ie.length() - 2; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        resto = soma % modulo;

        if (resto == 0 || (modulo == 11 && resto == 1)) {
            d1 = 0;
        } else {
            d1 = modulo - resto;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new ValidacaoException("Digito verificador inválido." + ie);
        }
    }

    /**
     * Valida inscrição estadual do estado do Cearí
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIECeara(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }
        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Espírito Santo
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEEspiritoSanto(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        if (resto < 2) {
            d = 0;
        } else if (resto > 1) {
            d = 11 - resto;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Goiís
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEGoias(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //vílida os dois primeiros dígito
        if (!"10".equals(ie.substring(0, 2))) {
            if (!"11".equals(ie.substring(0, 2))) {
                if (!"15".equals(ie.substring(0, 2))) {
                    throw new ValidacaoException("Inscrição estadual inválida");
                }
            }
        }

        if (ie.substring(0, ie.length() - 1).equals("11094402")) {
            if (!ie.substring(ie.length() - 1, ie.length()).equals("0")) {
                if (!ie.substring(ie.length() - 1, ie.length()).equals("1")) {
                    throw new ValidacaoException("Inscrição estadual inválida.");
                }
            }
        } else {

            //Cílculo do dígito verificador
            int soma = 0;
            int peso = 9;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            int resto = soma % 11;
            long faixaInicio = 10103105;
            long faixaFim = 10119997;
            long insc = Long.parseLong(ie.substring(0, ie.length() - 1));
            if (resto == 0) {
                d = 0;
            } else if (resto == 1) {
                if (insc >= faixaInicio && insc <= faixaFim) {
                    d = 1;
                } else {
                    d = 0;
                }
            } else if (resto != 0 && resto != 1) {
                d = 11 - resto;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new ValidacaoException("Digito verificador inválido.");
            }
        }
    }

    /**
     * Valida inscrição estadual do estado do Maranhío
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEMaranhao(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("12")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Mato Grosso
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEMatoGrosso(String ie) throws ValidacaoException {
        
        
        if (ie.length() == 9 && !ie.trim().substring(0, 2).equals("00")) {
            ie = "00" + ie;
        }

        //valida quantida de dígitos
        if (ie.length() != 11) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int pesoInicial = 3;
        int pesoFinal = 9;
        int d = -1;

        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicial;
                pesoInicial--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFinal;
                pesoFinal--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Mato Grosso do Sul
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEMatoGrossoSul(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("28")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        int resto = soma % 11;
        int result = 11 - resto;
        if (resto == 0) {
            d = 0;
        } else if (resto > 0) {
            if (result > 9) {
                d = 0;
            } else if (result < 10) {
                d = result;
            }
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Minas Gerais
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEMinasGerais(String ie) throws ValidacaoException {
        /*
	 * FORMATO GERAL: A1A2A3B1B2B3B4B5B6C1C2D1D2
	 * Onde: A= Cídigo do Município
	 * B= Nímero da inscrição
	 * C= Nímero de ordem do estabelecimento
	 * D= Dígitos de controle
         */

        // valida quantida de dígitos
        if (ie.length() != 13) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //iguala a casas para o cílculo
        //em inserir o algarismo zero "0" imediatamente apís o nímero de cídigo do município, 
        //desprezando-se os dígitos de controle.
        String str = "";
        for (int i = 0; i < ie.length() - 2; i++) {
            if (Character.isDigit(ie.charAt(i))) {
                if (i == 3) {
                    str += "0";
                    str += ie.charAt(i);
                } else {
                    str += ie.charAt(i);
                }
            }
        }

        //Cílculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 1;
        int pesoFim = 2;
        int d1 = -1; //primeiro dígito verificador
        for (int i = 0; i < str.length(); i++) {
            if (i % 2 == 0) {
                int x = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoInicio;
                String strX = Integer.toString(x);
                for (int j = 0; j < strX.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strX.charAt(j)));
                }
            } else {
                int y = Integer.parseInt(String.valueOf(str.charAt(i))) * pesoFim;
                String strY = Integer.toString(y);
                for (int j = 0; j < strY.length(); j++) {
                    soma += Integer.parseInt(String.valueOf(strY.charAt(j)));
                }
            }
        }

        int dezenaExata = soma;
        while (dezenaExata % 10 != 0) {
            dezenaExata++;
        }
        d1 = dezenaExata - soma; //resultado - primeiro dígito verificador

        //Cílculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 3;
        pesoFim = 11;
        int d2 = -1;
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11); //resultado - segundo dígito verificador
        if ((soma % 11 == 0) || (soma % 11 == 1)) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Parí
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEPara(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("15")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Paraíba
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEParaiba(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Calcula o dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Paraní
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEParana(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 10) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do primeiro dígito
        int soma = 0;
        int pesoInicio = 3;
        int pesoFim = 7;
        int d1 = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 2) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d1 = 0;
        }

        //cílculo do segundo dígito
        soma = d1 * 2;
        pesoInicio = 4;
        pesoFim = 7;
        int d2 = -1; //segundo dígito
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Pernambuco
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEPernambuco(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 14) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verificador
        int soma = 0;
        int pesoInicio = 5;
        int pesoFim = 9;
        int d = -1; //dígito verificador

        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 5) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if (d > 9) {
            d -= 10;
        }

        System.out.println(soma);
        System.out.println(11 - (soma % 11));
        System.out.println(d);

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Piauí
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEPiaui(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verficador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rio de Janeiro
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIERioJaneiro(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 8) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verficador
        int soma = 0;
        int peso = 7;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i == 0) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * 2;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }

        d = 11 - (soma % 11);
        if ((soma % 11) <= 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rio Grande do Norte
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIERioGrandeNorte(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 10 && ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("20")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        //calcula o dígito para inscrição de 9 dígitos
        if (ie.length() == 9) {
            int soma = 0;
            int peso = 9;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }

            d = ((soma * 10) % 11);
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new ValidacaoException("Digito verificador inválido.");
            }
        } else {
            int soma = 0;
            int peso = 10;
            int d = -1; //dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
            d = ((soma * 10) % 11);
            if (d == 10) {
                d = 0;
            }

            //valida o digito verificador
            String dv = d + "";
            if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
                throw new ValidacaoException("Digito verificador inválido.");
            }
        }

    }

    /**
     * Valida inscrição estadual do estado do Rio Grande do Sul
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIERioGrandeSul(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 10) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dífito verificador
        int soma = Integer.parseInt(String.valueOf(ie.charAt(0))) * 2;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 1; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 10 || d == 11) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Rondínia
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIERondonia(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 14) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dígito verificador
        int soma = 0;
        int pesoInicio = 6;
        int pesoFim = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i < 5) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 10) {
            d -= 10;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Roraíma
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIERoraima(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //valida os dois primeiros dígitos
        if (!ie.substring(0, 2).equals("24")) {
            throw new ValidacaoException("Inscrição estadual inválida.");
        }

        int soma = 0;
        int peso = 1;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso++;
        }

        d = soma % 9;

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Santa Catarina
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIESantaCatarina(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //Cílculo do dífito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if ((soma % 11) == 0 || (soma % 11) == 1) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Sío Paulo
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIESaoPaulo(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 12 && ie.length() != 13) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        if (ie.length() == 12) {
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro dígito verificador
            //cílculo do primeiro dígito verificador (nona posiíío)
            for (int i = 0; i < ie.length() - 4; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //cílculo do segunfo dígito
            soma = 0;
            int pesoInicio = 3;
            int pesoFim = 10;
            int d2 = -1; //segundo dígito verificador
            for (int i = 0; i < ie.length() - 1; i++) {
                if (i < 2) {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                    pesoInicio--;
                } else {
                    soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                    pesoFim--;
                }
            }

            d2 = soma % 11;
            String strD2 = Integer.toString(d2); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d2 = Integer.parseInt(String.valueOf(strD2.charAt(strD2.length() - 1)));

            //valida os dígitos verificadores
            if (!ie.substring(8, 9).equals(d1 + "")) {
                throw new ValidacaoException("Inscrição estadual inválida.");
            }
            if (!ie.substring(11, 12).equals(d2 + "")) {
                throw new ValidacaoException("Inscrição estadual inválida.");
            }

        } else {
            //valida o primeiro caracter
            if (ie.charAt(0) != 'P') {
                throw new ValidacaoException("Inscrição estadual inválida.");
            }

            String strIE = ie.substring(1, 10); //Obtím somente os dígitos utilizados no cílculo do dígito verificador
            int soma = 0;
            int peso = 1;
            int d1 = -1; //primeiro dígito verificador
            //cílculo do primeiro dígito verificador (nona posiíío)
            for (int i = 0; i < strIE.length() - 1; i++) {
                if (i == 1 || i == 7) {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * ++peso;
                    peso++;
                } else {
                    soma += Integer.parseInt(String.valueOf(strIE.charAt(i))) * peso;
                    peso++;
                }
            }

            d1 = soma % 11;
            String strD1 = Integer.toString(d1); //O dígito í igual ao algarismo mais a direita do resultado de (soma % 11)
            d1 = Integer.parseInt(String.valueOf(strD1.charAt(strD1.length() - 1)));

            //valida o dígito verificador
            if (!ie.substring(9, 10).equals(d1 + "")) {
                throw new ValidacaoException("Inscrição estadual inválida.");
            }
        }
    }

    /**
     * Valida inscrição estadual do estado do Sergipe
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIESergipe(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //cílculo do dígito verificador
        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
            peso--;
        }

        d = 11 - (soma % 11);
        if (d == 11 || d == 11 || d == 10) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Tocantins
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIETocantins(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 9 && ie.length() != 11) {
            throw new ValidacaoException("Quantidade de dígitos invílida.");
        } else if (ie.length() == 9) {
            ie = ie.substring(0, 2) + "02" + ie.substring(2);
        }

        int soma = 0;
        int peso = 9;
        int d = -1; //dígito verificador
        for (int i = 0; i < ie.length() - 1; i++) {
            if (i != 2 && i != 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * peso;
                peso--;
            }
        }
        d = 11 - (soma % 11);
        if ((soma % 11) < 2) {
            d = 0;
        }

        //valida o digito verificador
        String dv = d + "";
        if (!ie.substring(ie.length() - 1, ie.length()).equals(dv)) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }

    /**
     * Valida inscrição estadual do estado do Distrito Federal
     *
     * @param ie (Inscriíío estadual)
     * @throws ValidacaoException
     */
    private static void validaIEDistritoFederal(String ie) throws ValidacaoException {
        //valida quantida de dígitos
        if (ie.length() != 13) {
            throw new ValidacaoException("Quantidade de digitos inválidas.");
        }

        //cílculo do primeiro dígito verificador
        int soma = 0;
        int pesoInicio = 4;
        int pesoFim = 9;
        int d1 = -1; //primeiro dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 3) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d1 = 11 - (soma % 11);
        if (d1 == 11 || d1 == 10) {
            d1 = 0;
        }

        //cílculo do segundo dígito verificador
        soma = d1 * 2;
        pesoInicio = 5;
        pesoFim = 9;
        int d2 = -1; //segundo dígito verificador
        for (int i = 0; i < ie.length() - 2; i++) {
            if (i < 4) {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoInicio;
                pesoInicio--;
            } else {
                soma += Integer.parseInt(String.valueOf(ie.charAt(i))) * pesoFim;
                pesoFim--;
            }
        }

        d2 = 11 - (soma % 11);
        if (d2 == 11 || d2 == 10) {
            d2 = 0;
        }

        //valida os digitos verificadores
        String dv = d1 + "" + d2;
        if (!dv.equals(ie.substring(ie.length() - 2, ie.length()))) {
            throw new ValidacaoException("Digito verificador inválido.");
        }
    }
}
