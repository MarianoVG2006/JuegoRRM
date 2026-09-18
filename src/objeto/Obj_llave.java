package objeto;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_llave extends Superobjetos {
    public Obj_llave(){
        nombre = "key";

        try {
            image = ImageIO.read( getClass().getResourceAsStream("/objecto/llave.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
        solidarea.x = 5;
    }
}
