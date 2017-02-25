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


    private Led(ImageView image, int stepNo, boolean[] activeSteps){

        this.image = image;
        this.activeSteps = activeSteps;
        this.stepNo = stepNo;

        bindToggle();
    }

    private void bindToggle(){

        image.setOnMouseClicked(event->{

            if(activeSteps[stepNo] == true){
                image.setImage(new Image("res/led_off.png"));
                activeSteps[stepNo] = false;
            } else {
                image.setImage(new Image("res/led.png"));
                activeSteps[stepNo] = true;
            }
        });
    }

    public boolean getActive(){
        return activeSteps[stepNo];
    }

    public void setActive(boolean active){
        activeSteps[stepNo] = active;

        if(active) image.setImage(new Image("res/led.png"));
        else image.setImage(new Image("res/led_off.png"));
    }

    public static void createAndBind(ImageView iv, int stepNo, boolean[] activeSteps){

        Led led = new Led(iv, stepNo, activeSteps);

        leds.add(led);
    }
}
