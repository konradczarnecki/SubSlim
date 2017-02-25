package subslim;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by konra on 22.02.2017.
 */
public class Led {

    public final static ArrayList<Led> leds = new ArrayList<>();

    private ImageView image;
    private boolean[] activeSteps;
    private int stepNo;


    public Led(ImageView iv, int stepNo, boolean[] activeSteps){

        image = iv;
        this.activeSteps = activeSteps;
        this.stepNo = stepNo;

        iv.setOnMouseClicked(event->{

            if(activeSteps[stepNo] == true){
                iv.setImage(new Image("res/led_off.png"));
                activeSteps[stepNo] = false;
            } else {
                iv.setImage(new Image("res/led.png"));
                activeSteps[stepNo] = true;
            }
        });

        leds.add(this);
    }

    public boolean getActive(){
        return activeSteps[stepNo];
    }

    public void setActive(boolean active){
        activeSteps[stepNo] = active;

        if(active) image.setImage(new Image("res/led.png"));
        else image.setImage(new Image("res/led_off.png"));
    }
}
