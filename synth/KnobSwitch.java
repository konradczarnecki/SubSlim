package synth;


import javafx.scene.image.ImageView;

/**
 * Created by konra on 15.02.2017.
 */
public class KnobSwitch <E> {

    private double currentCursorPosition;
    private int position;

    public KnobSwitch(E[] values, ImageView knob, AdjustableValue<E> target){

        if (values.length % 2 == 0) throw new IllegalArgumentException();

        double[] anglePositions = new double[values.length];

        double positionsOnSide = (values.length - 1)/2;

        position = (int) positionsOnSide;

        for(int i = 0; i < values.length; i++){
            anglePositions[i] = (-30)*positionsOnSide + i*30;
        }

        knob.setOnMousePressed(event -> {
            currentCursorPosition = event.getScreenY();
        });

        knob.setOnMouseDragged(event-> {

            if(event.getScreenY() - currentCursorPosition < -50 && position < positionsOnSide*2){
                position++;
                knob.setRotate(anglePositions[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }

            if(event.getScreenY() - currentCursorPosition > 50 && position > 0){
                position--;
                knob.setRotate(anglePositions[position]);
                currentCursorPosition = event.getScreenY();
                target.setValue(values[position]);
            }
        });


    }
}
