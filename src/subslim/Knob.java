package subslim;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by konra on 15.02.2017.
 */
public class Knob {

    public static ArrayList<Knob> knobs = new ArrayList<>();

    protected double maxValue;
    protected double minValue;
    protected double defaultValue;
    protected double currentValue;
    protected double angle;
    protected double currentCursorScreenPosition;
    protected AdjustableValue<Double> target;
    ImageView knobImage;

    public Knob(ImageView knobImage, double min, double max, double def, AdjustableValue<Double> target){

        maxValue = max;
        minValue = min;
        defaultValue = def;
        currentValue = def;
        this.target = target;
        this.knobImage = knobImage;

        knobs.add(this);

        setMovement();
        bindKnob();
    }

    protected void bindKnob(){

        knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {

            angle = newValue.doubleValue();
            currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

            target.setValue(currentValue);
        });
    }

    protected void setMovement(){

        knobImage.setRotate(300*((defaultValue-minValue)/(maxValue-minValue))-150);

        knobImage.setOnMousePressed(event->{
            currentCursorScreenPosition = event.getScreenY();
        });

        knobImage.setOnMouseDragged(event->{

            if((event.getScreenY()- currentCursorScreenPosition < 0) && knobImage.getRotate() < 150)

                knobImage.setRotate(knobImage.getRotate()+4);

            else if (knobImage.getRotate() > -150)

                knobImage.setRotate(knobImage.getRotate()-4);

            currentCursorScreenPosition = event.getScreenY();
        });
    }

    public double getRotation(){
        return knobImage.getRotate();
    }

    public void setRotation(double rotation){
        knobImage.setRotate(rotation);
    }


}
