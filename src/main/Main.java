package main;

import javax.swing.JFrame;


public class Main {
    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("My Blue Boy");
        Juego juego = new Juego();
        window.add(juego);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);


        juego.setupgame();


    }
}
