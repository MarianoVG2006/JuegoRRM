package entidad;
import main.Juego;
import main.Recojerporteclado;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Jugaodr extends Entidad {
    Juego gp;  // Referencia al juego principal
    Recojerporteclado keyh;  // Captura de las teclas presionadas

    // Posición del jugador en la pantalla (manteniéndolo centrado)
    public final int screenX;
    public final int screenY;

    // Contador de llaves recogidas
    public int haskey = 0;

    // Constructor de la clase
    public Jugaodr(Juego gp, Recojerporteclado keyh) {
        this.gp = gp;
        this.keyh = keyh;

        // Centrar el jugador en la pantalla
        screenX = gp.pantallawidth / 2 - (gp.total / 2);
        screenY = gp.pantallaheight / 2 - (gp.total / 2);

        // Configuración del área de colisión del jugador
        soidArea = new Rectangle();
        soidArea.x = 8;
        soidArea.y = 16;
        solidareaX = soidArea.x;
        solidareay = soidArea.y;
        soidArea.width = 32;
        soidArea.height = 32;

        setvalordefecto();  // Establece la posición inicial y velocidad
        getjugadorimagen(); // Carga los sprites del personaje
    }

    // Establece los valores predeterminados del jugador
    public void setvalordefecto() {
        mundox = gp.total * 23;  // Posición inicial en el mapa
        mundoy = gp.total * 21;
        velocidad = 4;
        direccion = "down";  // Dirección inicial hacia abajo
    }

    // Carga las imágenes del personaje para cada dirección
    public void getjugadorimagen() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/jugador/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Actualiza la posición del jugador
    public void update() {
        if (keyh.upPressed || keyh.downPressed || keyh.leftPressed || keyh.rightPressed) {
            // Determinar la dirección del movimiento
            if (keyh.upPressed) {
                direccion = "up";
            } else if (keyh.downPressed) {
                direccion = "down";
            } else if (keyh.leftPressed) {
                direccion = "left";
            } else if (keyh.rightPressed) {
                direccion = "right";
            }

            // Chequear colisión
            colisionOn = false;
            gp.colisioncheck.colisiontiles(this);
            int objIndex = gp.colisioncheck.checkObject(this, true);

            // Recoger objeto si hay colisión con uno
            pickobjeto(objIndex);

            // Mover jugador si no hay colisión
            if (!colisionOn) {
                switch (direccion) {
                    case "up": mundoy -= velocidad; break;
                    case "down": mundoy += velocidad; break;
                    case "left": mundox -= velocidad; break;
                    case "right": mundox += velocidad; break;
                }
            }

            // Animación del sprite
            spritecounter++;
            if (spritecounter >= 10) { // Cambia de sprite cada 10 ciclos
                spritenumber = (spritenumber == 1) ? 2 : 1;
                spritecounter = 0;
            }
        }
    }

    // Método para recoger objetos
    public void pickobjeto(int i) {
        if (i != 999) {  // 999 significa que no hay objeto en la casilla
            String objectName = gp.obj[i].nombre;

            switch (objectName) {
                case "key": // Recoge llave
                    gp.playse(3);
                    haskey++;
                    gp.obj[i] = null;
                    gp.textopantalla.mostrarmensaje("¡Cogiste una llave!");
                    break;
                case "puerta": // Abre puerta si tiene llave
                    if (haskey > 0) {
                        gp.playse(1);
                        gp.obj[i] = null;
                        haskey--;
                        gp.textopantalla.mostrarmensaje("¡Abriste la puerta!");
                    } else {
                        gp.textopantalla.mostrarmensaje("Cerrado. Consigue las llaves");
                    }
                    break;
                case "botas": // Recoge botas que aumentan la velocidad
                    gp.playse(4);
                    velocidad += 1;
                    gp.obj[i] = null;
                    gp.textopantalla.mostrarmensaje("¡Aceleraaa!");
                    break;
                case "cofre": // Fin del juego al encontrar el cofre
                    gp.textopantalla.JuegoTerminado = true;
                    gp.pararmusica();
                    break;
            }
        }
    }

    // Dibuja al jugador en pantalla
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Seleccionar la imagen según la dirección y el número de sprite
        switch (direccion) {
            case "up":
                image = (spritenumber == 1) ? up1 : up2;
                break;
            case "down":
                image = (spritenumber == 1) ? down1 : down2;
                break;
            case "left":
                image = (spritenumber == 1) ? left1 : left2;
                break;
            case "right":
                image = (spritenumber == 1) ? right1 : right2;
                break;
        }

        // Dibuja la imagen del personaje en la pantalla
        g2.drawImage(image, screenX, screenY, gp.total, gp.total, null);
    }
}


