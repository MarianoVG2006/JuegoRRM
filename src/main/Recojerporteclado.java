package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Recojerporteclado implements KeyListener {

    // Variables booleanas para detectar si una tecla está presionada
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    // Referencia al juego
    Juego gp;

    /*Constructor que recibe una instancia del juego.

     */
    public Recojerporteclado(Juego gp) {
        this.gp = gp;
    }

    /*Método de KeyListener que se ejecuta cuando se escribe un carácter.

     */
    @Override
    public void keyTyped(KeyEvent e) {
        // No implementado
    }

    /*Método que detecta cuando una tecla es presionada.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int codigo = e.getKeyCode(); // Obtiene el código de la tecla presionada

        // Si el juego ha terminado y el jugador presiona "R", se reinicia el juego
        if (gp.textopantalla.JuegoTerminado) {
            if (codigo == KeyEvent.VK_R) {
                gp.reiniciarJuego();
            }
        }

        // Movimiento del jugador con las teclas W, A, S, D
        if (codigo == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (codigo == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (codigo == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (codigo == KeyEvent.VK_D) {
            rightPressed = true;
        }
    }

    /*Método que detecta cuando una tecla es soltada.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int codigo = e.getKeyCode(); // Obtiene el código de la tecla liberada

        // Se actualizan las variables booleanas al soltar las teclas
        if (codigo == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (codigo == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (codigo == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (codigo == KeyEvent.VK_D) {
            rightPressed = false;
        }
    }
}

