package synth;

import javafx.beans.property.DoubleProperty;
import javafx.scene.image.ImageView;

/**
 * Created by konra on 15.02.2017.
 */
public class Knob {

    double maxValue;
    double minValue;
    double defaultValue;
    double currentValue;
    double angle;
    double currentCursorPosition;
    Double target;

    public Knob(ImageView knobImage, double min, double max, double def, Double target){

        maxValue = max;
        minValue = min;
        defaultValue = def;
        currentValue = def;
        this.target = target;

        currentCursorPosition = 0;

        knobImage.setOnMousePressed(event->{
            currentCursorPosition = event.getScreenY();
        });

        knobImage.setOnMouseDragged(event->{

            if((event.getScreenY()-currentCursorPosition < 0) && knobImage.getRotate() < 150)

                knobImage.setRotate(knobImage.getRotate()+3);

            else if (knobImage.getRotate() > -150)

              knobImage.setRotate(knobImage.getRotate()-3);

            currentCursorPosition = event.getScreenY();
        });

        knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {
            angle = newValue.doubleValue();


            currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);
        });


    }


}
