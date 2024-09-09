package controller;

import java.util.concurrent.Semaphore;

public class CozinheiroThread extends Thread {
    private static final Semaphore semaforoEntrega = new Semaphore(1); // Controle de entrega
    private int id;
    
    public CozinheiroThread(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            if (id % 2 == 1) {
                // Sopa de Cebola
                realizarCozimento("Sopa de Cebola", 0.5, 0.8);
            } else {
                // Lasanha Bolonhesa
                realizarCozimento("Lasanha à Bolonhesa", 0.6, 1.2);
            }
            realizarEntrega();
        } catch (InterruptedException e) {
            System.err.println("Cozinheiro " + id + " foi interrompido.");
        }
    }

    private void realizarCozimento(String prato, double tempoMinimo, double tempoMaximo) throws InterruptedException {
        double tempoCozimento = tempoMinimo + (Math.random() * (tempoMaximo - tempoMinimo));
        int totalEtapas = (int) (tempoCozimento / 0.1); // Cada etapa 0,1 segundos

        System.out.printf("Prato %d (%s) iniciou o cozimento, tempo total: %.2f segundos.\n", id, prato, tempoCozimento);

        for (int i = 1; i <= totalEtapas; i++) {
            Thread.sleep(100); // Aguarda 0,1 segundos
            int percentual = (int) ((i / (double) totalEtapas) * 100);
            System.out.printf("Prato %d (%s) cozimento: %d%%\n", id, prato, percentual);
        }

        System.out.printf("Prato %d (%s) está pronto!\n", id, prato);
    }

    private void realizarEntrega() throws InterruptedException {
        semaforoEntrega.acquire(); // Somente um cozinheiro pode fazer a entrega 
        try {
            System.out.printf("Prato %d sendo entregue...\n", id);
            Thread.sleep(500); // Entrega leva 0,5 segundos
            System.out.printf("Prato %d foi entregue.\n", id);
        } finally {
            semaforoEntrega.release(); // Libera o semáforo para a próxima entrega
        }
    }
}