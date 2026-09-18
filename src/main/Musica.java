package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

import javax.sound.sampled.*;
import java.net.URL;

public class Musica {
    // Objeto Clip para reproducir sonidos
    Clip clip;

    // Array para almacenar las rutas de los archivos de sonido
    URL soundURL[] = new URL[30];

    /**
     * Constructor que inicializa las rutas de los archivos de sonido.
     */
    public Musica() {
        // Se asignan archivos de sonido a diferentes índices
        soundURL[0] = getClass().getResource("/musica/game-music-player-console-8bit-background-intro-theme-297305.wav");
        soundURL[1] = getClass().getResource("/musica/cerrar-puerta-81438.wav");
        soundURL[2] = getClass().getResource("/musica/abrir-puerta-80075.wav");
        soundURL[3] = getClass().getResource("/musica/coin-257878.wav");
        soundURL[4] = getClass().getResource("/musica/camera-flash-204151.wav");
    }


    public void setFile(int i) {
        try {
            // Carga el archivo de audio correspondiente
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip(); // Se obtiene una nueva instancia de Clip
            clip.open(ais); // Se abre el archivo de audio
        } catch (Exception e) {
            e.printStackTrace(); // Muestra el error si ocurre
        }
    }

    /* Reproduce el sonido seleccionado.*/
    public void play() {
        clip.start();
    }

    /* Reproduce el sonido en bucle continuo.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /*Detiene la reproducción del sonido.
     */
    public void parar() {
        clip.stop();
    }
}

