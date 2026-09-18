package main;

import objeto.Obj_llave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UID {

    Juego gp;  // Referencia al juego
    Font arial_40, arial_80B;  // Fuentes para el texto en pantalla
    BufferedImage keyImage;  // Imagen de la llave
    public boolean mensajeon = false;  // Indica si un mensaje debe mostrarse en pantalla
    public String mensaje = "";  // Texto del mensaje a mostrar
    int mensajeTiempo = 0;  // Temporizador para ocultar el mensaje después de cierto tiempo
    public boolean JuegoTerminado = false;  // Indica si el juego ha terminado

    double tiempodejuego;  // Contador del tiempo transcurrido en el juego
    DecimalFormat forma = new DecimalFormat("#0.00");  // Formato para mostrar el tiempo con dos decimales


    public UID(Juego gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        Obj_llave key = new Obj_llave();
        keyImage = key.image;  // Asigna la imagen de la llave
    }

    /*Muestra un mensaje temporal en pantalla.

     */
    public void mostrarmensaje(String texto) {
        mensaje = texto;
        mensajeon = true;
    }

    /*Dibuja los elementos de la interfaz de usuario en la pantalla.

     */
    public void pintar(Graphics2D g2) {

        // Si el juego ha terminado, mostrar la pantalla de victoria
        if (JuegoTerminado) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String texto;
            int textWidth;
            int x;
            int y;

            // Mensaje de victoria
            texto = "HAS CONSEGUIDO EL TESORO";
            textWidth = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x = gp.pantallawidth / 2 - textWidth / 2;
            y = gp.pantallaheight / 2 - (gp.total * 3);
            g2.drawString(texto, x, y);

            // Mensaje para reiniciar
            texto = "Pulsa R para ir al menú";
            textWidth = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x = gp.pantallawidth / 2 - textWidth / 2;
            y = gp.pantallaheight / 2 + (gp.total * 5);
            g2.drawString(texto, x, y);

            // Tiempo total de juego
            texto = "Tu tiempo ha sido de: " + forma.format(tiempodejuego);
            textWidth = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x = gp.pantallawidth / 2 - textWidth / 2;
            y = gp.pantallaheight / 2 + (gp.total * 4);
            g2.drawString(texto, x, y);

            // Mensaje de "Enhorabuena"
            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            texto = "ENHORABUENA";
            textWidth = (int) g2.getFontMetrics().getStringBounds(texto, g2).getWidth();
            x = gp.pantallawidth / 2 - textWidth / 2;
            y = gp.pantallaheight / 2 + (gp.total * 2);
            g2.drawString(texto, x, y);

            // Detiene el hilo del juego al finalizar
            gp.juegoThread = null;

        } else {
            // Interfaz de juego en curso
            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);

            // Dibuja la llave y el contador de llaves recogidas
            g2.drawImage(keyImage, gp.total / 2, gp.total / 2, gp.total, gp.total, null);
            g2.drawString("x " + gp.jugaodr.haskey, 74, 50);

            // Actualiza y muestra el tiempo de juego
            tiempodejuego += (double) 1 / 60;
            g2.drawString("Tiempo: " + forma.format(tiempodejuego), gp.total * 11, 65);

            // Si hay un mensaje activo, lo muestra en pantalla
            if (mensajeon) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(mensaje, gp.total / 2, gp.total * 5);

                mensajeTiempo++;

                // Oculta el mensaje después de 2 segundos (120 fotogramas a 60 FPS)
                if (mensajeTiempo > 120) {
                    mensajeTiempo = 0;
                    mensajeon = false;
                }
            }
        }
    }
}
