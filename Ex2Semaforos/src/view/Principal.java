package view;

import controller.CozinheiroThread;

public class Principal {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            CozinheiroThread thread = new CozinheiroThread(i);
            thread.start();
        }
    }
}