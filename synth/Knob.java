package synth;

import javafx.scene.image.ImageView;

/**
 * Created by konra on 15.02.2017.
 */
public class Knob {

    protected double maxValue;
    protected double minValue;
    protected double defaultValue;
    protected double currentValue;
    protected double angle;
    protected double currentCursorPosition;
    protected AdjustableValue<Double> target;
    ImageView knobImage;

    public Knob(ImageView knobImage, double min, double max, double def, AdjustableValue<Double> target){

        maxValue = max;
        minValue = min;
        defaultValue = def;
        currentValue = def;
        this.target = target;
        this.knobImage = knobImage;

        bindKnob();

    }

    private void bindKnob(){
        knobImage.setRotate(300*((defaultValue-minValue)/(maxValue-minValue))-150);

        knobImage.setOnMousePressed(event->{
            currentCursorPosition = event.getScreenY();
        });


        knobImage.setOnMouseDragged(event->{

            if((event.getScreenY()-currentCursorPosition < 0) && knobImage.getRotate() < 150)

                knobImage.setRotate(knobImage.getRotate()+4);

            else if (knobImage.getRotate() > -150)

                knobImage.setRotate(knobImage.getRotate()-4);

            currentCursorPosition = event.getScreenY();
        });

        knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {
            angle = newValue.doubleValue();

            currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

            target.setValue(currentValue);
        });
    }


}
