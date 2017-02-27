package subslim.ui;


import javafx.scene.image.ImageView;
import subslim.AdjustableValue;
import subslim.synth.Synth;

import java.util.ArrayList;

/**
 * Created by konra on 15.02.2017.
 */
public class KnobSwitch <E> {

    public static ArrayList<KnobSwitch> switches = new ArrayList<>();

    private double currentCursorPosition;
    private int position;
    private AdjustableValue<E> target;
    private E[] values;
    protected ImageView image;


    public KnobSwitch(E[] values, ImageView image, AdjustableValue<E> target){

        this.target = target;
        this.values = values;
        this.image = image;

        if (values.length % 2 == 0) throw new IllegalArgumentException();

        bind();
    }

    private void bind(){

        double[] positionsAngles = new double[values.length];

        double positionsOnSide = (values.length - 1)/2;

        position = (int) positionsOnSide;

        for(int i = 0; i < values.length; i++){
            positionsAngles[i] = (-30)*positionsOnSide + i*30;
        }

        image.setOnMousePressed(event -> {
            currentCursorPosition = event.getScreenY();
        });

        image.setOnMouseDragged(event-> {

            if(event.getScreenY() - currentCursorPosition < -50 && position < positionsOnSide*2){
                position++;
                image.setRotate(positionsAngles[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }

            if(event.getScreenY() - currentCursorPosition > 50 && position > 0){
                position--;
                image.setRotate(positionsAngles[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }
        });
    }

    public double getRotation(){
        return image.getRotate();
    }

    public int getPosition(){
        return position;
    }

    public void setState(double rotation, int position){

        image.setRotate(rotation);
        this.position = position;
        target.setValue(values[position]);
    }

    public static <R> void createAndBind(R[] values, ImageView knob, AdjustableValue<R> target){

        KnobSwitch<R> knobSwitch = new KnobSwitch<R>(values,knob,target);

        switches.add(knobSwitch);
    }



}
