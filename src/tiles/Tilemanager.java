package tiles;

import main.Juego;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/*Clase encargada de gestionar los tiles (bloques del mapa) en el juego.

 */
public class Tilemanager {
    Juego gp;  // Referencia al juego principal
    public Tiles[] tiles;  // Arreglo que almacena los diferentes tipos de tiles
    public int maptilenum[][];  // Matriz que representa el mapa con los números de tiles

    /*Constructor de la clase Tilemanager
     */
    public Tilemanager(Juego gp) {
        this.gp = gp;
        tiles = new Tiles[10];  // Inicializa el array con 10 tipos de tiles
        maptilenum = new int[gp.maxWorldCol][gp.maxWorldRow];  // Matriz que almacena el mapa
        gettileimage();  // Carga las imágenes de los tiles
        cargarelmapa("/maps/world01.txt");  // Carga el mapa desde un archivo de texto
    }

    /*Método que carga las imágenes de los tiles y define cuáles tienen colisión.
     */
    public void gettileimage() {
        try {
            // Carga la imagen del césped (sin colisión)
            tiles[0] = new Tiles();
            tiles[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cesped.png"));

            // Carga la imagen de la piedra (con colisión)
            tiles[1] = new Tiles();
            tiles[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/piedra.png"));
            tiles[1].colisionable = true;

            // Carga la imagen del agua (con colisión)
            tiles[2] = new Tiles();
            tiles[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/agua.png"));
            tiles[2].colisionable = true;

            // Carga la imagen de la tierra (sin colisión)
            tiles[3] = new Tiles();
            tiles[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            // Carga la imagen del árbol (con colisión)
            tiles[4] = new Tiles();
            tiles[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/arbol.png"));
            tiles[4].colisionable = true;

            // Carga la imagen de la arena (sin colisión)
            tiles[5] = new Tiles();
            tiles[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/arena.png"));

        } catch (IOException e) {
            e.printStackTrace();  // Muestra errores en caso de que la imagen no se cargue correctamente
        }
    }

    /*Método que carga el mapa desde un archivo de texto y lo almacena en la matriz maptilenum.
     */
    public void cargarelmapa(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String linea = br.readLine();  // Lee una línea del archivo

                while (col < gp.maxWorldCol) {
                    String numbers[] = linea.split(" ");  // Divide la línea en números
                    int num = Integer.parseInt(numbers[col]);  // Convierte cada número a entero
                    maptilenum[col][row] = num;  // Guarda el número en la matriz del mapa
                    col++;
                }
                if (col == gp.maxWorldCol) {  // Cuando se completa una fila, pasa a la siguiente
                    col = 0;
                    row++;
                }
            }
            br.close();  // Cierra el archivo después de leerlo
        } catch (Exception e) {
            e.printStackTrace();  // Manejo de errores en caso de fallo al leer el archivo
        }
    }

    /*Método encargado de dibujar el mapa en la pantalla del juego.

     */
    public void draw(Graphics2D g2) {
        int Worldcol = 0;  // Columna en el mundo del juego
        int Worldrow = 0;  // Fila en el mundo del juego

        while (Worldcol < gp.maxWorldCol && Worldrow < gp.maxWorldRow) {
            int titlenum = maptilenum[Worldcol][Worldrow];  // Obtiene el tipo de tile en la posición actual

            // Calcula la posición en el mundo real
            int worldX = Worldcol * gp.total;
            int worldY = Worldrow * gp.total;

            // Calcula la posición en la pantalla en relación con el jugador
            int screenX = worldX - gp.jugaodr.mundox + gp.jugaodr.screenX;
            int screenY = worldY - gp.jugaodr.mundoy + gp.jugaodr.screenY;

            // Dibuja el tile si está dentro del área visible del jugador
            g2.drawImage(tiles[titlenum].image, screenX, screenY, gp.total, gp.total, null);

            Worldcol++;  // Avanza a la siguiente columna

            // Si se llega al final de una fila, pasa a la siguiente fila
            if (Worldcol == gp.maxWorldCol) {
                Worldcol = 0;
                Worldrow++;
            }
        }
    }
}
