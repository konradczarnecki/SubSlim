package subslim;


import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by konra on 15.02.2017.
 */
public class KnobSwitch <E> {

    public static ArrayList<KnobSwitch> switches = new ArrayList<>();

    private double currentCursorPosition;
    private int position;
    private ImageView knob;


    public KnobSwitch(E[] values, ImageView knob, AdjustableValue<E> target){

        this.knob = knob;

        if (values.length % 2 == 0) throw new IllegalArgumentException();

        double[] positionsAngles = new double[values.length];

        double positionsOnSide = (values.length - 1)/2;

        position = (int) positionsOnSide;

        for(int i = 0; i < values.length; i++){
            positionsAngles[i] = (-30)*positionsOnSide + i*30;
        }

        knob.setOnMousePressed(event -> {
            currentCursorPosition = event.getScreenY();
        });

        knob.setOnMouseDragged(event-> {

            if(event.getScreenY() - currentCursorPosition < -50 && position < positionsOnSide*2){
                position++;
                knob.setRotate(positionsAngles[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }

            if(event.getScreenY() - currentCursorPosition > 50 && position > 0){
                position--;
                knob.setRotate(positionsAngles[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }
        });

        switches.add(this);
    }

    public double getRotation(){
        return knob.getRotate();
    }

    public int getPosition(){
        return position;
    }

    public void setState(double rotation, int position){
        knob.setRotate(rotation);
        this.position = position;
    }
}
