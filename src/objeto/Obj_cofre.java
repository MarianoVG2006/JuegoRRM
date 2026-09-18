package objeto;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Obj_cofre extends Superobjetos {

    public Obj_cofre(){
        nombre = "cofre";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objecto/cofre.png"));
        }catch (IOException e ){
            e.printStackTrace();
        }

    }
}
