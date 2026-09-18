package objeto;

import main.Juego;
import java.awt.*;
import java.awt.image.BufferedImage;

/*Clase que representa un objeto en el mundo del juego.
 */
public class Superobjetos {

    public BufferedImage image; // Imagen del objeto
    public String nombre; // Nombre del objeto
    public boolean colision = false; // Indica si el objeto tiene colisión o no
    public int mundox, mundoy; // Posición del objeto en el mundo
    public Rectangle solidarea = new Rectangle(0, 0, 48, 48); // Área sólida para colisiones

    public int solidareax = 0; // Desplazamiento del área sólida en X
    public int solidareay = 0; // Desplazamiento del área sólida en Y

    /*Dibuja el objeto en la pantalla si está dentro del área visible del jugador.
     */
    public void draw(Graphics2D g2, Juego gp) {
        // Calcula la posición en la pantalla relativa al jugador
        int screenX = mundox - gp.jugaodr.mundox + gp.jugaodr.screenX;
        int screenY = mundoy - gp.jugaodr.mundoy + gp.jugaodr.screenY;

        // Verifica si el objeto está dentro del área visible del jugador antes de dibujarlo
        if (mundox + gp.total > gp.jugaodr.mundox - gp.jugaodr.screenX &&
                mundox - gp.total < gp.jugaodr.mundox + gp.jugaodr.screenX &&
                mundoy + gp.total > gp.jugaodr.mundoy - gp.jugaodr.screenY &&
                mundoy - gp.total < gp.jugaodr.mundoy + gp.jugaodr.screenY) {

            // Dibuja el objeto en la pantalla
            g2.drawImage(image, screenX, screenY, gp.total, gp.total, null);
        }
    }
}






