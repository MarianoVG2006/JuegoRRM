package objeto;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_puerta extends Superobjetos {

    public Obj_puerta(){
        nombre = "puerta";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objecto/puerta.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

        colision = true;
    }
}
