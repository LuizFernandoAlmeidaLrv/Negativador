package br.com.martinello.util;

import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class Extenso {

    private ArrayList nro;
    private BigInteger num;
    double numero;
    String numero2;

    private String Qualificadores[][] = {
        {"centavo", "centavos"},
        {"", ""},
        {"mil", "mil"},
        {"milhão", "milhões"},
        {"bilhão", "bilhões"},
        {"trilhão", "trilhões"},
        {"quatrilhão", "quatrilhões"},
        {"quintilhão", "quintilhões"},
        {"sextilhão", "sextilhões"},
        {"septilhão", "septilhões"}
    };
    private String Numeros[][] = {
        {"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez",
            "onze", "doze", "treze", "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
        {"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
        {"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos",
            "setecentos", "oitocentos", "novecentos"}
    };

    /**
     * Construtor
     */
    public Extenso() {
        nro = new ArrayList();
    }

    /**
     * Construtor
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(BigDecimal dec) {
        this();
        setNumber(dec);
    }

    /**
     * Constructor for the Extenso object
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(double dec) {
        this();
        setNumber(dec);
    }

    /**
     * Sets the Number attribute of the Extenso object
     *
     * @param dec The new Number value
     */
    public void setNumber(BigDecimal dec) {
        // Converte para inteiro arredondando os centavos   
        num = dec
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .toBigInteger();

        // Adiciona valores   
        nro.clear();
        if (num.equals(BigInteger.ZERO)) {
            // Centavos   
            nro.add(new Integer(0));
            // Valor   
            nro.add(new Integer(0));
        } else {
            // Adiciona centavos   
            addRemainder(100);

            // Adiciona grupos de 1000   
            while (!num.equals(BigInteger.ZERO)) {
                addRemainder(1000);
            }
        }
    }

    public void setNumber(double dec) {
        setNumber(new BigDecimal(dec));
    }

    /**
     * Description of the Method
     */
    public void show() {
        Iterator valores = nro.iterator();

        while (valores.hasNext()) {
            System.out.println(((Integer) valores.next()).intValue());
        }
        System.out.println(toString());
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        int numero = ((Integer) nro.get(0)).intValue();
        int ct;

        for (ct = nro.size() - 1; ct > 0; ct--) {
            // Se ja existe texto e o atual não é zero   
            if (buf.length() > 0 && !ehGrupoZero(ct)) {
                buf.append(" e ");
            }
            buf.append(numToString(((Integer) nro.get(ct)).intValue(), ct));
        }
        if (buf.length() > 0) {
            if (ehUnicoGrupo()) {
                buf.append(" de ");
            }
            while (buf.toString().endsWith(" ")) {
                buf.setLength(buf.length() - 1);
            }
            if (ehPrimeiroGrupoUm()) {
                buf.insert(0, "h");
            }
            if (nro.size() == 2 && ((Integer) nro.get(1)).intValue() == 1) {
                buf.append(" real");
            } else {
                buf.append(" reais");
            }
            if (((Integer) nro.get(0)).intValue() != 0) {
                buf.append(" e ");
            }
        }
        if (((Integer) nro.get(0)).intValue() != 0) {
            buf.append(numToString(((Integer) nro.get(0)).intValue(), 0));
        }
        return buf.toString().substring(0, 1).toUpperCase() + buf.toString().substring(1, buf.toString().length());
    }

    private boolean ehPrimeiroGrupoUm() {
        if (((Integer) nro.get(nro.size() - 1)).intValue() == 1) {
            return true;
        }
        return false;
    }

    /**
     * Adds a feature to the Remainder attribute of the Extenso object
     *
     * @param divisor The feature to be added to the Remainder attribute
     */
    private void addRemainder(int divisor) {
        // Encontra newNum[0] = num modulo divisor, newNum[1] = num dividido divisor   
        BigInteger[] newNum = num.divideAndRemainder(BigInteger.valueOf(divisor));

        // Adiciona modulo   
        nro.add(new Integer(newNum[1].intValue()));

        // Altera numero   
        num = newNum[0];
    }

    /**
     * Description of the Method
     *
     * @param ps Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean temMaisGrupos(int ps) {
        for (; ps > 0; ps--) {
            if (((Integer) nro.get(ps)).intValue() != 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Description of the Method
     *
     * @param ps Description of Parameter
     * @return Description of the Returned Value
     */
    private boolean ehUltimoGrupo(int ps) {
        return (ps > 0) && ((Integer) nro.get(ps)).intValue() != 0 && !temMaisGrupos(ps - 1);
    }

    /**
     * Description of the Method
     *
     * @return Description of the Returned Value
     */
    private boolean ehUnicoGrupo() {
        if (nro.size() <= 3) {
            return false;
        }
        if (!ehGrupoZero(1) && !ehGrupoZero(2)) {
            return false;
        }
        boolean hasOne = false;
        for (int i = 3; i < nro.size(); i++) {
            if (((Integer) nro.get(i)).intValue() != 0) {
                if (hasOne) {
                    return false;
                }
                hasOne = true;
            }
        }
        return true;
    }

    boolean ehGrupoZero(int ps) {
        if (ps <= 0 || ps >= nro.size()) {
            return true;
        }
        return ((Integer) nro.get(ps)).intValue() == 0;
    }

    /**
     * Description of the Method
     *
     * @param numero Description of Parameter
     * @param escala Description of Parameter
     * @return Description of the Returned Value
     */
    private String numToString(int numero, int escala) {
        int unidade = (numero % 10);
        int dezena = (numero % 100); //* nao pode dividir por 10 pois verifica de 0..19   
        int centena = (numero / 100);
        StringBuffer buf = new StringBuffer();

        if (numero != 0) {
            if (centena != 0) {
                if (dezena == 0 && centena == 1) {
                    buf.append(Numeros[2][0]);
                } else {
                    buf.append(Numeros[2][centena]);
                }
            }

            if ((buf.length() > 0) && (dezena != 0)) {
                buf.append(" e ");
            }
            if (dezena > 19) {
                dezena /= 10;
                buf.append(Numeros[1][dezena - 2]);
                if (unidade != 0) {
                    buf.append(" e ");
                    buf.append(Numeros[0][unidade]);
                }
            } else if (centena == 0 || dezena != 0) {
                buf.append(Numeros[0][dezena]);
            }

            buf.append(" ");
            if (numero == 1) {
                buf.append(Qualificadores[escala][0]);
            } else {
                buf.append(Qualificadores[escala][1]);
            }
        }

        return buf.toString();
    }

    public String reaisExtenso(String valor) {
        String extenso = ""; //variavel que retornará o valor por extenso
        String tipo = ""; //variavel que definirá o tipo de número ( unitario, dezena, centena)
        String parte1 = ""; //variavel que armazenará temporáriamente o valor que foi analizado
        int cont = valor.length(); //conta quantas números tem o valor
        int i = 0; //variavel de controle do while
        int somar = 1; //variavel que dirá o valor a ser somado à variável "i"

        while (i < cont) {
            somar = 1;
//verifica se o caracter atual é número ou uma "," ou "."
            if (valor.substring(i, i + 1).equals(",") == false && valor.substring(i, i + 1).equals(".") == false) {
//o valor passado tem 3 dígitos, ex: R$ 1,20
                if (cont == 4 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 4 && i == 2) {
                    tipo = "dezena";
                } else if (cont == 4 && i == 3) {
                    tipo = "unitario";
                } //o valor passado tem 4 dígitos, ex: R$ 11,20
                else if (cont == 5 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 5 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 4) {
                    tipo = "unitario";
                } //o valor passado tem 5 dígitos, ex: R$ 111,20
                else if (cont == 6 && i == 0) {
                    tipo = "centena";
                } else if (cont == 6 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 6 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 5) {
                    tipo = "unitario";
                } //o valor passado tem 6 dígitos, ex: R$ 1.111,20
                else if (cont == 8 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 2) {
                    tipo = "centena";
                } else if (cont == 8 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 4) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 6) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 7) {
                    tipo = "unitario";
                } //o valor passado tem 7 dígitos, ex: R$ 11.111,20
                else if (cont == 9 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 3) {
                    tipo = "centena";
                } else if (cont == 9 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 5) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 7) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 8) {
                    tipo = "unitario";
                } //o valor passado tem 8 dígitos, ex: R$ 111.111,20
                else if (cont == 10 && i == 0) {
                    tipo = "centena";
                } else if (cont == 10 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 4) {
                    tipo = "centena";
                } else if (cont == 10 && i == 5) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 6) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 8) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 9) {
                    tipo = "unitario";
                }

