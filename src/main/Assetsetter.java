package main;

import objeto.Obj_botas;
import objeto.Obj_cofre;
import objeto.Obj_llave;
import objeto.Obj_puerta;

public class Assetsetter {
    Juego gp;  // Referencia al juego principal

    // Constructor que recibe la instancia del juego
    public Assetsetter(Juego gp) {
        this.gp = gp;
    }

    // Método para colocar los objetos en el mundo del juego
    public void setObjecto() {
        // Llaves en diferentes posiciones
        gp.obj[0] = new Obj_llave();
        gp.obj[0].mundox = 23 * gp.total;
        gp.obj[0].mundoy = 7 * gp.total;

        gp.obj[1] = new Obj_llave();
        gp.obj[1].mundox = 23 * gp.total;
        gp.obj[1].mundoy = 40 * gp.total;

        gp.obj[2] = new Obj_llave();
        gp.obj[2].mundox = 38 * gp.total;
        gp.obj[2].mundoy = 10 * gp.total;

        gp.obj[3] = new Obj_llave();
        gp.obj[3].mundox = 5 * gp.total;
        gp.obj[3].mundoy = 44 * gp.total;

        // Puertas en diferentes posiciones
        gp.obj[4] = new Obj_puerta();
        gp.obj[4].mundox = 10 * gp.total;
        gp.obj[4].mundoy = 11 * gp.total;

        gp.obj[5] = new Obj_puerta();
        gp.obj[5].mundox = 19 * gp.total;
        gp.obj[5].mundoy = 42 * gp.total;

        gp.obj[6] = new Obj_puerta();
        gp.obj[6].mundox = 8  * gp.total;
        gp.obj[6].mundoy = 28 * gp.total;

        gp.obj[7] = new Obj_puerta();
        gp.obj[7].mundox = 12 * gp.total;
        gp.obj[7].mundoy = 22 * gp.total;

        // Cofre (final del juego)
        gp.obj[8] = new Obj_cofre();
        gp.obj[8].mundox = 10 * gp.total;
        gp.obj[8].mundoy = 7  * gp.total;

        // Botas (aumentan velocidad)
        gp.obj[9] = new Obj_botas();
        gp.obj[9].mundox = 35 * gp.total;
        gp.obj[9].mundoy = 41 * gp.total;
    }
}
