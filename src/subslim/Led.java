package subslim;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by konra on 22.02.2017.
 */
public class Led {

    public Led(ImageView iv, int stepNo, boolean[] activeSteps){

        iv.setOnMouseClicked(event->{

            if(activeSteps[stepNo] == true){
                iv.setImage(new Image("res/led_off.png"));
                activeSteps[stepNo] = false;
            } else {
                iv.setImage(new Image("res/led.png"));
                activeSteps[stepNo] = true;
            }
        });
    }
}