// se o tipo analisado for do tipo unitário, define a variável "parte1"
                if (tipo == "unitario") {
                    if (valor.substring(i, i + 1).equals("1")) {
                        parte1 = "Um ";
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Dois ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Três ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatro ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinco ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seis ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Sete ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oito ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Nove ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
// se o tipo analisado for do tipo dezena, define a variável "parte1"
                if (tipo == "dezena") {
                    if (valor.substring(i, i + 1).equals("1")) {
//se o caracter começa com "1" é avaliado tb o caracter seguinte
                        if (valor.substring(i, i + 2).equals("10")) {
                            parte1 = "Dez ";
                        } else if (valor.substring(i, i + 2).equals("11")) {
                            parte1 = "Onze ";
                        } else if (valor.substring(i, i + 2).equals("12")) {
                            parte1 = "Doze ";
                        } else if (valor.substring(i, i + 2).equals("13")) {
                            parte1 = "Treze ";
                        } else if (valor.substring(i, i + 2).equals("14")) {
                            parte1 = "Quatorze ";
                        } else if (valor.substring(i, i + 2).equals("15")) {
                            parte1 = "Quinze ";
                        } else if (valor.substring(i, i + 2).equals("16")) {
                            parte1 = "Dezesseis ";
                        } else if (valor.substring(i, i + 2).equals("17")) {
                            parte1 = "Dezesete ";
                        } else if (valor.substring(i, i + 2).equals("18")) {
                            parte1 = "Dezoito ";
                        } else if (valor.substring(i, i + 2).equals("19")) {
                            parte1 = "Dezenove ";
                        }
                        somar = 2; //como foi analisado duas casas do valor, a variável "i" será acrescentada em 2
                    } //se o caracter não começa com "1", já é definido a variável parte1
                    else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Vinte ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trinta ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quarenta ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinquenta ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Sessenta ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setenta ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitenta ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Noventa ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
// se o tipo analisado for do tipo centena, define a variável "parte1"
                if (tipo == "centena") {
                    if (valor.substring(i, i + 1).equals("1")) {
//se o caracter começa com "1", avalia os proximos dois caracteres, para 
//definir se é "Cem" ou "Cento"
                        if (valor.substring(i, i + 3).equals("100")) {
                            parte1 = "Cem ";
                        } else {
                            parte1 = "Cento ";
                        }
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Duzentos ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trezentos ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatrocentos ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Quinhentos ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seiscentos ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setecentos ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitocentos ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Novecentos ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
//se o caracter é igual a "0" ou "," não é adicionado a palavra "e "
                if (i == 0 || valor.substring(i - 1, i) == ",") {
                    extenso = extenso + parte1;
                } else if (valor.substring(i, i + 1).equals("0") == false) { //se o caracter é igual à "0"
                    if (extenso.equals("")) { //verifica se a variável extenso é nula
                        extenso = extenso + parte1;
                    } else {
                        extenso = extenso + "e " + parte1;
                    }
                }

            } //verifica se o caracter atual é ","
            else if (valor.substring(i, i + 1).equals(",")) {
                if (cont == 4 && Integer.parseInt(valor.substring(i - 1, i)) == 1) {
//se o valor tem 3 dígitos e começa com "1", adiciona a palavra Real
                    extenso = extenso + "Real ";
                } else if (cont > 4 || Integer.parseInt(valor.substring(i - 1, i)) > 1) {
//se o valor tem mais 3 dígitos ou não começa com "1", adiciona a palavra Reais
                    extenso = extenso + "Reais ";
                }

            } //verifica se o caracter atual é "."
            else if (valor.substring(i, i + 1).equals(".")) {
                extenso = extenso + "Mil "; //se sim, acrescenta a palavra "Mil"
            }

            i = i + somar;

        }
//verifica se as duas casas decimais é maior que 1
        if (Integer.parseInt(valor.substring(cont - 2, cont)) > 1) {
            extenso = extenso + "Centavos"; //se sim, acrescenta a palavra Centavos
        } else if (Integer.parseInt(valor.substring(cont - 2, cont)) == 1) {
            extenso = extenso + "Centavo"; //se não, acrescenta a palavra Centavo
        }

        return extenso;
    }

    public void valor() {
        numero2 = "10";
        numero = Double.parseDouble(numero2);

    }

    /**
     * Para teste
     *
     * @param args numero a ser convertido
     */
    public static void main(String[] args) {

        /**
         * if (args.length == 0) { * System.out.println("Sintax : ...Extenso
         * <numero>"); return; } *
         */
        Extenso teste = new Extenso(new BigDecimal(1000000.00));
//      System.out.println("Numero  : " + (new DecimalFormat().format(Double.valueOf(args[0]))));   
        System.out.println("Extenso : " + teste.toString());
    }
}
