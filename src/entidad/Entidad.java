package entidad;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entidad {
    // Coordenadas de la entidad en el mundo del juego
    public int mundox, mundoy;

    // Velocidad de movimiento de la entidad
    public int velocidad;

    // Sprites de animación para cada dirección (arriba, abajo, izquierda, derecha)
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    // Dirección actual de la entidad
    public String direccion;

    // Contadores para la animación del sprite
    public int spritecounter = 0; // Contador de tiempo entre cambios de sprite
    public int spritenumber = 1;  // Indica qué imagen de la animación se está usando

    // Área de colisión de la entidad
    public Rectangle soidArea; // Área de colisión de la entidad
    public int solidareaX, solidareay; // Posición relativa del área de colisión

    // Estado de colisión (si está chocando con algo o no)
    public boolean colisionOn = false;
}

