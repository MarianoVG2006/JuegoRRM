package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;

public class RecojerportecladoMenu implements KeyListener {

    Juego gp; // Referencia al juego

    /*Constructor que recibe la instancia del juego.

     */
    public RecojerportecladoMenu(Juego gp) {
        this.gp = gp;
    }

    /*Método no utilizado en este caso, pero requerido por la interfaz KeyListener.
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /*Método que se ejecuta cuando el usuario presiona una tecla.

     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // Obtiene el código de la tecla presionada

        // Si el juego está en el estado del menú principal
        if (gp.estadoJuego == gp.titleState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.pararmusica();  // Detener la música del menú
                gp.estadoJuego = gp.playState; // Cambia al estado de juego
                gp.setupgame();  // Inicializa el juego y su música
            } else if (code == KeyEvent.VK_ESCAPE) {
                System.exit(0);  // Salir del juego
            } else if (code == KeyEvent.VK_I) {
                mostrarInstrucciones(); // Muestra las instrucciones del juego
            }
        }
    }

    /*Método no utilizado en este caso, pero requerido por la interfaz KeyListener.
     */
    @Override
    public void keyReleased(KeyEvent e) {}

    /*
     Muestra un cuadro de diálogo con las instrucciones del juego.*/

    public void mostrarInstrucciones() {
        JOptionPane.showMessageDialog(null,
                "Bienvenido al juego. Tu misión es encontrar las llaves que están repartidas por todo el mapa y llegar a conseguir el cofre del tesoro.\n" +
                        "- Usa las teclas W (adelante), D (derecha), S (abajo) y A (izquierda) para moverte.\n" +
                        "- También habrá una bota en el mapa que te permitirá moverte más rápido.\n" +
                        "- ¡Buena suerte!",
                "¿Cómo jugar?",
                JOptionPane.INFORMATION_MESSAGE);
    }
}



