package view;

import controller.ServerThread;

public class Principal {
    public static void main(String[] args) {
        for (int i = 1; i <= 21; i++) {
            ServerThread thread = new ServerThread(i);
            thread.start();
        }
    }
}