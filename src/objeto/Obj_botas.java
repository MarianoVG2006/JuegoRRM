package objeto;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_botas extends Superobjetos {

    public Obj_botas(){
        nombre = "botas";

        try {
            image = ImageIO.read( getClass().getResourceAsStream("/objecto/botas.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
