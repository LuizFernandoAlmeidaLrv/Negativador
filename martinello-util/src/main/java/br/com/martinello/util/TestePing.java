/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.martinello.util;

import br.com.martinello.util.excessoes.ErroSistemaException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;

public class TestePing {

    public static void main(String[] args) {
        TestePing testePing = new TestePing();

        testePing.testarIP("10.0.0.1");
    }

    public void testarIP(String enderecoIP) {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(enderecoIP);
        } catch (UnknownHostException e) {
            System.out.println("Cannot lookup host " + enderecoIP);
            return;
        }
        try {
            if (address.isReachable(5000)) {
                long nanos = 0;
                long millis = 0;
                long iterations = 0;
                while (true) {
                    iterations++;
                    try {
                        nanos = System.nanoTime();
                        address.isReachable(500); // this invocation is the offender
                        nanos = System.nanoTime() - nanos;
                    } catch (IOException e) {
                        System.out.println("Failed to reach host");
                    }
                    millis = Math.round(nanos / Math.pow(10, 6));
                    System.out.println("Resposta do IP: " + address.getHostAddress() + " com de tempo=" + millis + " ms");
                    try {
                        Thread.sleep(Math.max(0, 1000 - millis));
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                System.out.println("Iterations: " + iterations);
            } else {
                System.out.println("Host " + address.getHostName() + " is not reachable even once.");
            }
        } catch (IOException e) {
            System.out.println("Network error.");
        }
    }

    public void verificarConexao(String enderecoIP) throws ErroSistemaException {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(enderecoIP);
        } catch (UnknownHostException e) {
            throw new ErroSistemaException("Não foi possível realizar o ping.", e);
        }

        try {
            if (address.isReachable(5000)) {
                long nanos = 0;
                long millis = 0;
                long iterations = 0;
                while (true) {
                    iterations++;
                    try {
                        nanos = System.nanoTime();
                        address.isReachable(500); // this invocation is the offender
                        nanos = System.nanoTime() - nanos;
                    } catch (IOException e) {
                        System.out.println("Failed to reach host");
                    }
                    millis = Math.round(nanos / Math.pow(10, 6));
                    System.out.println("Resposta do IP: " + address.getHostAddress() + " com de tempo=" + millis + " ms");
                    try {
                        Thread.sleep(Math.max(0, 1000 - millis));
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                System.out.println("Iterations: " + iterations);
            } else {
                System.out.println("Host " + address.getHostName() + " is not reachable even once.");
                throw new ErroSistemaException("Não foi possível realizar o ping.");
            }
        } catch (IOException e) {
            System.out.println("Network error.");

            throw new ErroSistemaException("Não foi possível realizar o ping.", e);
        }
    }
}
