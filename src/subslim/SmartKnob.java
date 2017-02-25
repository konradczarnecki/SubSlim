package subslim;


import javafx.scene.image.ImageView;

/**
 * Created by konra on 15.02.2017.
 */
public class SmartKnob extends Knob {



    public SmartKnob(ImageView knobImage, double min, double max, double def, AdjustableValue<Double> target, SmartKnob slave){

        super(knobImage,min,max,def,target);



        if(slave != null){

            knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {

                slave.setMax(this.maxValue+70-currentValue);
            });
        }
    }

    public void setMax(double max){

        this.maxValue = max;
    }
}
