package controller;

import java.util.concurrent.Semaphore;

public class ServerThread extends Thread {
    private static final Semaphore semaforoBD = new Semaphore(1); // Controle de acesso ao BD
    private int id;
    
    public ServerThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            if (id % 3 == 1) {
                realizarCalculos(0.2, 1.0);
                realizarTransacaoBD(1.0);
                realizarCalculos(0.2, 1.0);
                realizarTransacaoBD(1.0);
            } else if (id % 3 == 2) {
                realizarCalculos(0.5, 1.5);
                realizarTransacaoBD(1.5);
                realizarCalculos(0.5, 1.5);
                realizarTransacaoBD(1.5);
                realizarCalculos(0.5, 1.5);
                realizarTransacaoBD(1.5);
            } else {
                realizarCalculos(1.0, 2.0);
                realizarTransacaoBD(1.5);
                realizarCalculos(1.0, 2.0);
                realizarTransacaoBD(1.5);
                realizarCalculos(1.0, 2.0);
                realizarTransacaoBD(1.5);
            }
        } catch (InterruptedException e) {
            System.err.println("Thread " + id + " foi interrompida.");
        }
    }

    private void realizarCalculos(double tempoMinimo, double tempoMaximo) throws InterruptedException {
        double tempoCalculo = tempoMinimo + (Math.random() * (tempoMaximo - tempoMinimo));
        System.out.printf("Thread %d realizando cálculos por %.2f segundos.\n", id, tempoCalculo);
        Thread.sleep((long) (tempoCalculo * 1000));
    }

    private void realizarTransacaoBD(double tempo) throws InterruptedException {
        semaforoBD.acquire(); // Garante que apenas uma thread realiza transação
        try {
            System.out.printf("Thread %d realizando transação de BD por %.2f segundos.\n", id, tempo);
            Thread.sleep((long) (tempo * 1000));
        } finally {
            semaforoBD.release(); // Libera o semáforo para a próxima thread
        }
    }
}