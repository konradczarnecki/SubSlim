package subslim.ui;

import javafx.scene.image.ImageView;
import subslim.AdjustableValue;
import subslim.synth.Synth;

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

    protected Knob(ImageView knobImage, double min, double max, double def, AdjustableValue<Double> target){

        maxValue = max;
        minValue = min;
        defaultValue = def;
        currentValue = def;
        this.target = target;
        this.knobImage = knobImage;

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

    public static Knob createAndBind(ImageView image, double min, double max, double def,
                                     AdjustableValue<Double> target){

        Knob knob = new Knob(image, min, max, def, target);

        knobs.add(knob);

        return knob;
    }

    public static void createAndBindDelay1(ImageView image, double min, double max, double def, Synth synth){

        Knob knob = new Knob(image,min,max,def,null){

            protected void bindKnob(){

                knobImage.setOnMouseReleased(event->{

                    angle = knobImage.getRotate();

                    currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

                    synth.amp();

                    synth.amp().echo().setDelay1(currentValue);
                });
            }
        };

        knobs.add(knob);
    }

    public static void createAndBindDelay2(ImageView image, double min, double max, double def, Synth synth){

        Knob knob = new Knob(image,min,max,def,null){

            protected void bindKnob(){

                knobImage.setOnMouseReleased(event->{

                    angle = knobImage.getRotate();

                    currentValue = minValue + ((angle + 150)/300)*(maxValue - minValue);

                    synth.amp().echo().setDelay2(currentValue);
                });
            }
        };

        knobs.add(knob);
    }

    public static void createAndBindSmartKnob(ImageView image, double min, double max, double def,
                                              AdjustableValue<Double> target, Knob slave){

        Knob knob = new Knob(image,min,max,def,target){

            protected void bindKnob(){

                super.bindKnob();

                if(slave != null){

                    knobImage.rotateProperty().addListener((observable, oldValue, newValue) -> {

                        slave.setMax(this.maxValue+90-currentValue);
                    });
                }
            }

        };
        knobs.add(knob);
    }

    public void setMax(double max){

        this.maxValue = max;
    }
}



