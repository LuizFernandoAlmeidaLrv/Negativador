/*
 * Decompiled with CFR 0_123.
 */
package br.com.martinello.componentesbasicos.campos2;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogPPGD {

    private static Logger logger;
    static /* synthetic */ Class class$serpro$ppgd$negocio$util$LogPPGD;

    private static void instanciaEAplicaLevel() {
        Class class_ = class$serpro$ppgd$negocio$util$LogPPGD == null ? (LogPPGD.class$serpro$ppgd$negocio$util$LogPPGD = LogPPGD.class$("serpro.ppgd.negocio.util.LogPPGD")) : class$serpro$ppgd$negocio$util$LogPPGD;
        InputStream in = class_.getResourceAsStream("/aplicacao.properties");
        Properties p = new Properties();
        try {
            if (in != null) {
                p.load(in);
                String strLevel = p.getProperty("logger.level", "ERRO");
                if (strLevel.compareTo("ERRO") == 0) {
                    strLevel = "SEVERE";
                } else if (strLevel.compareTo("AVISO") == 0) {
                    strLevel = "WARNING";
                } else if (strLevel.compareTo("DEBUG") == 0) {
                    strLevel = "INFO";
                }
                logger = Logger.getLogger((class$serpro$ppgd$negocio$util$LogPPGD == null ? (LogPPGD.class$serpro$ppgd$negocio$util$LogPPGD = LogPPGD.class$("serpro.ppgd.negocio.util.LogPPGD")) : class$serpro$ppgd$negocio$util$LogPPGD).getName());
                logger.setLevel(Level.parse(strLevel));
            } else {
                System.err.println("Nao foi poss\u00edvel localizar o arquivo: /aplicacao.properties");
                System.err.println("O log n\u00e3o vai funcionar assim!");
            }
        } catch (IOException ioe) {
            System.err.println("N\u00e3o foi poss\u00edvel carregar arquivo: /aplicacao.properties");
            System.err.println("O log n\u00e3o vai funcionar assim!");
            ioe.printStackTrace();
        }
    }

    public static void debug(String msg) {
        if (logger == null) {
            System.err.println("H\u00e1 algum problema com o logger, n\u00e3o foi instanciado.");
        } else {
            logger.info(msg);
        }
    }

    public static void aviso(String msg) {
        if (logger == null) {
            System.err.println("H\u00e1 algum problema com o logger, n\u00e3o foi instanciado.");
        } else {
            logger.warning(msg);
        }
    }

    public static void erro(String msg) {
        if (logger == null) {
            System.err.println("H\u00e1 algum problema com o logger, n\u00e3o foi instanciado.");
        } else {
            logger.severe(msg);
        }
    }

    public static void main(String[] args) {
        LogPPGD.debug("Este \u00e9 um debug!");
        LogPPGD.aviso("Este \u00e9 um aviso!");
        LogPPGD.erro("Este \u00e9 um erro!");
    }

    static /* synthetic */ Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    static {
        LogPPGD.instanciaEAplicaLevel();
    }
}
