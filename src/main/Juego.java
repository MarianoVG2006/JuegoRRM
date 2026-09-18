package main;

import entidad.Jugaodr;
import objeto.Superobjetos;
import tiles.Tilemanager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Juego extends JPanel implements Runnable {
    // Definición de los atributos del juego

    // Tamaño de los elementos en pantalla
    final int titulo = 16; // Tamaño base del tile
    final int escala = 3; // Escalado de los tiles
    public final int total = titulo * escala; // Tamaño final de cada tile en píxeles

    // Definición del tamaño de la pantalla
    public final int maxscreenalto = 20; // Número máximo de tiles en altura
    public final int maxscreenbajo = 14; // Número máximo de tiles en ancho
    public final int pantallawidth = total * maxscreenalto; // Ancho total en píxeles
    public final int pantallaheight = total * maxscreenbajo; // Alto total en píxeles

    // Dimensiones del mundo del juego
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // Variables para el doble buffer
    BufferedImage tempscreen;
    Graphics2D g2;

    // Estados del juego
    public final int titleState = 0; // Estado del menú
    public final int playState = 1; // Estado de juego
    int estadoJuego = titleState; // Se inicia en el menú principal

    // FPS del juego
    int fps = 60;

    // Gestión del mapa
    Tilemanager tilem = new Tilemanager(this);

    // Música del juego
    Musica musica = new Musica();
    Musica se = new Musica(); // Sonidos de efectos

    // Gestión de entradas del teclado
    Recojerporteclado keyh = new Recojerporteclado(this);
    Thread juegoThread; // Hilo principal del juego

    // Gestión de colisiones
    public ColisionJugador colisioncheck = new ColisionJugador(this);

    // Gestión de entrada del menú
    public RecojerportecladoMenu keyhmenu = new RecojerportecladoMenu(this);

    // Jugador
    public Jugaodr jugaodr = new Jugaodr(this, keyh);

    // Gestión de objetos en el mapa
    public Assetsetter aset = new Assetsetter(this);

    // Interfaz de usuario
    public UID textopantalla = new UID(this);

    // Array de objetos en el juego
    public Superobjetos obj[] = new Superobjetos[10];

    // Constructor del juego
    public Juego() {
        this.setPreferredSize(new Dimension(pantallawidth, pantallaheight)); // Establece el tamaño del panel
        this.setBackground(Color.BLACK); // Fondo negro
        this.setDoubleBuffered(true); // Mejora la renderización
        this.addKeyListener(keyh); // Agrega el listener del teclado
        this.addKeyListener(keyhmenu);
        this.setFocusable(true); // Permite capturar eventos del teclado
        this.empezareljuego(); // Inicia el juego
    }

    // Reiniciar el juego
    public void reiniciarJuego() {
        estadoJuego = titleState; // Volver al menú

        // Reiniciar jugador
        jugaodr.setvalordefecto();
        jugaodr.haskey = 0; // Reiniciar llaves obtenidas

        // Reiniciar objetos y mapa
        aset.setObjecto();

        // Reiniciar tiempo
        textopantalla.tiempodejuego = 0;
        textopantalla.JuegoTerminado = false;

        // Reiniciar música
        pararmusica();
        empezarmusica(0); // Música del menú

        // Reiniciar hilo del juego si no está en ejecución
        if (juegoThread == null) {
            empezareljuego();
        }
    }

    // Configuración inicial del juego
    public void setupgame() {
        aset.setObjecto(); // Posicionar los objetos en el mapa
        empezarmusica(0); // Iniciar la música
        tempscreen = new BufferedImage(pantallawidth, pantallaheight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempscreen.getGraphics(); // Obtener el contexto gráfico
    }

    // Método para iniciar el hilo del juego
    public void empezareljuego() {
        juegoThread = new Thread(this);
        juegoThread.start();
    }

    // Método que controla la ejecución del juego (Game Loop)
    public void run() {
        double drawInterval = 1000000000 / fps; // Tiempo entre cada frame
        double delta = 0;
        long lasttime = System.nanoTime();
        long timer = 0;
        int drawCount = 0;

        while (juegoThread != null) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lasttime) / drawInterval;
            timer += (currentTime - lasttime);
            lasttime = currentTime;

            if (delta >= 1) {
                update(); // Actualiza el estado del juego
                repaint(); // Redibuja el juego
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount); // Muestra los FPS en consola
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // Método que actualiza el estado del juego
    public void update() {
        if (estadoJuego == playState) {
            jugaodr.update(); // Actualiza el jugador
        }
    }

    // Método que pinta los elementos en pantalla
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (estadoJuego == titleState) {
            drawMenu(g2); // Dibuja el menú
        } else if (estadoJuego == playState) {
            tilem.draw(g2); // Dibuja el mapa

            // Dibuja los objetos en el mapa
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            jugaodr.draw(g2); // Dibuja el jugador
            textopantalla.pintar(g2); // Dibuja la UI
        }

        g2.dispose();
    }

    // Método auxiliar para centrar texto
    private int centrarTexto(Graphics2D g2, String texto) {
        return pantallawidth / 2 - g2.getFontMetrics().stringWidth(texto) / 2;
    }

    // Dibuja el menú principal
    public void drawMenu(Graphics2D g2) {
        // Carga la imagen de fondo del menú
        Image fondo = new ImageIcon(getClass().getResource("/Menu/MenuFondo.png")).getImage();
        g2.drawImage(fondo, 0, 0, pantallawidth, pantallaheight, null);

        // Definir opciones del menú
        String start = "Comenzar(enter)";
        String exit = "Salir(esc)";
        String info = "Instrucciones(I)";

        // Estilo del texto
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        g2.setColor(Color.blue);

        // Dibujar las opciones del menú
        g2.drawString(start, centrarTexto(g2, start), pantallaheight - 250);
        g2.drawString(exit, centrarTexto(g2, exit), pantallaheight - 200);
        g2.drawString(info, centrarTexto(g2, info), pantallaheight - 110);
    }

    // Método para iniciar la musica
    public void empezarmusica(int i) {
        musica.setFile(i);
        musica.play();
        musica.loop();
    }

    // Método para detener la música
    public void pararmusica() {
        musica.parar();
    }

    // Método para reproducir efectos de sonido
    public void playse(int i) {
        se.setFile(i);
        se.play();
    }
}


