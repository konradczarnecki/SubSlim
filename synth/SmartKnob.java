package synth;


import javafx.scene.image.ImageView;

/**
 * Created by konra on 15.02.2017.
 */
public class SmartKnob extends Knob {


    AdjustableValue<Double> max;

    public SmartKnob(ImageView knobImage, double min, double max, double def, AdjustableValue<Double> target, SmartKnob slave){

        super(knobImage,min,max,def,target);

        this.max = new AdjustableValue<>(max);

        if(slave != null){

            knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {

                slave.setMax(this.max.getValue()+10-currentValue);
            });
        }
    }

    public void setMax(double max){

        this.maxValue = max;
    }
}
